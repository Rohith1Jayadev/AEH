package com.rohith.aeh.hub.encryption;

import com.rohith.aeh.hub.exception.AEHHubException;


public interface EncryptionHubManager {

	/**
	 * API for encrypting keys
	 * 
	 * @param value
	 * @return
	 * @throws AEHClientException
	 */
	public byte[] encrypt(byte[] value) throws AEHHubException;

	/**
	 * API for decrypting keys
	 * 
	 * @param value
	 * @return
	 * @throws AEHClientException
	 */
	public byte[] decrypt(byte[] value) throws AEHHubException;
}
