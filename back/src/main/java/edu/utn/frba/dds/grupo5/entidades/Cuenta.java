package edu.utn.frba.dds.grupo5.entidades;

public class Cuenta {

	private String descripcion;
	private Double valor;
	
	public Cuenta(){
		
	}
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
