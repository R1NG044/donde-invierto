package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.persistencia.Cuentas;
import edu.utn.frba.dds.grupo5.persistencia.Repositorio;
import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.ConfigManager;

public class TestCuentas {
	static List<Cuenta> cuentasFromConfig;
	static EntityManager em;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp() throws IndicadorException, Exception {
		String cuentasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("cuentas.json"));
		Type listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();

		listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();
		cuentasFromConfig = ((List<Cuenta>)new Gson().fromJson(cuentasString, listType));
		
		String ds = ConfigManager.getInstance().getProperty("dbsource");
		
		EntityManagerFactory fact = Persistence.createEntityManagerFactory(ds);
		em = fact.createEntityManager();
	}
	
	@Test
	public void testBorraCuentas() throws Exception, IndicadorException{
		Cuentas cuentas = new Cuentas(em, "cuentas prueba");
		ServiceManager.getInstance().guardarCuentas(cuentasFromConfig);

		List<Cuenta> cuentasFromDB = cuentas.all();
		
		assertEquals(cuentasFromConfig.size(), cuentasFromDB.size());
		
		cuentas.clear();
		
		cuentasFromDB = cuentas.all();
		assertEquals(0, cuentasFromDB.size());
				
	}
	
	

}
