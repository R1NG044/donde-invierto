package edu.utn.frba.dds.grupo5.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQueries({
	@NamedQuery(name="search_all_cuentas",query="select c from Cuenta c")
})
@Table(name="di_cuenta")
@Entity
public class Cuenta extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8144207758650677012L;
	private String descripcion;
	
	public Cuenta(){
		
	}
	
	
	@Column(name="cu_descripcion",length=200,nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
