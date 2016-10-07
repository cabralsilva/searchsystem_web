package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Titulo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long codigo;
	private String titulo;
	private String subTitulo;
	private String localPublicacao;
	private String numeroChamada;
	private Long numeroEdicao;
	private Long anoPublicacao;
	private Long descricaoFisica;
	private String isbn;
	private Date data;
	private String serie;
	private String numeroClasse;
	private Autor autorTitulo;
	private String autorNumero;
	private Editora editoraTitulo;
	private Long numeroVolume;
	private String referencia;
	private String tomo;
	private String area;
	private Long parte;
	private Long quantidadeAssunto;
	private Long quantidadeAutoresSecundarios;
	private Long quantidadeExemplares;
	private Idioma idiomaTitulo;
	private TipoTitulo tipoTitulo;
	private String textoPesquisa;
	private String periodicidade;
	private List<Assunto> lstAssunto;
	private List<Exemplar> lstExemplar;
	private List<Autor> lstAutores;
	
	//VARIÁVEL UTILIZADA EXCLUSIVAMENTO PARA PESQUISA
	private Campo campo;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSubTitulo() {
		return subTitulo;
	}
	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}
	public String getLocalPublicacao() {
		return localPublicacao;
	}
	public void setLocalPublicacao(String localPublicacao) {
		this.localPublicacao = localPublicacao;
	}
	public String getNumeroChamada() {
		return numeroChamada;
	}
	public void setNumeroChamada(String numeroChamada) {
		this.numeroChamada = numeroChamada;
	}
	public Long getNumeroEdicao() {
		return numeroEdicao;
	}
	public void setNumeroEdicao(Long numeroEdicao) {
		this.numeroEdicao = numeroEdicao;
	}
	public Long getAnoPublicacao() {
		return anoPublicacao;
	}
	public void setAnoPublicacao(Long anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}
	public Long getDescricaoFisica() {
		return descricaoFisica;
	}
	public void setDescricaoFisica(Long descricaoFisica) {
		this.descricaoFisica = descricaoFisica;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNumeroClasse() {
		return numeroClasse;
	}
	public void setNumeroClasse(String numeroClasse) {
		this.numeroClasse = numeroClasse;
	}
	public Autor getAutorTitulo() {
		return autorTitulo;
	}
	public void setAutorTitulo(Autor autorTitulo) {
		this.autorTitulo = autorTitulo;
	}
	public String getAutorNumero() {
		return autorNumero;
	}
	public void setAutorNumero(String autorNumero) {
		this.autorNumero = autorNumero;
	}
	public Editora getEditoraTitulo() {
		return editoraTitulo;
	}
	public void setEditoraTitulo(Editora editoraTitulo) {
		this.editoraTitulo = editoraTitulo;
	}
	public Long getNumeroVolume() {
		return numeroVolume;
	}
	public void setNumeroVolume(Long numeroVolume) {
		this.numeroVolume = numeroVolume;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTomo() {
		return tomo;
	}
	public void setTomo(String tomo) {
		this.tomo = tomo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Long getParte() {
		return parte;
	}
	public void setParte(Long parte) {
		this.parte = parte;
	}
	public Long getQuantidadeAssunto() {
		return quantidadeAssunto;
	}
	public void setQuantidadeAssunto(Long quantidadeAssunto) {
		this.quantidadeAssunto = quantidadeAssunto;
	}
	public Long getQuantidadeAutoresSecundarios() {
		return quantidadeAutoresSecundarios;
	}
	public void setQuantidadeAutoresSecundarios(Long quantidadeAutoresSecundarios) {
		this.quantidadeAutoresSecundarios = quantidadeAutoresSecundarios;
	}
	public Long getQuantidadeExemplares() {
		return quantidadeExemplares;
	}
	public void setQuantidadeExemplares(Long quantidadeExemplares) {
		this.quantidadeExemplares = quantidadeExemplares;
	}
	public Idioma getIdiomaTitulo() {
		return idiomaTitulo;
	}
	public void setIdiomaTitulo(Idioma idiomaTitulo) {
		this.idiomaTitulo = idiomaTitulo;
	}
	public Campo getCampo() {
		return campo;
	}
	public void setCampo(Campo campo) {
		this.campo = campo;
	}
	public TipoTitulo getTipoTitulo() {
		return tipoTitulo;
	}
	public void setTipoTitulo(TipoTitulo tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}
	public String getTextoPesquisa() {
		return textoPesquisa;
	}
	public void setTextoPesquisa(String textoPesquisa) {
		this.textoPesquisa = textoPesquisa;
	}
	
	public String getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}
	
	
	public List<Assunto> getLstAssunto() {
		return lstAssunto;
	}
	public void setLstAssunto(List<Assunto> lstAssunto) {
		this.lstAssunto = lstAssunto;
	}
	
	
	public List<Exemplar> getLstExemplar() {
		return lstExemplar;
	}
	public void setLstExemplar(List<Exemplar> lstExemplar) {
		this.lstExemplar = lstExemplar;
	}
	
	
	public List<Autor> getLstAutores() {
		return lstAutores;
	}
	public void setLstAutores(List<Autor> lstAutores) {
		this.lstAutores = lstAutores;
	}
	@Override
	public String toString() {
		return "Titulo [codigo=" + codigo + ", titulo=" + titulo + ", subTitulo=" + subTitulo + ", autorTitulo="
				+ autorTitulo + ", editoraTitulo=" + editoraTitulo + ", idiomaTitulo=" + idiomaTitulo + ", tipoTitulo="
				+ tipoTitulo + ", textoPesquisa=" + textoPesquisa + ", campo=" + campo + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anoPublicacao == null) ? 0 : anoPublicacao.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((autorNumero == null) ? 0 : autorNumero.hashCode());
		result = prime * result + ((autorTitulo == null) ? 0 : autorTitulo.hashCode());
		result = prime * result + ((campo == null) ? 0 : campo.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricaoFisica == null) ? 0 : descricaoFisica.hashCode());
		result = prime * result + ((editoraTitulo == null) ? 0 : editoraTitulo.hashCode());
		result = prime * result + ((idiomaTitulo == null) ? 0 : idiomaTitulo.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((localPublicacao == null) ? 0 : localPublicacao.hashCode());
		result = prime * result + ((lstAssunto == null) ? 0 : lstAssunto.hashCode());
		result = prime * result + ((lstAutores == null) ? 0 : lstAutores.hashCode());
		result = prime * result + ((lstExemplar == null) ? 0 : lstExemplar.hashCode());
		result = prime * result + ((numeroChamada == null) ? 0 : numeroChamada.hashCode());
		result = prime * result + ((numeroClasse == null) ? 0 : numeroClasse.hashCode());
		result = prime * result + ((numeroEdicao == null) ? 0 : numeroEdicao.hashCode());
		result = prime * result + ((numeroVolume == null) ? 0 : numeroVolume.hashCode());
		result = prime * result + ((parte == null) ? 0 : parte.hashCode());
		result = prime * result + ((periodicidade == null) ? 0 : periodicidade.hashCode());
		result = prime * result + ((quantidadeAssunto == null) ? 0 : quantidadeAssunto.hashCode());
		result = prime * result
				+ ((quantidadeAutoresSecundarios == null) ? 0 : quantidadeAutoresSecundarios.hashCode());
		result = prime * result + ((quantidadeExemplares == null) ? 0 : quantidadeExemplares.hashCode());
		result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result + ((subTitulo == null) ? 0 : subTitulo.hashCode());
		result = prime * result + ((textoPesquisa == null) ? 0 : textoPesquisa.hashCode());
		result = prime * result + ((tipoTitulo == null) ? 0 : tipoTitulo.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((tomo == null) ? 0 : tomo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Titulo other = (Titulo) obj;
		if (anoPublicacao == null) {
			if (other.anoPublicacao != null)
				return false;
		} else if (!anoPublicacao.equals(other.anoPublicacao))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (autorNumero == null) {
			if (other.autorNumero != null)
				return false;
		} else if (!autorNumero.equals(other.autorNumero))
			return false;
		if (autorTitulo == null) {
			if (other.autorTitulo != null)
				return false;
		} else if (!autorTitulo.equals(other.autorTitulo))
			return false;
		if (campo != other.campo)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricaoFisica == null) {
			if (other.descricaoFisica != null)
				return false;
		} else if (!descricaoFisica.equals(other.descricaoFisica))
			return false;
		if (editoraTitulo == null) {
			if (other.editoraTitulo != null)
				return false;
		} else if (!editoraTitulo.equals(other.editoraTitulo))
			return false;
		if (idiomaTitulo == null) {
			if (other.idiomaTitulo != null)
				return false;
		} else if (!idiomaTitulo.equals(other.idiomaTitulo))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (localPublicacao == null) {
			if (other.localPublicacao != null)
				return false;
		} else if (!localPublicacao.equals(other.localPublicacao))
			return false;
		if (lstAssunto == null) {
			if (other.lstAssunto != null)
				return false;
		} else if (!lstAssunto.equals(other.lstAssunto))
			return false;
		if (lstAutores == null) {
			if (other.lstAutores != null)
				return false;
		} else if (!lstAutores.equals(other.lstAutores))
			return false;
		if (lstExemplar == null) {
			if (other.lstExemplar != null)
				return false;
		} else if (!lstExemplar.equals(other.lstExemplar))
			return false;
		if (numeroChamada == null) {
			if (other.numeroChamada != null)
				return false;
		} else if (!numeroChamada.equals(other.numeroChamada))
			return false;
		if (numeroClasse == null) {
			if (other.numeroClasse != null)
				return false;
		} else if (!numeroClasse.equals(other.numeroClasse))
			return false;
		if (numeroEdicao == null) {
			if (other.numeroEdicao != null)
				return false;
		} else if (!numeroEdicao.equals(other.numeroEdicao))
			return false;
		if (numeroVolume == null) {
			if (other.numeroVolume != null)
				return false;
		} else if (!numeroVolume.equals(other.numeroVolume))
			return false;
		if (parte == null) {
			if (other.parte != null)
				return false;
		} else if (!parte.equals(other.parte))
			return false;
		if (periodicidade == null) {
			if (other.periodicidade != null)
				return false;
		} else if (!periodicidade.equals(other.periodicidade))
			return false;
		if (quantidadeAssunto == null) {
			if (other.quantidadeAssunto != null)
				return false;
		} else if (!quantidadeAssunto.equals(other.quantidadeAssunto))
			return false;
		if (quantidadeAutoresSecundarios == null) {
			if (other.quantidadeAutoresSecundarios != null)
				return false;
		} else if (!quantidadeAutoresSecundarios.equals(other.quantidadeAutoresSecundarios))
			return false;
		if (quantidadeExemplares == null) {
			if (other.quantidadeExemplares != null)
				return false;
		} else if (!quantidadeExemplares.equals(other.quantidadeExemplares))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		if (subTitulo == null) {
			if (other.subTitulo != null)
				return false;
		} else if (!subTitulo.equals(other.subTitulo))
			return false;
		if (textoPesquisa == null) {
			if (other.textoPesquisa != null)
				return false;
		} else if (!textoPesquisa.equals(other.textoPesquisa))
			return false;
		if (tipoTitulo == null) {
			if (other.tipoTitulo != null)
				return false;
		} else if (!tipoTitulo.equals(other.tipoTitulo))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (tomo == null) {
			if (other.tomo != null)
				return false;
		} else if (!tomo.equals(other.tomo))
			return false;
		return true;
	}	
	
	
}
