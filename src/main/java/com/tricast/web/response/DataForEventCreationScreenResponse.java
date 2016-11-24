package com.tricast.web.response;

import java.util.HashMap;
import java.util.Map;

public class DataForEventCreationScreenResponse {

    Map<Long, String> leagues = new HashMap<>();
    Map<Long, String> countries = new HashMap<>();
    Map<Long, String> teams = new HashMap<>();
    Map<Long, String> periods = new HashMap<>();

    public Map<Long, String> getLeagues() {
        return leagues;
    }

    public void setLeagues(Map<Long, String> leagues) {
        this.leagues = leagues;
    }

    public Map<Long, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<Long, String> countries) {
        this.countries = countries;
    }

    public Map<Long, String> getTeams() {
        return teams;
    }

    public void setTeams(Map<Long, String> teams) {
        this.teams = teams;
    }

    public Map<Long, String> getPeriods() {
        return periods;
    }

    public void setPeriods(Map<Long, String> periods) {
        this.periods = periods;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((countries == null) ? 0 : countries.hashCode());
        result = prime * result + ((leagues == null) ? 0 : leagues.hashCode());
        result = prime * result + ((periods == null) ? 0 : periods.hashCode());
        result = prime * result + ((teams == null) ? 0 : teams.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DataForEventCreationScreenResponse other = (DataForEventCreationScreenResponse) obj;
        if (countries == null) {
            if (other.countries != null) {
                return false;
            }
        } else if (!countries.equals(other.countries)) {
            return false;
        }
        if (leagues == null) {
            if (other.leagues != null) {
                return false;
            }
        } else if (!leagues.equals(other.leagues)) {
            return false;
        }
        if (periods == null) {
            if (other.periods != null) {
                return false;
            }
        } else if (!periods.equals(other.periods)) {
            return false;
        }
        if (teams == null) {
            if (other.teams != null) {
                return false;
            }
        } else if (!teams.equals(other.teams)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "DataForEventCreationScreenResponse [leagues=" + leagues + ", countries=" + countries + ", teams="
                + teams + ", periods=" + periods + "]";
    }


}
