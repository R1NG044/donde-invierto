package edu.utn.frba.dds.grupo5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RestController
@RequestMapping("/cuenta")
public class CuentasService {

	@Autowired
	private ServiceManager service;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<Cuenta> all() throws Exception {
		return service.getCuentas();
	}

	public void setService(ServiceManager service) {
		this.service = service;
	}
}
