package com.rohith.aeh.hub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rohith.aeh.hub.authentication.AEHAuthCallBack;
import com.rohith.aeh.hub.authentication.AuthenticationCallBack;
import com.rohith.aeh.hub.authentication.GoogleAPIAuthenticator;
import com.rohith.aeh.hub.authentication.RequestAuthenticator;
import com.rohith.aeh.hub.authentication.SimpleFileAuthenticator;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationParam;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.validators.AuthenicationRequestVaidator;
import com.rohith.aeh.hub.validators.RequestValidator;

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RequestValidator validator;

	private RequestAuthenticator aunthenticator;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			if (validator.validate(req)) {
				this.aunthenticator.authenticate(getCallBack(req, resp));
			} else {
				resp.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");

			}
		} catch (AEHHubException e) {
			throw new ServletException("Exception Occured while handling request", e);
		}

	}

	/**
	 * Default gives a simple call back but needs to do more about this
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	private AEHAuthCallBack getCallBack(HttpServletRequest req, HttpServletResponse resp) {

		return new AuthenticationCallBack(new AuthenticationParam(req, resp));
	}

	@Override
	public void init() throws ServletException {

		super.init();

		this.validator = new AuthenicationRequestVaidator();

		this.aunthenticator = new SimpleFileAuthenticator();
	}

}
