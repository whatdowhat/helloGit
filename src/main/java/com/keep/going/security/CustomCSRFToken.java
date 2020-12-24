package com.keep.going.security;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCSRFToken {

	/**
	 * The token parameter name
	 */
	static final String CSRF_PARAM_NAME = "CSRFToken";
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCSRFToken.class);

	/**
	 * The location on the session which stores the token
	 */
	private final static String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CustomCSRFToken.class.getName() + ".tokenval";

	
	public static String getTokenForSession (HttpSession session) {
		String token = null;
		// I cannot allow more than one token on a session - in the case of two requests trying to
		// init the token concurrently
		synchronized (session) {
			token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
			LOGGER.debug("CustomCSRFToken 1//"+token);
			if (null==token) {
				token=UUID.randomUUID().toString();
				session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
				LOGGER.debug("CustomCSRFToken 2//"+CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
			}
		}
		return token;
	}

	/**
	 * Extracts the token value from the session
	 * @param request
	 * @return
	 */
	public static String getTokenFromRequest(HttpServletRequest request) {
		LOGGER.debug("CustomCSRFToken 3//"+request.getParameter(CSRF_PARAM_NAME));
		return request.getParameter(CSRF_PARAM_NAME);
	}

	public CustomCSRFToken() {};
}
