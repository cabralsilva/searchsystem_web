package model;

import java.io.Serializable;
import java.sql.Date;

public class Editora implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long codigo;
	private String nome;
	private Integer statusRegistro;
	private Integer statusTotalRegistro;
	private Date data;
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
	
	
}
