package edu.utn.frba.dds.grupo5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RestController
@RequestMapping("/empresa")
public class EmpresasService {
	
	@Autowired
	private ServiceManager service;
	
    @RequestMapping(value="/all", method= RequestMethod.GET, produces="application/json")
    public List<Empresa> all() throws Exception {
    	return service.getEmpresas();
    }

	public void setService(ServiceManager service) {
		this.service = service;
	}
	
}
