package edu.utn.frba.dds.grupo5.entidades;

import java.util.List;

public class Indicador {
	
	private String expression;
	private List<Indicador> indicadores;
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
}
