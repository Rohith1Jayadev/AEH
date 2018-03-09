package com.rohith.aeh.hub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rohith.aeh.hub.authentication.AEHAuthCallBack;
import com.rohith.aeh.hub.authentication.AutherizationCallBack;
import com.rohith.aeh.hub.authentication.GoogleAPIAutherizer;
import com.rohith.aeh.hub.authentication.RequestAutherizer;
import com.rohith.aeh.hub.authentication.SimpleFileAuthorizer;
import com.rohith.aeh.hub.authentication.dataobjects.AutherizationParam;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.validators.AuthGrantValidator;
import com.rohith.aeh.hub.validators.RequestValidator;

@WebServlet("/autherize")
public class AutherizationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RequestValidator requestValidator = null;

	private RequestAutherizer requestAutherizer = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			if (this.requestValidator.validate(req)) {

				this.requestAutherizer.autherize(getCallBack(req, resp));

			} else {

				resp.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");

			}
		} catch (AEHHubException e) {
			throw new ServletException("Exception Occured while handling request", e);
		}

	}

	private AEHAuthCallBack getCallBack(HttpServletRequest req, HttpServletResponse resp) {

		return new AutherizationCallBack(new AutherizationParam(req, resp));
	}

	@Override
	public void init() throws ServletException {

		super.init();
		
		this.requestValidator = new AuthGrantValidator();

		this.requestAutherizer = new SimpleFileAuthorizer();
	}
}
