package edu.utn.frba.dds.grupo5.entidades;

import java.util.List;

import edu.utn.frba.dds.grupo5.util.Util;

public class Condicion {
	String indicador;
	public Boolean antiguedad(Empresa empresa, Integer cant){
		return empresa.antiguedad() > cant;
	}
	
	public List<Empresa> ordenarPorAntiguedad(List<Empresa> empresas, Integer cant){
		//List<Empresa> newEm= Util.filterByPredicate(empresas.getEmpresas(),e -> antiguedad(e,cant));
		//ordenar(newEm);
		//return newEm;
	}
}
