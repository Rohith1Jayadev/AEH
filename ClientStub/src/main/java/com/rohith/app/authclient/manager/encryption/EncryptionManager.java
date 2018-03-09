package com.rohith.app.authclient.manager.encryption;

import com.rohith.app.authclient.exception.AEHClientException;

public interface EncryptionManager {
	
	/**
	 * API for encrypting keys
	 * 
	 * @param value
	 * @return
	 * @throws AEHClientException
	 */
	public byte[] encrypt(byte[] value) throws AEHClientException;
	
	/**
	 * API for decrypting keys
	 * 
	 * @param value
	 * @return
	 * @throws AEHClientException
	 */
	public byte[] decrypt(byte[] value) throws AEHClientException;
	
}
