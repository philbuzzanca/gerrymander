'use strict';
function register(username, password){
    let formData = {username: username, password: password};
    $.post("/register", formData.serialize(), function (data, status) {
        if (status === 'error'){
            $('#invalidLogin').text(data.message);
        }
    }, "json");
}

function login(username, password){
    let formData = {username: username, password: password};
    $.post("/login", formData, function (data, status) {
        if (status === "success"){
            $("#invalidLogin").hide();
            $("#registerLink").hide();
            $("#logoutLink").show();
            $("#registerLoginModal").modal("toggle");

        }
        else {
            $('#invalidLogin').show();
            $('#invalidLogin').text(data.message);
        }
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
    $('#invalidLogin').hide()
    $("#logoutLink").hide();
    $("#loginForm").submit((event) => {
        event.preventDefault();
        let username = $('#loginUsername').val();
        let password = $('#loginPassword').val();
        login(username, password);
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
});