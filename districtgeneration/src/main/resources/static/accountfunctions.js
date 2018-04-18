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
    $("#logoutLink").click((event) => {
        event.preventDefault();
        logout();
    });

    $("#logoutButton").click((event) => {
        event.preventDefault();
        logout();
    });
});
