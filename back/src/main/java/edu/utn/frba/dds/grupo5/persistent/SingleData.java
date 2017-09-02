package edu.utn.frba.dds.grupo5.persistent;

import javax.persistence.EntityManager;

public class SingleData {

	protected String nombre;
	protected EntityManager em;
	
	
	protected void clear(){
		em.getTransaction().begin();
		em.createQuery("DELETE FROM "+nombre).executeUpdate();
		em.getTransaction().commit();
	}
	
}
