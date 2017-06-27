package edu.utn.frba.dds.grupo5.entidades;

public class FiltroAntiguedad implements IFiltro{

	private Integer anios;
	
	@Override
	public boolean evaluate(Empresa e1) {
		return e1.antiguedad()>anios;
	}
	
	@Override
	public void setAnios(Integer anios) {
		this.anios = anios;
	}

	@Override
	public void setIndicador(String indicador) {
	}
	
	
	
}
