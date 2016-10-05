var selectedHolidayId = null;

//this method runs every time when the page is reloading
$(document).ready(function(){
	//assign actions to links and buttons
	assignActions();
	
	getHolidays();
});

function assignActions() {
	//assign action to Open button 
	$("#openHoliday").click(function(e) {		
		window.location.href = "holiday.html?"+selectedHolidayId;
	});		
}

function getHolidays() {
	var url = "/days-off-calendar/services/holidays";
	sendAjax("GET", url, null, 
	 	function(data) {
			populateHolidays(data);
		}, null
	);
}

//--------Holiday list functions-------
var tplHolidayRow = null;
function populateHolidays(holidays) {	
	var elementRow = $("#holidayRowTemplate");
	elementRow.show();
	tplHolidayRow = elementRow[0].outerHTML;
	//elementRow.hide();		
	$("#holidaysTable").find("tr:gt(1)").remove();	
	$("#noHolidayRow").hide();
	
	if(holidays.length == 0){
		$("#noHolidayRow").show();			
	}	
	for (var i = 0; i < holidays.length; i++) {
		appendHolidayRow(holidays[i]);
	}
	
	$('tr').click(function() {
		$('tr').removeClass("selected");
	    $(this).addClass("selected");
	    selectedHolidayId = $(this).children('td.id').html();
	});
};

function appendHolidayRow(holiday) {
	var model = {};
	model.id = holiday.id;
	model.accountId = holiday.accountId;
	model.fromDay = holiday.fromDay;
	model.toDay = holiday.toDay;
	model.type = holiday.type;
	
	var row = render(tplHolidayRow, model);			
	$("#holidaysTable").append(row);
};

function render(template, data) {
    var replaced = template;
    for (var key in data) {
        var reg = new RegExp("{{" + key + "}}","g");
        replaced = replaced.replace(reg, data[key]);
    }
    
    var newItem = $(replaced.replace(/{{.+}}/g, "")); 
    newItem.removeAttr('id');
    return newItem;
};