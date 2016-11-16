var teamId = null;

$(document).ready(function() {
    assignAction();
});

function assignAction() {
	
    var teams = getAllTeams();
    
    $("#btnAddTeam").click(function(e) {
        addTeam();
    	});
    
}

function getAllTeams() {
	
	var url = "/tricast-2016-sportsbook/services/teams";
    var teams = new Map();

sendAjax("GET", url, null, function(data, textStatus, xhr) {
        for (var i = 0; i < data.length; i++) {
            teams.set(data[i].id, data[i].description);
            
            $('#teamTable > tbody:last-child').append(
                    '<tr><td>' +data[i].id+
                    '</td><td>' +data[i].description+
                    '</td></tr>');
        }
        $('.el').hide();
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

return teams;
}

function addTeam() {
	
	var addteam = getTeamParams();
	var method = "POST";
	var url ="/tricast-2016-sportsbook/services/teams";
	
	  sendAjax(method, url, JSON.stringify(addteam),
		  function(data, textStatus, xhr ) {
		  	alert("Succesfully saved");
	
	    }, function(xhr) {
	        var errormsg = getErrorMsg(xhr);
	        alert(errormsg);
	    });
}

function getTeamParams(){
	var addteam ={};
	addteam.id = teamId;
	addteam.description = $('#inputTeam').val();
	
	return addteam;
}

function goToEditTeam() {
	window.location.href = "/tricast-2016-sportsbook/team/editTeam.html";
}









