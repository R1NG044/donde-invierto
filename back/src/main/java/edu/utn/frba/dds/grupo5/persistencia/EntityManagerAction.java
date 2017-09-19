package edu.utn.frba.dds.grupo5.persistencia;

import javax.persistence.EntityManager;

public interface EntityManagerAction {
		
	public Object execute(EntityManager em) throws Exception;
}
