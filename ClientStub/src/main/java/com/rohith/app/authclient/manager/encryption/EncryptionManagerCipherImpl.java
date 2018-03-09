package com.rohith.app.authclient.manager.encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.config.EncryptionConfig;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;

public class EncryptionManagerCipherImpl implements EncryptionManager {

	private final String DEFAULT_ENCRYPT_ALGO = "AES";

	private final String DEFAULT_PADDING_SCHEME = "PKCS5Padding";

	private final String DEFAULT_MODE_TRANSFORM = "CBC";

	private final int KEY_SIZE = 256;

	private AEHClientManager manager;

	EncryptionConfig encryptConfig;

	private Key cryptKey;

	public EncryptionManagerCipherImpl(AEHClientManager manager) throws NoSuchAlgorithmException {
		this.manager = manager;
		init();
	}
	
	public EncryptionManagerCipherImpl() throws NoSuchAlgorithmException{
		
			KeyGenerator keyGenerator = null;
	
			keyGenerator = KeyGenerator.getInstance(DEFAULT_ENCRYPT_ALGO);
			keyGenerator.init(KEY_SIZE);
			this.cryptKey = keyGenerator.generateKey();
	}
	
	/**
	 * Initializing method
	 * @throws NoSuchAlgorithmException
	 */
	private void init() throws NoSuchAlgorithmException {

		AEHMasterConfig config = this.manager.getMasterConfig();
		this.encryptConfig = config.getEncryptionConfig();
		KeyGenerator keyGenerator = null;
		if (null == encryptConfig || null == this.encryptConfig.getEncryptionAlgorthim()) {
			keyGenerator = KeyGenerator.getInstance(DEFAULT_ENCRYPT_ALGO);
			keyGenerator.init(KEY_SIZE);
		} else {
			keyGenerator = KeyGenerator.getInstance(this.encryptConfig.getEncryptionAlgorthim());
			if (encryptConfig.getKeySize() == 0) {
				keyGenerator.init(KEY_SIZE); // Key size
			} else {
				keyGenerator.init(encryptConfig.getKeySize());
			}
		}
		this.cryptKey = keyGenerator.generateKey();
	}

	public byte[] encrypt(byte[] value) throws AEHClientException {
		Cipher cipher = null;
		byte[] cipherBytes = null;
		try {
			cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, this.cryptKey);
			cipherBytes = cipher.doFinal(value);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new AEHClientException("Error occured while ecryting key", e);
		}
		return cipherBytes;
	}

	public byte[] decrypt(byte[] value) throws AEHClientException {

		Cipher cipher = null;
		byte[] decipheredBytes = null;
		try {
			cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, this.cryptKey);
			decipheredBytes = cipher.doFinal(value);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new AEHClientException("Error occured while ecryting key", e);
		}
		return decipheredBytes;
	}

	/**
	 * private function for creating a cipher instance
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	private Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {

		Cipher cipher = null;
		if (null == this.encryptConfig) {
			cipher = Cipher.getInstance(DEFAULT_ENCRYPT_ALGO);
		} else {
			cipher = Cipher.getInstance(encryptConfig.getEncryptionAlgorthim());

		}
		return cipher;
	}

}
