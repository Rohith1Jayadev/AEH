package com.rohith.aeh.crypto;

import com.rohith.aeh.crypto.config.AEHCryptoConfig;
import com.rohith.aeh.crypto.exception.AEHCipherException;

public interface AEHCipher {

	public String cipher(String plainString) throws AEHCipherException;

	public String decipher(String cryptedString) throws AEHCipherException;

	public void init(AEHCryptoConfig config);

}
