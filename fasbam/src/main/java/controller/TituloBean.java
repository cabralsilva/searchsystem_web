package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import model.Campo;
import model.Titulo;

@Named
@ViewScoped
public class TituloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Titulo tituloDetalhe;
	private List<String> lstExemplares;
	
	private String textoPesquisa = "testes";
	
	public void detalhes() throws IOException {
//		System.out.println("Loading..");
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		tituloDetalhe = (Titulo) sessao.getAttribute("tituloSelected");
		if (tituloDetalhe == null) FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml?faces-redirect=true");
//		System.out.println(tituloDetalhe);
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
	
	public String pesquisaAssunto(){
//		System.out.println("Pesquisar assunto: " + textoPesquisa);
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (sessao.getAttribute("tituloPesquisa") != null){
			Titulo tituloPesquisa = new Titulo();
			tituloPesquisa.setTextoPesquisa(textoPesquisa);
			tituloPesquisa.setCampo(Campo.Assunto);
			sessao.setAttribute("tituloPesquisa", tituloPesquisa);
		}
		return "index.xhtml?faces-redirect=true";
	}
	
	public String pesquisaAutor(){
//		System.out.println("Pesquisar autor: " + textoPesquisa);
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (sessao.getAttribute("tituloPesquisa") != null){
			Titulo tituloPesquisa = new Titulo();
			tituloPesquisa.setTextoPesquisa(textoPesquisa);
			tituloPesquisa.setCampo(Campo.Autor);
			sessao.setAttribute("tituloPesquisa", tituloPesquisa);
		}
		return "index.xhtml?faces-redirect=true";
	}

	public Titulo getTituloDetalhe() {
		return tituloDetalhe;
	}

	public void setTituloDetalhe(Titulo tituloDetalhe) {
		this.tituloDetalhe = tituloDetalhe;
	}

	public List<String> getLstExemplares() {
		return lstExemplares;
	}

	public String getTextoPesquisa() {
		return textoPesquisa;
	}

	public void setTextoPesquisa(String textoPesquisa) {
		this.textoPesquisa = textoPesquisa;
	}

}