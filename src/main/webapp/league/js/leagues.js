//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {
    var leagues = getAllLeagues();

}

function getAllLeagues() {

    var url = "/tricast-2016-sportsbook/services/leagues";
    var leagues = new Map();

    sendAjax("GET", url, null, function(data, textStatus, xhr) {
        for (var i = 0; i < data.length; i++) {
            leagues.set(data[i].id, data[i].description);
            
            $('#leagueTable > tbody:last-child').append(
                    '<tr><td>' +data[i].id+
                    '</td><td>' +data[i].description+
            		'</td></tr>');
        }

    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

    return leagues;
}