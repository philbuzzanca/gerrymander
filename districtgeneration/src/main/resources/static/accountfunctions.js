'use strict';
function register(username, password){
    let formData = {username: username, password: password};
    $.post("/register", formData, function (data, status) {
        if (status === 'success'){
            $('#registerSuccess').text("Success!");
            $('#invalidLogin').hide();
            $('#registerSuccess').show();

        }
        if (status === 'error'){
            $('#registerSuccess').hide();
            $('#invalidLogin').text(data.message);
            $('#invalidLogin').show();
        }
    });
}

function login(username, password){
    let formData = {username: username, password: password};
    $.post("/login", formData, function (data, status) {
        if (status === "success"){
            $("#invalidLogin").hide();
            $("#registerLink").hide();
            $("#logoutLink").show();
            $("#registerLoginModal").modal("toggle");
            $('#registerSuccess').hide();
            if(data.admin){
                $("#adminLink").show();
            }
        }
    }).fail(function(){
        $('#invalidLogin').show();
        $('#invalidLogin').text("Invalid login.");
    });
}

function logout() {
    $.post("/logout", function (data) {
        $("#logoutLink").hide();
        $("#registerLink").show();
    });
}

function updateAccount(newUsername, newPassword, newParty) {
    let formData = {newUsername: newUsername, newPassword: newPassword, newParty: newParty};
    $.post("/update", formData);
}

$(document).ready(function(){
    $('#adminLink').hide();
    $('#invalidLogin').hide();
    $('#registerSuccess').hide();
    $("#logoutLink").hide();
    $("#loginForm").submit((event) => {
        event.preventDefault();
        let username = $('#loginUsername').val();
        let password = $('#loginPassword').val();
        login(username, password);
    });

    $("#registerForm").submit((event) => {
        event.preventDefault();
        let username = $('#registerUsername').val();
        let password = $('#registerPassword').val();
        register(username, password);
    });
});


$(document).ready(function(){
    $.get("/getUsers", function(data){
        if(data !== null){
            for(var i = 0; i < data.length; i++){
                $("#userTable").append("<tr><td>"+data[i].username+"</td><td>"
                        +data[i].party+"</td><td>"+data[i].admin+"</td></tr>");
            }
        }
    });
});

$(document).ready(function(){
    $("#logoutLink").click((event) => {
        event.preventDefault();
        logout();
    });

    $("#logoutButton").click((event) => {
        event.preventDefault();
        logout();
    });
    
    $("#deleteNotifactionLabel").hide();
    
    $("#deleteUserButton").click((event) => {
        let formData = {username: $("#userToDeleteInput").val()};
       $.post("/deleteUser", formData, function(data, status){
           if(status === 'success'){
               $("#deleteNotificationLabel").show();
               $("#deleteNotificationLabel").text("Success!");
           }
           else {
               $("#deleteNotificationLabel").show();
               $("#deleteNotificationLabel").text("error: user not found");
           }
       });
    });
});

$(document).ready(function() {
    $("#compareDistrictsTable").hide();
    $("#compareDistrictsButton").click(function(){
        let formData = {d1: $("#compareCdOne").val(), d2: $("#compareCdTwo").val()};
        $.post("/compareDistricts", formData, function(data) {
            if(data[0] !== null && data[1] !== null){
                $("#compareDistrictsTable").show();
                $("#comparisonDistrictOne").text(data[0].cdID);
                $("#hispanicOne").text(data[0].hispanic);
                $("#whiteOne").text(data[0].white);
                $("#blackOne").text(data[0].black);
                $("#nativeOne").text(data[0].nativeAmerican);
                $("#asianOne").text(data[0].asian);
                $("#pacificOne").text(data[0].pacificIslander);
                $("#multipleOne").text(data[0].multiple);
                $("#otherOne").text(data[0].other);
                $("#populationOne").text(data[0].population);
                $("#comparisonDistrictTwo").text(data[1].cdID);
                $("#hispanicTwo").text(data[1].hispanic);
                $("#whiteTwo").text(data[1].white);
                $("#blackTwo").text(data[1].black);
                $("#nativeTwo").text(data[1].nativeAmerican);
                $("#asianTwo").text(data[1].asian);
                $("#pacificTwo").text(data[1].pacificIslander);
                $("#multipleTwo").text(data[1].multiple);
                $("#otherTwo").text(data[1].other);
                $("#populationTwo").text(data[1].population);
            }
        }, "json");
    }); 
});

$(function(){
    $('#precinctOptions').hide();
});