package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
	
	private List<Periodo> periodos;
	private String nombre;
	
	public Empresa(){
		
	}
	
	public List<Periodo> getPeriodos() {
		return periodos;
	}
	public void addPeriodo(Periodo periodo) {
		if(periodos == null)
			periodos = new ArrayList<Periodo>();
		periodos.add(periodo);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
