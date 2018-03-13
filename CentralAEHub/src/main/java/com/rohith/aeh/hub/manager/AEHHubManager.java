package com.rohith.aeh.hub.manager;

import javax.xml.bind.JAXBException;

import com.rohith.aeh.hub.encryption.EncryptionConfigurationReader;
import com.rohith.aeh.hub.encryption.EncryptionHubManager;
import com.rohith.aeh.hub.encryption.HubEncryptionConfig;

public class AEHHubManager {

	
	private static final AEHHubManager manager = new AEHHubManager();

	private HubEncryptionConfig encryptionConfig;

	private EncryptionHubManager encryptionHubManager;

	private AEHHubManager() {
		init();
	}

	public static AEHHubManager getManager() {

		return manager;
	}

	public HubEncryptionConfig getEncryptionConfig() {
		return this.encryptionConfig;
	}

	public EncryptionHubManager getEncrytionManager() {

		return this.encryptionHubManager;
	}

	private void init() {
		readEncryptionConfig();
		initializeEncryptionManager();
	}

	private void initializeEncryptionManager() {
		this.encryptionHubManager = new EncryptionHubManager(this);
	}

	private void readEncryptionConfig() {
		try {
			String path = EncryptionConfigurationReader.resolvePath();
			if (EncryptionConfigurationReader.isPresent(path)) {
				System.out.println("read");
				this.encryptionConfig = (HubEncryptionConfig) EncryptionConfigurationReader.readXML(path,
						HubEncryptionConfig.class);
			} else {
				this.encryptionConfig = EncryptionConfigurationReader.getDefaultConfig(path);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
