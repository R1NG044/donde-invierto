package edu.utn.frba.dds.grupo5.entidades;

public interface IFiltro {
	public boolean evaluate(Empresa e1);//Retorna true si la empresa cumple
	public void setAnios(Integer anios);
	public void setIndicador(String indicador);
}
