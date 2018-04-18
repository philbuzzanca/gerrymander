'use strict';
function register(username, password) {
    let formData = {username: username, password: password};
    $.post("/register", formData.serialize(), function (data) {
        if (data.error) {
            // do stuff
        }
       
    }, "json");
}

function login(username, password) {
    /*var xhttp = new XMLHttpRequest();
     var fd = new FormData();
     fd.append("email", email);
     fd.append("password", password); 
     xhttp.open("POST", "/login", false);
     xhttp.send(fd);*/
    let formData = {username: username, password: password};
    $.post("/login", formData.serialize(), function (data) {
        console.log('done');
        // do stuff
    }, "json");

}

function logout() {
    /*var xhttp = new XMLHttpRequest();
     xhttp.open("POST", "/logout", false);
     xhttp.send(); */
    $.post("/logout", function (data) {
        // do stuff
    }, "json");
}

function updateAccount(newUsername, newPassword, newParty) {
    /* var fd = new FormData();
     fd.append("newUsername", newUsername);
     fd.append("newPassword", newPassword);
     fd.append("newParty", newParty);
     var xhttp = new XMLHttpRequest();
     xhttp.open("POST", "/update", false);
     xhttp.send(fd); */
    let formData = {newUsername: newUsername, newPassword: newPassword, newParty: newParty};
    $.post("/update", formData.serialize(),
            function (data) {
                // do stuff
            },
            "json");
}

$('#loginButton').submit((event) => {
    event.preventDefault();
    let loginUsername = $('#loginUsername').value();
    let loginPassword = $('#loginPassword').value();
    login(loginUsername, loginPassword);
});
