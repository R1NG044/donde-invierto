package edu.utn.frba.dds.grupo5.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="di_periodo_indicador")
@Entity
public class PeriodoIndicador extends TimestampPersistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -95184737704529837L;
	
	private Double calculatedValue;
	private Indicador indicador;
	
	@Column(name="pi_calc_value",nullable=false,scale=2)
	public Double getCalculatedValue() {
		return calculatedValue;
	}

	public void setCalculatedValue(Double calculatedValue) {
		this.calculatedValue = calculatedValue;
	}
	
	@ManyToOne
	@JoinColumn(name="pi_ind_oid",nullable=false,foreignKey=@ForeignKey(name="fk_ind_user_oid"))
	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	

}
