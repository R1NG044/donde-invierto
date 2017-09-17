package edu.utn.frba.dds.grupo5.persistencia;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Persistent;

public class SingleData {

	protected String nombre;
	protected EntityManager em;
	
	
	public void clear(){
		em.getTransaction().begin();
		em.createQuery("DELETE FROM "+nombre).executeUpdate();
		em.getTransaction().commit();
	}
	

	public <T extends Persistent> T findByPK(Class<T> clazz,Object pk) throws Exception{
		T object = em.find(clazz, pk);
		if(object == null){	
			throw new Exception("Entidad no encontrada");
		}
		return object;
	}
	
	public void delete(Class<? extends Persistent> clazz,Object pk) throws Exception{
		em.getTransaction().begin(); 
		em.remove(findByPK(clazz, pk));
		em.getTransaction().commit();
	}
	
	
	public <T extends Persistent> void update(T obj) throws Exception{
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}
	
}
