//this method runs every time when the page is reloading
var accountId = null;
$(document).ready(function() {
    var url = document.URL;
    accountId = url.split("id=")[1];
    if (accountId !== null) {
        displayAccountInfo();
    }
    assignActions();

});

function assignActions() {

    $("#updateBtn").click(function(e) {
        e.preventDefault();

        if (accountId !== null) {
            window.location.href = "/tricast-2016-sportsbook/account/changeinfo.html?id=" + accountId;
        }

    });

    $("#eventListBtn").click(function(e) {
        e.preventDefault();

        window.location.href = "/tricast-2016-sportsbook/eventsoperator/eventsoperator.html?id=" + accountId;
    });
    $("#periodsBtn").click(function(e) {
        e.preventDefault();

        // window.location.href = "/tricast-2016-sportsbook/<>.html?id=" + accountId;
    });
    $("#countriesBtn").click(function(e) {
        e.preventDefault();

        window.location.href = "/tricast-2016-sportsbook/country/countries.html?id=" + accountId;
    });
    $("#leaguesBtn").click(function(e) {
        e.preventDefault();

        window.location.href = "/tricast-2016-sportsbook/league/leagues.html?id=" + accountId;
    });
    $("#teamsBtn").click(function(e) {
        e.preventDefault();

        window.location.href = "/tricast-2016-sportsbook/team/teams.html?id=" + accountId;
    });

}

function displayAccountInfo() {
    var url = "/tricast-2016-sportsbook/services/accounts/" + accountId;
    sendAjax("GET", url, null, function(data) {
        $("#welcomeUser").html("Hi " + data.firstName + "!");
    }, null);
}
