package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Market;
import com.tricast.database.Workspace;
import com.tricast.web.response.MarketResponse;

public interface MarketManager {
	   List<Market> getAll(Workspace workspace) throws SQLException, IOException;

	   	Market getById(Workspace workspace, long id) throws SQLException, IOException;

	   	Market create(Workspace workspace, Market newMarket) throws SQLException, IOException;

	   	Market update(Workspace workspace, Market updateMarket) throws SQLException, IOException;

	    boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
	    
	    List<MarketResponse> getDetailsByEventId(Workspace workspace, long eventId) throws SQLException, IOException;
}
