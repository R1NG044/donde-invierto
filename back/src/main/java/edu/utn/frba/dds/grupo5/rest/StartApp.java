package edu.utn.frba.dds.grupo5.rest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.ConfigManager;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@EnableScheduling
public class StartApp {
	
	@Autowired
	private ServiceManager service;

	private static final Logger logger = Logger.getLogger(StartApp.class);
	
	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
	}
	
	@Scheduled(fixedDelay = 300000)
	public synchronized void checkFilesForUpdate(){
		try{
			logger.info("Inicia proceso de actualizacion de empresas");
			
			String path = ConfigManager.getInstance().getProperty("path.company.update");
			
			File directory = new File(path);
			
			if(directory.exists() && directory.canRead() && directory.isDirectory()){
				List<File> forProcess = Arrays.asList(directory.listFiles((pathname,name) -> name.toLowerCase().endsWith(".json")));
				
				logger.info("Cantidad de archivos a procesar "+forProcess.size());
				
				for(File file:forProcess){
					String nombreArchivo = file.getName();
					try{
						logger.info("Procesando "+nombreArchivo+"...");
						service.importarEmpresas(file);
						file.delete();
						logger.info("Termino de procesar archivo "+nombreArchivo);
					}catch(Exception e){
						logger.error("Error procesando archivo "+nombreArchivo,e);
					}
				}
			}else{
				throw new Exception("El directorio "+path+" no existe|no tiene los permisos adecuados (read/write) |no es un directorio");
			}
			
			logger.info("Finaliza proceso de actualizacion de empresas");
			
		}catch(Exception e){
			logger.error("Error al correr proceso de actualizacion de empresas", e);
		}
	}
}
