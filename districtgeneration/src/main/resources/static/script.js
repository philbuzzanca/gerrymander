'use strict';
var mymap = L.map('mapid').setView([37.7, -79.5], 8);
const stateFocus = {
    'New Mexico': new L.LatLng(34, -105.87),
    'Virginia': new L.LatLng(37.7, -79.5),
    'Utah': new L.LatLng(39.3, -111.1)
};
const stateZoom = {
    'New Mexico': 7,
    'Virginia': 8,
    'Utah': 7
};

const stateDistricts = {
      "Virginia": 11,
      "New Mexico": 3,
      "Utah": 4
    };

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoicGhpbGJ1enphbmNhIiwiYSI6ImNqZTB1eGIzYzY0YWsyeHFoaDRwamlxcXoifQ.EzbAaOMVsKV5_OIks8_67w', {
    id: 'mapbox.light'
}).addTo(mymap);

function resetMap() {
    mapFocus('Virginia')
}

function onEachFeature(feature, layer) {
    let properties = [];
    const exclusions = {NEIGHBORS: 1};
    feature.properties["LOCKED"] = false;
    for (let property of Object.keys(feature.properties)) {
        if (!(property in exclusions)){
            properties.push(`${property}: ${feature.properties[property]}`);
        }
    }
    layer.bindPopup(properties.join("<br />"));
    layer.on({
        click : selectTile
    });
}

var selectedState;

function selectTile(e) {
    selectedState = e.target;
    $("#precinctOptions").show();
    $("#precinctLockedCheckbox").prop('checked', selectedState.feature.properties.LOCKED);
    var i;
    var $pds = $("#precinctDistrictSelect");
    $($pds.empty());
    for(i = 1; i <= stateDistricts[$("#stateSelect").val()]; i++){
        $($pds.append('<option id=district' + i + ' value=' + i + '> District ' + i + '</option>'));
    }
    $($pds.val(selectedState.feature.properties.CD));
}

$(document).ready(function() {
    $("#precinctDistrictSelect").change(function(){
        selectedState.setStyle({
           fillColor: getColor($("#precinctDistrictSelect").val()) 
        });
        selectedState.feature.properties['CD'] = parseInt($("#precinctDistrictSelect").val());
    });
});

$(document).ready(function() {
    $("#precinctLockedCheckbox").change(function(){
        selectedState.feature.properties['LOCKED'] = this.checked;
    }); 
});

function mapFocus(state) {
    $($("#precinctOptions").hide());
    mymap.flyTo(stateFocus[state], stateZoom[state]);
}

function getColor(d) {
    const COLORS = ['#00FFFF', '#660066', '#FF66FF', '#FFFF66', '#FF0000', '#00FF00', '#0000FF', '#FFFFFF'];
    return COLORS[d % 7];
}
//Colors precinct map based on county
function precinctStyle(feature) {
    return {
        fillColor: getColor(feature.properties.CD),
        weight: 0.4
    };
}

L.geoJSON(vaGeoData, {
    onEachFeature: onEachFeature,
    style: precinctStyle
}).addTo(mymap);
L.geoJSON(nm, {
    onEachFeature: onEachFeature,
    style: precinctStyle
}).addTo(mymap);
