package edu.utn.frba.dds.grupo5.indicadores;

import edu.utn.frba.dds.grupo5.util.Util;
import net.sourceforge.jeval.EvaluationException;

public class IndicadorException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndicadorException(String message) {
		super(message);
	}

	public IndicadorException(NumberFormatException cause){
		super(extractError(cause.getMessage().toLowerCase()));
	}
	
	public IndicadorException(EvaluationException e){
		super(extractError(e.getLocalizedMessage().toLowerCase()));
	}
	
	private static String extractError(String message){
		if(!message.contains("\"")){
			if(message.contains("consecutive unary operators are not allowed")){
				return "Los operadores (+,-,*,/) no deben ser usados consecutivamente";
			}else if (message.contains("a variable has not been closed")){
				return "Los cuentas/indicadores deben estar declaradas como cuenta/indicador{NOMBRE}";
			}else	
				return "Sintaxis de indicador incorrecta!";
		}
		return "Error de sintaxis en el argumento: "+Util.substringsBetween(message, "\"", "\"").get(0);
	}
	
}
