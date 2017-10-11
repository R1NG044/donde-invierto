package edu.utn.frba.dds.grupo5.persistencia;

import javax.persistence.EntityManager;

public class Periodos extends SingleData {

	public Periodos(EntityManager em, String nombre) {
		this.em = em;
		this.nombre = nombre;
	}

}
