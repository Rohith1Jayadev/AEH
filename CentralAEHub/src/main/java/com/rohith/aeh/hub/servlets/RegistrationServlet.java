package com.rohith.aeh.hub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.servlets.constants.ClientContainer;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String parameter = req.getHeader(AEHubConstants.CLIENT_NAME_HEADER);
		if (null == parameter || "".equals(parameter)) {
			resp.sendError(400, AEHubConstants.CLIENT_NAME_NOT_VALID_ERROR);
		} else {
			ClientContainer container = ClientContainer.container();
			try {
				String clientSecret = container.addClient(parameter);
				if (null == container.addClient(parameter)) {
					resp.setHeader(AEHubConstants.CLIENT_REGISTRATION_STATUS_HEADER,
							AEHubConstants.REGISTRATION_FAILED);
				} else {
					resp.setHeader(AEHubConstants.CLIENT_REGISTRATION_STATUS_HEADER, AEHubConstants.REGISTRATION_DONE);
					resp.setHeader(AEHubConstants.CLIENT_SECRET_HEADER, clientSecret);
					resp.setStatus(200);
					resp.flushBuffer();
				}
			} catch (AEHHubException e) {
				throw new ServletException("Exception while fetching client secret", e);
			}
		}
	}

}
