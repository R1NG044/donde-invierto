package edu.utn.frba.dds.grupo5.entidades;

public class CuentaEmpresa {
	
	public Cuenta cuenta;
	public Double valor;
	
	public CuentaEmpresa(){
		
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
