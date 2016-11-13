var teamId = null;

$(document).ready(function() {
    assignAction();
});


function assignAction() {
	$("#btnAddTeam").click(function(e) {
    addTeam();
	});
}

function addTeam() {
	
	var team = getTeamParams();
	var method = "POST";
	var url ="/tricast-2016-sportsbook/services/teams";
	
	  sendAjax(method, url, JSON.stringify(team),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully saved");
	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getTeamParams(){
	var team ={};
	team.id = teamId;
	team.description = $('#addTeamId').val();
	
	return team;
}


