package com.tricast.web.filters;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.auth0.jwt.JWTExpiredException;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import com.tricast.web.exceptions.TokenException;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter, ResourceFilter {

	private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// Get the HTTP Authorization header from the request
        String authorizationHeader = 
            request.getHeaderValue(HttpHeaders.AUTHORIZATION);   
       
        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || authorizationHeader.isEmpty() || authorizationHeader.equals("null")) {
        	throw new TokenException("Please log in!");
        }

        // Validate the token    
        validateToken(authorizationHeader);
			
        return request;   
    }

    private void validateToken(String token) throws WebApplicationException {   	
    	try {
    	    final JWTVerifier verifier = new JWTVerifier(AuthenticationSettings.SECRET_KEY);
    	    final Map<String, Object> claims= verifier.verify(token);	    
    	} catch (JWTExpiredException e) {       		
    		throw new TokenException("Expired token"); 
    	} catch (JWTVerifyException e) {
    		log.error("Invalid token", e);
    		throw new TokenException(); 
    	} catch (Exception e) {
    		log.error("Error at token validation", e);
    		throw new TokenException(); 
    	}
    }

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return null;
	}
}


