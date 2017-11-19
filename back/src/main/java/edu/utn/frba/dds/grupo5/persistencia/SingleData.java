package edu.utn.frba.dds.grupo5.persistencia;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Persistent;

public class SingleData {

	protected String nombre;
	protected EntityManager em;

	public void clear() throws Exception {
		doAction(em -> {
			return em.createQuery("DELETE FROM " + nombre).executeUpdate();
		});
	}

	@SuppressWarnings("unchecked")
	public <T extends Persistent> T findByPK(Class<T> clazz, Object pk) throws Exception {
		if(pk == null) return null;
		
		return (T) doAction(em -> {
			T object = em.find(clazz, pk);
			if (object == null) {
				throw new Exception("Entidad no encontrada");
			}
			return object;
		});
	}

	public void delete(Class<? extends Persistent> clazz, Object pk) throws Exception {
		doAction(em -> {
			em.remove(em.find(clazz, pk));
			return null;
		});
	}
	
	public void detach(Class<? extends Persistent> clazz, Object pk) throws Exception {
		doAction(em -> {
			em.detach(em.find(clazz, pk));
			return null;
		});
	}


	public <T extends Persistent> void update(T obj) throws Exception {
		doAction(em -> {
			em.merge(obj);
			return null;
		});
	}
	
	public <T extends Persistent> void persist(T obj) throws Exception {
		doAction(em -> {
			em.persist(obj);
			return null;
		});
	}

	public Object doAction(EntityManagerAction action) throws Exception {
		synchronized (em) {
			try {
				em.getTransaction().begin();
				Object obj = action.execute(em);
				em.getTransaction().commit();
				return obj;
			} catch (Exception e) {
				if (em.getTransaction() != null && em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw e;
			}
		}
	}

}
