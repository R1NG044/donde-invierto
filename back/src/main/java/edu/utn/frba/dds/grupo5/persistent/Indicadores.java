package edu.utn.frba.dds.grupo5.persistent;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Indicador;

public class Indicadores extends SingleData{

	public Indicadores(EntityManager em,String nombre){
		this.em = em;
		this.nombre =nombre;
	}
	
	@SuppressWarnings("unchecked")
	public List<Indicador> all(){
		return (List<Indicador>)em.createNamedQuery("search_all_indicadores").getResultList();
	}
	
	public Indicador findByName(String indicador){
		try{
			return (Indicador) em.createNamedQuery("search_indicadores").setParameter("nombre", indicador).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	public void save(Indicador ind) throws Exception{
		
		Indicador duplicated = findByName(ind.getNombre());
		
		if(duplicated != null){
			throw new Exception("El indicador con el nombre "+duplicated.getNombre()+" ya existe");
		}
		
		em.getTransaction().begin();
		em.persist(ind);
		em.getTransaction().commit();
	}
	
	@Override
	protected void clear() {
		for(Indicador i: all()){
			em.getTransaction().begin();
			em.remove(i);
			em.getTransaction().commit();
		}
	}
	
}
