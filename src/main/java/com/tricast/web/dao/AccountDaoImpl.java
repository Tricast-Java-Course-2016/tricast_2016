package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tricast.beans.Account;
import com.tricast.beans.AccountType;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class AccountDaoImpl implements AccountDao {

    private static final SqlManager sqlManager = SqlManager.getInstance();
    private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(AccountDao.class);

    @Override
    @JdbcTransaction
    public List<Account> getAll(Workspace workspace) throws SQLException, IOException {
        List<Account> result = new ArrayList<Account>();
        String sql = sqlManager.get("accountsGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(buildAccount(rs, false));
            }
        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        }
        return result;
    }

    private Account buildAccount(ResultSet rs, boolean isLogin) throws SQLException {
        Account account = new Account();
        int i = 1;

        account.setId(rs.getLong(i++));
        long typeId = rs.getLong(i++);
        account.setType(AccountType.getType(typeId));
        
        account.setUserName(rs.getString(i++));
        // TODO HASHING
        if (isLogin) {
            account.setPassword(rs.getString(i++));
        }
        account.setFirstName(rs.getString(i++));
        account.setLastName(rs.getString(i++));
        account.setDOB(rs.getString(i++));
        account.setAddress(rs.getString(i++));
        account.setEmailAddress(rs.getString(i++));
        account.setPhoneNumber(rs.getString(i++));
        account.setPIN(rs.getString(i++));
        account.setBankAccountNumber(rs.getString(i++));
        account.setBankCardNumber(rs.getString(i++));
        account.setCreatedDate(rs.getDate(i++));
        return account;
    }

    @Override
    @JdbcTransaction
    public Account getById(Workspace workspace, long id) throws SQLException, IOException {
        Account result = null;
        ResultSet rs = null;
        String sql = sqlManager.get("accountGetById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildAccount(rs, true);
            }
        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Account login(Workspace workspace, String username, String password) throws SQLException, IOException {
        Account result = null;
        ResultSet rs = null;
        String sql = sqlManager.get("accountLogin.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            int i = 1;
            ps.setString(i++, username);
            // TODO HASHING
            ps.setString(i++, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = buildAccount(rs, false);
            }
        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Long create(Workspace workspace, Account newItem) throws SQLException, IOException {
        Long result = null;
        ResultSet rs = null;
        String sql = sqlManager.get("accountCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getType().getTypeId());
            ps.setString(i++, newItem.getUserName());
            ps.setString(i++, newItem.getPassword());
            ps.setString(i++, newItem.getFirstName());
            ps.setString(i++, newItem.getLastName());
            ps.setString(i++, newItem.getDOB());
            ps.setString(i++, newItem.getAddress());
            ps.setString(i++, newItem.getEmailAddress());
            ps.setString(i++, newItem.getPhoneNumber());
            ps.setString(i++, newItem.getPIN());
            ps.setString(i++, newItem.getBankAccountNumber());
            ps.setString(i++, newItem.getBankCardNumber());
            ps.setDate(i++, newItem.getCreatedDate());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Long update(Workspace workspace, Account updateItem) throws SQLException, IOException {
        Long result = null;
        ResultSet rs = null;
        String sql = sqlManager.get("accountUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setString(i++, updateItem.getPassword());
            ps.setString(i++, updateItem.getFirstName());
            ps.setString(i++, updateItem.getLastName());
            ps.setString(i++, updateItem.getDOB());
            ps.setString(i++, updateItem.getAddress());
            ps.setString(i++, updateItem.getEmailAddress());
            ps.setString(i++, updateItem.getPhoneNumber());
            ps.setString(i++, updateItem.getPIN());
            ps.setString(i++, updateItem.getBankAccountNumber());
            ps.setString(i++, updateItem.getBankCardNumber());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        boolean result = false;
        String sql = sqlManager.get("accountDeleteById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            int i = 1;
            ps.setLong(i++, Id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        }
        return result;
    }
}
