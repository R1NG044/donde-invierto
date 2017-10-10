package edu.utn.frba.dds.grupo5.tests;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.rest.EmpresasService;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestREST {
	
	@Rule 
	public RuleStartupDB testDb = new RuleStartupDB();
	
	@Autowired
	private EmpresasService empresasService;
	
	@Test
	public void testPeriodo() throws Exception{
		CuentaEmpresa ce = new CuentaEmpresa();
		ce.setValor(100D);
		Cuenta cuenta = new Cuenta();
		cuenta.setDescripcion("EBITDA-23");
		ce.setCuenta(cuenta);
		Empresa old = empresasService.all().get(0);
		
		Periodo per = old.getPeriodos().stream().findFirst().orElse(null);
		Integer oldSize = per.getCuentas().size();
		empresasService.addPeriodo(per.getOid(), ce);
		
		Empresa newEmp = empresasService.get(old.getOid());
		
		assertEquals(oldSize+1,newEmp.getPeriodoByOid(per.getOid()).getCuentas().size());
	}
	
	@Test
	public void testBusquedaEmpresas() throws Exception{
		assertEquals(6,empresasService.all().size());
	}
	
	@After
	public void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}	
}
