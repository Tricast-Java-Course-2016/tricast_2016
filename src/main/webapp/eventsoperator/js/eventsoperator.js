//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {
    var teams = getAllTeams();
    var leagues = getAllLeagues();
    var countries = getAllCountries();
    var events = getAllEvents(teams, leagues, countries);

}

function getAllTeams() {

    var url = "/tricast-2016-sportsbook/services/teams";
    var teams = new Map();

    sendAjax("GET", url, null, function(data, textStatus, xhr) {
        for (var i = 0; i < data.length; i++) {
            teams.set(data[i].id, data[i].description);
        }

    }, null);

    return teams;
}

function getAllLeagues() {

    var url = "/tricast-2016-sportsbook/services/leagues";
    var leagues = new Map();

    sendAjax("GET", url, null, function(data, textStatus, xhr) {
        for (var i = 0; i < data.length; i++) {
            leagues.set(data[i].id, data[i].description);
        }
    }, null);

    return leagues;
}

function getAllCountries() {

    var url = "/tricast-2016-sportsbook/services/countries";
    var countries = new Map();

    sendAjax("GET", url, null, function(data, textStatus, xhr) {
        for (var i = 0; i < data.length; i++) {
            countries.set(data[i].id, data[i].description);
        }
    }, null);

    return countries;
}

function getAllEvents(teams, leagues, countries) {
    var url = "/tricast-2016-sportsbook/services/events";

    sendAjax("GET", url, null, function(data) {
        for (var i = 0; i < data.length; i++) {

            if (data[i].status.trim() == "OPEN") {
                $('#eventTable > tbody:last-child').append(
                        '<tr><td>' + leagues.get(data[i].leagueId) + '</td><td>' + countries.get(data[i].countryId)
                                + '</td><td>' + teams.get(data[i].homeTeamId) + '</td><td>'
                                + teams.get(data[i].awayTeamId) + '</td><td>' + data[i].description + '</td><td>'
                                + data[i].status + '</td></tr>');
            }

        }
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

}
