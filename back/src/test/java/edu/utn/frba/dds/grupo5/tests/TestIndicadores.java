package edu.utn.frba.dds.grupo5.tests;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;

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
	private Periodo periodo;

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
		
    	CuentaEmpresa ce1 = new CuentaEmpresa();
		ce1.setCuenta(c1);
		ce1.setValor(5.0);
    	
		CuentaEmpresa ce2 = new CuentaEmpresa();
    	ce2.setCuenta(c2);
    	ce2.setValor(300.0);
    	
    	periodo = new Periodo();
    	periodo.addCuenta(ce1);
    	periodo.addCuenta(ce2);
    	periodo.setNombre("Semestre 2016");
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
    public void throwsFactoryBadSyntax2() throws Exception{
    	thrown.expectMessage("Sintaxis de formula incorrecta!");
    	FactoryIndicadores.getInstance().build("22+cuent{EBITDA}+cuenta{CASH}+indicador{I1}", "C2", cuentas, indicadores);
    }
    
    @Test 
    public void throwsFactoryBadSyntax3() throws Exception{
    	thrown.expectMessage("Sintaxis de formula incorrecta!");
    	FactoryIndicadores.getInstance().build("22+cuenta{EBITDA}+cuentsa{CASH}+indicrador{I1}", "C2", cuentas, indicadores);
    }
    
    @Test 
    public void throwsFactoryBadSyntax4() throws Exception{
    	thrown.expectMessage("Sintaxis de formula incorrecta!");
    	FactoryIndicadores.getInstance().build("22a+cuenta{EBITDA}+cuenta{CASH}+indicador{I1}", "C2", cuentas, indicadores);
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
    	Assert.assertEquals(EvaluadorExpresiones.realizarCalculo(i, periodo),333.0,0); 
	}

	@Test
	public void testIndicadores() throws Exception{
		Indicador indicador1 = gson1.fromJson(archivoIndicadores1.get(0).toString(),Indicador.class);
		Assert.assertEquals(indicador1.getNombre(),"indicador1");
		Assert.assertEquals(indicador1.getExpression(),"cuenta{EBITDA}*2+cuenta{CASH}");
		
		indicador1 = FactoryIndicadores.getInstance().build(indicador1.getExpression(), indicador1.getNombre(), cuentas, indicadores);
		indicadores.add(indicador1);
		
		Assert.assertEquals(EvaluadorExpresiones.realizarCalculo(indicador1, periodo),310,0);
		
		Indicador indicador2 = gson1.fromJson(archivoIndicadores1.get(1).toString(),Indicador.class);
		Assert.assertEquals(indicador2.getNombre(),"indicador2");
		Assert.assertEquals(indicador2.getExpression(),"(cuenta{EBITDA}*2+indicador{indicador1})/2");
		
		indicador2 = FactoryIndicadores.getInstance().build(indicador2.getExpression(), indicador2.getNombre(), cuentas, indicadores);
		indicadores.add(indicador2);
		
		Assert.assertEquals(EvaluadorExpresiones.realizarCalculo(indicador2, periodo),160,0);
		
		Indicador indicador3 = gson1.fromJson(archivoIndicadores1.get(2).toString(),Indicador.class);
		Assert.assertEquals(indicador3.getNombre(),"indicador3");
		Assert.assertEquals(indicador3.getExpression(),"indicador{indicador2}+cuenta{CASH}");
		
		indicador3 = FactoryIndicadores.getInstance().build(indicador3.getExpression(), indicador3.getNombre(), cuentas, indicadores);
		
		Assert.assertEquals(EvaluadorExpresiones.realizarCalculo(indicador3, periodo),460,0);
	}
	
	@Test
	public void testCuentaNoExiste() throws Exception{
		Cuenta cuentaNueva = new Cuenta();
		cuentaNueva.setDescripcion("CASH2");
		cuentas.add(cuentaNueva);
		
		Indicador indicadorFalla = FactoryIndicadores.getInstance().build("cuenta{CASH2}", "NUEVA", cuentas, indicadores);
		
		thrown.expectMessage("Cuenta CASH2 inexistente en el periodo: Semestre 2016");
		EvaluadorExpresiones.realizarCalculo(indicadorFalla, periodo);
	}
	
	@Test
	public void testIndicadorNoExiste() throws Exception{
		Cuenta cuentaNueva = new Cuenta();
		cuentaNueva.setDescripcion("CASH2");
		cuentas.add(cuentaNueva);
		
		Indicador indicadorFalla = FactoryIndicadores.getInstance().build("cuenta{CASH2}", "NUEVO", cuentas, indicadores);
		indicadores.add(indicadorFalla);
		
		Indicador indicadorCompuesto = FactoryIndicadores.getInstance().build("indicador{NUEVO}+cuenta{CASH}*55", "COMPUESTO", cuentas, indicadores);
		
		thrown.expectMessage("Cuenta CASH2 inexistente en el periodo: Semestre 2016");
		EvaluadorExpresiones.realizarCalculo(indicadorCompuesto, periodo);
	}
	
}
