package edu.utn.frba.dds.grupo5.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RestController
@RequestMapping("/indicador")
public class IndicadoresService {

	@Autowired
	private ServiceManager service;

	@RequestMapping(value = "/{userOid}", method = RequestMethod.POST, produces = "application/json")
	public Indicador add(@PathVariable Long userOid,@RequestBody Indicador indicador) throws Exception, IndicadorException {
		indicador.setUsuario(service.getUsuario(userOid));
		return service.guardarIndicador(indicador);
	}
	
	@RequestMapping(value = "/{userOid}", method = RequestMethod.GET, produces = "application/json")
	public List<Indicador> all(@PathVariable Long userOid) throws Exception {
		return service.getIndicadores(userOid);
	}

	@RequestMapping(value = "/evaluar/{oid}/{empresaOid}/{perOid}", method = RequestMethod.GET, produces = "application/json")
	public ResultIndicador evaluar(@PathVariable Long oid, @PathVariable Long empresaOid, @PathVariable Long perOid)
			throws Exception {

		Indicador ind = service.getIndicador(oid);
		Empresa empresa = service.getEmpresa(empresaOid);
		Periodo per = empresa.getPeriodoByOid(perOid);
		
		return new ResultIndicador(EvaluadorExpresiones.realizarCalculo(ind, per));
	}

	public void setService(ServiceManager service) {
		this.service = service;
	}
	
	@SuppressWarnings("unused")
	private class ResultIndicador{
		private Double resultado;
		
		public ResultIndicador(Double resultado){
			this.setResultado(resultado);
		}
		
		public Double getResultado(){
			return resultado;
		}

		public void setResultado(Double resultado) {
			this.resultado = resultado;
		}
	}
}
