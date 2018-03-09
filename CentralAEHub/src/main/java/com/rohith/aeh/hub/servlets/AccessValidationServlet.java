package com.rohith.aeh.hub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.validators.AccessGrantValidator;
import com.rohith.aeh.hub.validators.RequestValidator;

@WebServlet("/access")
public class AccessValidationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RequestValidator requestValidator;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			if (requestValidator.validate(req)) {

				resp.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "true");

			} else {
				resp.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
				resp.setHeader(AEHubConstants.LOGIN_PAGE_REDIRECTION_URL, createURL(req));
			}
		} catch (AEHHubException e) {
			throw new ServletException("Exception occured while handling request",e);
		}

	}

	private String createURL(HttpServletRequest req) {
		StringBuilder builder = new StringBuilder(req.getScheme()).append("://").append(req.getServerName()).append(":")
				.append(req.getServerPort()).append(req.getContextPath()).append("/")
				.append("loginresource/ServerLogin.jsp");
		return builder.toString();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		this.requestValidator = new AccessGrantValidator();
	}
}
