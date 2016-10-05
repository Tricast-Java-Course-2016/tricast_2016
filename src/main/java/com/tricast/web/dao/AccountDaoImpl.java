package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Account;
import com.tricast.database.Workspace;

public class AccountDaoImpl implements AccountDao {

    @Override
    public List<Account> getAll(Workspace workspace) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getById(Workspace workspace, long id) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account login(Workspace workspace, String username, String password) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long create(Workspace workspace, Account newItem) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long update(Workspace workspace, Account updateItem) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        // TODO Auto-generated method stub
        return false;
    }

}
