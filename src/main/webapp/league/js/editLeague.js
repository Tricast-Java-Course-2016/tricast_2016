var leagueId = null;

$(document).ready(function() {
	logInCheck();
	assignAction();
});


function assignAction() {
	$("#btnReplaceLeague").click(function(e) {		
		 replaceLeague();
		 alert("megynyomtad a replacet");
		 
	});
}


function replaceLeague() {
	
	var league = getLeagueParams();
	var method = "PUT";
	var url ="/tricast-2016-sportsbook/services/leagues";
	
	
	  sendAjax(method, url, JSON.stringify(league),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully replaced");
		  	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getLeagueParams(){
	var league ={};
	league.id = $('#replaceLeagueId').val();
	league.description = $('#replaceLeague').val();
	
	return league;
}
