	package edu.utn.frba.dds.grupo5.indicadores;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;


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
	
	public static String realizarCalculo(Indicador indicador, String periodo, Empresa empresa) throws Exception{
		Periodo p = empresa.getPeriodoByName(periodo);
		Evaluator evaluator = new Evaluator();
		
		for (Cuenta cuenta : indicador.getCuentas()) {
			evaluator.putVariable(cuenta.getDescripcion(), Double.toString(p.getCuentaValorByName(cuenta.getDescripcion())));
		}
		for (Indicador ind : indicador.getIndicadores()) {
			evaluator.putVariable(ind.getNombre(), realizarCalculo(ind,periodo,empresa));
		}
		
		String resultado = evaluator.evaluate(getFinalFormula(indicador.getExpression()));
		return resultado;
	}
}