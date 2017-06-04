package edu.utn.frba.dds.grupo5.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.util.Util;

public class ServiceManager {
	
	private static ServiceManager instance;
	private List<Indicador> indicadores;
	private List<Cuenta> cuentas;
	
	private ServiceManager() throws Exception{
		Type listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();
		cuentas = new Gson().fromJson(
		IOUtils.toString(ServiceManager.class.getClassLoader().getResource("cuentas.json")), listType);
		
		listType = new TypeToken<ArrayList<Indicador>>(){}.getType();
		indicadores = new Gson().fromJson(
		IOUtils.toString(ServiceManager.class.getClassLoader().getResource("indicadores-predefinidos.json")), listType);
	}
	
	public static ServiceManager getInstance() throws Exception{
		if(instance==null)
			instance=new ServiceManager();
		return instance;
	}
	
	public void guardarIndicadores(List<Indicador> cargados) throws IndicadorException{
		for(Indicador ind: cargados){
			Indicador generated = FactoryIndicadores.getInstance().build(ind.getExpression(), ind.getNombre(), cuentas, indicadores);
			indicadores.add(generated);
		}
	}
	
	public Indicador recuperarIndicador(String nombre) throws Exception{
		List<Indicador> recuperados = Util.filterByPredicate(indicadores, ind -> ind.getNombre().equals(nombre));
		if(recuperados.size() != 0){
			return recuperados.get(0);
		}
		throw new Exception("Indicador '"+nombre+"' no encontrado");
	}
	
	public double evaluarIndicador(String ind,Empresa empresa,String per) throws Exception{
		Indicador indicador = recuperarIndicador(ind);
		Periodo periodo = empresa.getPeriodoByName(per);
		
		return EvaluadorExpresiones.realizarCalculo(indicador, periodo);
	}
}
