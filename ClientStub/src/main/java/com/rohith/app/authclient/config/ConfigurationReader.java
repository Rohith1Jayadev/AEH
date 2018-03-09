package com.rohith.app.authclient.config;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ConfigurationReader {

	public static final String CONFIG = "config.xml";

	/**
	 * Reads XML
	 * 
	 * @param filePath
	 * @param classType
	 * @return
	 * @throws JAXBException
	 */
	public static Object readXML(String filePath, Class classType) throws JAXBException {

		File file = new File(filePath).getAbsoluteFile();
		JAXBContext jaxbContext = JAXBContext.newInstance(classType);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(file);

	}

	/**
	 * Writes XML
	 * 
	 * @param filePath
	 * @param classType
	 * @param obj
	 * @throws JAXBException
	 */
	public static Object writeXML(String filePath, Class classType, Object obj) throws JAXBException {

		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(classType);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(obj, file);

		return readXML(filePath, classType);

	}

	public static boolean isPresent(String filePath) {

		File f = new File(filePath);

		if (f.exists() && !f.isDirectory()) {

			return true;
		}

		return false;
	}

	public static AEHMasterConfig getDefaultConfig(String pathName) throws JAXBException {

		AEHMasterConfig config = new AEHMasterConfig();
		config.setMasterHost("localhost");
		config.setMasterPort(8080);
		config.setMasterUrl("/CentralAEHub/validate");
		config.setClientSecret("AUCLIENT789");
		config.setMaxTotalConnection(50);
		config.setMaxConnectionPerRoute(10);
		return (AEHMasterConfig) ConfigurationReader.writeXML(pathName, AEHMasterConfig.class, config);
	}

	public static String resolvePath() {

		ClassLoader classLoader = ConfigurationReader.class.getClassLoader();
		File file = new File(classLoader.getResource(CONFIG).getFile());
		System.out.println(file.getAbsolutePath());
		return file.getAbsolutePath();
	}
}
