package model;

public enum Campo {
	Titulo("Titulo"),
	Autor("Autor"),
	Editora("Editora"),
	Area("Área"),
	Assunto("Assunto");
//	MEI("Microempreendedor Individual"), 
//	EIRELI("Empresa Individual de Responsabilidade Limitada"),
//	LTDA("Sociedade Limitada"),
//	SA("Sociedade Anônima");
	
	private String descricao;

	Campo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
