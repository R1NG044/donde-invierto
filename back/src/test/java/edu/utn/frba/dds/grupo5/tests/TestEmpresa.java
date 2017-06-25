package edu.utn.frba.dds.grupo5.tests;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Periodo;


public class TestEmpresa {
	
	private List<Empresa> empresas;
	
	@Before
	public void setUp() throws Exception {
		String archivo = IOUtils.toString(TestEmpresa.class.getClassLoader().getResource("empresas.json"));
		Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
		empresas = new Gson().fromJson(archivo, listType);
		
	}

	@Test
	public void testPeriodos() throws Exception{
		Empresa empresa = empresas.get(0);
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().size()==2);
		Assert.assertEquals(empresa.getNombre(),"Snapchat");
		Assert.assertTrue(empresa.antiguedad() == 6);
		periodoEquals(empresa.getPeriodos().get(0), "Semestral","2016", 0, 6);
		periodoEquals(empresa.getPeriodos().get(1), "Anual","2016", 0, 12);
		
		empresa = empresas.get(1);
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().size()==1);
		Assert.assertEquals(empresa.getNombre(),"Facebook");
		
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
		Empresa empresa = empresas.get(0);
		
		Assert.assertEquals(empresa.getNombre(),"Snapchat");
		
		Assert.assertTrue(empresa.getPeriodos()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(0).getCuentas()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(0).getCuentas().size()==3);
		
		CuentaEmpresa cta = empresa.getPeriodos().get(0).getCuentas().get(0);
		cuentaEquals(cta,"EBITDA","100200.0");
		cta = empresa.getPeriodos().get(0).getCuentas().get(1);
		cuentaEquals(cta,"CASH","50000.0");
		cta = empresa.getPeriodos().get(0).getCuentas().get(2);
		cuentaEquals(cta,"Free Cash Flow","10022.0");
		
		Assert.assertTrue(empresa.getPeriodos().get(1).getCuentas()!=null);
		Assert.assertTrue(empresa.getPeriodos().get(1).getCuentas().size()==1);
		
		cta = empresa.getPeriodos().get(1).getCuentas().get(0);
		cuentaEquals(cta,"EBITDA","40000.0");
		
		empresa = empresas.get(1);
		
		Assert.assertEquals(empresa.getNombre(),"Facebook");
		
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

	private void cuentaEquals(CuentaEmpresa cuenta, String descripcion, String valor) {
		if(cuenta == null)
			Assert.fail("La cuenta debe ser distinto de null");
		Assert.assertEquals(cuenta.getCuenta().getDescripcion(),descripcion);
		Assert.assertEquals(cuenta.getValor(),new Double(valor));
	}
	
	
}
