package edu.utn.frba.dds.grupo5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Metodologia;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RestController
@RequestMapping("/metodologia")
public class MetodologiasService {

	@Autowired
	private ServiceManager service;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<Metodologia> all() throws Exception {
		return service.getMetodologias();
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
	public List<Empresa> evaluate(@PathVariable String name) throws Exception {
		return service.evaluateMetodologia(name, service.getEmpresas());
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = "application/json")
	public List<Metodologia> refresh() throws Exception {
		service.refreshMetodologias();
		return service.getMetodologias();
	}

	public void setService(ServiceManager service) {
		this.service = service;
	}

}
