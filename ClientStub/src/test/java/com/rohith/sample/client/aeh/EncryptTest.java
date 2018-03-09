package com.rohith.sample.client.aeh;

import java.security.NoSuchAlgorithmException;

import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.encryption.EncryptionManager;
import com.rohith.app.authclient.manager.encryption.EncryptionManagerCipherImpl;

public class EncryptTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, AEHClientException {
		
		EncryptionManager manager = new EncryptionManagerCipherImpl();
	
		for(int i=0;i<3;i++){
			
			new MyThread(manager, "entor"+i).start();
		}
	}
	
	private static class MyThread extends Thread{
		
		private EncryptionManager manager;
		private String val;
		public MyThread(EncryptionManager manager, String val){
			
			this.manager = manager;
			this.val=val;
		}
		public void run(){
			
			
			byte[] encrypt;
			try {
				encrypt = manager.encrypt(val.getBytes());
				System.out.println(new String(manager.decrypt(encrypt)));
			} catch (AEHClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}
