package controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.UnselectEvent;

import model.Assunto;
import model.Autor;
import model.Exemplar;
import model.Titulo;
import service.AssuntoService;
import service.AutorService;
import service.ExemplarService;
import util.FacesMessages;

@Named
@ViewScoped
public class ListSessaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AssuntoService as = new AssuntoService();
	private AutorService ass = new AutorService();
	private ExemplarService es = new ExemplarService();
	
	@Inject
	private FacesMessages messages;
	
	private List<Titulo> listSessao = new ArrayList<>();
	private List<Titulo> listSelecionados = new ArrayList<>();
	
	private Titulo tituloSelected;
	
	public void buscarLista() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Set<Titulo> lstSessao = new HashSet<>();
		if (sessao.getAttribute("listaPropria") != null){
			lstSessao = (Set<Titulo>) sessao.getAttribute("listaPropria");
		}
		
		for(Titulo t : lstSessao) {
			listSessao.add(t);
			listSelecionados.add(t);
		}
	}
	
	public String pegarSelecionado() throws SQLException {

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
		return "titulo.xhtml?faces-redirect=true";
	}
	
	public void removeLista(){
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Set<Titulo> lstSessao = new HashSet<>();
		if (sessao.getAttribute("listaPropria") != null){
			lstSessao = (Set<Titulo>) sessao.getAttribute("listaPropria");
		}
		for(Titulo t : listSelecionados) lstSessao.remove(t);
		
		sessao.setAttribute("listaPropria", lstSessao);
		listSessao = new ArrayList<>();
		buscarLista();
	}
	
	public void onAllSelect(AjaxBehaviorEvent a) {

		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Set<Titulo> lstSessao = new HashSet<>();
		
		for (Titulo t : listSelecionados) lstSessao.add(t);
		
		sessao.setAttribute("listaPropria", lstSessao);
		listSessao = listSelecionados;
		
		RequestContext.getCurrentInstance().update("frm:titulos-table");
		
	}
	
	
	public String voltar(){
		return "index.xhtml?faces-redirect=true";
	}
	
	public String novaBusca() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		sessao.setAttribute("tituloPesquisa", null);
		sessao.setAttribute("indicePrimeiro", null);
		sessao.setAttribute("indiceUltimo", null);
		return "index.xhtml?faces-redirect=true";
	}
	public void onRowUnSelectCheckbox(UnselectEvent event) {
		System.out.println("Removendo na lista: ");
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		Set<Titulo> lstSessao = new HashSet<>();
		
		for (Titulo t : listSelecionados){
			lstSessao.add(t);
		}
		listSessao = listSelecionados;
		sessao.setAttribute("listaPropria", lstSessao);
		
		RequestContext.getCurrentInstance().update("frm:titulos-table");
	}

	public List<Titulo> getListSessao() {
		return listSessao;
	}

	public List<Titulo> getListSelecionados() {
		return listSelecionados;
	}

	public void setListSelecionados(List<Titulo> listSelecionados) {
		this.listSelecionados = listSelecionados;
	}

	public Titulo getTituloSelected() {
		return tituloSelected;
	}

	public void setTituloSelected(Titulo tituloSelected) {
		this.tituloSelected = tituloSelected;
	}
	
	
}