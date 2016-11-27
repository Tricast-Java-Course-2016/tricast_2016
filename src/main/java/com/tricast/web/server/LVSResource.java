package com.tricast.web.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public abstract class LVSResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    HttpHeaders headers;
    @Context
    HttpServletRequest httpServletRequest;

    public static final String DATASOURCE_HEADER = "X-LVS-Datasource";
    public static final String PRICE_FORMAT_HEADER = "X-LVS-PriceFormat";
    public static final String MAC_ADDRESS_HEADER = "X-LVS-MacAddress";
    public static final String FORCEFEED_DATASOURCE = "forcefeed";
    public static final String DB_DATASOURCE = "database";
    public static final String REQUEST_REF_HEADER = "X-LVS-RequestRef";

    // Unless we need to vary our cache requirements at runtime, we construct a static final CacheControl instance that
    // can be used in all our responses.

    public LVSResource() {
    }

    public LVSResource(LVSResource parent) {
        this();
        this.uriInfo = parent.uriInfo;
        this.request = parent.request;
        this.headers = parent.headers;
        this.httpServletRequest = parent.httpServletRequest;
    }

    public Locale getLocale() {
        Locale locale = headers.getAcceptableLanguages().get(0);
        if (locale.getLanguage().equalsIgnoreCase("*")) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public Response respondGet(Object entity) {
        ResponseBuilder responseBuilder = createResponseBuilder(entity);
        return responseBuilder.build();
    }

    public Response respondGet(Object entity, int responseCode) {
        ResponseBuilder responseBuilder = Response.status(responseCode);
        responseBuilder.entity(entity);
        return responseBuilder.build();
    }

    public Response respondGet(Object entity, Map<String, ?> headersMap) {

        ResponseBuilder responseBuilder = createResponseBuilder(entity);
        // add headers
        for (String name : headersMap.keySet()) {
            responseBuilder.header(name, headersMap.get(name));
        }
        return responseBuilder.build();

    }

    public Response respondGetNotOK(Object entity, Map<String, Object> headersMap, int responseCode) {
        ResponseBuilder responseBuilder = Response.status(responseCode);
        responseBuilder.entity(entity);

        // add headers
        for (String name : headersMap.keySet()) {
            responseBuilder.header(name, headersMap.get(name));
        }
        return responseBuilder.build();
    }

    private ResponseBuilder createResponseBuilder(Object entity) {
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        ResponseBuilder responseBuilder = null;

        String entityTagString;
        entityTagString = Integer.toString(entity.hashCode());

        EntityTag tag = new EntityTag(entityTagString);

        responseBuilder = request.evaluatePreconditions(tag);
        if (responseBuilder == null) {
            // Preconditions not met!
            responseBuilder = Response.ok(entity);
        }
        responseBuilder.tag(tag);

        return responseBuilder;

    }

    public Response respondPost(Object entity, String location, Map<String, Object> headersMap) {
        ResponseBuilder responseBuilder = createPostResponseBuilder(entity, location, 200);
        return respond(responseBuilder, headersMap);
    }

    public Response respondPost() {
        return respondPost(null);
    }

    public Response respondPost(Map<String, Object> headersMap) {
        return respond(Response.noContent(), headersMap);
    }

    private Response respond(ResponseBuilder responseBuilder, Map<String, Object> headersMap) {
    	if (headersMap != null) {
			for (String name : headersMap.keySet()) {
				responseBuilder.header(name, headersMap.get(name));
			}
		}
        return responseBuilder.build();
    }

    public Response respondPost(Object entity, String location) {
        return respondPost(entity, location, 200);
    }

    public Response respondPost(Object entity, String location, int statusCode) {
        ResponseBuilder responseBuilder = createPostResponseBuilder(entity, location, statusCode);
        return respond(responseBuilder, (Map<String, Object>) null);
    }

    private ResponseBuilder createPostResponseBuilder(Object entity, String location, int statusCode) {
        ResponseBuilder responseBuilder = null;

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        ub.path(location);

        responseBuilder = Response.status(statusCode).location(ub.build());
        responseBuilder.entity(entity);

        return responseBuilder;
    }

    public Response respondPut() {
        return Response.noContent().build();
    }

    public Response respondPut(Object entity) {
        return Response.ok(entity).build();
    }

    public Response respondPut(Object entity, int responseCode) {
        ResponseBuilder responseBuilder = Response.status(responseCode);
        responseBuilder.entity(entity);

        return responseBuilder.build();
    }

    public Response respondPutNotOK(Object entity, Map<String, Object> headersMap, int responseCode) {
        ResponseBuilder responseBuilder = Response.status(responseCode);
        responseBuilder.entity(entity);

        // add headers
        for (String name : headersMap.keySet()) {
            responseBuilder.header(name, headersMap.get(name));
        }
        return responseBuilder.build();
    }

    public Response respondDelete() {
        return Response.noContent().build();
    }

    public Response respondDelete(Map<String, Object> headersMap) {
        return respond(Response.noContent(), headersMap);
    }

    public Response respondDelete(Object entity) {
        return Response.ok(entity).build();
    }

    public Response respondDelete(Object entity, Map<String, Object> headersMap) {
        return respond(Response.ok(entity), headersMap);
    }

    public Response respondDeleteNotOK(Object entity, Map<String, Object> headersMap, int responseCode) {
        ResponseBuilder responseBuilder = Response.status(responseCode);
        responseBuilder.entity(entity);

        // add headers
        for (String name : headersMap.keySet()) {
            responseBuilder.header(name, headersMap.get(name));
        }
        return responseBuilder.build();
    }

    protected long[] convertToArray(String csvString) {
        if (csvString == null) {
            return new long[0];
        }
        String[] strings = csvString.split(",");

        List<Long> list = converToList(strings);

        // Then convert to an array (toArray does not work with primitives)
        long[] array = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public List<Long> converToList(String[] strings) {
        // Filter the value using a list
        List<Long> list = new ArrayList<Long>();
        for (String string : strings) {
            if (string.trim().length() > 0) {
                list.add(Long.parseLong(string));
            }
        }
        return list;
    }

    protected List<Long> convertToList(String csvString) {

        // Filter the value using a list
        List<Long> list = new ArrayList<Long>();
        if (csvString != null && !csvString.isEmpty()) {
            String[] strings = csvString.split(",");
            for (String string : strings) {
                if (string != null && string.trim().length() > 0) {
                    list.add(Long.parseLong(string));
                }
            }
        }

        return list;
    }

    protected String getDatasource() {
        String datasource = headers.getRequestHeaders().getFirst(DATASOURCE_HEADER);
        if (datasource == null) {
            return FORCEFEED_DATASOURCE;
        }
        if (datasource.equalsIgnoreCase(DB_DATASOURCE)) {
            return DB_DATASOURCE;
        } else {
            return FORCEFEED_DATASOURCE;
        }
    }

    protected String getIpAddress() {
        return httpServletRequest.getRemoteAddr();
    }

    protected String getRequestRef() {
        return headers.getRequestHeaders().getFirst(REQUEST_REF_HEADER);
    }

}
