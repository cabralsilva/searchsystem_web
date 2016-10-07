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
import model.Exemplar;
import util.ConnectFilemaker;

public class ExemplarService {

	private HttpSession sessao;
	private ThreadConnection thread;
	public ExemplarService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public List<Exemplar> getListExemplar(Long tituloCodigo) throws SQLException {
		List<Exemplar> lstExemplar = new ArrayList<>();
		try {

			String sql = "select Exemplar.Codigo, Exemplar.Status, Exemplar.Volume from Exemplar "
					+ "where Exemplar.CodigoTitulo = " + tituloCodigo;

			// System.out.println(sql);
			long tempoInicio = System.currentTimeMillis();
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			System.out.println("Tempo Query Exemplar: " + (System.currentTimeMillis() - tempoInicio) + " ms");

			while (rs.next()) {
				Exemplar exemplar = new Exemplar();
				exemplar.setCodigo(rs.getLong("Codigo"));
				exemplar.setStatus(rs.getString("Status"));
				exemplar.setVolume(rs.getString("Volume"));
				lstExemplar.add(exemplar);
			}
			rs.close();
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
			lstExemplar = getListExemplar(tituloCodigo);

		}

		return lstExemplar;
	}
}
