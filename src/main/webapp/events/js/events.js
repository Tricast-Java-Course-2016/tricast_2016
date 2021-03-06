//this method runs every time when the page is reloading
var accountId;

$(document).ready(function() {
	logInCheck();
    assignAction();
});

function assignAction() {

	accountId = document.URL.split("id=")[1];
	
	if (accountId !== undefined && accountId !== null && accountId !== "null") {
		var events = getAllEvents();
		
	    $("#homeBtn").click(function(e) {
	        e.preventDefault();

	            window.location.href = "/tricast-2016-sportsbook/account/playerhome.html?id=" + accountId;

	    });

	    $("#eventsBtn").click(function(e) {
	        e.preventDefault();

	        window.location.href = "/tricast-2016-sportsbook/events/events.html?id=" + accountId;
	    });

	    $("#transactionBtn").click(function(e) {
	        e.preventDefault();

	        window.location.href = "/tricast-2016-sportsbook/account/transactions.html?id=" + accountId;
	    });

	    $("#historyBtn").click(function(e) {
	        e.preventDefault();

	        // window.location.href = "/tricast-2016-sportsbook/<>.html?id=" + accountId;
	    });
		
    }
}

function getAllEvents() {
    var url = "/tricast-2016-sportsbook/services/events/open";

    sendAjax("GET", url, null, function(data) {
        for (var i = 0; i < data.length; i++) {

                $('#eventTable > tbody:last-child').append(
                		
                        '<tr id="'  + data[i].id +
                        '"><td>'    + data[i].league + 
                        '</td><td>' + data[i].country + 
                        '</td><td>' + data[i].homeTeam + 
                        '</td><td>' + data[i].awayTeam + 
                        '</td><td>' + data[i].description + 
                        '</td><td>' + data[i].startTime + '</td></tr>');
                		
		            	$('#' + data[i].id).click(function() {
		            		window.location.href = "../bets/betPlacement.html?event=" + this.id + "&account=" + accountId;
		            	});
		            	
        }
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

}
