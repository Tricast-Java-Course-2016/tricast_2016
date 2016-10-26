package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public List<Event> getAll(Workspace workspace) throws SQLException, IOException {
		// TODO Auto-generated method stub
		List<Event> list = new ArrayList<Event>();
		list = this.eventDao.getAll(workspace);
		return list;
		
	}

	@Override
	public Event getById(Workspace workspace, long id) throws SQLException, IOException {
		Event event = this.eventDao.getById(workspace, id);
		return event;
	}

	@JdbcTransaction
	@Override
	public Long create(Workspace workspace, Event newItem) throws SQLException, IOException {
		Long l;
		l = this.eventDao.create(workspace, newItem);
		return l;
	}

	@JdbcTransaction
	@Override
	public Long update(Workspace workspace, Event updateItem) throws SQLException, IOException {
		Long l;
		l = this.eventDao.update(workspace, updateItem);
		return l;
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		Boolean b = false;
		b = this.eventDao.deleteById(workspace, Id);
		return b;
	}

}
