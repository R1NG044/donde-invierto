package edu.utn.frba.dds.grupo5.entidades;

public interface ICondicion {
	public int evaluate(Empresa e1,Empresa e2);//Retorna true si e1 > e2
	public void setIndicador(String ind);
	public void setAnios(Integer anios);
}
