package com.rohith.aeh.hub.encryption;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HubEncryptionConfig {

	private String encryptionAlgorthim;
	
	private String transformationMode;
	
	private String paddingScheme;
	
	private int keySize;
	
	public HubEncryptionConfig(){
		
	}
	
	public String getEncryptionAlgorthim() {
		return encryptionAlgorthim;
	}
	
	@XmlElement
	public void setEncryptionAlgorthim(String encryptionAlgorthim) {
		this.encryptionAlgorthim = encryptionAlgorthim;
	}

	public String getTransformationMode() {
		return transformationMode;
	}
	
	@XmlElement
	public void setTransformationMode(String transformationMode) {
		this.transformationMode = transformationMode;
	}

	public String getPaddingScheme() {
		return paddingScheme;
	}
	
	@XmlElement
	public void setPaddingScheme(String paddingScheme) {
		this.paddingScheme = paddingScheme;
	}

	public int getKeySize() {
		return keySize;
	}
	
	@XmlElement
	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}


	
}
