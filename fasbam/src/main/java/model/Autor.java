package model;

import java.io.Serializable;
import java.sql.Date;

public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long codigo;
	private String nome;
	private int principal;
	
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
	public int getPrincipal() {
		return principal;
	}
	public void setPrincipal(int principal) {
		this.principal = principal;
	}
	@Override
	public String toString() {
		return "Autor [codigo=" + codigo + ", nome=" + nome + ", principal=" + principal + "]";
	}

	
	
	
}
