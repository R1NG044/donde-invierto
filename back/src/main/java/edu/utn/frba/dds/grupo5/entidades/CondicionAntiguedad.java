package edu.utn.frba.dds.grupo5.entidades;

public class CondicionAntiguedad implements ICondicion{
	
	@Override
	public int evaluate(Empresa e1, Empresa e2) {
		return e1.antiguedad().compareTo(e2.antiguedad());
	}
	
	public void setIndicador(String ind){
	}

	@Override
	public void setAnios(Integer anios) {
	}
	
}
