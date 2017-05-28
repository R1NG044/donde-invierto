package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;

import edu.utn.frba.dds.grupo5.util.Util;

public class Periodo {

	private List<CuentaEmpresa> cuentas;
	private String anio;
	private int startRange;
	private int endRange;
	private String nombre;
	
	public Periodo(){
		
	}
	
	public List<CuentaEmpresa> getCuentas() {
		if(cuentas == null)
			cuentas = new ArrayList<CuentaEmpresa>();
		return cuentas;
	}
	public void addCuenta(CuentaEmpresa cuenta) {
		if(!cuentaExist(cuenta.getCuenta().getDescripcion()))
			getCuentas().add(cuenta);
	}
	
	public Double getCuentaValorByName(String name){
		if(!cuentaExist(name))
			return null;
		return Util.filterByPredicate(getCuentas(), c -> c.getCuenta().getDescripcion().equalsIgnoreCase(name)).get(0).getValor();
	}
	
	private boolean cuentaExist(String descripcion){
		return !Util.filterByPredicate(getCuentas(),c -> c.getCuenta().getDescripcion().equalsIgnoreCase(descripcion)).isEmpty();
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