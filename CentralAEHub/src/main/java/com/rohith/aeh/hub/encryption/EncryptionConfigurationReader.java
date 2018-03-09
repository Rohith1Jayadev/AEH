package com.rohith.aeh.hub.encryption;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class EncryptionConfigurationReader {

	public static final String CONFIG = "encryptionconfig.xml";

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

	public static HubEncryptionConfig getDefaultConfig(String pathName) throws JAXBException {
		HubEncryptionConfig config = new HubEncryptionConfig();
		config.setEncryptionAlgorthim("AES");
		config.setKeySize(256);
		config.setPaddingScheme("");
		config.setTransformationMode("");
		return (HubEncryptionConfig) EncryptionConfigurationReader.writeXML(pathName, HubEncryptionConfig.class, config);
	}

	public static String resolvePath() {

		ClassLoader classLoader = HubEncryptionConfig.class.getClassLoader();
		File file = new File(classLoader.getResource(CONFIG).getFile());
		System.out.println(file.getAbsolutePath());
		return file.getAbsolutePath();
	}

}
