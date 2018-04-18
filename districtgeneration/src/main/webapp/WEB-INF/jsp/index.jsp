<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>CSE 308</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700" rel="stylesheet">

    <!-- FontAwesome -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
    <!--Leaflet-->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.1/dist/leaflet.css"
          integrity="sha512-Rksm5RenBEKSKFjgI3a41vrjkw4EVPlJ3+OiI65vTjIdo9brlAacEuKOiQ5OFh7cOI1bkDwLqdLw3Zg0cRJAAQ=="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.3.1/dist/leaflet.js"
            integrity="sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw=="
            crossorigin=""></script>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <!--Bootstrap-->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"
            integrity="sha384-feJI7QwhOS+hwpX2zkaeJQjeiwlhOP+SdQDqhgvvo1DsjtiSQByFdThsxO669S2D"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/10.0.0/bootstrap-slider.js"></script>

    <!--State data-->
    <script src="vaprecincts2013.js"></script>
    <script src="nm.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="navbar-brand">CSE308</div>
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="" data-toggle="modal" data-target="#aboutModal">About</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="" data-toggle="modal" data-target="#helpModal">Help</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="" data-toggle="modal"
                   data-target="#accountModal">${sessionScope.user.getUsername()}</i></a>
            </li>
            <c:if test="${empty sessionScope.user}">
                <li class="nav-item">
                    <a class="nav-link" href="" id="registerLink" data-toggle="modal" data-target="#registerLoginModal">Register/Login
                        <i class="fas fa-sign-in-alt"></i></a>
                    <a class="nav-link" href="" id="logoutLink">Logout</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>


<!-- accountModal -->
<div class="modal fade" id="accountModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Update account</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <h6 for="updateUsername">Change username</h6>
                        <input type="text" name="updateUsername" id="updateUsername" style="width: 50%"></input>
                    </div>
                    <div class="form-group">
                        <h6 for="updatePassword">Change password</h6>
                        <input type="password" name="updatePassword" id="updatePassword" style="width: 50%"></input>
                    </div>
                    <div class="form-group">
                        <h6 for="partySelect">Party: ${sessionScope.user.getParty()}</h6>
                        <select name="partySelect" id="partySelect" class="form-control" style="width: 50%">
                            <option selected disabled hidden value="">Change party</option>
                            <option value="0">Republican</option>
                            <option value="1">Democrat</option>
                            <option value="2">Green</option>
                            <option value="3">Libertarian</option>
                            <option value="4">Other</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-success"
                        onclick="updateAccount(updateUsername.value, updatePassword.value, partySelect.value); window.location.reload(false);"
                        data-dismiss="modal">Save & close
                </button>
                <button id="#logoutButton" type="button" class="btn btn-danger">Log out</button>
            </div>
        </div>
    </div>
</div>


<!-- aboutModal -->
<div class="modal fade" id="aboutModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">About Gerrymandering</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ${aboutText}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                </button>
            </div>
        </div>
    </div>
</div>

<!-- helpModal -->
<div class="modal fade" id="helpModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Help</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ${helpText}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                </button>
            </div>
        </div>
    </div>
</div>

<!-- registerLoginModal -->
<div class="modal fade" data-backdrop="static" id="registerLoginModal" tabindex="-1"
     role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-body row">
                <div id="invalidLogin" class="alert alert-danger"></div>
            </div>
            <div class="modal-body row">
                <div class="col-md-6 col-lg-6">
                    <div id="registerBox">
                        <h2>Register <i class="fa fa-user-plus"></i></h2>
                        <form id="registerForm">
                            <div class="form-group">
                                <label for="registerEmail">Username</label>
                                <input type="text" id="registerUsername"
                                       class="form-control" placeholder="username">
                            </div>
                            <div class="form-group">
                                <label for="registerPassword">Password</label>
                                <input type="password" id="registerPassword"
                                       class="form-control" placeholder="********">
                            </div>
                            <input class="btn btn-success" type="submit" value="Register">
                        </form>
                    </div>
                </div>
                <div class="col-md-6 col-lg-6">
                    <div id="loginBox">
                        <h2>Log in <i class="fas fa-sign-in-alt"></i></h2>
                        <form id="loginForm">
                            <div class="form-group">
                                <label for="loginUsername">Username</label>
                                <input type="text" id="loginUsername" class="form-control"
                                       placeholder="username">
                            </div>
                            <div class="form-group">
                                <label for="loginPassword">Password</label>
                                <input type="password" id="loginPassword"
                                       class="form-control" placeholder="********">
                            </div>
                            <input id="loginButton" class="btn btn-primary" type="submit"
                                   value="Log in"/>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                </button>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid fill">
    <div class="row no-gutters">
        <div class="col-sm-2 col-lg-2">
            <div class="sidebar">
                <form>
                    <div class="form-group">
                        <h4 for="stateSelect">Target state</h4>
                        <select id="stateSelect" class="form-control"
                                onchange="mapFocus(this.value)">
                            <option value="va">Virginia</option>
                            <option value="nm">New Mexico</option>
                            <option value="ut">Utah</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <h4 for="levelSelect">Political Level</h4>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="levelRadio"
                                   id="stateLevelRadio" value="state" checked>
                            <label class="form-check-label" for="exampleRadios1">
                                State
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="levelRadio"
                                   id="congressionalLevelRadio" value="congressional">
                            <label class="form-check-label" for="exampleRadios2">
                                Congressional
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <h4>Constraints</h4>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox"
                                   id="preserveExistingCommunitiesCheck">
                            <label class="form-check-label"
                                   for="preserveExistingCommunitiesCheck">Preserve existing
                                communities</label>
                        </div>
                        <h4>Measures</h4>
                        <label class="form-check-label"
                               for="compactnessCheck">Compactness</label>
                        <span id="compactOut" class="measuresOut"></span>
                        <div class="slidecontainer">
                            <input type="range" min="0" max="100" value="0" class="slider"
                                   id="compactnessSlider">
                            <script>
                                var compactSlider = document.getElementById("compactnessSlider");
                                var compactOut = document.getElementById("compactOut");
                                compactOut.innerHTML = compactSlider.value;
                                compactSlider.oninput = function () {
                                    compactOut.innerHTML = this.value;
                                };
                            </script>
                        </div>
                        <label class="form-check-label"
                               for="contiguityCheck">Contiguity</label>
                        <span id="contiguityOut" class="measuresOut"></span>
                        <div class="slidecontainer">
                            <input type="range" min="0" max="100" value="0" class="slider"
                                   id="contiguitySlider">
                            <script>
                                var contigSlider = document.getElementById("contiguitySlider");
                                var contiguityOut = document.getElementById("contiguityOut");
                                contiguityOut.innerHTML = contigSlider.value;
                                contigSlider.oninput = function () {
                                    contiguityOut.innerHTML = this.value;
                                };
                            </script>
                        </div>
                        <label class="form-check-label" for="equalPopulationCheck">Equal
                            population</label>
                        <span id="equalOut" class="measuresOut"></span>
                        <div class="slidecontainer">
                            <input type="range" min="0" max="100" value="0" class="slider"
                                   id="equalPopSlider">
                            <script>
                                var equalSlider = document.getElementById("equalPopSlider");
                                var equalOut = document.getElementById("equalOut");
                                equalOut.innerHTML = equalSlider.value;
                                equalSlider.oninput = function () {
                                    equalOut.innerHTML = this.value;
                                };
                            </script>
                        </div>
                        <label class="form-check-label" for="partisanFairnessCheck">Partisan
                            fairness</label>
                        <span id="partisanOut" class="measuresOut"></span>
                        <div class="slidecontainer">
                            <input type="range" min="0" max="100" value="0" class="slider"
                                   id="partisanSlider">
                            <script>
                                var partisanSlider = document.getElementById("partisanSlider");
                                var partisanOut = document.getElementById("partisanOut");
                                partisanOut.innerHTML = partisanSlider.value;
                                partisanSlider.oninput = function () {
                                    partisanOut.innerHTML = this.value;
                                };
                            </script>
                        </div>
                        <label class="form-check-label" for="racialFairnessCheck">Racial
                            fairness</label>
                        <span id="racialOut" class="measuresOut"></span>
                        <div class="slidecontainer">
                            <input type="range" min="0" max="100" value="0" class="slider"
                                   id="racialSlider">
                            <span id="racialOut"></span>
                            <script>
                                var racialSlider = document.getElementById("racialSlider");
                                var racialOut = document.getElementById("racialOut");
                                racialOut.innerHTML = racialSlider.value;
                                racialSlider.oninput = function () {
                                    racialOut.innerHTML = this.value;
                                };
                            </script>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary" style="width:100%">Build
                    </button>
                </form>
            </div>
        </div>
        <div class="col-sm-10 col-lg-10">
            <div id="mapid">
                <script src="script.js"></script>
                <script src="accountfunctions.js"></script>
            </div>
        </div>
    </div>
</div>
</body>
</html>
