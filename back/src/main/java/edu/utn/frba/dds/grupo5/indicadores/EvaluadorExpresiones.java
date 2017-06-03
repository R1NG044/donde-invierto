	package edu.utn.frba.dds.grupo5.indicadores;

import org.apache.commons.lang3.StringUtils;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;


public class EvaluadorExpresiones {
	
	private static String INDICADOR_VARIABLE="indicador\\{";
	private static String CUENTA_VARIABLE="cuenta\\{";
	
	public static void checkSintax(String expression) throws IndicadorException{
			try{
				if(expression == null || StringUtils.containsAny(expression, "|","!","&","<",">","~","="))
					throw new IndicadorException("Caracteres no permitidos: (|,!,&,<,>,~,=)");
				Evaluator evaluator = new Evaluator();
				evaluator.setVariableResolver(new VariableResolver() {
					public String resolveVariable(String variableName) throws FunctionException {
						return String.valueOf(variableName.hashCode());
					}
				});
				evaluator.getNumberResult(getFinalFormula(expression));
				
			}catch(EvaluationException e){
				if(e.getCause() instanceof NumberFormatException){
					throw new IndicadorException((NumberFormatException)e.getCause());
				}else{
					throw new IndicadorException(e);
				}
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