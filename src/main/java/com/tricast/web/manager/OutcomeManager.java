package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Outcome;
import com.tricast.database.Workspace;

public interface OutcomeManager {
    List<Outcome> getAll(Workspace workspace) throws SQLException, IOException;

    Outcome getById(Workspace workspace, long id) throws SQLException, IOException;

    Outcome create(Workspace workspace, Outcome newOutcome) throws SQLException, IOException;

    Outcome update(Workspace workspace, Outcome updateOutcome) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
