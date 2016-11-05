package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Team;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.TeamDao;

public class TeamManagerImpl implements TeamManager {

	private final TeamDao teamDao;
	@Inject
	public TeamManagerImpl(TeamDao teamdao) {
		this.teamDao = teamdao ;
	}

    @JdbcTransaction
	@Override
	public List<Team> getAll(Workspace workspace) throws SQLException, IOException {
        return teamDao.getAll(workspace);

	}

	@Override
    @JdbcTransaction
	public Team getById(Workspace workspace, long id) throws SQLException, IOException {
		Team team;
        team = teamDao.getById(workspace, id);
		return team;
	}

	@JdbcTransaction
	@Override
    public Team create(Workspace workspace, Team newTeam) throws SQLException, IOException {

        Long id = this.teamDao.create(workspace, newTeam);
        if (id != null) {
            return teamDao.getById(workspace, id);
        } else {
            return null;
        }

	}

	@JdbcTransaction
	@Override
    public Team update(Workspace workspace, Team updateTeam) throws SQLException, IOException {
        Long id = teamDao.update(workspace, updateTeam);
        if (id != null) {
            return teamDao.getById(workspace, id);
        }
        else {
            return null;
        }
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        return teamDao.deleteById(workspace, Id);

	}
}
