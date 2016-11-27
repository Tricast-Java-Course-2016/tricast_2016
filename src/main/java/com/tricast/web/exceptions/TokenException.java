package com.tricast.web.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class TokenException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public TokenException() {
		super(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build());
	}
	
	public TokenException(String message) {
		super(Response.status(Response.Status.UNAUTHORIZED).entity(message).build());
	}
}
