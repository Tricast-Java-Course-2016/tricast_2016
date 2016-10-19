package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Event;
import com.tricast.database.Workspace;

public interface EventManager {

	List<Event> getAll(Workspace workspace) throws SQLException, IOException;

	Event getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, Event event) throws SQLException, IOException;

	Long update(Workspace workspace, Event event) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
