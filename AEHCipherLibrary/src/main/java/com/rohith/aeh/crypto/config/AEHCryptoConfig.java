package com.rohith.aeh.crypto.config;

public class AEHCryptoConfig {

	private String encryptionAlgorthim;

	private String transformationMode;

	private String paddingScheme;

	private int keySize;

	private String className;

	public String getEncryptionAlgorthim() {
		return encryptionAlgorthim;
	}

	public void setEncryptionAlgorthim(String encryptionAlgorthim) {
		this.encryptionAlgorthim = encryptionAlgorthim;
	}

	public String getTransformationMode() {
		return transformationMode;
	}

	public void setTransformationMode(String transformationMode) {
		this.transformationMode = transformationMode;
	}

	public String getPaddingScheme() {
		return paddingScheme;
	}

	public void setPaddingScheme(String paddingScheme) {
		this.paddingScheme = paddingScheme;
	}

	public int getKeySize() {
		return keySize;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isValid() {

		if (null == this.encryptionAlgorthim || null == this.transformationMode || null == paddingScheme
				|| keySize <= 0) {

			return false;
		}

		return true;
	}

}
