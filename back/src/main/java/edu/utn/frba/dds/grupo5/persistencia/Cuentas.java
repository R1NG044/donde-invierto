package edu.utn.frba.dds.grupo5.persistencia;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;

public class Cuentas extends SingleData{

	public Cuentas(EntityManager em,String nombre){
		this.em = em;
		this.nombre =nombre;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cuenta> all(){
		return (List<Cuenta>)em.createNamedQuery("search_all_cuentas").getResultList();
	}
	
	public void save(Cuenta cuenta) throws Exception{
		Cuenta duplicated = findByName(cuenta.getDescripcion());
		
		if(duplicated != null){
			throw new Exception("La cuenta con descripcion "+cuenta.getDescripcion()+" ya existe");
		}
		
		em.getTransaction().begin();
		em.persist(cuenta);
		em.getTransaction().commit();
	}
	
	public Cuenta findByName(String nombre){
		try{
			return (Cuenta) em.createNamedQuery("search_cuentas").setParameter("descripcion", nombre).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
}
