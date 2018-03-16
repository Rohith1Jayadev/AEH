package com.rohith.aeh.crypto;

import com.rohith.aeh.crypto.config.AEHCryptoConfig;
import com.rohith.aeh.crypto.exception.AEHCipherException;

public class AEHCryptoManager {

	private AEHCryptoConfig config;

	private AEHCipher cipher;

	public AEHCryptoManager(AEHCryptoConfig config) throws AEHCipherException {
		this.config = config;
		init();
	}
	private void init() throws AEHCipherException {
		if (null == config) {
			throw new AEHCipherException("AEHCipher Config cannot be null");
		}
		if (!this.config.isValid()) {
			throw new AEHCipherException("AEHCipher Config cannot be null");
		}
		try {
			this.cipher = (AEHCipher) Class.forName(config.getClassName()).newInstance();
			this.cipher.init(config);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new AEHCipherException("Cannot Instantiate the AEH Cipher ", e);
		}
	}
	
	
	public AEHCipher cipher(){
		
		return this.cipher;
	}

}
