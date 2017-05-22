package edu.utn.frba.dds.grupo5.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;

public class TestIndicadores {
	
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	@Before
	public void setUp() throws Exception {
		Cuenta c1 = new Cuenta();
		c1.setDescripcion("EBITDA");
		Cuenta c2 = new Cuenta();
		c2.setDescripcion("CASH");
		cuentas.add(c1);
		cuentas.add(c2);
		
		Indicador i1 = new Indicador();
		i1.setNombre("I1");
		i1.setExpression("1+cuenta{EBITDA}");
		i1.getCuentas().add(c1);
		indicadores.add(i1);
		
	}

	@Test
	public void testFactory() throws Exception{
		Indicador i = FactoryIndicadores.getInstance().build("22+indicador{I1}", "C2", cuentas, indicadores);
		Assert.assertTrue(i.getNombre().equals("C2"));
	}
	
}
