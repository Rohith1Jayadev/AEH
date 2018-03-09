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

public class ConnectionManager {

	private AEHClientManager manager;

	private PoolingHttpClientConnectionManager clientConnectionManager;

	private CloseableHttpClient httpClient;
	
	
	public ConnectionManager(AEHClientManager aehClientManager) {

		this.manager = aehClientManager;

		init();
	}

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
	public HttpClientContext getClientContext(){
		
		return HttpClientContext.create();

	}

	
	/**
	 * API for executing a http get request
	 * 
	 * @param getRequest
	 * @param context
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse executeGetRequest(HttpGet getRequest, HttpClientContext context) throws ClientProtocolException, IOException{
		
		
		return this.httpClient.execute(
				getRequest, context);
	}
	
	public CloseableHttpResponse executePostRequest(HttpPost postRequest, HttpClientContext context) throws ClientProtocolException, IOException{
		
		
		return this.httpClient.execute(
				postRequest, context);
	}
	
	
	
	public void destroy() {

		if (null != clientConnectionManager) {
			this.clientConnectionManager.closeExpiredConnections();
			this.clientConnectionManager.close();
		}
	}

}
