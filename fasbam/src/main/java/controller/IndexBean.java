package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import model.Assunto;
import model.Autor;
import model.Campo;
import model.Exemplar;
import model.Idioma;
import model.TipoTitulo;
import model.Titulo;
import service.AssuntoService;
import service.AutorService;
import service.ExemplarService;
import service.IdiomaService;
import service.TipoTituloService;
import service.TituloService;
import util.FacesMessages;

@Named
@ViewScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private IdiomaService is = new IdiomaService();
	private TituloService ts = new TituloService();
	private TipoTituloService tts = new TipoTituloService();
	private AssuntoService as = new AssuntoService();
	private AutorService ass = new AutorService();
	private ExemplarService es = new ExemplarService();
	private List<TipoTitulo> listTipoTitulo;
	private List<Idioma> listIdiomas;
	private Idioma idiomaSelected;
	private TipoTitulo tipoTituloSelected;
	private List<Titulo> listTitulosResult;
	private LazyDataModel<Titulo> tituloReturns;
	private List<Titulo> lstSelected = new ArrayList<>();
	private Titulo tituloSelected;
	private Titulo tituloPesquisa = new Titulo();
	private int totalRegistros;
	private int page = 0;
	private int totalPage = 0;
	private DataTable dataTable;
	private String dadosPaginacao = "";
	private Boolean refresh = true;
	@Inject
	private FacesMessages messages;
	@SuppressWarnings("unchecked")
	
	public void buscarListas() throws SQLException {

		listIdiomas = is.getListIdioma();
		for (int i = 0; i < listIdiomas.size(); i++) {
			Idioma idioma = listIdiomas.get(i);
			if (idioma.getNomeIdioma() == null)
				listIdiomas.remove(i);
		}
		listTipoTitulo = tts.getListTipoTitulo();
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (sessao.getAttribute("tituloPesquisa") != null) {
			if ((Titulo) sessao.getAttribute("tituloPesquisa") == (Titulo) sessao
					.getAttribute("tituloUltimaPesquisa")) {
				System.out.println("IGUAIS");
				totalRegistros = (int) sessao.getAttribute("totalRegistrosPesquisa");
			} else {
				System.out.println("DIFERENTES");
				sessao.setAttribute("indicePrimeiro", 0);
				totalRegistros = ts.pegarTotalRegistros((Titulo) sessao.getAttribute("tituloPesquisa"));
			}
			// System.out.println(sessao.getAttribute("tituloUltimaPesquisa"));
			tituloPesquisa = (Titulo) sessao.getAttribute("tituloPesquisa");
			pesquisar2();
		}

		if (sessao.getAttribute("listaPropria") != null) {
			for (Titulo t : (Set<Titulo>) sessao.getAttribute("listaPropria"))
				lstSelected.add(t);
		}

	}

	public void totalRecords() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		montarPaginacao(sessao);
		RequestContext.getCurrentInstance().update(Arrays.asList(":frm:msgs"));

	}

	@SuppressWarnings("unchecked")
	public void addLista() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Set<Titulo> lstSessao = new HashSet<>();
		if (sessao.getAttribute("listaPropria") != null) {
			lstSessao = (Set<Titulo>) sessao.getAttribute("listaPropria");
		}
		for (Titulo t : lstSelected) {
			if (!lstSessao.contains(t))
				lstSessao.add(t);
		}

		sessao.setAttribute("listaPropria", lstSessao);
		for (Titulo t : (Set<Titulo>) sessao.getAttribute("listaPropria"))
			System.out.println(t);

		// System.out.println(lstSelected);
	}

	public String pegarSelecionado() throws SQLException {
		long tempoInicio = System.currentTimeMillis();
		List<Exemplar> lstExemplar = new ArrayList<>();
		lstExemplar = es.getListExemplar(tituloSelected.getCodigo());

		List<Assunto> lstAssunto = new ArrayList<>();
		lstAssunto = as.getListAssunto(tituloSelected.getCodigo());

		List<Autor> lstAutores = new ArrayList<>();
		lstAutores = ass.getListAutores(tituloSelected.getCodigo());
		System.out.println("Tempo Query Detalhamento: " + (System.currentTimeMillis() - tempoInicio) + " ms");
		tituloSelected.setLstAssunto(lstAssunto);
		tituloSelected.setLstAutores(lstAutores);
		tituloSelected.setLstExemplar(lstExemplar);
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		sessao.setAttribute("tituloSelected", tituloSelected);
		return "titulo.xhtml?faces-redirect=true";
	}

	public String montarMsgSucesso() {
		String retorno = "Foram encontrados " + this.totalRegistros + " registros";
        if (this.tituloPesquisa != null && (this.tituloPesquisa.getTextoPesquisa() != "" || this.tituloPesquisa.getIdiomaTitulo() != null || this.tituloPesquisa.getCampo() != null || this.tituloPesquisa.getTipoTitulo() != null)) {
            if (this.tituloPesquisa.getTextoPesquisa() != "") {
                retorno = String.valueOf(retorno) + " por '" + this.tituloPesquisa.getTextoPesquisa() + "'";
            }
            if (this.tituloPesquisa.getIdiomaTitulo() != null) {
                retorno = String.valueOf(retorno) + " no idioma '" + this.tituloPesquisa.getIdiomaTitulo().getNomeIdioma() + "'";
            }
            retorno = this.tituloPesquisa.getCampo() != null ? String.valueOf(retorno) + " no campo '" + this.tituloPesquisa.getCampo().getDescricao() + "'" : String.valueOf(retorno) + " em todos os campos";
            if (this.tituloPesquisa.getTipoTitulo() != null) {
                retorno = String.valueOf(retorno) + " do tipo '" + this.tituloPesquisa.getTipoTitulo().getNome() + "'";
            }
        }
        return retorno;
	}

	public void pesquisar() throws SQLException {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if ((sessao.getAttribute("connectionBD") == "") || (sessao.getAttribute("connectionBD") == null)) {
			System.out.println("SESSÃO EXPIROU");
		} else {
			if (tituloPesquisa.getTextoPesquisa().length() < 3) {
				messages.error("Digite um informação maior no campo texto (Mínimo 3 caracteres)!");
				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs"));
				return;
			} else {
				long tempoInicio = System.currentTimeMillis();
				totalRegistros = ts.pegarTotalRegistros(tituloPesquisa);
				try {
					tituloReturns = new LazyDataModel<Titulo>() {
						private static final long serialVersionUID = 1L;
						List<Titulo> lst = null;

						public String strPaginacao = "";

						@Override
						public List<Titulo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
								Map<String, Object> filters) {

							ts.setFirstRecord(first);
							ts.setAmountRecord(pageSize);
							ts.setAscendent(SortOrder.ASCENDING.equals(sortOrder));
							ts.setPropertySort(sortField);
							setRowCount(totalRegistros);

							try {
								lst = ts.getPesquisa(tituloPesquisa);
								sessao.setAttribute("indicePrimeiro", (first + 1));
								int last = (((first) + pageSize) < totalRegistros) ? (first) + pageSize
										: totalRegistros;
								sessao.setAttribute("indiceUltimo", last);

							} catch (SQLException e) {
								messages.error("Erro Paginação " + e.getMessage());
								e.printStackTrace();
							}
							System.out.println(
									"Tempo Pesquisa Completa: " + (System.currentTimeMillis() - tempoInicio) + " ms");
							return lst;
						}

						@Override
						public Titulo getRowData(String rowKey) {
							for (Titulo tit : lst) {
								if (tit.getCodigo().toString().equals(rowKey))
									return tit;
							}

							return null;
						}

						@Override
						public Object getRowKey(Titulo tit) {
							return tit.getCodigo();
						}

					};
					messages.info(montarMsgSucesso());
				} catch (Exception e) {
					messages.error("Erro lazing ->" + e.getMessage());
				}

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs"));
			}

			sessao.setAttribute("tituloPesquisa", tituloPesquisa);
			sessao.setAttribute("tituloUltimaPesquisa", tituloPesquisa);
			sessao.setAttribute("totalRegistrosPesquisa", totalRegistros);

		}
	}

	public void pesquisar2() {

		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		try {
			long tempoInicio = System.currentTimeMillis();
			try {
				tituloReturns = new LazyDataModel<Titulo>() {
					private static final long serialVersionUID = 1L;
					List<Titulo> lst = null;

					@Override
					public List<Titulo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
							Map<String, Object> filters) {
						dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
								.findComponent(":frm:titulos-table");
						
						if (sessao.getAttribute("rowsPerPage") != null) {
							System.out.println("Teste rowsPerPage -> " + sessao.getAttribute("rowsPerPage"));
							if (refresh) {
								dataTable.setRows((int) sessao.getAttribute("rowsPerPage"));
								pageSize = (int) sessao.getAttribute("rowsPerPage");
								refresh = false;
							}
						}else sessao.setAttribute("rowsPerPage", pageSize);
						ts.setAmountRecord(pageSize);
						
						sessao.setAttribute("rowsPerPage", pageSize);
						if (sessao.getAttribute("indicePrimeiro") != null){
							sessao.setAttribute("paginaAtual", (int)sessao.getAttribute("indicePrimeiro") / pageSize);
							first = (int) sessao.getAttribute("paginaAtual") * pageSize;
						}
						
						ts.setFirstRecord(first);

						ts.setAscendent(SortOrder.ASCENDING.equals(sortOrder));
						ts.setPropertySort(sortField);
						setRowCount(totalRegistros);
						sessao.setAttribute("qtdePage", pageSize);
						try {
							lst = ts.getPesquisa(tituloPesquisa);
							if (sessao.getAttribute("paginaAtual") != null) {
								dataTable.setFirst((int) sessao.getAttribute("paginaAtual") * pageSize);
							}
							sessao.setAttribute("indicePrimeiro", (first + 1));
							int last = (((first) + pageSize) < totalRegistros) ? (first) + pageSize : totalRegistros;
							sessao.setAttribute("indiceUltimo", last);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						System.out.println(
								"Tempo Pesquisa Completa: " + (System.currentTimeMillis() - tempoInicio) + " ms");
						return lst;
					}

					@Override
					public Titulo getRowData(String rowKey) {
						for (Titulo tit : lst) {
							if (tit.getCodigo().toString().equals(rowKey))
								return tit;
						}

						return null;
					}

					@Override
					public Object getRowKey(Titulo tit) {
						return tit.getCodigo();
					}
				};
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}

		sessao.setAttribute("tituloPesquisa", tituloPesquisa);
		sessao.setAttribute("tituloUltimaPesquisa", tituloPesquisa);
		sessao.setAttribute("totalRegistrosPesquisa", totalRegistros);
	}

	public void onChangeRowPerPage() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frm:titulos-table");
		int rowsperpage = dataTable.getRows();
		if ((rowsperpage != (int) sessao.getAttribute("rowsPerPage"))) {
			sessao.setAttribute("paginaAtual", dataTable.getFirst() / tituloReturns.getPageSize());
			sessao.setAttribute("indicePrimeiro", ((int) sessao.getAttribute("paginaAtual") * rowsperpage) + 1);
			sessao.setAttribute("indiceUltimo", ((int) sessao.getAttribute("paginaAtual") * rowsperpage) + rowsperpage);
			if ((int) sessao.getAttribute("indiceUltimo") > totalRegistros)
				sessao.setAttribute("indiceUltimo", totalRegistros);
//			montarPaginacao(sessao);
//			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs"));
		}
		montarPaginacao(sessao);
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs"));
	}

	public void updatePage(PageEvent pv) {
		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frm:titulos-table");
		
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		sessao.setAttribute("ponteiroPrimeiro", dataTable.getFirst());
		
		sessao.setAttribute("paginaAtual", pv.getPage());
		sessao.setAttribute("indicePrimeiro", ((pv.getPage() * tituloReturns.getPageSize()) + 1));
		sessao.setAttribute("indiceUltimo",
				((pv.getPage() * tituloReturns.getPageSize()) + tituloReturns.getPageSize()));

		sessao.setAttribute("rowsPerPage", ((DataTable) pv.getSource()).getRows());
		
		
		if ((int) sessao.getAttribute("indiceUltimo") > totalRegistros)
			sessao.setAttribute("indiceUltimo", totalRegistros);

		montarPaginacao(sessao);
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs"));
		
		
	}

	public void montarPaginacao(HttpSession sessao) {
		if (sessao.getAttribute("indicePrimeiro") != null && sessao.getAttribute("indiceUltimo") != null) {
			dadosPaginacao = "Exibindo de " + sessao.getAttribute("indicePrimeiro") + " até "
					+ sessao.getAttribute("indiceUltimo");

			String retorno = "Foram encontrados " + totalRegistros + " registros";

			if (tituloPesquisa != null) {
				if (!((tituloPesquisa.getTextoPesquisa() == "") && (tituloPesquisa.getIdiomaTitulo() == null)
						&& (tituloPesquisa.getCampo() == null) && (tituloPesquisa.getTipoTitulo() == null))) {
					if (tituloPesquisa.getTextoPesquisa() != "")
						retorno += " por '" + tituloPesquisa.getTextoPesquisa() + "'";
					if (tituloPesquisa.getIdiomaTitulo() != null)
						retorno += " no idioma '" + tituloPesquisa.getIdiomaTitulo().getNomeIdioma() + "'";
					if (tituloPesquisa.getCampo() != null)
						retorno += " no campo '" + tituloPesquisa.getCampo().getDescricao() + "'";
					else
						retorno += " em todos os campos";
					if (tituloPesquisa.getTipoTitulo() != null)
						retorno += " do tipo '" + tituloPesquisa.getTipoTitulo().getNome() + "'";
				}
				retorno += "<span style='float: right;'>" + dadosPaginacao + "</span>";
			}
			messages.info(retorno);
		}
	}

	public void onRowSelect(SelectEvent event) throws IOException, SQLException {

		tituloSelected = lstSelected.get(lstSelected.size() - 1);

		List<Exemplar> lstExemplar = new ArrayList<>();
		lstExemplar = es.getListExemplar(tituloSelected.getCodigo());

		List<Assunto> lstAssunto = new ArrayList<>();
		lstAssunto = as.getListAssunto(tituloSelected.getCodigo());

		List<Autor> lstAutores = new ArrayList<>();
		lstAutores = ass.getListAutores(tituloSelected.getCodigo());

		tituloSelected.setLstAssunto(lstAssunto);
		tituloSelected.setLstAutores(lstAutores);
		tituloSelected.setLstExemplar(lstExemplar);

		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		sessao.setAttribute("tituloSelected", tituloSelected);
		FacesContext.getCurrentInstance().getExternalContext().redirect("titulo.xhtml?faces-redirect=true");
	}

	@SuppressWarnings("unchecked")
	public void onRowSelectCheckbox(SelectEvent event) {
		System.out.println("Inserindo na lista");
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Set<Titulo> lstSessao = new HashSet<>();
		if (sessao.getAttribute("listaPropria") != null) {
			lstSessao = (Set<Titulo>) sessao.getAttribute("listaPropria");
		}
		for (Titulo t : lstSelected) {
			if (!lstSessao.contains(t))
				lstSessao.add(t);
		}

		sessao.setAttribute("listaPropria", lstSessao);

	}

	public void onRowUnSelectCheckbox(UnselectEvent event) {
		System.out.println("Removendo na lista: ");

		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Set<Titulo> lstSessao = new HashSet<>();
		for (Titulo t : lstSelected)
			lstSessao.add(t);
		sessao.setAttribute("listaPropria", lstSessao);
	}

	public void onAllSelect(AjaxBehaviorEvent a) {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Set<Titulo> lstSessao = new HashSet<>();
		for (Titulo t : lstSelected)
			lstSessao.add(t);

		sessao.setAttribute("listaPropria", lstSessao);

	}

	public List<Idioma> getListIdiomas() {
		return listIdiomas;
	}

	public void setListIdiomas(List<Idioma> listIdiomas) {
		this.listIdiomas = listIdiomas;
	}

	public Idioma getIdiomaSelected() {
		return idiomaSelected;
	}

	public void setIdiomaSelected(Idioma idiomaSelected) {
		this.idiomaSelected = idiomaSelected;
	}

	public List<TipoTitulo> getListTipoTitulo() {
		return listTipoTitulo;
	}

	public void setListTipoTitulo(List<TipoTitulo> listTipoTitulo) {
		this.listTipoTitulo = listTipoTitulo;
	}

	public TipoTitulo getTipoTituloSelected() {
		return tipoTituloSelected;
	}

	public void setTipoTituloSelected(TipoTitulo tipoTituloSelected) {
		this.tipoTituloSelected = tipoTituloSelected;
	}

	public Titulo getTituloPesquisa() {
		return tituloPesquisa;
	}

	public void setTituloPesquisa(Titulo tituloPesquisa) {
		this.tituloPesquisa = tituloPesquisa;
	}

	public Campo[] getCampos() {
		return Campo.values();
	}

	public List<Titulo> getListTitulosResult() {
		return listTitulosResult;
	}

	public void setListTitulosResult(List<Titulo> listTitulosResult) {
		this.listTitulosResult = listTitulosResult;
	}

	public LazyDataModel<Titulo> getTituloReturns() {
		return tituloReturns;
	}

	public List<Titulo> getLstSelected() {
		return lstSelected;
	}

	public void setLstSelected(List<Titulo> lstSelected) {
		this.lstSelected = lstSelected;
	}

	public Titulo getTituloSelected() {
		return tituloSelected;
	}

	public void setTituloSelected(Titulo tituloSelected) {
		this.tituloSelected = tituloSelected;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

}