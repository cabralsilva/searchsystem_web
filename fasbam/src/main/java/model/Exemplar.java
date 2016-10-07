package model;

import java.io.Serializable;
import java.sql.Date;

public class Exemplar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long codigo;
	private String status;
	private String volume;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	
	
}
