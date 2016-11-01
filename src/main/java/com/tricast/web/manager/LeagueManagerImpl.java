package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.League;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.LeagueDao;

public class LeagueManagerImpl implements LeagueManager {
	private final LeagueDao leagueDao;
	
	@Inject
	public LeagueManagerImpl(LeagueDao leagueDao) {
		this.leagueDao = leagueDao;
	}
	
	@Override
	@JdbcTransaction
	public List<League> getAll(Workspace workspace) throws SQLException, IOException {
		 return leagueDao.getAll(workspace);
	}

	@Override
	@JdbcTransaction
	public League getById(Workspace workspace, long id) throws SQLException, IOException {
		League league = leagueDao.getById(workspace, id);
		return league;
	}

	@JdbcTransaction
	@Override
	public League create(Workspace workspace, League newLeague) throws SQLException, IOException {
		Long id = leagueDao.create(workspace, newLeague);
		if (id != null) {
			return leagueDao.getById(workspace, id);
		} else {
			return null;
		}
	}

	@JdbcTransaction
	@Override
	public League update(Workspace workspace, League updateLeague) throws SQLException, IOException {
		Long id = leagueDao.update(workspace, updateLeague);
        if (id != null) {
            return leagueDao.getById(workspace, id);
        } else {
            return null;
        }
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		return leagueDao.deleteById(workspace, Id);
		
	}

}
