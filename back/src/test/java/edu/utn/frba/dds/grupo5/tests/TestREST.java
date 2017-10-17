package edu.utn.frba.dds.grupo5.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.entidades.Usuario;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.rest.CuentasService;
import edu.utn.frba.dds.grupo5.rest.EmpresasService;
import edu.utn.frba.dds.grupo5.rest.IndicadoresService;
import edu.utn.frba.dds.grupo5.rest.UsuariosService;
import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestREST {
	
	@Rule 
	public RuleStartupDB testDb = new RuleStartupDB();
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private EmpresasService empresasService;
	
	@Autowired
	private IndicadoresService indicadoresService;
	
	@Autowired
	private CuentasService cuentasService;
	
	@Autowired
	private UsuariosService usuariosService;
	
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
	
	@Test
	public void testBusquedaCuentas() throws Exception{
		assertEquals(9,cuentasService.all().size());
	}
	
	@Test
	public void testAgregaIndicador() throws Exception, IndicadorException{
		Indicador ind = new Indicador();
		ind.setNombre("Prueba-1");
		ind.setExpression("cuenta{EBITDA}+44");
		
		indicadoresService.add(ind);
		
		List<Indicador> indicadores = indicadoresService.all(null);
		
		ind = Util.find(indicadores, i -> i.getNombre().equals("Prueba-1"));
		
		assertTrue(ind!=null);
		assertEquals("Prueba-1",ind.getNombre());
		assertEquals("cuenta{EBITDA}+44",ind.getExpression());
	}
	
	@Test
	public void testUser() throws Exception{
		Usuario user = usuariosService.login("admin", "admin");
		
		assertTrue(user != null);
		assertTrue("admin".equals(user.getUsername()));
		assertTrue("admin".equals(user.getPassword()));
		
		exception.expect(Exception.class);
		usuariosService.login("admin", "pppp");
	}
	
	@After
	public void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}	
}
