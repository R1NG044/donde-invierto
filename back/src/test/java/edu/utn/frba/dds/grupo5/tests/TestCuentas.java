package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class TestCuentas {
	static List<Cuenta> cuentasFromConfig;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp() throws IndicadorException, Exception {
		String cuentasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("cuentas.json"));
		Type listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();

		listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();
		cuentasFromConfig = ((List<Cuenta>)new Gson().fromJson(cuentasString, listType));
		ServiceManager.getInstance().clearRepo();
	}
	
	@Test
	public void testBorraCuentas() throws Exception, IndicadorException{
		ServiceManager.getInstance().guardarCuentas(cuentasFromConfig);

		List<Cuenta> cuentasFromDB = ServiceManager.getInstance().getCuentas();
		
		assertEquals(cuentasFromConfig.size(), cuentasFromDB.size());
		
		ServiceManager.getInstance().clearRepo();
		
		cuentasFromDB = ServiceManager.getInstance().getCuentas();
		assertEquals(0, cuentasFromDB.size());
	}
	
	

}
