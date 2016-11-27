var teamId = null;

$(document).ready(function() {
	logInCheck();
	assignAction();
});


function assignAction() {
	$("#btnreplaceTeam").click(function(e) {
		 replaceTeam();
		 alert("megynyomtad a replacet");
	});
}


function replaceTeam() {
	
	var team = getTeamParams();
	var method = "PUT";
	var url ="/tricast-2016-sportsbook/services/teams";
	
	  sendAjax(method, url, JSON.stringify(team),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully replaced");
		  	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getTeamParams(){
	var team ={};
	team.id = $('#replaceTeamId').val();
	team.description = $('#replaceTeam').val();
	
	return team;
}


