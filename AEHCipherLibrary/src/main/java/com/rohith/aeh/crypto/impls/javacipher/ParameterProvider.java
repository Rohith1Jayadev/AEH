package com.rohith.aeh.crypto.impls.javacipher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public interface ParameterProvider {

	public IvParameterSpec getParamSpec() throws UnsupportedEncodingException;

	public SecretKeySpec getKeySpec() throws UnsupportedEncodingException;

	public Cipher getCipher(String algo, String tranform, String padding)
			throws NoSuchAlgorithmException, NoSuchPaddingException;

}
