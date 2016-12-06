//this method runs every time when the page is reloading
$(document).ready(function() {
	logInCheck();
    assignAction();

});

function assignAction() {
    var events = getAllEvents();
    getIndex();
    $('#addEventButton').on('click',function() {
    	addNewEvent();
    });
    

}
var tomb = [];
function getAllEvents(teams, leagues, countries, periods) {
    var url = "/tricast-2016-sportsbook/services/events/all";

    sendAjax(
            "GET",
            url,
            null,
            function(data) {
                for (var i = 0; i < data.length; i++) {
                    tomb[i] = data[i].periodId;
                    $('#eventTable > tbody:last-child')
                            .append(

                                    '<tr id="'
                                            + data[i].id
                                            + '"><td>'
                                            + data[i].id
                                            + '</td><td>'
                                            + data[i].league
                                            + '</td><td>'
                                            + data[i].country
                                            + '</td><td>'
                                            + data[i].homeTeam
                                            + '</td><td>'
                                            + data[i].awayTeam
                                            + '</td><td>'
                                            + data[i].description
                                            + '</td><td>'
                                            + data[i].period
                                            + '</td><td>'
                                            + data[i].status
                                            + '</td><td class="periodResult">'
                                            + data[i].result
                                            + '</td><td><button id="edit" class="btn btn-info"  data-toggle="modal" data-target="#editEventModal">Edit result</button>'
                                            + '</td></tr>');

                }

            }, function(xhr) {
                var errormsg = getErrorMsg(xhr);
                alert(errormsg);
            });

}

function getIndex() {
    var row;
    $('table').on('click', '#edit', function() {
        row = this.parentNode.parentNode.rowIndex;
    });

    $('#save').on('click', function() {

        var hteam = $('#hteam').val();
        var ateam = $('#ateam').val();
        getPeriodParams(row, hteam, ateam);

    });
}

function getPeriodParams(row, hteam, ateam) {
    var period = {};

    var url = "/tricast-2016-sportsbook/services/periods/" + tomb[row - 1];

    sendAjax("GET", url, null, function(data) {
        // alert("Success get");

        data.homeTeamScore = hteam;
        data.awayTeamScore = ateam;
        var url2 = "/tricast-2016-sportsbook/services/periods/";
        sendAjax("PUT", url2, JSON.stringify(data), function(data, textStatus, xhr) {
            // alert("Successfully saved");
            var selector = '#eventTable tr:eq(' + row + ') .periodResult';
            $(selector).html(hteam + " : " + ateam);
        }, function(xhr) {
            var errormsg2 = getErrorMsg(xhr);
            alert(errormsg2);
        });
    }, function(xhr) {
        var errormsg = getErrorMsg(xhr);
        alert(errormsg);
    });

    return 1;
}


function addNewEvent() {
	populateListsToNewEvent();
	$('#btnSave').on('click',function() {
		saveNewEvent();
	});
}

function populateListsToNewEvent() {
	var url = "/tricast-2016-sportsbook/services/events/eventcreationdata";

    sendAjax(
            "GET",
            url,
            null,
            function(data) {
            	var list = $("#leaguesList");
            	$.each(data.leagues, function(index, item) {
            	  list.append(new Option(item,index));
            	});  
            	 	
            	list = $("#countriesList");
            	$.each(data.countries, function(index, item) {
            	  list.append(new Option(item,index));
            	});
            	list = $("#hometeamsList");
            	$.each(data.teams, function(index, item) {
            	  list.append(new Option(item,index));
            	});
            	list = $("#awayteamsList");
            	$.each(data.teams, function(index, item) {
            	  list.append(new Option(item,index));
            	});

            }, function(xhr) {
                var errormsg = getErrorMsg(xhr);
                alert(errormsg);
            });
}

function saveNewEvent() {

	var league = $('#leaguesList').find(":selected").val();
	var country = $('#countriesList').find(":selected").val();
	var hometeam = $('#hometeamsList').find(":selected").val();
	var awayteam = $('#awayteamsList').find(":selected").val();
	var time = $('#time').val();
	time = time.replace('T',' ');
	if (hometeam !== awayteam) {
	var addEvent = {};
	addEvent.countryId = country;
	addEvent.leagueId = league;  
	addEvent.homeTeamId = hometeam;
	addEvent.awayTeamId = awayteam;
	addEvent.startDateTime = time;
    alert(JSON.stringify(addEvent));
    $('#countriesList').clear();
    $('#leaguesList').clear();
     $('#hometeamsList').clear();
     $('#awayteamsList').clear();
    var url = "/tricast-2016-sportsbook/services/events";
    var method = "POST";
	    sendAjax(method, url, JSON.stringify(addEvent), function(data, textStatus, xhr) {      
	         alert(textStatus);
	    }, function(xhr) {
	        var errormsg2 = getErrorMsg(xhr);
	        alert(errormsg2);
	    });
	}
	else {
		alert("Nem adhatod meg ugyanazt a csapatot hazai és vendégnek!");
	}
	
}

function stringToDate(dateString) {

	dateTimeParts = dateString.split(' '),
	timeParts = dateTimeParts[1].split(':'),
	dateParts = dateTimeParts[0].split('-'),
	date = new Date(dateParts[2], parseInt(dateParts[1], 10) - 1, dateParts[0], timeParts[0], timeParts[1]);
	return date;
}