package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Periodo {

	private List<Cuenta> cuentas;
	private String anio;
	private int startRange;
	private int endRange;
	private String nombre;
	
	public Periodo(){
		
	}
	
	public List<Cuenta> getCuentas() {
		if(cuentas == null)
			cuentas = new ArrayList<Cuenta>();
		return cuentas;
	}
	public void addCuenta(Cuenta cuenta) {
		if(!cuentaExist(cuenta.getDescripcion()))
			getCuentas().add(cuenta);
	}
	
	public Double getCuentaByName(String name){
		if(!cuentaExist(name))
			return null;
		return getCuentas().stream().filter(c -> c.getDescripcion().equalsIgnoreCase(name))
		.collect(Collectors.toList()).get(0).getValor();
	}
	
	private boolean cuentaExist(String descripcion){
		return !(getCuentas().stream().filter(c -> c.getDescripcion().equalsIgnoreCase(descripcion))
				.collect(Collectors.toList()).isEmpty());
	}
	
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public int getStartRange() {
		return startRange;
	}
	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}
	public int getEndRange() {
		return endRange;
	}
	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
