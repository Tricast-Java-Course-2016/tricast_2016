package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Transaction;
import com.tricast.database.Workspace;

public interface TransactionDao {

    List<Transaction> getAll(Workspace workspace) throws SQLException, IOException;

    Transaction getById(Workspace workspace, long id) throws SQLException, IOException;

    Long create(Workspace workspace, Transaction newItem) throws SQLException, IOException;

    Long update(Workspace workspace, Transaction updateItem) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long id) throws SQLException, IOException;
    
    double getAmountByAccountId(Workspace workspace, long accountId) throws SQLException, IOException;
}
