package util;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ManagerSession implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent ev) {

		try {
			HttpSession sessao = ev.getSession();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent ev) {
		try {
			
			HttpSession sessao = ev.getSession();
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			System.out.println("Sessão: " + sessao.getAttributeNames());
			
			// stmt.close();
			// cf.getCon().close();
			// System.out.println("Id -> " + sessao.getId());
			// Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			// for(Thread t : threadSet) {
			// System.out.println("Nome Thread - > " + t.getName());
			// }
			
			if (sessao.getAttribute("timerCloseConnection") != null) {
				System.out.println("Encerrando threads...");
				((ScheduledFuture<?>) sessao.getAttribute("timerCloseConnection")).cancel(true);
				((ScheduledExecutorService) sessao.getAttribute("Executor")).shutdown();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("A SESSÃO FOI ENCERRADA");
	}

	public void createConnection() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
