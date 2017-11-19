package edu.utn.frba.dds.grupo5.persistencia;

import java.util.List;

import javax.persistence.EntityManager;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class Empresas extends SingleData{

	public Empresas(EntityManager em,String nombre){
		this.em = em;
		this.nombre =nombre;
	}
	
	@SuppressWarnings("unchecked")
	public List<Empresa> all() throws Exception{
		return (List<Empresa>) doAction(em ->  {
				return (List<Empresa>)em.createNamedQuery("search_all_empresas").getResultList();
		});
	}
	
	public Empresa findByName(String empresa){
		try{
			return (Empresa) doAction(em -> {
				return (Empresa) em.createNamedQuery("search_empresas").setParameter("nombre", empresa).getSingleResult();
		});

		}catch(Exception e){
			return null;
		}
	}
	
	public void save(Empresa emp) throws Exception{
		
		Empresa duplicated = findByName(emp.getNombre());
		
		if(duplicated != null){
			throw new Exception("La empresa con el nombre "+duplicated.getNombre()+" ya existe");
		}
		
		doAction(em->{
				em.merge(emp);
				return null;
		});
	}
	
	@Override
	public void clear() throws Exception{
		List<Empresa> empresas = all();
		doAction(em -> {
			em.flush();
			for(Empresa e: empresas){
				em.remove(e);
			}
			return null;
		});
	}
	
	public void fecthAndUpdateOrInsert(Empresa emp) throws Exception{
		List<Cuenta> cuentas = ServiceManager.getInstance().getCuentas();
		Empresa fromDB = this.findByName(emp.getNombre());
		
		if(fromDB != null){
			delete(Empresa.class,fromDB.getOid());
		}
		
		emp.getPeriodos().forEach(p -> p.populateCuentas(cuentas));
		update(emp);
	}
	
}
