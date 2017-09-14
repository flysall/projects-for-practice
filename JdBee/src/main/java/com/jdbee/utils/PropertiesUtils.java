package com.jdbee.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtils {
	private static final String DRIVER_PROPERTIES_PATH = "src/main/resource/config.properties";
	public static final String PHTANTOMJS_DRIVER_PATH = "phantomjs.driveer.path";
	public static final String PHANTOMJS_JS = "phantomjs.js";
	
	private static Properties properties;
	
	public static String getProperty(String key){
		if(properties == null)
			properties = readProperties();
		return properties.getProperty(key);
	}
	
	public static Properties readProperties(){
		Properties props = new Properties();
		try{
			InputStream inputStream = Files.newInputStream(Paths.get(DRIVER_PROPERTIES_PATH));
			props.load(inputStream);
		} catch (IOException e){
			e.printStackTrace();
		}
		return props;
		
	}
}
