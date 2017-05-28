package edu.utn.frba.dds.grupo5.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;


public class TestIndicadores {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
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
		Indicador i = FactoryIndicadores.getInstance().build("22+cuenta{EBITDA}+cuenta{CASH}+indicador{I1}", "C2", cuentas, indicadores);
		Assert.assertTrue(i.getNombre().equals("C2"));
		Assert.assertTrue(i.getCuentas().size()==2);
		Assert.assertTrue(i.getIndicadores().size()==1);
		Assert.assertTrue(i.getCuentas().get(0).getDescripcion()=="EBITDA");
		Assert.assertTrue(i.getCuentas().get(1).getDescripcion()=="CASH");
		Assert.assertTrue(i.getIndicadores().get(0).getNombre()=="I1");
		Assert.assertTrue(i.getIndicadores().get(0).getExpression()=="1+cuenta{EBITDA}");
		Assert.assertTrue(i.getIndicadores().get(0).getCuentas().size()==1);
		Assert.assertTrue(i.getIndicadores().get(0).getCuentas().get(0).getDescripcion()=="EBITDA");
	}
	
    @Test 
    public void throwsFactoryBadSyntax() throws Exception{
    	thrown.expectMessage("Sintaxis de formula incorrecta!");
    	FactoryIndicadores.getInstance().build("22+cuenta{EBITDA+cuenta{CASH}+indicador{I1}", "C2", cuentas, indicadores);
    }
    
    @Test 
    public void throwsFactoryNoName() throws Exception{
		thrown.expectMessage("El nombre del indicador no puede ser nulo!");
		FactoryIndicadores.getInstance().build("22+cuenta{EBITDA}+cuenta{CASH}+indicador{I1}", null, cuentas, indicadores);
    }
    
    @Test 
    public void throwsFactoryIndicatorNoEncontrado() throws Exception{
		thrown.expectMessage("Indicadores no encontrados:");
		FactoryIndicadores.getInstance().build("22+cuenta{EBITDA}+cuenta{CASH}+indicador{I123}", "C2", cuentas, indicadores);
    }
    
    @Test 
    public void throwsFactoryCuentaNoEncontrada() throws Exception{
		thrown.expectMessage("Cuentas no encontradas:");
		FactoryIndicadores.getInstance().build("22+cuenta{EBITDA11}+cuenta{CASH22}+indicador{I1}", "C2", cuentas, indicadores);
    }
	@Test
	public void testEvaluadorExpresion() throws Exception {
		Indicador i = FactoryIndicadores.getInstance().build("22+cuenta{EBITDA}+cuenta{CASH}+indicador{I1}", "C2", cuentas, indicadores);
		Cuenta c1 = new Cuenta();c1.setDescripcion("EBITDA");
    	Cuenta c2 = new Cuenta();c2.setDescripcion("CASH");
		CuentaEmpresa ce1 = new CuentaEmpresa();ce1.setCuenta(c1);ce1.setValor(5.0);
    	CuentaEmpresa ce2 = new CuentaEmpresa();ce2.setCuenta(c2);ce2.setValor(3.0);
    	Periodo p = new Periodo();p.addCuenta(ce1);p.addCuenta(ce2);p.setNombre("2007");
    	Empresa facebook = new Empresa();facebook.addPeriodo(p);  
		System.out.println(EvaluadorExpresiones.realizarCalculo(i, "2007" , facebook)); 
	}
}
