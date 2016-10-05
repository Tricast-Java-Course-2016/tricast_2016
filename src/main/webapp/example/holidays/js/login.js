//this method runs every time when the page is reloading
$(document).ready(function(){
	
	//assign actions to links and buttons
	assignActions();	 
});


function assignActions() {
	//assign login action 
	$("#loginSubmit").click(function(e) {
		//prevent the default form behaviour
		e.preventDefault();
		
		var user = $("#lgusername").val();
		var pwd = $("#lgpassword").val();		
		if (user == "" || pwd == "") {
			return;
		}
		login(user, pwd);		
	});
	
	//assign action to Home button 
	$("#homeBtn").click(function(e) {		
		window.location.href = "home.html";
	});
}

//--------Account functions-------
function login(user, pwd) {
	var url = "/days-off-calendar/services/accounts/login";
	var request = {};
	request.userName = user;
	request.password = pwd;
	sendAjax("POST", url, JSON.stringify(request), 
	 	function(data, textStatus, xhr ) {
			window.location.href = "accountInfo.html?"+data.id;
		},
		function(xhr) {
			$("#loginMsg").html(getErrorMsg(xhr));
		}
	);	
}
