//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {
    var events = getAllEvents();
}

function getAllEvents(teams, leagues, countries) {
    var url = "/tricast-2016-sportsbook/services/events/open";

    sendAjax("GET", url, null, function(data) {
        for (var i = 0; i < data.length; i++) {
        	
                $('#eventTable > tbody:last-child').append(
                		
                        '<tr id="'+ data[i].id +'"><td>'  + data[i].league + 
                        '</td><td>' + data[i].country + 
                        '</td><td>' + data[i].homeTeam + 
                        '</td><td>' + data[i].awayTeam + 
                        '</td><td>' + data[i].description + 
                        '</td><td>' + data[i].status + '</td></tr>');
                		
                		var eventid = data[i].id;
		            	$('#' + eventid).click(function() {
		            		window.location.href = "../bets/betPlacement.html?=" + eventid;
		            	});
		            	
        }
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

}
