package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;

public class Indicador {
	
	private String expression;
	private List<Indicador> indicadores;
	private List<Cuenta> cuentas;
	private String nombre;
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public List<Indicador> getIndicadores() {
		if(indicadores == null)
			indicadores = new ArrayList<Indicador>();
		return indicadores;
	}
	
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public List<Cuenta> getCuentas() {
		if(cuentas == null)
			cuentas = new ArrayList<Cuenta>();
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
