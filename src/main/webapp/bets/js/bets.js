//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();
});

function assignAction() {
	getAllData();
	
	// if we change period, we replace all the markets and outcomes with new ones
	// so, we have to uncheck every previously checked radio buttons
	$('select').on('change', function() {
		  $("input:radio").prop('checked', false);
		});
	
	//on submit
	$("#betForm").submit(function(e) {
        e.preventDefault();

        var stake = parseFloat($("#stake").val());
        var selectedOutcome = $('input[name=outcomes]:checked', '#betForm').val();
        
        if(!isNaN(stake) && stake > 0 && selectedOutcome != null) {
        	
            // which outcome has been selected?
            var outcomeId;
            alert("=====DEBUG=====\nStake: " + stake + 
            		"\nThis outcome has been checked" + selectedOutcome);
            
            //TODO get to know who's placing this bet?
            var accountId;
            
            //TODO finish this method below
            //placeBet(stake, outcomeId, accountId);
        
        } else {
        	alert("Error! You haven't selected any outcome or the stake is missing.");
        }
        
    });
        
}
	
function getAllData() {
	//get the actual event's id from the URL
	var eventId = location.search.split('event=')[1];
	
	if(eventId == null) {
		alert("=====DEBUG=====\nevent=EventId missing from the URL");
		return false;
	}
	
	//TODO fix this placeholder - create dao,manager,service with the usage of BetPlacementResponse
    var url = "/tricast-2016-sportsbook/services/events/" + eventId;

    sendAjax("GET", url, null, function(data) {
        //TODO balance calculation from Transactions
        $("#balance").html();
        
        //TODO replace IDs with actual names
        $("#country").html(data.countryId);
        $("#league").html(data.leagueId);
        $("#team1").html(data.homeTeamId);
        $("#team2").html(data.awayTeamId);
        
        //TODO generate radio button Outcome ids for easier outcome id detection later
        
        //TODO get all Periods and Outcomes for this event
        
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });
}


function placeBet(stake, outcomeId, accountId) {
	
	//TODO create service,manager,dao for placing bet
    var url = "/tricast-2016-sportsbook/services/bets/";

    var req = {};
    req.accountId = accountId;
    req.outcomeId = outcomeId;
    req.stake = stake;
    
    sendAjax("POST", url, JSON.stringify(req), function(data, textStatus, xhr) {
        
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });
}
