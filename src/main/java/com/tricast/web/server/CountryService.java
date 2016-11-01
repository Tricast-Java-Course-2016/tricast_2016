package com.tricast.web.server;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tricast.beans.Country;
import com.tricast.database.Workspace;
import com.tricast.guice.OutOfTransactionException;
import com.tricast.web.manager.CountryManager;

@Path("/countries")
public class CountryService extends LVSResource {

		private static final Logger log = LogManager.getLogger(CountryService.class);

	    private final CountryManager manager;

	    private Workspace workspace;

	    @Inject
    public CountryService(CountryManager manager, Workspace workspace) {
			this.manager = manager;
			this.workspace = workspace;
		}

	    @GET
	    @Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all Countries");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @GET
	    @Path("{id}")
	    @Produces(APPLICATION_JSON)
	    public Response getById(@PathParam("id") long id)throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get countryData countryId = " + id);
	        try {
	            return respondGet(manager.getById(workspace,id));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createCountry(Country newCountry) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create this country #"
	                + newCountry.getDescription());
	        try {
	            return respondPost(manager.create(workspace, newCountry), "\\countries");
	        } catch (SQLException ex) {
	            return respondPost(ex.getMessage(), "\\countries", 500);
	        }
	    }

	    @PUT
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response updateCountry(Country updateCountry) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to update Country with this countryId #" + updateCountry.getId() + "and this countryDescription #"
	                + updateCountry.getDescription());
	        try {
	            return respondPut(manager.update(workspace, updateCountry));
	        } catch (SQLException ex) {
	            return respondPut(ex.getMessage(), 500);
	        }
	    }

	    @DELETE
	    @Path("/{countryId}")
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response deleteCountry(@PathParam("countryId") long countryId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete countrydata with this countryId #" + countryId);
	        try {
	            return respondDelete(manager.deleteById(workspace, countryId));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }



}
