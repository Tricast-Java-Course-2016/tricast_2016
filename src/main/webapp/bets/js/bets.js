var currBalance = 0.0;
var loadedMarkets;
var accountId;

$(document).ready(function() {
	assignAction();
});

function assignAction() {
	getAllData();
	
	// if we change period, we replace all the markets and outcomes with new ones
	// so, we have to uncheck every previously checked radio buttons
	$('select').on('change', function() {
		  $("input:radio").prop('checked', false);
		  loadMarkets(($('select').prop('selectedIndex') + 1));
		});
	
	//on submit
	$("#betForm").submit(function(e) {
        e.preventDefault();

        var stake = parseFloat($("#stake").val());
        var selectedOutcome = $('input[name=outcomes]:checked', '#betForm').val();
        
        if(!isNaN(stake) && stake > 0 && selectedOutcome != null) {
        	if(stake > currBalance) {
        		alert("Hey, you don't have enough money to place this bet :" + stake);
        		return false;
        	}
            // which outcome has been selected?
            var outcomeId = selectedOutcome.split("e")[1];
            
            // SINGLE bet
            var betTypeId = 1; 
            
            placeBet(stake, outcomeId, betTypeId);
        
        } else {
        	alert("Error! You haven't selected any outcome or the stake is missing.");
        }
        
    });
        
}

var parseQueryString = function() {

    var str = window.location.search;
    var objURL = {};

    str.replace(
        new RegExp( "([^?=&]+)(=([^&]*))?", "g" ),
        function( $0, $1, $2, $3 ){
            objURL[ $1 ] = $3;
        }
    );
    return objURL;
};

function loadMarkets(periodId) {
	$("#wdwMarketOdds > tbody").html("");
	$("#ouMarketOdds > tbody").html("");
	$("#correctScoreMarketOdds > tbody").html("");
	$("#doubleChanceMarketOdds > tbody").html("");
	
	// iterate through all the markets
	for(var i = 0; i < loadedMarkets.length; i++){
		
		// we display only the markets that are in the previously chosen period
		if(loadedMarkets[i].periodId == periodId) {
			
			// different types of markets
			switch(loadedMarkets[i].marketTypeId) {
				
				case 1: // Win/Draw/Win
					var outcome1, outcome2, outcomeX;
					
					// get all the outcomes for WDW markets
					for(var j = 0; j < (loadedMarkets[i].outcomes).length; j++){
						
						// what is the outcome code here?
						switch(loadedMarkets[i].outcomes[j].outcomeCode) {
					
							case '1':	//homeTeamWin
								outcome1 = '<td>' + 
									loadedMarkets[i].outcomes[j].description + 
									'<input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case '2':	//awayTeamWin
								outcome2 = '<td>' + 
								loadedMarkets[i].outcomes[j].description + 
								'<input type="radio" name="outcomes" value="outcome' + 
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case 'X':	//draw
								outcomeX = '<td>' + 
								loadedMarkets[i].outcomes[j].description + 
								'<input type="radio" name="outcomes" value="outcome' + 
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
						}
						
					}
					
					$('#wdwMarketOdds > tbody:last-child').append(
							'<tr>' + outcome1 + outcome2 + outcomeX + '</tr>'				
					);
					
					break; 
					
				case 2: // Total Goals O/U 2.5
					var over, under;
					
					// get all the outcomes for O/U markets
					for(var j = 0; j < (loadedMarkets[i].outcomes).length; j++){
						
						// what is the outcome code here?
						switch(loadedMarkets[i].outcomes[j].outcomeCode) {
					
							case 'O':	//Over 2,5
								outcome1 = '<td>' + 
									loadedMarkets[i].outcomes[j].description + 
									'<input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case 'U':	//Under 2,5
								outcome2 = '<td>' + 
								loadedMarkets[i].outcomes[j].description + 
								'<input type="radio" name="outcomes" value="outcome' + 
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
						}
						
					}
					
					$('#ouMarketOdds > tbody:last-child').append(
							'<tr>' + outcome1 + outcome2 + '</tr>'				
					);
					
					break; 
				case 3: // Correct score
					
					// get all the outcomes for the correct score markets
					for(var j = 0; j < (loadedMarkets[i].outcomes).length; j++){
						
						$('#correctScoreMarketOdds > tbody:last-child').append(
								'<tr><td>'+
								loadedMarkets[i].outcomes[j].outcomeCode + 
								'</td><td><input type="radio" name="outcomes" value="outcome' +
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td></tr>'				
						);
					}
					break; 
				case 4: // Double Chance
					// TODO make this if neccessary
					
					break; 
			}
			
		}	
	
	}

}

function getAllData() {
	//get the actual event's id from the URL
	var params = parseQueryString();
	var eventId = params['event'];
	//get the account's id, who will place this bet
	accountId = params['account'];

	if(eventId == null) {
		alert("=====DEBUG=====\nevent=EventId missing from the URL");
		return false;
	}
	
	if(accountId == null) {
		alert("=====DEBUG=====\nevent=AccountId missing from the URL");
		return false;
	}
	
    var url = "/tricast-2016-sportsbook/services/bets/details/" + eventId + "/" + accountId;

    sendAjax("GET", url, null, function(data) {
    	
    	//periods
    	$("#balance").html(data.balance + " HUF");
    	currBalance = data.balance;
    	$("#country").html(data.countryDescription);
    	$("#league").html(data.leagueDescription);
    	$("#team1").html(data.homeTeamDescription);
    	$("#team2").html(data.awayTeamDescription);
    	$("#date").html(data.eventStartDate);
        
    	var s = "";
    	for(var i = 0; i < data.periodDescription.length; i++){
    		s += '<option value="'+ i + '">' + data.periodDescription[i] +'</option>';    		
    	}
    	$("#periods").html(s);
    	
    	//markets and outcomes
    	
    	loadedMarkets=data.markets;
    	loadMarkets(1);
    	
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });
}


function placeBet(stake, outcomeId, betTypeId) {
	
	var url = "/tricast-2016-sportsbook/services/bets/new";

    var req = {};
    req.stake = stake;
    req.outcomeId = outcomeId;
    req.accountId = accountId;    
    req.betTypeId = betTypeId;
    
    sendAjax("POST", url, JSON.stringify(req), function(data, textStatus, xhr) {
        alert(textStatus);
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });
}

