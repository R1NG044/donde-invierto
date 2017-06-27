package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import edu.utn.frba.dds.grupo5.util.Util;

public class Empresa {
	private List<Periodo> periodos;
	private String nombre;
	private Integer anioFundacion;
	
	public Empresa(){
		
	}
	
	public List<Periodo> getPeriodos() {
		if(periodos==null)
			periodos=new ArrayList<Periodo>();
		return periodos;
	}
	
	public Periodo getPeriodoByName(String name) throws Exception{
		if(!validarPeriodo(name)) {
			throw new Exception("Periodo '"+name+"' no encontrado");
		}
		return Util.filterByPredicate(getPeriodos(), p -> p.getNombre().equalsIgnoreCase(name)).get(0);
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
		return !Util.filterByPredicate(getPeriodos(),p -> p.getNombre().equalsIgnoreCase(periodo)).isEmpty();
	}	
	
	public List<Periodo> getPeriodosAnio(String anio){
		return Util.filterByPredicate(getPeriodos(),p->p.getAnio().equals(anio));
	}
	
	public Integer getAnioFundacion(){
		return anioFundacion;
	}
	
	public Integer antiguedad(){
		Integer ant=0;
		Calendar c2 = new GregorianCalendar();
		Integer anio = c2.get(Calendar.YEAR);
		ant=anio-anioFundacion;
		return ant;
	}
}