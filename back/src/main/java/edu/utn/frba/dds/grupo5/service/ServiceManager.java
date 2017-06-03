package edu.utn.frba.dds.grupo5.service;

import java.util.ArrayList;
import java.util.List;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Indicador;

public class ServiceManager {
	
	private ServiceManager instance;
	private List<Indicador> indicadores;
	private List<Cuenta> cuentas;
	
	
	private ServiceManager(){
		indicadores=new ArrayList<Indicador>();
		cuentas=new ArrayList<Cuenta>();
	}
	
	public ServiceManager getInstance(){
		if(instance==null)
			instance=new ServiceManager();
		return instance;
	}
	
	
}
