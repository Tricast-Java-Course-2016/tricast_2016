package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tricast.beans.Period;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.response.PeriodTypeResponse;

public class PeriodDaoImpl implements PeriodDao {

    private static final Logger log = LogManager.getLogger(PeriodDaoImpl.class);
	 private static final SqlManager sqlManager = SqlManager.getInstance();

	@Override
	public List<Period> getAll(Workspace workspace) throws SQLException, IOException {
		List<Period> result = new ArrayList<Period>();

		String sql = sqlManager.get("periodGetAll.sql");

		try (PreparedStatement ps = workspace.getPreparedStatement(sql);ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				result.add(buildPeriod(rs));
			}
		} catch(SQLException ex) {
	    	log.error(ex,ex);
	    }
	    return result;
	}

	@Override
	@JdbcTransaction
	public Period getById(Workspace workspace, long id) throws SQLException, IOException {
		Period result = null;
		ResultSet rs = null;

		String sql = sqlManager.get("periodGetById.sql");

		try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
			 ps.setLong(1, id);
			 rs = ps.executeQuery();

			 if (rs.next()) {
	                result = buildPeriod(rs);
	            }
	        } catch (SQLException ex) {
	            log.error(ex, ex);
	            throw ex;
	        } finally {
	            rs.close();
	        }
	        return result;
	    }


	@Override
	@JdbcTransaction
	public Long create(Workspace workspace, Period newItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("periodCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	int i = 1;
            //ps.setLong(i++, newItem.getId());
            ps.setLong(i++, newItem.getEventId());
            ps.setLong(i++, newItem.getPeriodTypeId());
    		ps.setString(i++, newItem.getDescription());
    		ps.setLong(i++, newItem.getHomeTeamScore());
    		ps.setLong(i++, newItem.getAwayTeamScore());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
	}

	@Override
	@JdbcTransaction
	public Long update(Workspace workspace, Period updateItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("periodUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	int i = 1;

            ps.setLong(i++, updateItem.getEventId());
            ps.setLong(i++, updateItem.getPeriodTypeId());
    		ps.setString(i++, updateItem.getDescription());
    		ps.setLong(i++, updateItem.getHomeTeamScore());
    		ps.setLong(i++, updateItem.getAwayTeamScore());
    		ps.setLong(i++, updateItem.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
	}

	@Override
	@JdbcTransaction
	public boolean deleteById(Workspace workspace, long id) throws SQLException, IOException {
		boolean result = false;

        String sql = sqlManager.get("periodDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
	}

	private Period buildPeriod(ResultSet rs) throws SQLException {
		Period period = new Period();
		int i = 1;
		period.setId(rs.getLong(i++));
		period.setEventId(rs.getLong(i++));
		period.setPeriodTypeId(rs.getLong(i++));
		period.setDescription(rs.getString(i++));
		period.setHomeTeamScore((int)rs.getLong(i++));
		period.setAwayTeamScore((int)rs.getLong(i++));
		return period;
	}
	
	@Override
	@JdbcTransaction
	public List<PeriodTypeResponse> getAllPeriodType(Workspace workspace) throws SQLException, IOException {
		List<PeriodTypeResponse> result = new ArrayList<PeriodTypeResponse>();

		String sql = sqlManager.get("periodtypeGetAll.sql");

		try (PreparedStatement ps = workspace.getPreparedStatement(sql);ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				
				PeriodTypeResponse pt = new PeriodTypeResponse();
				
				int i = 1;
				
				pt.setPeriodTypeId(rs.getLong(i++));
				pt.setDescription(rs.getString(i++));
				
				result.add(pt);
			}
		} catch(SQLException ex) {
	    	log.error(ex,ex);
	    }
	    return result;
	}

}
