package edu.utn.frba.dds.grupo5.persistencia;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Metodologia;

public class Metodologias extends SingleData {

	public Metodologias(EntityManager em, String nombre) {
		this.em = em;
		this.nombre = nombre;
	}

	@SuppressWarnings("unchecked")
	public List<Metodologia> all() throws Exception {
		return (List<Metodologia>) doAction(em -> {
			return em.createNamedQuery("search_all_metodologias").getResultList();
		});
	}

	public Metodologia findByName(String metodologia) {
		try {
			return (Metodologia) doAction(em -> {
				return em.createNamedQuery("search_metodologias").setParameter("nombre", metodologia).getSingleResult();
			});
		} catch (Exception e) {
			return null;
		}
	}

	public void save(Metodologia me) throws Exception {

		Metodologia duplicated = findByName(me.getNombre());

		if (duplicated != null) {
			throw new Exception("La metodologia con el nombre " + duplicated.getNombre() + " ya existe");
		}

		doAction(em -> {
			em.merge(me);
			return null;
		});
	}
}
