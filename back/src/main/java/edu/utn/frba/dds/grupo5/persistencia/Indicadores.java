package edu.utn.frba.dds.grupo5.persistencia;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Indicador;

public class Indicadores extends SingleData {

	public Indicadores(EntityManager em, String nombre) {
		this.em = em;
		this.nombre = nombre;
	}

	@SuppressWarnings("unchecked")
	public List<Indicador> all() throws Exception {
		return (List<Indicador>) doAction(em -> {
			return (List<Indicador>) em.createNamedQuery("search_all_indicadores").getResultList();
		});
	}

	public Indicador findByName(String indicador) {
		try {
			return (Indicador) doAction(em -> {
				return em.createNamedQuery("search_indicadores").setParameter("nombre", indicador).getSingleResult();
			});
		} catch (Exception e) {
			return null;
		}
	}

	public void save(Indicador ind) throws Exception {

		Indicador duplicated = findByName(ind.getNombre());

		if (duplicated != null) {
			throw new Exception("El indicador con el nombre " + duplicated.getNombre() + " ya existe");
		}

		doAction(em -> {
			em.merge(ind);
			return null;
		});
	}

	@Override
	public void clear() throws Exception {

		List<Indicador> indicadores = all();
		
		doAction(em -> {
			em.flush();
			for (Indicador i : indicadores) {
				em.remove(i);
			}
			return null;
		});

	}

}
