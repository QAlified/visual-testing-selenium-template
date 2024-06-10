package config;

import java.io.File;

public class Config {
	
	private Config() {
		throw new IllegalStateException("Clase Config es una clase utilitaria, no debe ser instanciada");
	}
	
	//Scaling factor
	public static final float SCALING_FACTOR = 1.25f;
	
	//Project root directory
	private static final String PROJECT_DIR = System.getProperty("user.dir");
	
	//Screenshot PRUEBA root directory
	public static final String SCREENSHOT_PRUEBA_PATH = PROJECT_DIR
			+ File.separator
			+ "screenshots"
			+ File.separator;
	
	//Screenshot PRUEBA root directory
		public static final String REPORTS_PATH = PROJECT_DIR
				+ File.separator
				+ "sparkReports"
				+ File.separator;
	
	
	//Screenshot root directory
	public static final String SCREENSHOT_PATH = PROJECT_DIR
			+ File.separator
			+ "screenshots"
			+ File.separator
			+ "img" 
			+ File.separator;
	
	//dataProvider root directory
	public static final String DATAPROVIDER_PATH = PROJECT_DIR
			+ File.separator
			+ "dataProviders"
			+ File.separator;
	
	//Properties Path
	public static final String PROPERTIES_PATH = PROJECT_DIR
			+ File.separator
			+ "properties"
			+ File.separator;
	
	//Files path
	public static final String FILES_PATH = PROJECT_DIR 
			+ File.separator 
			+ "files"
			+ File.separator;
	
	// driver path
	public static final String DRIVER_PATH = PROJECT_DIR 
			+ File.separator 
			+ "drivers"
			+ File.separator;
	
	//Excel path
	public static final String EXCEL_PATH = PROJECT_DIR 
			+ File.separator 
			+ "Excels"
			+ File.separator;
	
	public static final String AUTOIT_PATH = PROJECT_DIR 
			+ File.separator 
			+ "AutoIT"
			+ File.separator;
}

