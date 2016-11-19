package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Outcome;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.response.OutcomeResponse;

public class OutcomeManagerImpl implements OutcomeManager {
    private OutcomeDao outcomeDao;

    @Inject
    public OutcomeManagerImpl(OutcomeDao outcomeDao) {
        this.outcomeDao = outcomeDao;
    }

    @Override
    @JdbcTransaction
    public List<Outcome> getAll(Workspace workspace) throws SQLException, IOException {
        return outcomeDao.getAll(workspace);
    }

    @Override
    @JdbcTransaction
    public Outcome getById(Workspace workspace, long id) throws SQLException, IOException {
        Outcome outcome = outcomeDao.getById(workspace, id);
        return outcome;
    }

    @Override
    @JdbcTransaction
    public Outcome create(Workspace workspace, Outcome newOutcome) throws SQLException, IOException {

        Long id = outcomeDao.create(workspace, newOutcome);
        if (id != null) {
            return outcomeDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public Outcome update(Workspace workspace, Outcome updateOutcome) throws SQLException, IOException {
        Long id = outcomeDao.update(workspace, updateOutcome);
        if (id != null) {
            return outcomeDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        return outcomeDao.deleteById(workspace, Id);
    }
    
    @Override
    @JdbcTransaction
    public List<OutcomeResponse> getByMarketId(Workspace workspace, long marketId) throws SQLException, IOException {
        return outcomeDao.getByMarketId(workspace, marketId);
    }

}
