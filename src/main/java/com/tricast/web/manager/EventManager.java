package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Event;
import com.tricast.database.Workspace;
import com.tricast.web.response.EventOpenResponse;
import com.tricast.web.response.EventResponse;

public interface EventManager {

	List<EventResponse> getAll(Workspace workspace) throws SQLException, IOException;
	
	List<EventOpenResponse> getAllOpen(Workspace workspace) throws SQLException, IOException;

	Event getById(Workspace workspace, long id) throws SQLException, IOException;

	Event create(Workspace workspace, Event newEvent) throws SQLException, IOException;

	Event update(Workspace workspace, Event updateEvent) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
	
	EventResponse getEventDetails(Workspace workspace, long eventId) throws SQLException, IOException;
}
