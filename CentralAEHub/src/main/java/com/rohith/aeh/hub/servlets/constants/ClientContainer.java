package com.rohith.aeh.hub.servlets.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.rohith.aeh.hub.encryption.EncryptionHubManager;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.manager.AEHHubManager;

/**
 * Container Class for storing the client name against the client - id
 * 
 * @author Accolite
 *
 */
public final class ClientContainer {

	private static final ClientContainer container = new ClientContainer();

	public static final String CONTAINER = "CLIENT_CONTAINER";

	private Map<String, String> registeredClients;

	private final String CLIENT_NAME_PRE = "AUTHCLIENT";

	private AtomicInteger clientSequence;

	private ClientContainer() {
		this.registeredClients = new ConcurrentHashMap<String, String>();
		this.clientSequence = new AtomicInteger(1);
	}

	public static ClientContainer container() {
		return container;
	}

	/**
	 * API for adding clients
	 * 
	 * @param clientName
	 * @return
	 * @throws AEHHubException
	 */
	public String addClient(String clientName) throws AEHHubException {
		String secret = null;
		synchronized (clientName) {
			if (null == this.registeredClients.get(clientName)) {
				secret = getSecret();
				this.registeredClients.put(clientName, secret);
			} else {
				secret = this.registeredClients.get(clientName);
			}
		}
		return encryptAndSend(secret);
	}

	/**
	 * API for encrypting secret
	 * 
	 * @param secret
	 * @return
	 * @throws AEHHubException
	 */
	private String encryptAndSend(String secret) throws AEHHubException {

		return String.valueOf((secret.hashCode()));
	}

	/**
	 * API to check whether a client is present
	 * 
	 * @param clientSecret
	 * @param clientName
	 * @return
	 * @throws AEHHubException
	 */
	public boolean isPresent(String clientSecret, String clientName) throws AEHHubException {

		return ((this.registeredClients.get(clientName) != null)
				&& decryptAndCheck(clientSecret, this.registeredClients.get(clientName)));
	}

	private boolean decryptAndCheck(String received, String orig) throws AEHHubException {

		Integer decryptReceived = Integer.parseInt(received);

		if (decryptReceived.intValue() == orig.hashCode()) {

			return true;
		}

		return false;
	}

	private String getSecret() {

		int sequence = this.clientSequence.incrementAndGet();

		return CLIENT_NAME_PRE + sequence;

	}

}
