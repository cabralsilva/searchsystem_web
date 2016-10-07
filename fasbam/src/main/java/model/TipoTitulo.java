package model;

import java.io.Serializable;
import java.sql.Date;

public class TipoTitulo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long codigo;
	private String nome;
	private Integer statusRegistro;
	private Integer statusTotalRegistro;
	private Date data;
	private String tipoEmprestimo;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getStatusRegistro() {
		return statusRegistro;
	}
	public void setStatusRegistro(Integer statusRegistro) {
		this.statusRegistro = statusRegistro;
	}
	public Integer getStatusTotalRegistro() {
		return statusTotalRegistro;
	}
	public void setStatusTotalRegistro(Integer statusTotalRegistro) {
		this.statusTotalRegistro = statusTotalRegistro;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTipoEmprestimo() {
		return tipoEmprestimo;
	}
	public void setTipoEmprestimo(String tipoEmprestimo) {
		this.tipoEmprestimo = tipoEmprestimo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((statusRegistro == null) ? 0 : statusRegistro.hashCode());
		result = prime * result + ((statusTotalRegistro == null) ? 0 : statusTotalRegistro.hashCode());
		result = prime * result + ((tipoEmprestimo == null) ? 0 : tipoEmprestimo.hashCode());
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
		TipoTitulo other = (TipoTitulo) obj;
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (statusRegistro == null) {
			if (other.statusRegistro != null)
				return false;
		} else if (!statusRegistro.equals(other.statusRegistro))
			return false;
		if (statusTotalRegistro == null) {
			if (other.statusTotalRegistro != null)
				return false;
		} else if (!statusTotalRegistro.equals(other.statusTotalRegistro))
			return false;
		if (tipoEmprestimo == null) {
			if (other.tipoEmprestimo != null)
				return false;
		} else if (!tipoEmprestimo.equals(other.tipoEmprestimo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TipoTitulo [codigo=" + codigo + ", nome=" + nome + ", statusRegistro=" + statusRegistro
				+ ", statusTotalRegistro=" + statusTotalRegistro + ", data=" + data + ", tipoEmprestimo="
				+ tipoEmprestimo + "]";
	}
	
	
}
