var hasUsername = false;
var hasPassword = false;

$(document).ready(function(){
$('#registration-row').attr( "style", "display: none !important;" );
$('#register').on('click', function(){
    $('#log-in-row').attr( "style", "display: none !important;" );
    $('#registration-row').attr( "style", "display: flex !important;" );
});
$('#btn-back').click(function(){
    $('#log-in-row').attr( "style", "display: flex !important;" );
    $('#registration-row').attr( "style", "display: none !important;" );
})

/*$('#log-in-form').submit(function(e){
	e.preventDefault();
	
	$.ajax({
        method: "POST",
        url: "login",
        data: {
        	username: $('#log-in-username').val(),
			password: $('#log-in-password').val()
        },
        success: function(data){
        	window.location.replace(data);
        },	
        fail: function(){
        	window.location.href="index.html";
        }
     })
	
	
});*/
$("#log-in-username").on("keydown", function(){
    if($("#log-in-username").val().length>0){
        hasUsername = true;
    }
    else{
        hasUsername = false;
    }
    if(hasUsername && hasPassword){
        $("#login").removeAttr("disabled")
    }
    else{
        $("#login").attr("disabled", true)
    }
})
$("#log-in-password").on("keydown", function(){
    if($("#log-in-password").val().length>0){
        hasPassword = true;
    }
    else{
        hasPassword =false;
    }
    if(hasUsername && hasPassword){
        $("#login").removeAttr("disabled")
    }
    else{
        $("#login").attr("disabled", true)
    }



})
$("#log-in-username").on("keyup", function(){
    if($("#log-in-username").val().length>0){
    	hasUsername = true;
    }
    else{
    	hasUsername = false;
    }
    if(hasUsername && hasPassword){
        $("#login").removeAttr("disabled")
    }
    else{
        $("#login").attr("disabled", true)
    }
})
$("#log-in-password").on("keyup", function(){
    if($("#log-in-password").val().length>0){
    	hasPassword = true;
    }
    else{
    	hasPassword =false;
    }
    if(hasUsername && hasPassword){
        $("#submit-personalised-quote").removeAttr("disabled")
    }
    else{
        $("#submit-personalised-quote").attr("disabled", true)
    }

})



})