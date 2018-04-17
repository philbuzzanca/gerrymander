function register(username, email, password){
  var xhttp = new XMLHttpRequest();
  var fd = new FormData();
  fd.append("username", username);
  fd.append("email", email);
  fd.append("password", password);
  xhttp.open("POST", "/register", false);
  xhttp.send(fd);
}

function login(email, password){
  var xhttp = new XMLHttpRequest();
  var fd = new FormData();
  fd.append("email", email);
  fd.append("password", password);
  xhttp.open("POST", "/login", false);
  xhttp.send(fd);
}

function logout(){
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "/logout", false);
  xhttp.send();
}

function updateAccount(newUsername, newPassword, newParty){
  var fd = new FormData();
  fd.append("newUsername", newUsername);
  fd.append("newPassword", newPassword);
  fd.append("newParty", newParty);
  var xhttp = new XMLHttpRequest();
  xhttp.open("POST", "/update", false);
  xhttp.send(fd);
}