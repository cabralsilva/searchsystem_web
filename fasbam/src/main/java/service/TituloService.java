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

import com.mysql.jdbc.SQLError;

import model.Autor;
import model.Campo;
import model.Editora;
import model.Idioma;
import model.TipoTitulo;
import model.Titulo;
import util.ConnectFilemaker;

public class TituloService {
	private AutorService as = new AutorService();

	/* configurações para lazing load */
	private int firstRecord;
	private int amountRecord;
	private String propertySort;
	private boolean ascendent;
	private int amountTotalRecord;
	/*-------------------------*/

	private HttpSession sessao;
	private ThreadConnection thread;

	public TituloService() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public List<Titulo> getPesquisa(Titulo titulo) throws SQLException {
		List<Titulo> lista = new ArrayList<Titulo>();
		try {

			String sql = getSQL(titulo.getIdiomaTitulo(), titulo.getCampo(), titulo.getTipoTitulo(),
					titulo.getTextoPesquisa());

//			System.out.println(sql);
			long tempoInicio = System.currentTimeMillis();
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			System.out.println("Tempo Query Listagem Pagina: " + (System.currentTimeMillis() - tempoInicio) + " ms");

			while (rs.next()) {
				Titulo titulobusca = new Titulo();
				Autor autor = new Autor();
				Editora editora = new Editora();
				TipoTitulo tt = new TipoTitulo();
				Idioma idioma = new Idioma();
				titulobusca.setCodigo(rs.getLong("Codigo"));
				titulobusca.setTitulo(rs.getString("Titulo"));
				titulobusca.setSubTitulo(rs.getString("SubTitulo"));
				titulobusca.setLocalPublicacao(rs.getString("PublicacaoLocal"));
				titulobusca.setNumeroEdicao(rs.getLong("Edicao"));
				titulobusca.setDescricaoFisica(rs.getLong("DescricaoFisica"));
				titulobusca.setIsbn(rs.getString("ISBN"));
				titulobusca.setNumeroChamada(rs.getString("NumeroChamada"));

				editora.setCodigo(rs.getLong("CodigoEditora"));
				editora.setNome(rs.getString("NomeEditora"));
				titulobusca.setEditoraTitulo(editora);

				tt.setCodigo(rs.getLong("CodigoTipoTitulo"));
				tt.setNome(rs.getString("NomeTipoTitulo"));
				titulobusca.setTipoTitulo(tt);

				titulobusca.setNumeroVolume(rs.getLong("Volume"));
				titulobusca.setArea(rs.getString("Area"));
				titulobusca.setParte(rs.getLong("Parte"));

				idioma.setCodigoIdioma(rs.getLong("CodigoIdioma"));
				idioma.setNomeIdioma(rs.getString("NomeIdioma"));
				titulobusca.setIdiomaTitulo(idioma);

				autor = as.getAutor(rs.getLong("CodigoAutor"));
				titulobusca.setAutorTitulo(autor);

				lista.add(titulobusca);
			}
			rs.close();
			thread = new ThreadConnection(sessao);
			System.out.println("TituloService");
		} catch (Exception e) {
			e.printStackTrace();
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			lista = getPesquisa(titulo);
			
		}
		return lista;
	}

	public int pegarTotalRegistros(Titulo titulo) throws SQLException {
		try {
			String sql = montarSQLContagem(titulo.getIdiomaTitulo(), titulo.getCampo(), titulo.getTipoTitulo(),
					titulo.getTextoPesquisa());

			long tempoInicio = System.currentTimeMillis();
			ResultSet rs = (ResultSet) ((Statement) sessao.getAttribute("connectionBD")).executeQuery(sql);
			System.out.println("Tempo Contagem: " + (System.currentTimeMillis() - tempoInicio));

			rs.last();
			amountTotalRecord = rs.getRow();
			rs.beforeFirst();

			rs.close();
//			thread = new ThreadConnection(sessao);
		} catch (Exception e) {
			System.out.println("Erro ao busca lista de Titulos + " + e);
			((Statement) sessao.getAttribute("connectionBD")).close();
			((Statement) sessao.getAttribute("connectionBD2")).close();
			((ConnectFilemaker) sessao.getAttribute("cf")).getCon().close();
			sessao.setAttribute("cf", new ConnectFilemaker());
			sessao.setAttribute("connectionBD",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			sessao.setAttribute("connectionBD2",
					((ConnectFilemaker) sessao.getAttribute("cf")).getCon().createStatement());
			amountTotalRecord = pegarTotalRegistros(titulo);
			
		}
		return amountTotalRecord;
	}

	private String montarSQLContagem(Idioma idioma, Campo campo, TipoTitulo tipoTitulo, String texto) {
		texto = (texto != "" && texto != null) ? texto.toLowerCase() : null;
		String sql = "SELECT DISTINCT Titulo.Codigo FROM Titulo";
		String where = "";
		String joins = "";
		String whereIdioma = "";
		String whereTipoTitulo = "";
		Boolean previous = false;

		if (idioma != null) {
			if (previous)
				whereIdioma += " AND ";
			else
				whereIdioma += "\n\tWHERE ";
			previous = true;
			whereIdioma += " (Titulo.CodigoIdioma = " + idioma.getCodigoIdioma() + ")";
			where += whereIdioma;
		}

		if (tipoTitulo != null) {
			if (previous)
				whereTipoTitulo += " AND ";
			else
				whereTipoTitulo += "\n\tWHERE ";
			previous = true;
			whereTipoTitulo += "(Titulo.CodigoTipoTitulo = " + tipoTitulo.getCodigo() + ")";
			where += whereTipoTitulo;
		}

		if (campo != null) {
			switch (campo.getDescricao()) {
			case "Titulo":
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Titulo.Titulo) like '%" + texto + "%'" + "\n\t)";
				previous = true;
				break;
			case "Autor":
				joins += "\n\tLEFT OUTER JOIN AutorTitulo ON AutorTitulo.CodigoTitulo = Titulo.Codigo"
						+ "\n\tLEFT OUTER JOIN Autor ON Autor.Codigo = AutorTitulo.CodigoAutor";
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Autor.Nome) like '%" + texto + "%'" + "\n)";
				previous = true;
				break;

			case "Editora":
				joins += "\n\tLEFT OUTER join Editora ON Editora.Codigo = Titulo.CodigoEditora";
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Editora.Nome) like '%" + texto + "%'" + "\n)";
				previous = true;
				break;
			case "Área":
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Titulo.Area) like '%" + texto + "%'" + "\n)";
				previous = true;
				break;
			case "Assunto":
				joins += "\n\tLEFT OUTER JOIN AssuntoTitulo ON AssuntoTitulo.CodigoTitulo = Titulo.Codigo"
						+ "\n\tLEFT OUTER JOIN Assunto ON Assunto.Codigo = AssuntoTitulo.CodigoAssunto";
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\t(LOWER(Assunto.Nome) like '%" + texto + "%')" + "\n\t)";
				previous = true;
				break;
			default:
				break;
			}
		} else {
			joins += "\n\tLEFT OUTER join Editora ON Editora.Codigo = Titulo.CodigoEditora"
					+ "\n\tLEFT OUTER JOIN AutorTitulo ON AutorTitulo.CodigoTitulo = Titulo.Codigo"
					+ "\n\tLEFT OUTER JOIN Autor ON Autor.Codigo = AutorTitulo.CodigoAutor"
					+ "\n\tLEFT OUTER JOIN AssuntoTitulo ON AssuntoTitulo.CodigoTitulo = Titulo.Codigo"
					+ "\n\tLEFT OUTER JOIN Assunto ON Assunto.Codigo = AssuntoTitulo.CodigoAssunto";

			if (previous)
				where += " AND ";
			else
				where += "\n\t WHERE ";
			where += "(" + "\n\t\t(LOWER(Assunto.Nome) like '%" + texto + "%') or" + "\n\t\t(LOWER(Autor.Nome) like '%"
					+ texto + "%') or" + "\n\t\t(LOWER(Titulo.Area) like '%" + texto + "%') or"
					+ "\n\t\t(LOWER(Titulo.Titulo) like '%" + texto + "%') or" + "\n\t\t(LOWER(Editora.Nome) like '%"
					+ texto + "%')" + "\n)";

			previous = true;
		}

		sql += joins + where;
		return sql;
	}

	public String getSQL(Idioma idioma, Campo campo, TipoTitulo tipoTitulo, String texto) {
		texto = texto.toLowerCase();
		// String sql = "SELECT DISTINCT Titulo.* FROM Titulo";
		String sql = "SELECT DISTINCT Titulo.Codigo, Titulo.Titulo, Titulo.SubTitulo, Titulo.NumeroChamada, "
				+ "Titulo.PublicacaoLocal, Titulo.Edicao, Titulo.DescricaoFisica, Titulo.ISBN, Titulo.CodigoAutor, "
				+ "Titulo.CodigoEditora, Titulo.Volume, Titulo.Area, Titulo.CodigoTipoTitulo, Titulo.Periodicidade, Titulo.Parte, Titulo.CodigoIdioma, "
				+ "Titulo.NomeEditora, Titulo.NomeIdioma, Titulo.NomeTipoTitulo FROM Titulo";
		String where = "";
		String joins = "";
		String whereIdioma = "";
		String whereTipoTitulo = "";
		Boolean previous = false;

		if (idioma != null) {
			if (previous)
				whereIdioma += " AND ";
			else
				whereIdioma += "\n\tWHERE ";
			previous = true;
			whereIdioma += " (Titulo.CodigoIdioma = " + idioma.getCodigoIdioma() + ")";
			where += whereIdioma;
		}

		if (tipoTitulo != null) {
			if (previous)
				whereTipoTitulo += " AND ";
			else
				whereTipoTitulo += "\n\tWHERE ";
			previous = true;
			whereTipoTitulo += "(Titulo.CodigoTipoTitulo = " + tipoTitulo.getCodigo() + ")";
			where += whereTipoTitulo;
		}

		if (campo != null) {
			switch (campo.getDescricao()) {
			case "Titulo":
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Titulo.Titulo) like '%" + texto + "%'" + "\n\t)";
				previous = true;
				break;
			case "Autor":
				joins += "\n\tLEFT OUTER JOIN AutorTitulo ON AutorTitulo.CodigoTitulo = Titulo.Codigo"
						+ "\n\tLEFT OUTER JOIN Autor ON Autor.Codigo = AutorTitulo.CodigoAutor";
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Autor.Nome) like '%" + texto + "%'" + "\n)";
				previous = true;
				break;

			case "Editora":
				joins += "\n\tLEFT OUTER join Editora ON Editora.Codigo = Titulo.CodigoEditora";
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Editora.Nome) like '%" + texto + "%'" + "\n)";
				previous = true;
				break;
			case "Área":
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\tLOWER(Titulo.Area) like '%" + texto + "%'" + "\n)";
				previous = true;
				break;
			case "Assunto":
				joins += "\n\tLEFT OUTER JOIN AssuntoTitulo ON AssuntoTitulo.CodigoTitulo = Titulo.Codigo"
						+ "\n\tLEFT OUTER JOIN Assunto ON Assunto.Codigo = AssuntoTitulo.CodigoAssunto";
				if (previous)
					where += " AND ";
				else
					where += "\n\t WHERE ";
				where += "(" + "\n\t\t(LOWER(Assunto.Nome) like '%" + texto + "%')" + "\n\t)";
				previous = true;
				break;
			default:
				break;
			}
		} else {
			joins += "\n\tLEFT OUTER join Editora ON Editora.Codigo = Titulo.CodigoEditora"
					+ "\n\tLEFT OUTER JOIN AutorTitulo ON AutorTitulo.CodigoTitulo = Titulo.Codigo"
					+ "\n\tLEFT OUTER JOIN Autor ON Autor.Codigo = AutorTitulo.CodigoAutor"
					+ "\n\tLEFT OUTER JOIN AssuntoTitulo ON AssuntoTitulo.CodigoTitulo = Titulo.Codigo"
					+ "\n\tLEFT OUTER JOIN Assunto ON Assunto.Codigo = AssuntoTitulo.CodigoAssunto";

			if (previous)
				where += " AND ";
			else
				where += "\n\t WHERE ";
			where += "(" + "\n\t\t(LOWER(Assunto.Nome) like '%" + texto + "%') or" + "\n\t\t(LOWER(Autor.Nome) like '%"
					+ texto + "%') or" + "\n\t\t(LOWER(Titulo.Area) like '%" + texto + "%') or"
					+ "\n\t\t(LOWER(Titulo.Titulo) like '%" + texto + "%') or" + "\n\t\t(LOWER(Editora.Nome) like '%"
					+ texto + "%')" + "\n)";

			previous = true;
		}

		sql += joins + where;
		if ((this.ascendent) && (this.propertySort != null))
			sql += "\n ORDER BY " + this.propertySort;
		else if (this.propertySort != null)
			sql += "\n ORDER BY " + this.propertySort + " DESC";
		sql += "\n OFFSET " + this.firstRecord + " ROWS FETCH FIRST " + this.amountRecord + " ROWS ONLY";
		return sql;
	}

	public int getFirstRecord() {
		return firstRecord;
	}

	public void setFirstRecord(int firstRecord) {
		this.firstRecord = firstRecord;
	}

	public int getAmountRecord() {
		return amountRecord;
	}

	public void setAmountRecord(int amountRecord) {
		this.amountRecord = amountRecord;
	}

	public String getPropertySort() {
		return propertySort;
	}

	public void setPropertySort(String propertySort) {
		this.propertySort = propertySort;
	}

	public boolean isAscendent() {
		return ascendent;
	}

	public void setAscendent(boolean ascendent) {
		this.ascendent = ascendent;
	}

	public int getAmountTotalRecord() {
		return amountTotalRecord;
	}

	public void setAmountTotalRecord(int amountTotalRecord) {
		this.amountTotalRecord = amountTotalRecord;
	}

}
