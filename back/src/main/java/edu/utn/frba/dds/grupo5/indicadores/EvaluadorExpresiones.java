package edu.utn.frba.dds.grupo5.indicadores;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;

import java.util.Arrays;
import java.util.List;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.util.Util;


public class EvaluadorExpresiones {
	
	private static String INDICADOR_VARIABLE="indicador\\{";
	private static String CUENTA_VARIABLE="cuenta\\{";
	
	public static boolean checkSintax(String expression) {
		try{
			if(expression == null)
				return Boolean.FALSE;
			
			Evaluator evaluator = new Evaluator();
			evaluator.setVariableResolver(new VariableResolver() {
				public String resolveVariable(String variableName) throws FunctionException {
					return String.valueOf(variableName.hashCode());
				}
			});
			evaluator.getNumberResult(getFinalFormula(expression));
			
			return Boolean.TRUE;
		}catch(Exception e){
			return Boolean.FALSE;
		}
	}
	
	private static String getFinalFormula(String expression){
		return expression.replaceAll(INDICADOR_VARIABLE, "#{").replaceAll(CUENTA_VARIABLE, "#{"); 
	}
	public static float realizarCalculo(Indicador indicador, Periodo periodo, Empresa empresa){
	float resultado=0;
	if(!empresa.getPeriodos().contains(periodo)){
		System.out.println("No se encuentra ese periodo en la empresa");
		return -1;
	}
	String expresion= indicador.getExpression();
	int posicion=0;
	double valor;
	List<String> var= Arrays.asList(expresion.split("[-+*/]"));
	while(posicion<var.size()){
		int ind=0,cuen=0;
		switch(var.get(posicion).charAt(0)) {
		case 'i':
			Indicador nuevo= indicador.getIndicadores().get(ind) ;
			valor= realizarCalculo(nuevo,periodo,empresa);
			var.set(posicion, String.valueOf(valor));
			ind++;
			break;
		case 'c':
			String name= indicador.getCuentas().get(cuen).getDescripcion();
			valor= periodo.getCuentaByName(name);
			var.set(posicion, String.valueOf(valor));
			cuen++;
			break;
		default: 		break;
		} 
		posicion++;
	
	}
	
	return resultado;
	}
}
