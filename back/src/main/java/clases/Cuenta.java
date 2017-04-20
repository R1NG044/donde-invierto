package clases;

import java.math.BigDecimal;

public class Cuenta {

	private String descripcion;
	private BigDecimal valor;
	
	public Cuenta(){
		
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
