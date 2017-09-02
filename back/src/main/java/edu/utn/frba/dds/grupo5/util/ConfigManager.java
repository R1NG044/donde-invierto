package edu.utn.frba.dds.grupo5.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	
	private static ConfigManager instance;
	private static Properties prop;
	
	private ConfigManager(String nameConfig) throws Exception{
		
		try(InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(nameConfig)){
			prop = new Properties();
			prop.load(is);
		}catch(Exception e){
			throw new Exception("No se pudieron cargar las configuraciones del archivo",e);
		}
		
	}
	
	public static ConfigManager getInstance() throws Exception{
		if(instance == null){
			instance = new ConfigManager("config.properties");
		}
		return instance;
	}
	
	public String getProperty(String name) throws Exception{
		String value = prop.getProperty(name);
		
		if(value == null){
			throw new Exception("Property "+name+" not found");
		}
		
		return value;
	}
	
	
}
