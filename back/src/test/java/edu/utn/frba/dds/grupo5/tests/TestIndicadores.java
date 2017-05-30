package edu.utn.frba.dds.grupo5.tests;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestIndicadores {
	
	private JSONObject obj1;
	private JSONParser parser1 ;
	private URL input1;
	private JSONArray archivoIndicadores1;
	private Gson gson1;

	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	

	@Before
	public void setUp() throws Exception {
		
		parser1 = new JSONParser();
		input1 = TestIndicadores.class.getClassLoader().getResource("prueba-indicadores.json");
		obj1 = (JSONObject) parser1.parse(IOUtils.toString(input1));
		archivoIndicadores1 = ((JSONArray)obj1.get("archivoIndicadores1"));
		gson1= new GsonBuilder().create();
		
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
	public void testIndicadores() throws Exception{
		Indicador indicador = gson1.fromJson(archivoIndicadores1.get(0).toString(),Indicador.class);
		
		Assert.assertEquals(indicador.getNombre(),"indicador1");
		Assert.assertEquals(indicador.getExpression(),"cuenta{a}+cuenta{b}");
		Assert.assertTrue(indicador.getIndicadores().size()==0);
		Assert.assertTrue(indicador.getCuentas().size()==2);
		
		Assert.assertEquals(indicador.getCuentas().get(0).getDescripcion(),"a");
		Assert.assertEquals(indicador.getCuentas().get(1).getDescripcion(),"b");
		
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
		Cuenta c1 = new Cuenta();
		c1.setDescripcion("EBITDA");
    	
		Cuenta c2 = new Cuenta();
    	c2.setDescripcion("CASH");
		
    	CuentaEmpresa ce1 = new CuentaEmpresa();
		ce1.setCuenta(c1);
		ce1.setValor(5.0);
    	
		CuentaEmpresa ce2 = new CuentaEmpresa();
    	ce2.setCuenta(c2);
    	ce2.setValor(3.0);
    	
    	Periodo p = new Periodo();
    	p.addCuenta(ce1);
    	p.addCuenta(ce2);
    	p.setNombre("2000");
    	
    	Empresa facebook = new Empresa();
    	facebook.addPeriodo(p);
    	facebook.setNombre("facebook");
    	
    	Assert.assertEquals(EvaluadorExpresiones.realizarCalculo(i, p),36.0,0); 
	}
	
}
