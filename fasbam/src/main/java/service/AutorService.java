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

import model.Autor;
import util.ConnectFilemaker;

public class AutorService {

	private ConnectFilemaker cf;
	private Statement stmt;

	private HttpSession sessao;
	private ThreadConnection thread;
	

	public AutorService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public List<Autor> getListAutor() throws SQLException {

		List<Autor> lista = new ArrayList<Autor>();
		try {

			String sql = "SELECT * FROM Autor order by Codigo desc";
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);

			while (rs.next()) {
				Autor autor = new Autor();
				autor.setCodigo(rs.getLong("Codigo"));
				autor.setNome(rs.getString("Nome"));

				lista.add(autor);
			}

			rs.close();
			thread = new ThreadConnection(sessao);
			System.out.println("AutorService");
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de Autores + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lista = getListAutor();
			
		}
		return lista;
	}

	public Autor getAutor(Long codAutor) throws SQLException {
		Autor autor = new Autor();
		try {
			String sql = "SELECT Autor.Codigo, Autor.Nome FROM Autor WHERE Autor.Codigo = " + codAutor;
			ResultSet rsa = (ResultSet) ((Statement) sessao.getAttribute("connectionBD2")).executeQuery(sql);
			while (rsa.next()) {
				autor.setCodigo(rsa.getLong("Codigo"));
				autor.setNome(rsa.getString("Nome"));
			}

			rsa.close();
//			thread = new ThreadConnection(sessao);
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de Autores + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			autor = getAutor(codAutor);
		}

		return autor;
	}

	public List<Autor> getListAutores(Long tituloCodigo) throws SQLException {

		List<Autor> lstAutor = new ArrayList<>();
		try {

			String sql = "select AutorTitulo.* from AutorTitulo "
					+ "inner join Titulo on AutorTitulo.CodigoTitulo = Titulo.Codigo " + "where Titulo.Codigo = "
					+ tituloCodigo;

			long tempoInicio = System.currentTimeMillis();
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			System.out.println("Tempo Query AUTOR: " + (System.currentTimeMillis() - tempoInicio) + " ms");

			while (rs.next()) {
				Autor autor = new Autor();
				autor.setCodigo(rs.getLong("CodigoAutor"));
				autor.setNome(rs.getString("NomeAutor"));
				autor.setPrincipal(rs.getInt("AutorPrincipal"));
				lstAutor.add(autor);
			}
			rs.close();
			thread = new ThreadConnection(sessao);
			System.out.println("AutorService");
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de Autores + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lstAutor = getListAutores(tituloCodigo);
		}

		return lstAutor;
	}
}
