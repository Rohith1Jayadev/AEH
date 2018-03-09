package com.rohith.aeh.hub.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.manager.AEHHubManager;

public class AEHHubEncryptionImpl implements EncryptionHubManager {

	private final String DEFAULT_ENCRYPT_ALGO = "AES";

	private final String DEFAULT_PADDING_SCHEME = "PKCS5Padding";

	private final String DEFAULT_MODE_TRANSFORM = "CBC";

	private final int KEY_SIZE = 128;

	private AEHHubManager manager;

	private Key cryptKey;

	public AEHHubEncryptionImpl(AEHHubManager manager) {
		this.manager = manager;
		try {
			init();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public byte[] encrypt(byte[] value) throws AEHHubException {
		Cipher cipher = null;
		byte[] cipherBytes = null;
		try {
			cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, this.cryptKey);
			cipherBytes = cipher.doFinal(value);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new AEHHubException("Error occured while ecryting key", e);
		}
		return cipherBytes;
	}

	public byte[] decrypt(byte[] value) throws AEHHubException {
		Cipher cipher = null;
		byte[] decipheredBytes = null;
		try {
			cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, this.cryptKey);
			decipheredBytes = cipher.doFinal(value);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new AEHHubException("Error occured while ecryting key", e);
		}
		return decipheredBytes;
	}

	private Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {

		Cipher cipher = null;
		HubEncryptionConfig encryptionConfig = this.manager.getEncryptionConfig();
		if (null == encryptionConfig) {
			cipher = Cipher
					.getInstance(DEFAULT_ENCRYPT_ALGO);
		} else {
			cipher = Cipher
					.getInstance(DEFAULT_ENCRYPT_ALGO);
		}
		return cipher;
	}

	private void init() throws NoSuchAlgorithmException {
		HubEncryptionConfig config = this.manager.getEncryptionConfig();
		KeyGenerator keyGenerator = null;
		if (null == config || null == config.getEncryptionAlgorthim()) {
			keyGenerator = KeyGenerator.getInstance(DEFAULT_ENCRYPT_ALGO);
			keyGenerator.init(KEY_SIZE);
		} else {
			keyGenerator = KeyGenerator.getInstance(config.getEncryptionAlgorthim());
			if (config.getKeySize() == 0) {
				keyGenerator.init(KEY_SIZE); // Key size
			} else {
				keyGenerator.init(128);
			}
		}
		this.cryptKey = keyGenerator.generateKey();
	}

}
