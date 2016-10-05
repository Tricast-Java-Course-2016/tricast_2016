package com.tricast.guice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Provider;

public class ObjectMapperProvider implements Provider<ObjectMapper> {

	@Override
	public ObjectMapper get() {
		final ObjectMapper toReturn = new ObjectMapper();
		toReturn.enable(SerializationFeature.INDENT_OUTPUT); // This is the
																// important
																// setting
		return toReturn;
	}

}
