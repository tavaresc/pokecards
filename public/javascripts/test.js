$(document).ready(function(){
   $("#b").click(function(){
    var text = $("#foo").val();
    $("div#bar > p").append(text);
    console.log(text)
    }
    );
});
