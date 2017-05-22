package edu.utn.frba.dds.grupo5.indicadores;

import java.util.List;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.util.Util;

public class FactoryIndicadores {
	
	private static FactoryIndicadores instance;
	
	private FactoryIndicadores(){
		
	}
 	
	public static FactoryIndicadores getInstance(){
		if(instance == null)
			instance = new FactoryIndicadores();
		return instance;
	}
	
	//Recibe las cuentas y indicadores existentes
	public Indicador build(String expression,String nombre, List<Cuenta> cuentas, List<Indicador> indicadores) throws Exception{
		
		if(nombre==null)
			throw new Exception("El nombre del indicador no puede ser nulo!");
		
		if(EvaluadorExpresiones.checkSintax(expression))
			throw new Exception("Sintaxis de formula incorrecta!");
		
		List<String> indicadoresDesc = Util.substringsBetween(expression, "indicador{", "}");
		List<String> cuentasDesc = Util.substringsBetween(expression, "cuenta{", "}");
		
		List<String> cuentasExistentes = Util.map(cuentas, Cuenta::getDescripcion);
		List<String> indicadoresExistentes = Util.map(indicadores, Indicador::getNombre);
		
		List<String> indicadoresNotFound = Util.filterByPredicate(indicadoresDesc,s->!indicadoresExistentes.contains(s));
		List<String> cuentasNotFound = Util.filterByPredicate(cuentasDesc,s->!cuentasExistentes.contains(s));
		
		if(!indicadoresNotFound.isEmpty())
			throw new Exception("Indicadores no encontrados: "+indicadoresNotFound.toString());
		
		if(!cuentasNotFound.isEmpty())
			throw new Exception("Cuentas no encontradas: "+cuentasNotFound.toString());
		
		Indicador indicador = new Indicador();
		indicador.setExpression(expression);
		indicador.setNombre(nombre);
		indicador.setCuentas(Util.filterByPredicate(cuentas, c -> cuentasDesc.contains(c.getDescripcion()) ));
		indicador.setIndicadores(Util.filterByPredicate(indicadores,i -> indicadoresDesc.contains(i.getNombre()) ));
		
		return indicador;
	}
	
}
