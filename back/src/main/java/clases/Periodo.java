package clases;

import java.util.ArrayList;
import java.util.List;

public class Periodo {

	private List<Cuenta> cuentas;
	private String anio;
	private int startRange;
	private int endRange;
	private String nombre;
	
	public Periodo(){
		
	}
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	public void addCuenta(Cuenta cuenta) {
		if(cuentas == null)
			cuentas = new ArrayList<Cuenta>();
		cuentas.add(cuenta);
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
