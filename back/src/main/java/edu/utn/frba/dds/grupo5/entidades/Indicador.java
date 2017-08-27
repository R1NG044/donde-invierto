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

@Table(name="di_indicador")
@Entity
public class Indicador extends Persistent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8156980671136462943L;
	private String expression;
	private List<Indicador> indicadores;
	private List<Cuenta> cuentas;
	private String nombre;
	
	public Indicador (){
		
	}
	@Column(name="cu_descripcion",length=200,nullable=false)
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinColumn(name="ind_ind_oid",nullable=true,foreignKey=@ForeignKey(name="fk_ind_ind_oid"))
	public List<Indicador> getIndicadores() {
		if(indicadores == null)
			indicadores = new ArrayList<Indicador>();
		return indicadores;
	}
	
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinColumn(name="ind_cue_oid",nullable=true,foreignKey=@ForeignKey(name="fk_ind_cue_oid"))
	public List<Cuenta> getCuentas() {
		if(cuentas == null)
			cuentas = new ArrayList<Cuenta>();
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	@Column(name="ce_valor",nullable=false,scale=2)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
