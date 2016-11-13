var leagueId = null;

$(document).ready(function() {
    assignAction();
});


function assignAction() {
	$("#btnAddLeague").click(function(e) {
    addLeague();
	});
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
	league.description = $('#addLeagueId').val();
	
	return league;
}

