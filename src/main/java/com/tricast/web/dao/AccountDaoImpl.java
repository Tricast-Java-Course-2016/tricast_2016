package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tricast.beans.Account;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;

public class AccountDaoImpl implements AccountDao {

    private static final SqlManager sqlManager = SqlManager.getInstance();

    @Override
    public List<Account> getAll(Workspace workspace) throws SQLException, IOException {
        // TODO Auto-generated method stub

        List<Account> result = new ArrayList<Account>();

        String sql = sqlManager.get("accountsGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(buildAccount(rs, false));
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return result;

    }

    private Account buildAccount(ResultSet rs, boolean isLogin) throws SQLException {
        Account account = new Account();
        // TODO build the account object
        return account;
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
