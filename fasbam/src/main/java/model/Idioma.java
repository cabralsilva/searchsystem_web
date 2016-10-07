package model;

import java.io.Serializable;
import java.sql.Date;

public class Idioma implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codigoIdioma;
	private String nomeIdioma;
	private Integer statusRegistro;
	private Integer statusTotalRegistro;
	private Date data;
	public Long getCodigoIdioma() {
		return codigoIdioma;
	}
	public void setCodigoIdioma(Long codigoIdioma) {
		this.codigoIdioma = codigoIdioma;
	}
	public String getNomeIdioma() {
		return nomeIdioma;
	}
	public void setNomeIdioma(String nomeIdioma) {
		this.nomeIdioma = nomeIdioma;
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
	@Override
	public String toString() {
		return "Idioma [codigoIdioma=" + codigoIdioma + ", nomeIdioma=" + nomeIdioma + ", statusRegistro="
				+ statusRegistro + ", statusTotalRegistro=" + statusTotalRegistro + ", data=" + data + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoIdioma == null) ? 0 : codigoIdioma.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((nomeIdioma == null) ? 0 : nomeIdioma.hashCode());
		result = prime * result + ((statusRegistro == null) ? 0 : statusRegistro.hashCode());
		result = prime * result + ((statusTotalRegistro == null) ? 0 : statusTotalRegistro.hashCode());
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
		Idioma other = (Idioma) obj;
		if (codigoIdioma == null) {
			if (other.codigoIdioma != null)
				return false;
		} else if (!codigoIdioma.equals(other.codigoIdioma))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (nomeIdioma == null) {
			if (other.nomeIdioma != null)
				return false;
		} else if (!nomeIdioma.equals(other.nomeIdioma))
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
		return true;
	}
	
	
	
}
