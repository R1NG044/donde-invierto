	package edu.utn.frba.dds.grupo5.indicadores;

import org.apache.commons.lang3.StringUtils;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;


public class EvaluadorExpresiones {
	
	private static String INDICADOR_VARIABLE="indicador\\{";
	private static String CUENTA_VARIABLE="cuenta\\{";
	
	public static boolean checkSintax(String expression) {
		try{
			if(expression == null || StringUtils.containsAny(expression, "|","!","&","<",">","="))
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
	
	public static Double realizarCalculo(Indicador indicador, Periodo periodo) throws Exception{
		Evaluator evaluator = new Evaluator();
		
		for (Cuenta cuenta : indicador.getCuentas()) {
			evaluator.putVariable(cuenta.getDescripcion(), periodo.getCuentaValorByName(cuenta.getDescripcion()).toString());
		}
		for (Indicador ind : indicador.getIndicadores()) {
			evaluator.putVariable(ind.getNombre(), realizarCalculo(ind,periodo).toString());
		}
		
		return evaluator.getNumberResult(getFinalFormula(indicador.getExpression()));
	}
}