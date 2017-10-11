package edu.utn.frba.dds.grupo5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RestController
@RequestMapping("/empresa")
public class EmpresasService {

	@Autowired
	private ServiceManager service;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<Empresa> all() throws Exception {
		return service.getEmpresas();
	}

	@RequestMapping(value = "/{oid}", method = RequestMethod.GET, produces = "application/json")
	public Empresa get(@PathVariable(name = "oid", required = true) Long oid) throws Exception {
		return service.getEmpresa(oid);
	}

	@RequestMapping(value = "/{oid}", method = RequestMethod.DELETE, produces = "application/json")
	public void delete(@PathVariable(name = "oid", required = true) Long oid) throws Exception {
		service.deleteEmpresa(oid);
	}

	@RequestMapping(value = "/{oidPeriodo}", method = RequestMethod.PUT, produces = "application/json")
	public void addPeriodo(@PathVariable(name = "oidPeriodo", required = true) Long periodoOid, @RequestBody CuentaEmpresa cuenta)
			throws Exception {

		Periodo periodo = service.getPeriodo(periodoOid);
		
		Cuenta c = service.getCuentaByDesc(cuenta.getCuenta().getDescripcion());
		
		if(c != null){
			cuenta.setCuenta(c);
		}
		
		periodo.addCuenta(cuenta);

		service.actualizarPeriodo(periodo);
	}

	public void setService(ServiceManager service) {
		this.service = service;
	}

}
