package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.utn.frba.dds.grupo5.util.Util;

@Table(name="di_periodo")
@Entity
public class Periodo extends Persistent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8034409814871300586L;
	private List<CuentaEmpresa> cuentas;
	private String anio;
	private int startRange;
	private int endRange;
	private String nombre;
	
	public Periodo(){
		
	}
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,orphanRemoval=true)
	@JoinColumn(name="per_ce_oid",nullable=false,foreignKey=@ForeignKey(name="fk_per_ce_oid"))
	public List<CuentaEmpresa> getCuentas() {
		if(cuentas == null)
			cuentas = new ArrayList<CuentaEmpresa>();
		return cuentas;
	}
	
	public void setCuentas(List<CuentaEmpresa> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void addCuenta(CuentaEmpresa cuenta) {
		if(!cuentaExist(cuenta.getCuenta().getDescripcion()))
			getCuentas().add(cuenta);
	}
	
	public Double getCuentaValorByName(String name) throws Exception{
		if(!cuentaExist(name))
			throw new Exception("Cuenta "+name+" inexistente en el periodo: "+getNombre());
		return Util.filterByPredicate(getCuentas(), c -> c.getCuenta().getDescripcion().equalsIgnoreCase(name)).get(0).getValor();
	}
	
	private boolean cuentaExist(String descripcion){
		return !Util.filterByPredicate(getCuentas(),c -> c.getCuenta().getDescripcion().equalsIgnoreCase(descripcion)).isEmpty();
	}
	
	@Column(name="per_anio",nullable=false,length=4)
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	@Column(name="per_start",nullable=false,length=4)
	public int getStartRange() {
		return startRange;
	}
	
	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}
	
	@Column(name="per_end",nullable=false,length=4)
	public int getEndRange() {
		return endRange;
	}
	
	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}
	
	@Column(name="per_nombre",nullable=false,length=100)
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}