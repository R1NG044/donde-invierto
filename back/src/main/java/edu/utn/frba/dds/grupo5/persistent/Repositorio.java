package edu.utn.frba.dds.grupo5.persistent;

import javax.persistence.EntityManager;

public class Repositorio {
	
	private EntityManager em;
	private Cuentas cuentas;
	private Empresas empresas;
	private Indicadores indicadores;
	
	public Repositorio(EntityManager em){
		this.em = em;
	}
	
	public Cuentas getCuentas(){
		if(cuentas == null){
			cuentas = new Cuentas(em,"Cuenta");
		}
		return cuentas;
	}

	public Empresas getEmpresas() {
		if(empresas == null){
			empresas = new Empresas(em,"Empresa");
		}
		return empresas;
	}
	
	public Indicadores getIndicadores() {
		if(indicadores == null){
			indicadores = new Indicadores(em,"Indicador");
		}
		return indicadores;
	}
	
	public void clearAll(){
		getEmpresas().clear();
		getIndicadores().clear();
		getCuentas().clear();
	}
}
