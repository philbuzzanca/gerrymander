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
                           data-target="#adminModal" style="visibility: hidden">Administrator</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="" data-toggle="modal"
                           data-target="#accountModal">${sessionScope.user.getUsername()}</i></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="" id="registerLink" data-toggle="modal" data-target="#registerLoginModal">Register/Login
                            <i class="fas fa-sign-in-alt"></i></a>
                        <a class="nav-link" href="" id="logoutLink">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <!-- adminModal -->
        <div class="modal fade" id="adminModal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Administrator tools</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h5>User statistics</h5>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Users</th>
                                    <th scope="col">Total</th>
                                    <th scope="col">Republican</th>
                                    <th scope="col">Democrat</th>
                                    <th scope="col">Green</th>
                                    <th scope="col">Libertarian</th>
                                    <th scope="col">Other</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">Number of users</th>
                                    <td>10</td>
                                    <td>0</td>
                                    <td>1</td>
                                    <td>2</td>
                                    <td>3</td>
                                    <td>4</td>
                                </tr>
                                <tr>
                                    <th scope="row">Percent of total</th>
                                    <td>100%</td>
                                    <td>0%</td>
                                    <td>10%</td>
                                    <td>20%</td>
                                    <td>30%</td>
                                    <td>40%</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
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
                        <button id="#logoutButton" type="button" class="btn btn-danger" style="display:none">Log out</button>
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
                        <div id="registerSuccess" class="alert alert-success"></div>
                    </div>
                    <div class="modal-body row">
                        <div class="col-md-6 col-lg-6">
                            <div id="registerBox">
                                <h2>Register <i class="fa fa-user-plus"></i></h2>
                                <form id="registerForm">
                                    <div class="form-group">
                                        <label for="registerUsername">Username</label>
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
        
        <!-- compareDistrictsModal -->
        <div class="modal fade" data-backdrop="static" id="compareDistrictsModal" tabindex="-1"
             role="dialog">
            <div class="modal-dialog" id="compareDistrictsModalDialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Compare district data</h5>
                    </div>
                    <div class="modal-body">
                        <form class="form-inline">
                            <input type="text" class="form-control mb-2 mr-sm-2" id="compareCdOne" placeholder="VA01">
                            <input type="text" class="form-control mb-2 mr-sm-2" id="compareCdTwo" placeholder="VA02">
                            <button type="button" class="btn btn-secondary mb-2 mr-sm-2" id="compareDistrictsButton">Compare</button>
                        </form>
                        <table class="table" id="compareDistrictsTable">
                            <thead>
                                <tr>
                                    <th scope="col">District</th>
                                    <th scope="col">Population</th>
                                    <th scope="col">Hispanic</th>
                                    <th scope="col">White</th>
                                    <th scope="col">Black</th>
                                    <th scope="col">Native American</th>
                                    <th scope="col">Asian</th>
                                    <th scope="col">Pacific Islander</th>
                                    <th scope="col">Other</th>
                                    <th scope="col">Multiple races</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row" id="comparisonDistrictOne"></th>
                                    <td id="populationOne">10</td>
                                    <td id="hispanicOne">0</td>
                                    <td id="whiteOne">1</td>
                                    <td id="blackOne">2</td>
                                    <td id="nativeOne">3</td>
                                    <td id="asianOne">4</td>
                                    <td id="pacificOne">1</td>
                                    <td id="otherOne">2</td>
                                    <td id="multipleOne">3</td>
                                </tr>
                                <tr>
                                    <th scope="row" id="comparisonDistrictTwo"></th>
                                    <td id="populationTwo"></td>
                                    <td id="hispanicTwo"></td>
                                    <td id="whiteTwo"></td>
                                    <td id="blackTwo"></td>
                                    <td id="nativeTwo"></td>
                                    <td id="asianTwo"></td>
                                    <td id="pacificTwo"></td>
                                    <td id="otherTwo"></td>
                                    <td id="multipleTwo"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
                                    <option value="Virginia">Virginia</option>
                                    <option value="New Mexico">New Mexico</option>
                                    <option value="Utah">Utah</option>
                                </select>
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
                            <div class="dropdown">
                                <button type="button" class="btn btn-primary dropdown-toggle" id="toolsButton" data-toggle="dropdown" style="width:100%">Tools</button>
                                <div class="dropdown-menu" style="width:100%">
                                    <button class="dropdown-item" type="button">Run algorithm</button>
                                    <button class="dropdown-item" type="button" data-toggle="modal" data-target="#compareDistrictsModal">Compare districts</button>
                                    <button class="dropdown-item" type="button">Reset map</button>
                                </div>
                            </div>
                            <hr>
                            <div id="precinctOptions">
                                <h4>Precinct Options</h4>
                                <form>
                                    <div class="form-group">
                                        <label for="precinctDistrictSelect" id="precinctLabel">District</label>
                                        <select class="form-control" id="precinctDistrictSelect">
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="precinctLockedCheckbox"><span>Locked: </span></label>
                                        <input class="checkbox pull-right" type="checkbox" id="precinctLockedCheckbox">
                                    </div>
                                </form>
                            </div>
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
