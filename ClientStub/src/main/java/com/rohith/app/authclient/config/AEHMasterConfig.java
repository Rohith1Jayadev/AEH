package com.rohith.app.authclient.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AEHMasterConfig {

	public AEHMasterConfig(){
	
	}
	
	private String masterHost;
	
	private int masterPort;
	
	private String masterUrl;
	
	private String masterRegisterUrl;
	
	private String clientSecret;
	
	private int maxTotalConnection;
	
	private int maxConnectionPerRoute;

	private String masterAuthenticationUrl;
	
	private String masterAutherizatonUrl;
	
	private EncryptionConfig encryptionConfig;

	public String getMasterHost() {
		return masterHost;
	}

	@XmlElement
	public void setMasterHost(String masterHost) {
		this.masterHost = masterHost;
	}

	public int getMasterPort() {
		return masterPort;
	}
	
	@XmlElement
	public void setMasterPort(int masterPort) {
		this.masterPort = masterPort;
	}

	public String getMasterUrl() {
		return masterUrl;
	}

	@XmlElement
	public void setMasterUrl(String masterUrl) {
		this.masterUrl = masterUrl;
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	@XmlElement
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	
	public int getMaxTotalConnection() {
		return maxTotalConnection;
	}
	
	@XmlElement
	public void setMaxTotalConnection(int maxTotalConnection) {
		this.maxTotalConnection = maxTotalConnection;
	}

	public int getMaxConnectionPerRoute() {
		return maxConnectionPerRoute;
	}
	
	@XmlElement
	public void setMaxConnectionPerRoute(int maxConnectionPerRoute) {
		this.maxConnectionPerRoute = maxConnectionPerRoute;
	}

	
	public String getMasterAuthenticationUrl() {
		return masterAuthenticationUrl;
	}
	
	@XmlElement
	public void setMasterAuthenticationUrl(String masterAuthenticationUrl) {
		this.masterAuthenticationUrl = masterAuthenticationUrl;
	}

	public String getMasterRegisterUrl() {
		return masterRegisterUrl;
	}
	
	@XmlElement
	public void setMasterRegisterUrl(String masterRegisterUrl) {
		this.masterRegisterUrl = masterRegisterUrl;
	}

	public EncryptionConfig getEncryptionConfig() {
		return encryptionConfig;
	}
	
	@XmlElement
	public void setEncryptionConfig(EncryptionConfig encryptionConfig) {
		this.encryptionConfig = encryptionConfig;
	}
	public String getMasterAutherizatonUrl() {
		return masterAutherizatonUrl;
	}
	@XmlElement
	public void setMasterAutherizatonUrl(String masterAutherizatonUrl) {
		this.masterAutherizatonUrl = masterAutherizatonUrl;
	}


	
}
