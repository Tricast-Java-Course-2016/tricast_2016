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

		private static final Logger log = LogManager.getLogger(BetDataService.class);

	    private final CountryManager manager;

	    private Workspace workspace;

	    @Inject
		public CountryService(CountryManager manager, Workspace workspace) {
			super();
			this.manager = manager;
			this.workspace = workspace;
		}
	    
	    @GET
	    @Produces(APPLICATION_JSON)
	    public Response getAll() throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get all betdata");
	        try {
	            return respondGet(manager.getAll(workspace));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }
	    
	    @GET
	    @Path("{country}")
	    @Produces(APPLICATION_JSON)
	    public Response getById(@PathParam("country") long countryId, @PathParam("outcomeId") long outcomeId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Requested to get countryData countryId = " + countryId);
	        try {
	            return respondGet(manager.getById(workspace,countryId));
	        } catch (SQLException ex) {
	            return respondGet(ex.getMessage(), 500);
	        }
	    }

	    @POST
	    @Produces(APPLICATION_JSON)
	    @Consumes(APPLICATION_JSON)
	    public Response createCountry(Country newCountry) throws OutOfTransactionException, IOException {
	        log.trace("Trying to create new betdata with this betId #" + newCountry.getId() + "and this outcomeId #"
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
	    public Response updateBetData(Country updateCountry) throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to update betdata with this countryId #" + updateCountry.getId() + "and this countryDescription #"
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
	    public Response deleteCountryData(@PathParam("countryId") long countryId, @PathParam("outcomeId") long outcomeId)
	            throws SQLException, OutOfTransactionException, IOException {
	        log.trace("Trying to delete countrydata with this countryId #" + countryId + "and this outcomeId #" + outcomeId);
	        try {
	            return respondDelete(manager.deleteById(workspace, countryId));
	        } catch (SQLException ex) {
	            return respondDeleteNotOK(ex.getMessage(), null, 500);
	        }
	    }

	    
	    
}
