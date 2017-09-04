package edu.utn.frba.dds.grupo5.persistencia;

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
	
	public Empresa findByName(String empresa){
		try{
			return (Empresa) em.createNamedQuery("search_empresas").setParameter("nombre", empresa).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	public void save(Empresa emp) throws Exception{
		
		Empresa duplicated = findByName(emp.getNombre());
		
		if(duplicated != null){
			throw new Exception("La empresa con el nombre "+duplicated.getNombre()+" ya existe");
		}
		
		em.getTransaction().begin();
		em.persist(emp);
		em.getTransaction().commit();
	}
	
	@Override
	public void clear(){
		for(Empresa e: all()){
			em.getTransaction().begin();
			em.remove(em.contains(e) ? e : em.merge(e));
			em.getTransaction().commit();
		}
	}
	
}
