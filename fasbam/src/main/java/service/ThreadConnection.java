package service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import com.google.common.base.Throwables;

import util.ConnectFilemaker;

public class ThreadConnection {
	public ThreadConnection(HttpSession sessao) {

		if (sessao.getAttribute("timerCloseConnection") != null) {
			//System.out.println("JA EXISTE UM TIMER (CANCELANDO E INICIANDO OUTRO: " + (ScheduledFuture<?>) sessao.getAttribute("timerCloseConnection"));
			
			((ScheduledFuture<?>) sessao.getAttribute("timerCloseConnection")).cancel(true);
			((ScheduledExecutorService) sessao.getAttribute("Executor")).shutdown();

			ScheduledExecutorService threadPeriod = Executors.newScheduledThreadPool(1);
			ScheduledFuture<?> threadFuturo = threadPeriod.schedule(new Runnable() {
				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						try {
							((Statement) sessao.getAttribute("connectionBD")).close();
							((Statement) sessao.getAttribute("connectionBD2")).close();
							((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
//							System.out.println("FINALIZANDO THREAD. ID SESSAO: " + sessao.getId());
							sessao.setAttribute("timerCloseConnection", null);
//							System.out.println("ID THREAD timer" + Thread.currentThread().getId());
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (Throwable e) {
							e.printStackTrace();
						} finally {
							Thread.currentThread().interrupt();
						}
					}

				}
			}, 5, TimeUnit.SECONDS);
			
			sessao.setAttribute("timerCloseConnection", threadFuturo);
			sessao.setAttribute("Executor", threadPeriod);
			threadPeriod.shutdown();
		} else {
			System.out.println("INICIANDO TIMER");
			ScheduledExecutorService threadPeriod = Executors.newScheduledThreadPool(1);
			ScheduledFuture<?> threadFuturo = threadPeriod.schedule(new Runnable() {
				
				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						try {
							((Statement) sessao.getAttribute("connectionBD")).close();
							((Statement) sessao.getAttribute("connectionBD2")).close();
							((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
//							System.out.println("FINALIZANDO THREAD. ID SESSAO: " + sessao.getId());
							sessao.setAttribute("timerCloseConnection", null);
//							System.out.println("ID THREAD timer" + Thread.currentThread().getId());
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							Thread.currentThread().interrupt();
						}
					}

				}
			}, 5, TimeUnit.SECONDS);
			
			sessao.setAttribute("timerCloseConnection", threadFuturo);
			sessao.setAttribute("Executor", threadPeriod);
			threadPeriod.shutdown();
		}

	}
}
