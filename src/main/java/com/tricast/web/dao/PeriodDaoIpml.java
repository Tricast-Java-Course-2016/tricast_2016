package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.tricast.beans.Period;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class PeriodDaoIpml implements PeriodDao {

	private static final SqlManager sqlManager = SqlManager.getInstance();
	private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(CountryDao.class);
	@Override
	public List<Period> getAll(Workspace workspace) throws SQLException, IOException {
		// TODO Auto-generated method stub
		List<Period> result = new ArrayList<Period>();
		String sql = sqlManager.get("periodGetAll.sql");
		try (PreparedStatement preparedStatement = workspace.getPreparedStatement(sql);ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				result.add(buildPeriod(rs));
			}
		} catch(SQLException ex) {
	    	logger.error(ex,ex);
	    }
	    return result;
	}

	private Period buildPeriod(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Period period = new Period();
		int c = 1;
		period.setId(rs.getLong(c++));
		period.setEventId(rs.getLong(c++));
		period.setPeriodTypeId(rs.getLong(c++));
		period.setDescription(rs.getString(c++));
		period.setHomeTeamScore((int)rs.getLong(c++));
		period.setAwayTeamScore((int)rs.getLong(c++));
		return period;
	}

	@JdbcTransaction
	@Override
	public Period getById(Workspace workspace, long id) throws SQLException, IOException {
		// TODO Auto-generated method stub
		Period result = null;
		
		String sql = sqlManager.get("periodGetById.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql) ; ResultSet rs = ps.executeQuery()) {
			result = buildPeriod(rs);
		}
		catch(SQLException ex) {
		   logger.error(ex,ex);
		}	
		return result ;
	}

	@JdbcTransaction
	@Override
	public Long create(Workspace workspace, Period period) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("periodCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	int c = 1;
            ps.setLong(c++, period.getId());
            ps.setLong(c++, period.getEventId());
            ps.setLong(c++, period.getPeriodTypeId());
    		ps.setString(c++, period.getDescription());
    		ps.setLong(c++, period.getHomeTeamScore());
    		ps.setLong(c++, period.getAwayTeamScore());
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

	@JdbcTransaction
	@Override
	public Long update(Workspace workspace, Period period) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("periodUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	int c = 1;
            ps.setLong(c++, period.getId());
            ps.setLong(c++, period.getEventId());
            ps.setLong(c++, period.getPeriodTypeId());
    		ps.setString(c++, period.getDescription());
    		ps.setLong(c++, period.getHomeTeamScore());
    		ps.setLong(c++, period.getAwayTeamScore());
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
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		
		boolean result = false;
        String sql = sqlManager.get("perioddelete.sql");
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
