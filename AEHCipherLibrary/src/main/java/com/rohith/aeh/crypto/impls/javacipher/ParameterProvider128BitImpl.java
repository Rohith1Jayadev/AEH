package com.rohith.aeh.crypto.impls.javacipher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ParameterProvider128BitImpl implements ParameterProvider {

	private String initVector =  "abcdefghzyxwvuts";
	
	private String key = "SecretForEncodes";
	
	private String algo;
	
	public  ParameterProvider128BitImpl(String algo) {
		this.algo = algo;
	}
	
	@Override
	public IvParameterSpec getParamSpec() throws UnsupportedEncodingException {
	
		return new IvParameterSpec(initVector.getBytes("UTF-8"));
	}

	@Override
	public SecretKeySpec getKeySpec() throws UnsupportedEncodingException {
		
		return new SecretKeySpec(key.getBytes("UTF-8"), algo);
	}

	@Override
	public Cipher getCipher(String algo, String tranformMode, String padding) throws NoSuchAlgorithmException, NoSuchPaddingException {
		
		return Cipher.getInstance(algo+"/"+tranformMode+"/"+padding);
	}

	
	
}
