package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.Util;

public class TestCacheoIndicadores {
	
	@Rule 
	public RuleStartupDB testDb = new RuleStartupDB();
	private Empresa empresa;
	
	@Before
	public void setUp() throws IndicadorException, Exception {
		empresa = Util.find(ServiceManager.getInstance().getEmpresas(),e -> e.getNombre().equals("Snapchat"));
	}
	
	@Test
	public void testCacheoIndicador() throws Exception{
		assertEquals(1650000,ServiceManager.getInstance().evaluarIndicador("I1", empresa, "Semestral"),0);
		empresa = Util.find(ServiceManager.getInstance().getEmpresas(),e -> e.getNombre().equals("Snapchat"));
		assertEquals(1650000,ServiceManager.getInstance().evaluarIndicador("I1", empresa, "Semestral"),0);
		empresa = Util.find(ServiceManager.getInstance().getEmpresas(),e -> e.getNombre().equals("Snapchat"));
		ServiceManager.getInstance().actualizarPeriodo(empresa.getPeriodoByName("Semestral"));
		empresa = Util.find(ServiceManager.getInstance().getEmpresas(),e -> e.getNombre().equals("Snapchat"));
		assertEquals(1650000,ServiceManager.getInstance().evaluarIndicador("I1", empresa, "Semestral"),0);
		assertEquals(1650000,ServiceManager.getInstance().evaluarIndicador("I1", empresa, "Semestral"),0);
	}
	
	@AfterClass
	public static void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}
	
}
