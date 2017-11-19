package edu.utn.frba.dds.grupo5.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.utn.frba.dds.grupo5.util.Util;

@Table(name="di_cuenta_empresa")
@Entity
public class CuentaEmpresa extends Persistent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1795580121987768362L;
	private Cuenta cuenta;
	private Double valor;
	
	public CuentaEmpresa(){
		
	}

	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="ce_cuenta_oid",foreignKey=@ForeignKey(name="fk_ce_cuenta_oid"))
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	@Column(name="ce_valor",nullable=false,scale=2)
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public void recoverCuenta(List<Cuenta> source) {
		Cuenta existent = Util.find(source, c -> c.getDescripcion().equalsIgnoreCase(cuenta.getDescripcion()) );
		if(existent != null) {
			this.cuenta = existent;
		}
	}
	
}
