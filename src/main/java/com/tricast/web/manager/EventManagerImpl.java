package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Event;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.EventDao;

public class EventManagerImpl implements EventManager {
	public EventDao eventDao;
	
	@Inject
	public void EvenetManagerImpl(EventDao eventDao) {
		this.eventDao = eventDao;
	}
	
	@Override
    @JdbcTransaction
	public List<Event> getAll(Workspace workspace) throws SQLException, IOException {
		 return eventDao.getAll(workspace);
		
	}

	@Override
    @JdbcTransaction
	public Event getById(Workspace workspace, long id) throws SQLException, IOException {
		Event event =  eventDao.getById(workspace, id);
		return event;
	}

	@JdbcTransaction
	@Override
	public Event create(Workspace workspace, Event newEvent) throws SQLException, IOException {
		Long id = eventDao.create(workspace, newEvent);
		if (id != null) {
			return eventDao.getById(workspace, id);
		} else {
			return null;
		}
	}

	@JdbcTransaction
	@Override
	public Event update(Workspace workspace, Event updateEvent) throws SQLException, IOException {
		Long id = eventDao.update(workspace, updateEvent);
        if (id != null) {
            return eventDao.getById(workspace, id);
        } else {
            return null;
        }
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		return eventDao.deleteById(workspace, Id);
	}

}
