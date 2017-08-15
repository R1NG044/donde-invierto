package edu.utn.frba.dds.grupo5.entidades;

import java.util.List;

import javax.persistence.EntityManager;

public class Cuentas {

	private EntityManager em;
	
	public Cuentas(EntityManager em){
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cuenta> getAllCuentas(){
		return (List<Cuenta>)em.createNamedQuery("search_all_cuentas").getResultList();
	}
	
}
