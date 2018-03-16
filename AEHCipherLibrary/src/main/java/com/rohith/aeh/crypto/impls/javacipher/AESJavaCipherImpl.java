package com.rohith.aeh.crypto.impls.javacipher;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.rohith.aeh.crypto.AEHCipher;
import com.rohith.aeh.crypto.config.AEHCryptoConfig;
import com.rohith.aeh.crypto.exception.AEHCipherException;

public class AESJavaCipherImpl implements AEHCipher {

	private AEHCryptoConfig config;

	private ParameterProvider provider;

	public AESJavaCipherImpl() {

	}

	public String cipher(String value) throws AEHCipherException {

		try {
			IvParameterSpec iv = this.provider.getParamSpec();
			SecretKeySpec skeySpec = this.provider.getKeySpec();
			Cipher cipher = this.provider.getCipher(this.config.getEncryptionAlgorthim(),
					this.config.getTransformationMode(), this.config.getPaddingScheme());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			throw new AEHCipherException("Error while ciphering", e);
		}
	}

	public String decipher(String value) throws AEHCipherException {
		try {
			IvParameterSpec iv = this.provider.getParamSpec();
			SecretKeySpec skeySpec = this.provider.getKeySpec();
			Cipher cipher = this.provider.getCipher(this.config.getEncryptionAlgorthim(),
					this.config.getTransformationMode(), this.config.getPaddingScheme());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(value));
			return new String(original);
		} catch (Exception e) {
			throw new AEHCipherException("Error while deciphering", e);
		}

	}

	@Override
	public void init(AEHCryptoConfig config) {
		this.config = config;
		initialize();
	}

	private void initialize() {
		switch (config.getKeySize()) {
		case 128:
			this.provider = new ParameterProvider128BitImpl(config.getEncryptionAlgorthim());
			break;
		case 256:
			this.provider = new ParameterProvider256BitImpl(config.getEncryptionAlgorthim());
			break;
		default:
			this.provider = new ParameterProvider128BitImpl(config.getEncryptionAlgorthim());
			break;
		}
	}
}
