//this method runs every time when the page is reloading
$(document).ready(function() {
    assignAction();

});

function assignAction() {
    var events = getAllEvents();

    getIndex();

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
                                            + '</td><td><button id="edit" class="btn btn-info">Edit result</button>'
                                            + '</td><td class="el"><input type="text" name="hteam"><input type="text" name="ateam"></td>'
                                            + '<td class="el"><button id="submit">Submit result</button></td></tr>');

                }
                $('.el').hide();
            }, function(xhr) {
                var errormsg = getErrorMsg(xhr);
                alert(errormsg);
            });

}

function getIndex() {
    $('table').on('click', '#edit', function() {
        $('.el').toggle();
    });
    $('table').on('click', '#submit', function() {
        var row = this.parentNode.parentNode;
        var hteam = $(this).parent().parent().find("input[name='hteam']").val();
        var ateam = $(this).parent().parent().find("input[name='ateam']").val();
        getPeriodParams(row.rowIndex, hteam, ateam);

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
            // alert("Succesfully saved");
            // console.log(row);
            // $('#eventTable .periodResult').find("tr:1").html("test");
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
