package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Assunto;
import model.Autor;
import util.ConnectFilemaker;

public class AssuntoService {

	private HttpSession sessao;
	private ThreadConnection thread;
	
	public AssuntoService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public List<Assunto> getListAssunto(Long tituloCodigo) throws SQLException {

		List<Assunto> lstAssunto = new ArrayList<>();
		try {

			String sql = "select AssuntoTitulo.* from AssuntoTitulo "
					+ "inner join Titulo on AssuntoTitulo.CodigoTitulo = Titulo.Codigo " + "where Titulo.Codigo = "
					+ tituloCodigo;

			long tempoInicio = System.currentTimeMillis();
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			System.out.println("Tempo Query Assunto: " + (System.currentTimeMillis() - tempoInicio) + " ms");

			while (rs.next()) {
				Assunto assunto = new Assunto();
				assunto.setCodigo(rs.getLong("CodigoAssunto"));
				assunto.setNome(rs.getString("NomeAssunto"));
				lstAssunto.add(assunto);
			}
			rs.close();
//			thread = new ThreadConnection(sessao);
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de Autores + " + e);
			System.out.println("Erro ao busca lista de Autores + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lstAssunto = getListAssunto(tituloCodigo);

			
		}

		return lstAssunto;
	}
}
