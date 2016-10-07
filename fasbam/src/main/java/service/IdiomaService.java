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

import model.Idioma;
import util.ConnectFilemaker;

public class IdiomaService {

	private HttpSession sessao;
	private ThreadConnection thread;
	public IdiomaService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);	
	}
	
	public List<Idioma> getListIdioma() throws SQLException {

		List<Idioma> lista = new ArrayList<Idioma>();
		try {
			
			String sql = "SELECT * FROM Idioma order by Codigo desc";
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			while (rs.next()) {
				Idioma idioma = new Idioma();
				idioma.setCodigoIdioma(rs.getLong("Codigo"));
				idioma.setNomeIdioma(rs.getString("Nome"));
				idioma.setData(rs.getDate("Data"));
				lista.add(idioma);
			}
			rs.close();
//			thread = new ThreadConnection(sessao);
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de idiomas + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD", ((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2", ((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lista = getListIdioma();
			
		}
		return lista;
	}
}
