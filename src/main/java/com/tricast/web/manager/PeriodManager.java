package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Period;
import com.tricast.database.Workspace;
import com.tricast.web.response.PeriodTypeResponse;

public interface PeriodManager {

	List<Period> getAll(Workspace workspace) throws SQLException, IOException;

	Period getById(Workspace workspace, long id) throws SQLException, IOException;

	Period create(Workspace workspace, Period newPeriod) throws SQLException, IOException;

	Period update(Workspace workspace, Period updatePeriod) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
	
	List<PeriodTypeResponse> getAllPeriodType(Workspace workspace) throws SQLException, IOException;
}
