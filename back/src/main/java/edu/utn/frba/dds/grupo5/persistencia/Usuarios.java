package edu.utn.frba.dds.grupo5.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import edu.utn.frba.dds.grupo5.entidades.Usuario;

public class Usuarios extends SingleData {

	public Usuarios(EntityManager em, String nombre) {
		this.em = em;
		this.nombre = nombre;
	}
	
	public Usuario login(String name,String password) throws Exception{
		
		return (Usuario) doAction(em ->  {
			try{
				Usuario user = (Usuario) em.createNamedQuery("search_usuario").setParameter("username", name).setParameter("password", password).getSingleResult();
				return user;
			}catch(NoResultException e){
				throw new Exception("Nombre de usuario/contraseña incorrectos");
			}catch(Exception e){
				throw new Exception("Error al intentar realizar el logueo");
			}
		});
		
	}

}
