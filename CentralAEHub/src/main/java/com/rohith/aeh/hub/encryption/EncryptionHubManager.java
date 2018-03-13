package com.rohith.aeh.hub.encryption;

import com.rohith.aeh.crypto.AEHCipher;
import com.rohith.aeh.crypto.AEHCryptoManager;
import com.rohith.aeh.crypto.config.AEHCryptoConfig;
import com.rohith.aeh.crypto.exception.AEHCipherException;
import com.rohith.aeh.hub.manager.AEHHubManager;

public class EncryptionHubManager {

	private AEHHubManager manager;

	private AEHCryptoManager cryptoManager;

	public EncryptionHubManager(AEHHubManager manager) {
		this.manager = manager;
		try {
			init();
		} catch (AEHCipherException e) {
			e.printStackTrace();
		}
	}

	
	public AEHCipher getCipher(){
		return this.cryptoManager.cipher();
	}
	

	private void init() throws AEHCipherException {
		AEHCryptoConfig cryptoConfig = createCryptoConfig(this.manager.getEncryptionConfig());
		this.cryptoManager = new AEHCryptoManager(cryptoConfig);

	}
	private AEHCryptoConfig createCryptoConfig(HubEncryptionConfig hubEncryptionConfig) {
		AEHCryptoConfig config = new AEHCryptoConfig();
		config.setClassName(hubEncryptionConfig.getEncryptionClass());
		config.setEncryptionAlgorthim(hubEncryptionConfig.getEncryptionAlgorthim());
		config.setKeySize(hubEncryptionConfig.getKeySize());
		config.setPaddingScheme(hubEncryptionConfig.getPaddingScheme());
		config.setTransformationMode(hubEncryptionConfig.getTransformationMode());
		return config;
	}

}
