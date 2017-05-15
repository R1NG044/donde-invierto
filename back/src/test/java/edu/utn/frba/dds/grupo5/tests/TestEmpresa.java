package edu.utn.frba.dds.grupo5.tests;

import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Periodo;


public class TestEmpresa {
	
	private JSONObject obj;
	private JSONParser parser ;
	private URL input1;
	private JSONArray empresas;
	private Gson gson;
	
	@Before
	public void setUp() throws Exception {
		parser = new JSONParser();
		input1 = TestEmpresa.class.getClassLoader().getResource("Prueba-Archivo.json");
		obj = (JSONObject) parser.parse(IOUtils.toString(input1));
		empresas = ((JSONArray)obj.get("empresas"));
		gson= new GsonBuilder().create();
		
	}

	@Test
	public void testPeriodos() throws Exception{
		Empresa empresa = gson.fromJson(empresas.get(0).toString(),Empresa.class);
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().size()==2);
		Assert.assertEquals(empresa.getNombre(),"snapchat");
		
		periodoEquals(empresa.getPeriodos().get(0), "Semestral","2016", 0, 6);
		periodoEquals(empresa.getPeriodos().get(1), "Anual","2016", 0, 12);
		
		empresa = gson.fromJson(empresas.get(1).toString(),Empresa.class);
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().size()==1);
		Assert.assertEquals(empresa.getNombre(),"facebook");
		
		periodoEquals(empresa.getPeriodos().get(0), "Semestral","2017", 0, 6);
	}
	
	public void periodoEquals(Periodo per, String nombre, String anio, int start,int end){
		if(per == null)
			Assert.fail("El periodo debe ser distinto de null");
		Assert.assertEquals(per.getNombre(),nombre);
		Assert.assertEquals(per.getAnio(),anio);
		Assert.assertEquals(per.getStartRange(),start);
		Assert.assertEquals(per.getEndRange(),end);
	}
	
	@Test
	public void testCuentas(){
		Empresa empresa = gson.fromJson(empresas.get(0).toString(),Empresa.class);
		
		Assert.assertEquals(empresa.getNombre(),"snapchat");
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(0).getCuentas()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(0).getCuentas().size()==3);
		
		Cuenta cta = empresa.getPeriodos().get(0).getCuentas().get(0);
		cuentaEquals(cta,"EBITDA","100200.0");
		cta = empresa.getPeriodos().get(0).getCuentas().get(1);
		cuentaEquals(cta,"FDS","50000.0");
		cta = empresa.getPeriodos().get(0).getCuentas().get(2);
		cuentaEquals(cta,"Free Cash Flow","10022.0");
		
		Assert.assertTrue(empresa.getPeriodos().get(1).getCuentas()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(1).getCuentas().size()==1);
		
		cta = empresa.getPeriodos().get(1).getCuentas().get(0);
		cuentaEquals(cta,"EBITDA","40000.0");
		
		empresa = gson.fromJson(empresas.get(1).toString(),Empresa.class);
		
		Assert.assertEquals(empresa.getNombre(),"facebook");
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(0).getCuentas()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(0).getCuentas().size()==3);
		
		cta = empresa.getPeriodos().get(0).getCuentas().get(0);
		cuentaEquals(cta,"EBITDA","1255677.0");
		cta = empresa.getPeriodos().get(0).getCuentas().get(1);
		cuentaEquals(cta,"FDS","0.0");
		cta = empresa.getPeriodos().get(0).getCuentas().get(2);
		cuentaEquals(cta,"Free Cash Flow","55908.0");
		
	}

	private void cuentaEquals(Cuenta cuenta, String descripcion, String valor) {
		if(cuenta == null)
			Assert.fail("La cuenta debe ser distinto de null");
		Assert.assertEquals(cuenta.getDescripcion(),descripcion);
		Assert.assertEquals(cuenta.getValor(),new Double(valor));
	}
	
	
}
