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
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="di_indicador")
@Entity
@NamedQueries({
	@NamedQuery(name="search_all_indicadores",query="select i from Indicador i "),
	@NamedQuery(name="search_indicadores",query="select i from Indicador i where i.nombre = :nombre"),
	@NamedQuery(name="search_indicadores_by_user",query="select i from Indicador i left join i.usuario as u where (u is null or u.oid = :userOid)")
})
public class Indicador extends Persistent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8156980671136462943L;
	private String expression;
	private Set<Indicador> indicadores;
	private List<Cuenta> cuentas;
	private String nombre;
	private Usuario usuario;
	
	public Indicador (){
		
	}
	@Column(name="ind_expression",nullable=false,length=200)
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@JoinTable(name="di_indicadores",
	joinColumns=@JoinColumn(name="ind_rel_oid"),
	inverseJoinColumns=@JoinColumn(name="ind_oid"))
	public Set<Indicador> getIndicadores() {
		if(indicadores == null)
			indicadores = new HashSet<>();
		return indicadores;
	}
	
	public void setIndicadores(Collection<Indicador> indicadores) {
		if(indicadores != null){
			this.indicadores = (Set<Indicador>) indicadores;
		}
	}

	@ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
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
	
	@Column(name="ind_nombre",nullable=false,unique=true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void addIndicadores(List<Indicador> indicadores) {
		getIndicadores().addAll(indicadores);
	}
	
	@ManyToOne(optional=true,cascade={CascadeType.ALL})
	@JoinColumn(name="ind_user_oid",nullable=true,foreignKey=@ForeignKey(name="fk_ind_user_oid"))
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
