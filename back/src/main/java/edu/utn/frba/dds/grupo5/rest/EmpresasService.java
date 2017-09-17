package edu.utn.frba.dds.grupo5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/{oidEmpresa}/{oidPeriodo}", method = RequestMethod.PUT, produces = "application/json")
	public void addPeriodo(@PathVariable(name = "oidEmpresa", required = true) Long empresaOid,
			@PathVariable(name = "oidPeriodo", required = true) Long periodoOid, @RequestBody CuentaEmpresa cuenta)
			throws Exception {

		Empresa emp = service.getEmpresa(empresaOid);
		Periodo periodo = emp.getPeriodos().stream().filter(p -> periodoOid.equals(p.getOid())).findFirst()
				.orElse(null);
		periodo.addCuenta(cuenta);

		service.actualizarEmpresa(emp);
	}

	public void setService(ServiceManager service) {
		this.service = service;
	}

}
