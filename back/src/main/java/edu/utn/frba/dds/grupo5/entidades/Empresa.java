package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;

import edu.utn.frba.dds.grupo5.util.Util;

public class Empresa {
	private List<Periodo> periodos;
	private String nombre;
	
	public Empresa(){
		
	}
	
	public List<Periodo> getPeriodos() {
		return periodos;
	}
	public Periodo getPeriodoByName(String name) throws Exception{ //tambien controla si tiene el periodo
		if(!validarPeriodo(name)) {
			return null;
		}
		return Util.filterByPredicate(getPeriodos(), p -> p.getNombre()==name).get(0);
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

	public boolean validarPeriodo(String periodo) {
		List<String> nombrePeriodos = Util.map(this.getPeriodos(),Periodo::getNombre);
		return nombrePeriodos.contains(periodo);
	}	
	
	

	
}