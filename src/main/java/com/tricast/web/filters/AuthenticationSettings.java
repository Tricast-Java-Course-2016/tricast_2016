package com.tricast.web.filters;

public interface AuthenticationSettings {
	static final String SECRET_KEY = "verySecretKey";
	static final String ISSUER = "TricastTanf2016";
	static final long EXPIRY_TIME_IN_SEC = 60L*10L;
}
