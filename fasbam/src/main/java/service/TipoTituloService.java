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

import model.Editora;
import model.TipoTitulo;
import util.ConnectFilemaker;

public class TipoTituloService {

	private HttpSession sessao;
	private ThreadConnection thread;
	public TipoTituloService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public List<TipoTitulo> getListTipoTitulo() throws SQLException {

		List<TipoTitulo> lista = new ArrayList<TipoTitulo>();
		try {
			String sql = "SELECT * FROM TipoTitulo order by Codigo desc";
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			while (rs.next()) {
				TipoTitulo tipoTitulo = new TipoTitulo();
				tipoTitulo.setCodigo(rs.getLong("Codigo"));
				tipoTitulo.setNome(rs.getString("Nome"));
				tipoTitulo.setData(rs.getDate("Data"));
				tipoTitulo.setTipoEmprestimo(rs.getString("TipoEmprestimo"));
				lista.add(tipoTitulo);
			}

			rs.close();
			thread = new ThreadConnection(sessao);
			System.out.println("TipoTituloService");
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de tipo titulo + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lista = getListTipoTitulo();
			
		}
		return lista;
	}

	public TipoTitulo getTipoTitulo(Long codTipoTitulo) throws SQLException {
		TipoTitulo tipoTitulo = new TipoTitulo();
		try {
			String sql = "SELECT TipoTitulo.Codigo, TipoTitulo.Nome FROM TipoTitulo WHERE TipoTitulo.Codigo = "
					+ codTipoTitulo + " order by Codigo desc";
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			while (rs.next()) {
				tipoTitulo.setCodigo(rs.getLong("Codigo"));
				tipoTitulo.setNome(rs.getString("Nome"));
			}

			rs.close();
			thread = new ThreadConnection(sessao);
			System.out.println("TipoTituloService");
		} catch (Exception e) {
			System.out.println("Erro ao busca TipoTitulo + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			tipoTitulo = getTipoTitulo(codTipoTitulo);
			
		}
		return tipoTitulo;
	}
}
