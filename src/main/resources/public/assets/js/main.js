/// <reference path="../typings/tsd.d.ts" /
var hasLName = false;
var hasFName = false;
$(document).ready(function(){
$("#random-quote-container").hide();
$("#personalised-quote-container").hide();

    function doApiRequest(type){       
        $.ajax({
           method: "GET",
           url: QueryBuilder(type),
           dataType: "json"
        }).done(function(response){
            switch(type){
                case "random": getRandomQuote(response); break;
                case "": getAllQuotes(response); break;
                case "personalised": getPersonalisedQuote(response); break;
            }
        }).fail(function(){
            console.log("Something went wrong")
        })
}
function getAllQuotes(data){
    clearAllQuotes()
    showChangeViewButton();
    for (i=0;i<data.value.length;i++){
    var template = $('#card-template').clone();
    template.attr('id', '');
    template.find('span').text(decodeEntities(data.value[i].joke))
    var img =template.find('img');
    generateImage(img);
    $('#quote-container').append(template);
    template.show();
    }
}

function clearAllQuotes(){
 $("#quote-container").empty();
}
function getPersonalisedQuote(data){
    $("#result-personalised-quote").text(decodeEntities(data.value.joke))
    $("#personalised-quote-container").show();
    $("#first-name").val("");
    $("#last-name").val("");
    $("#submit-personalised-quote").attr("disabled", true)


}


function getRandomQuote(data){
    $("#first-name").val("");
    $("#last-name").val("");
    $("#result-random-quote").text(decodeEntities(data.value.joke))
    $("#random-quote-container").show();
    $("#submit-personalised-quote").attr("disabled", true)
}
function decodeEntities(encodedString) {
    var textArea = document.createElement('textarea');
    textArea.innerHTML = encodedString;
    return textArea.value;
  }

function changeView(){
    var quotes = $("#quote-container>div");
    var images;
    if(quotes.length>0){
        
        images = $("#quote-container").find("img");
        button = $("#quote-container").find("button");
        if(quotes.hasClass("card")){
            quotes.removeClass("card");
            quotes.removeClass("m-2");
            quotes.removeClass("shadow");
            quotes.removeClass("card-quote");
            quotes.addClass("d-flex");
            quotes.addClass("border-top");
            quotes.addClass("w-100");
            images.addClass("thumbnail");
            images.removeClass("img-full-size");
            button.removeClass("mb-3");
            button.addClass("mt-3");
            button.removeClass("d-inline-block");
            button.addClass("d-block");
            button.addClass("w-100");
        }
        else{
            quotes.addClass("card");
            quotes.addClass("m-2");
            quotes.addClass("shadow");
            quotes.addClass("card-quote");
            quotes.removeClass("d-flex");
            quotes.removeClass("border-top");
            quotes.removeClass("w-100");
            images.removeClass("thumbnail");
            images.addClass("img-full-size");
            button.removeClass("mt-3");
            button.addClass("mb-3");
            button.addClass("d-inline-block");
            button.removeClass("d-block");
            button.removeClass("w-100");
        }
    }
}
function showChangeViewButton(){
    $("#change-view-btn").show();

}
function generateImage(img){
    var random = getRandomInt(7);
    var imgPath = "assets/images/"+random.toString()+".jpg";
    img.attr("src",imgPath);
    img.show();

}
function getRandomInt(max) {
    return 1+Math.floor(Math.random() * Math.floor(max));
  }

$('input[name="limit"]').click(function(){
  doApiRequest("")
})
$("#submit-random-quote").click(function(){
    doApiRequest("random")
})
$("#submit-personalised-quote").click(function(){
    doApiRequest("personalised")
    hasFName=false;
    hasLName=false;
})
$("#first-name").on("keydown", function(){
    if($("#first-name").val().length>0){
        hasFName = true;
    }
    else{
        hasFName = false;
    }
    if(hasFName && hasLName){
        $("#submit-personalised-quote").removeAttr("disabled")
    }
    else{
        $("#submit-personalised-quote").attr("disabled", true)
    }
})
$("#last-name").on("keydown", function(){
    if($("#last-name").val().length>0){
        hasLName = true;
    }
    else{
        hasLName =false;
    }
    if(hasFName && hasLName){
        $("#submit-personalised-quote").removeAttr("disabled")
    }
    else{
        $("#submit-personalised-quote").attr("disabled", true)
    }



})
$("#first-name").on("keyup", function(){
    if($("#first-name").val().length>0){
        hasFName = true;
    }
    else{
        hasFName = false;
    }
    if(hasFName && hasLName){
        $("#submit-personalised-quote").removeAttr("disabled")
    }
    else{
        $("#submit-personalised-quote").attr("disabled", true)
    }
})
$("#last-name").on("keyup", function(){
    if($("#last-name").val().length>0){
        hasLName = true;
    }
    else{
        hasLName =false;
    }
    if(hasFName && hasLName){
        $("#submit-personalised-quote").removeAttr("disabled")
    }
    else{
        $("#submit-personalised-quote").attr("disabled", true)
    }

})
$("#change-view-btn").click(function(){
    changeView();
})
function QueryBuilder(type){
    var fname="";
    var lname="";
    var limit="";
    var creds="";
    var apiLimit="";
    var apiType="";

    if(type === "random" || type ==="personalised"){
    apiType ="random";
    fname = $("#first-name").val();
    lname = $("#last-name").val();
    if(fname !=="" && lname !==""){
        creds = "firstName=" + fname + "&lastName=" + lname;
    }
    }
    else{
    apiType="";
    creds="";;
    }
   var limit = $('input[name="limit"]:checked').val();
   if(limit === undefined){
       limit="all";
   }
    switch(limit){
        case "nerdy": apiLimit ="?limitTo=[nerdy]";
        if(fname !=="" && lname !=="" && (type==="random" || type==="personalised")){
            creds = "&firstName=" + fname + "&lastName=" + lname};
         break;
        case "explicit": apiLimit ="?limitTo=[explicit]";
        if(fname !=="" && lname !=="" && (type==="random" || type==="personalised")){
            creds = "&firstName=" + fname + "&lastName=" + lname}; 
        break;
        case "all": apiLimit ="";
        if(fname !=="" && lname !=="" && (type==="random" || type==="personalised")){
            creds = "?firstName=" + fname + "&lastName=" + lname;
        }; break;
        default: apiLimit =""; break;
    }
    if(type === "random"){
        creds ="";
    }

    var query = "https://api.icndb.com/jokes/"+apiType+apiLimit+creds;
    return query;




}
})