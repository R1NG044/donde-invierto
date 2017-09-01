package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name="di_indicador")
@Entity
public class Indicador extends Persistent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8156980671136462943L;
	private String expression;
	private Set<Indicador> indicadores;
	private List<Cuenta> cuentas;
	private String nombre;
	
	public Indicador (){
		
	}
	@Column(name="ind_expression",nullable=false,length=200)
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinTable(name="di_indicadores",
	joinColumns=@JoinColumn(name="ind_rel_oid"),
	inverseJoinColumns=@JoinColumn(name="ind_oid"))
	public Set<Indicador> getIndicadores() {
		if(indicadores == null)
			indicadores = new HashSet<>();
		return indicadores;
	}
	
	public void setIndicadores(Collection<Indicador> indicadores) {
		getIndicadores().addAll(indicadores);
	}

	@ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	 @JoinTable(name="ind_cu",
	  joinColumns=@JoinColumn(name="cu_oid"),
	  inverseJoinColumns=@JoinColumn(name="ind_oid"))
	public List<Cuenta> getCuentas() {
		if(cuentas == null)
			cuentas = new ArrayList<Cuenta>();
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	@Column(name="ind_valor",nullable=false,scale=2)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
