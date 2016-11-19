package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Event;
import com.tricast.database.Workspace;
import com.tricast.web.response.EventOpenResponse;
import com.tricast.web.response.EventResponse;

public interface EventDao {

	List<Event> getAll(Workspace workspace) throws SQLException, IOException;

	Event getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, Event newItem) throws SQLException, IOException;

	Long update(Workspace workspace, Event updateItem) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
	
	List<EventOpenResponse> getOpenEvents(Workspace workspace) throws SQLException, IOException;
	
	EventResponse getEventDetails(Workspace workspace, long eventId) throws SQLException, IOException;
}
