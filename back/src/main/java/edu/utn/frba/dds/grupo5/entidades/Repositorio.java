package edu.utn.frba.dds.grupo5.entidades;

import javax.persistence.EntityManager;

public class Repositorio {
	
	private EntityManager em;
	private Cuentas cuentas;
	
	public Repositorio(EntityManager em){
		this.em = em;
	}
	
	public Cuentas getCuentas(){
		if(cuentas == null){
			cuentas = new Cuentas(em);
		}
		return cuentas;
	}
	
	
}
