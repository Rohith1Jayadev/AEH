package com.rohith.app.authclient.manager.requesthandlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;
import com.rohith.app.authclient.manager.connection.ConnectionManager;

/**
 * 
 * Base Class in the Request handling Hierarchy
 * 
 * @author Accolite
 *
 */
public abstract class RequestHandlerBase {

	private RequestType myResponsibilty;

	private AEHClientManager manager;

	public RequestHandlerBase(RequestType requestType, AEHClientManager manager) {

		this.myResponsibilty = requestType;

		this.manager = manager;
	}

	/**
	 * Check whether the current request is his responsibility
	 * 
	 * @param requestType
	 * @return
	 */
	public boolean checkResponsibility(RequestType requestType) {

		return (requestType == myResponsibilty);
	}

	/**
	 * Implemented based on the Concrete Implementation Type
	 * 
	 * @param param
	 * @throws AEHClientException
	 */
	public abstract void handleRequest(AEHClientParam param) throws AEHClientException;

	/**
	 * Returns the manager back
	 * 
	 * @return
	 */
	protected AEHClientManager getManager() {

		return this.manager;
	}

	protected CloseableHttpResponse sendGetRequest(HttpGet getRequest) throws ClientProtocolException, IOException {
		ConnectionManager connectionManager = getManager().getConnectionManager();
		HttpClientContext clientContext = connectionManager.getClientContext();
		return connectionManager.executeGetRequest(getRequest, clientContext);
	}
	protected CloseableHttpResponse sendPostRequest(HttpPost getRequest) throws ClientProtocolException, IOException {
		ConnectionManager connectionManager = getManager().getConnectionManager();
		HttpClientContext clientContext = connectionManager.getClientContext();
		return connectionManager.executePostRequest(getRequest, clientContext);
	}
	protected void sendErrorResponse(AEHClientParam param, String errorMessage) throws IOException{
		System.out.println(errorMessage);
		HttpServletResponse res = param.getResponse();
		res.setContentType("text");
		res.setStatus(400);
		PrintWriter writer = res.getWriter();
		writer.println(errorMessage);
		writer.flush();
	}
}
