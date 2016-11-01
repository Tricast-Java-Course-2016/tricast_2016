package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tricast.beans.Country;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class CountryDaoImpl implements CountryDao {

	private static final SqlManager sqlManager = SqlManager.getInstance();
	private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(CountryDao.class);

	@Override
	@JdbcTransaction
	public List<Country> getAll(Workspace workspace) throws SQLException, IOException {
		// TODO Auto-generated method stub
		List<Country> result = new ArrayList<Country>();
		String sql = sqlManager.get("countryGetAll.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql);ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				result.add(buildCountry(rs));
			}
		} catch(SQLException ex) {
	    	logger.error(ex,ex);
	    	throw ex;
	    }
	    return result;
	}

	@JdbcTransaction
	private Country buildCountry(ResultSet rs) throws SQLException {
		Country country = new Country();
		int c = 1;
		country.setId(rs.getLong(c++));
		country.setDescription(rs.getString(c++));
		return country;
	}

	@Override
	@JdbcTransaction
	public Country getById(Workspace workspace, long id) throws SQLException, IOException {
		Country result = null;
		ResultSet rs = null;
		
		String sql = sqlManager.get("countryGetById.sql");
		
		try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
			 ps.setLong(1, id);
	         rs = ps.executeQuery();
	         
	        if (rs.next()) { 
			result = buildCountry(rs);
	        }
		}
		catch(SQLException ex) {
		   logger.error(ex,ex);
		   throw ex;
		}
		finally {
            rs.close();
        }

		return result ;
	}


	@Override
	@JdbcTransaction
	public Long create(Workspace workspace, Country newItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("countryCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
          
            ps.setString(i++, newItem.getDescription());
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
    public Long update(Workspace workspace, Country updateItem) throws SQLException, IOException {

        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("countryUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setString(i++, updateItem.getDescription());
            ps.setLong(i++, updateItem.getId());
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
		// TODO Auto-generated method stub

		boolean result = false;

        String sql = sqlManager.get("countryDelete.sql");

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
