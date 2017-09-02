package edu.utn.frba.dds.grupo5.persistent;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Empresa;

public class Empresas extends SingleData{

	public Empresas(EntityManager em,String nombre){
		this.em = em;
		this.nombre =nombre;
	}
	
	@SuppressWarnings("unchecked")
	public List<Empresa> all(){
		return (List<Empresa>)em.createNamedQuery("search_all_empresas").getResultList();
	}
	
}
