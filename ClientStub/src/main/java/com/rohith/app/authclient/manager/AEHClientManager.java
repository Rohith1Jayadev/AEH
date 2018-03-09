package com.rohith.app.authclient.manager;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBException;

import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.config.ConfigurationReader;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.connection.ConnectionManager;
import com.rohith.app.authclient.manager.encryption.EncryptionManager;
import com.rohith.app.authclient.manager.encryption.EncryptionManagerCipherImpl;

public class AEHClientManager {

	private AuthClient client;

	private static final AEHClientManager manager = new AEHClientManager();

	private AEHMasterConfig masterConfig;

	private ConnectionManager connectionManager;
	
	private EncryptionManager encryptionManager;
	
	private AEHClientManager() {

		readConfig();
		
		this.client = new SimpleAuthClient(this);

		this.connectionManager = new ConnectionManager(this);
		
		try {
			this.encryptionManager = new EncryptionManagerCipherImpl(this);
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
	}

		


	public static AEHClientManager getManager(){
		
		return manager;
		
	}
	
	public AuthClient client(){
		
		return this.client;
	}
	
	private void readConfig() {

		try {

			String path = ConfigurationReader.resolvePath();
			
			if (ConfigurationReader.isPresent(path)) {
				System.out.println("read");
				this.masterConfig = (AEHMasterConfig) ConfigurationReader.readXML(path,
						AEHMasterConfig.class);
			} else {
     
				this.masterConfig = ConfigurationReader.getDefaultConfig(path);
			}
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}

	}
	
    public AEHMasterConfig getMasterConfig(){
    	
    	return this.masterConfig;
    }

	public void destroy() {
	
		this.connectionManager.destroy();
	}
	
	
	public ConnectionManager getConnectionManager(){
		
		return this.connectionManager;
	}


    
    
}
