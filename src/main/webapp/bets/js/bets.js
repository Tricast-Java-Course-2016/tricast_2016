var currBalance = 0.0;
var loadedMarkets;
var loadedPeriods;
var accountId;

$(document).ready(function() {
	logInCheck();
	assignAction();
});

function assignAction() {
	getAllData();
	
	if(accountId !== undefined && accountId !== null && accountId !== "null") {
		
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
	$("#outcomeList").attr("hidden", "hidden");
	
	$("#outcomeMessage").html("");
	$("#wdwMarketOdds > tbody").html("");
	$("#ouMarketOdds > tbody").html("");
	$("#correctScoreMarketOdds > tbody").html("");
	$("#doubleChanceMarketOdds > tbody").html("");
	

	var isMarketAvailable = false;
	
	// iterate through all the markets
	for(var i = 0; i < loadedMarkets.length; i++) {
		
		// we display only the markets that are in the previously chosen period
		if(loadedMarkets[i].periodTypeId == periodId) {
			
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
									' <input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case '2':	//awayTeamWin
								outcome2 = '<td>' + 
									loadedMarkets[i].outcomes[j].description + 
									' <input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case 'X':	//draw
								outcomeX = '<td>' + 
									loadedMarkets[i].outcomes[j].description + 
									' <input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
						}
						
					}
					
					$('#wdwMarketOdds > tbody:last-child').append(
							'<tr>' + outcome1 + outcomeX + outcome2 + '</tr>'				
					);
					
					isMarketAvailable = true;
					
					break; 
					
				case 2: // Total Goals O/U 2.5
					var over, under;
					
					// get all the outcomes for O/U markets
					for(var j = 0; j < (loadedMarkets[i].outcomes).length; j++){
						
						// what is the outcome code here?
						switch(loadedMarkets[i].outcomes[j].outcomeCode) {
					
							case 'O':	//Over 2,5
								outcome1 = '<td>' + 
									'<input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case 'U':	//Under 2,5
								outcome2 = '<td>' + 
								'<input type="radio" name="outcomes" value="outcome' + 
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
						}
						
					}
					
					$('#ouMarketOdds > tbody:last-child').append(
							'<tr>' + outcome1 + outcome2 + '</tr>'				
					);
					
					isMarketAvailable = true;
					
					break; 
					
				case 3: // Correct score
					
					// get all the outcomes for the correct score markets
					for(var j = 0; j < (loadedMarkets[i].outcomes).length; j++){
						
						// here the outcome code is the match result e.g. '3-0'
						var outcomeText = loadedMarkets[i].outcomes[j].outcomeCode;
						
						// but there is an Other option, where there is no correct score there
						if (loadedMarkets[i].outcomes[j].outcomeCode == 'O') {
							outcomeText = "Other";
						}
						
						$('#correctScoreMarketOdds > tbody:last-child').append(
								'<tr><td>'+ outcomeText + 
								'</td><td><input type="radio" name="outcomes" value="outcome' +
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td></tr>'				
						);
					}
					
					isMarketAvailable = true;
					
					break; 
					
				case 4: // Double Chance
					
					var outcome1X, outcome2X, outcome12;
					
					// get all the outcomes for the Double Chance markets
					for(var j = 0; j < (loadedMarkets[i].outcomes).length; j++){
						
						// what is the outcome code here?
						switch(loadedMarkets[i].outcomes[j].outcomeCode) {
					
							case '1X':
								outcome1X = '<td>' + 
									'<input type="radio" name="outcomes" value="outcome' + 
									loadedMarkets[i].outcomes[j].outcomeId + '">' + 
									loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case '2X':
								outcome2X = '<td>' + 
								'<input type="radio" name="outcomes" value="outcome' + 
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							case '12':
								outcome12 = '<td>' + 
								'<input type="radio" name="outcomes" value="outcome' + 
								loadedMarkets[i].outcomes[j].outcomeId + '">' + 
								loadedMarkets[i].outcomes[j].odds + '</td>';
								break;
							}

					}
					
					$('#doubleChanceMarketOdds > tbody:last-child').append(
							'<tr>' + outcome1X + outcome2X + outcome12 + '</tr>'				
					);
					
					isMarketAvailable = true;
					
					break; 
			}
		}	
	
	}
	
	// if there are no markets for this period, then show a message about it
	if(!isMarketAvailable) {
		$("#outcomeMessage").html("There are no available markets for this period.");
	} else {
		$("#outcomeList").removeAttr("hidden");
	}
	

}

function loadPeriods() {

	var s = "";
	
	for(var i = 0; i < loadedPeriods.length; i++){
		s += '<option value="'+ loadedPeriods[i].periodTypeId + 
			'">' + loadedPeriods[i].description +'</option>';    		
	}
	
	$("#periods").html(s);
		
}

function getAllData() {
	//get the actual event's id from the URL
	var params = parseQueryString();
	var eventId = params['event'];
	//get the account's id, who will place this bet
	accountId = params['account'];

	if(eventId == null) {
		//alert("=====DEBUG=====\nevent=EventId missing from the URL");
		return false;
	}
	
	if(accountId == null) {
		//alert("=====DEBUG=====\nevent=AccountId missing from the URL");
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
        
    	//periods list
    	loadedPeriods = data.periods;
    	loadPeriods();
    	
    	//markets and outcomes
    	loadedMarkets = data.markets;
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
    	
        if(textStatus == "success") {
        	alert("Your bet has been placed. Thank you!");
        	window.location.href = "../events/events.html?id=" + accountId;
        	
        } else {
        	
        	alert("Error while placing your bet.");
        }
        
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });
}

