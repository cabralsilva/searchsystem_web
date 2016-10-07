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
import model.Editora;
import util.ConnectFilemaker;

public class EditoraService {

	private HttpSession sessao;
	private ThreadConnection thread;
	public EditoraService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	

	public List<Editora> getListAutor() throws SQLException {

		List<Editora> lista = new ArrayList<Editora>();
		try {
			String sql = "SELECT * FROM Editora order by Codigo desc";
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);

			while (rs.next()) {
				Editora editora = new Editora();
				editora.setCodigo(rs.getLong("Codigo"));
				editora.setNome(rs.getString("Nome"));
				editora.setData(rs.getDate("Data"));
				lista.add(editora);
			}

			rs.close();
			thread = new ThreadConnection(sessao);
			System.out.println("EditoraService");
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de Autores + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD", ((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2", ((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lista = getListAutor();
			
		}
		return lista;
	}

	public Editora getEditora(Long codEditora) throws SQLException {
		Editora editora = new Editora();
		try {
			
			String sql = "SELECT Editora.Codigo, Editora.Nome FROM Editora WHERE Editora.Codigo = " + codEditora
					+ " order by Codigo desc";
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);

			while (rs.next()) {
				editora.setCodigo(rs.getLong("Codigo"));
				editora.setNome(rs.getString("Nome"));
			}

			rs.close();
//			thread = new ThreadConnection(sessao);
		} catch (Exception e) {
			System.out.println("Erro ao busca Editora + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD", ((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2", ((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			editora = getEditora(codEditora);
			
		}
		return editora;
	}
}
