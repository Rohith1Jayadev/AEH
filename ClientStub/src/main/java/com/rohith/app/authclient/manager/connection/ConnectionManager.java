package com.rohith.app.authclient.manager.connection;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.manager.AEHClientManager;

/**
 * 
 * <p>
 * The Connection Manager maintain a pool of HTTP connection that
 * 
 * enable the client to communicate with the server for executing GET and POST
 * request
 * 
 * 
 * <p>
 * Uses the Apache HTTP Client API underneath and creates a pooled connection
 * manager making it thread safe.
 * </p>
 * 
 * 
 * 
 * @author Accolite
 *
 */
public class ConnectionManager {

	private AEHClientManager manager;

	private PoolingHttpClientConnectionManager clientConnectionManager;

	private CloseableHttpClient httpClient;

	public ConnectionManager(AEHClientManager aehClientManager) {

		this.manager = aehClientManager;

		init();
	}

	/**
	 * Private Method which initializes the connection manager up on start up
	 * 
	 * <p>
	 * Creates a poolable HTTP Connection Manager
	 * </p>
	 * 
	 * 
	 * 
	 */
	private void init() {

		this.clientConnectionManager = new PoolingHttpClientConnectionManager();

		AEHMasterConfig config = this.manager.getMasterConfig();

		clientConnectionManager.setMaxTotal(config.getMaxTotalConnection());

		HttpHost localhost = new HttpHost(config.getMasterHost(), config.getMasterPort());

		clientConnectionManager.setMaxPerRoute(new HttpRoute(localhost), config.getMaxConnectionPerRoute());

		this.httpClient = HttpClients.custom().setConnectionManager(this.clientConnectionManager).build();

	}

	/**
	 * API for getting the client
	 * 
	 * @return
	 */
	public CloseableHttpClient getClient() {

		return this.httpClient;
	}

	/**
	 * Creating a client context for each client
	 * 
	 * @return
	 */
	public HttpClientContext getClientContext() {

		return HttpClientContext.create();

	}

	/**
	 * API for executing a HTTP get request
	 * 
	 * @param getRequest
	 * @param context
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse executeGetRequest(HttpGet getRequest, HttpClientContext context)
			throws ClientProtocolException, IOException {

		return this.httpClient.execute(getRequest, context);
	}

	/**
	 * API for executing a HTTP Post Request
	 * 
	 * @param postRequest
	 * @param context
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse executePostRequest(HttpPost postRequest, HttpClientContext context)
			throws ClientProtocolException, IOException {

		return this.httpClient.execute(postRequest, context);
	}

	/**
	 * API for closing HTTP response
	 * 
	 * @param response
	 * @throws IOException
	 */
	public void closeResponse(CloseableHttpResponse response) throws IOException {

		if (null != response) {
			response.close();
		}

	}

	/**
	 * API for destroying the connection manager
	 */
	public void destroy() {

		if (null != clientConnectionManager) {
			this.clientConnectionManager.closeExpiredConnections();
			this.clientConnectionManager.close();
		}
	}

}
