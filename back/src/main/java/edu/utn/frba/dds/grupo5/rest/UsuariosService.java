package edu.utn.frba.dds.grupo5.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.frba.dds.grupo5.entidades.Usuario;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RestController
@RequestMapping("/usuario")
public class UsuariosService {

	@Autowired
	private ServiceManager service;

	@RequestMapping(value = "/login/{usuario}/{password}", method = RequestMethod.GET, produces = "application/json")
	public Usuario login(@PathVariable String usuario,@PathVariable String password) throws Exception {
		return service.doLogin(usuario, password);
	}

	public void setService(ServiceManager service) {
		this.service = service;
	}

}
