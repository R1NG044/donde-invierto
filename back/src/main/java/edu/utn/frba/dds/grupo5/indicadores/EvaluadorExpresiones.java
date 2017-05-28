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
	
	//-------------------------------OPCION1
		public static float realizarCalculo(Indicador indicador, String periodo, Empresa empresa) throws Exception{
		float resultado=0;
		//List<double> getValorDeTodasLasClases= Util.map(indicador.getClases(), Clase::getNombre); //TODO no lo termine de pensar
		return resultado;
		}
	//-------------------------------OPCION2	
		public float resolverExpresionSegun(Indicador indicador, Empresa empresa, String periodo) throws Exception{
			if(validarEmpresa(indicador, empresa, periodo)){
				return this.operar(indicador, empresa, periodo);
			}
				throw new Exception("Cuenta o periodo no existente");			
		}

		public boolean validarEmpresa(Indicador indicador, Empresa empresa, String periodo) throws Exception{
			return ((empresa.validarPeriodo(periodo)) && (empresa.validarCuentas(indicador)));
		}

		public float operar(Indicador indicador, Empresa empresa, String periodo){
			return 0;																			//MOMENTANEO
		}
}
