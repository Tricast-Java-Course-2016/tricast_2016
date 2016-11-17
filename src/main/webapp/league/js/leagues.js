//this method runs every time when the page is reloading
var leagueId = null;
$(document).ready(function() {
    assignAction();
});

function assignAction() {
    var leagues = getAllLeagues();
    $("#btnAddLeague").click(function (e) {  	
    	addLeague();
    	alert("Megnyomtad az add-ot!");  	
    });

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



function goToEditLeague() {
	window.location.href = "/tricast-2016-sportsbook/league/editLeague.html";
}

function addLeague() {
	
	var league = getLeagueParams();
	var method = "POST";
	var url ="/tricast-2016-sportsbook/services/leagues";
	
	  sendAjax(method, url, JSON.stringify(league),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully saved");
	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getLeagueParams(){
	var league ={};
	league.id = leagueId;
	league.description = $('#inputLeague').val();
	
	return league;
}