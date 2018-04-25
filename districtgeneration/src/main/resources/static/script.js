'use strict';
var currentLayer;
var mymap = L.map('mapid').setView([37.0902, -95.7129], 4);
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
// New token: pk.eyJ1IjoiYWFsaWJlcnRpIiwiYSI6ImNqZ2JqeWNoMTJyODUyd3JudGxnNmhocHYifQ.FlEvzCpCA2Muv2ECqboflQ
// Old token: pk.eyJ1IjoicGhpbGJ1enphbmNhIiwiYSI6ImNqZTB1eGIzYzY0YWsyeHFoaDRwamlxcXoifQ.EzbAaOMVsKV5_OIks8_67w
// L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.html?access_token=pk.eyJ1IjoiYWFsaWJlcnRpIiwiYSI6ImNqZ2JqeWNoMTJyODUyd3JudGxnNmhocHYifQ.FlEvzCpCA2Muv2ECqboflQ', {
//     id: 'mapbox.light'
// }).addTo(mymap);
L.tileLayer('https://api.tiles.mapbox.com/styles/v1/mapbox/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoiYWFsaWJlcnRpIiwiYSI6ImNqZ2JqeWNoMTJyODUyd3JudGxnNmhocHYifQ.FlEvzCpCA2Muv2ECqboflQ', {
    id: 'dark-v9'
}).addTo(mymap);
// L.tileLayer('http://api.mapbox.com/v4/mapbox.dark.html?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NDg1bDA1cjYzM280NHJ5NzlvNDMifQ.d6e-nNyBDtmQCVwVNivz7A#7/34.048/-105.908', {
//     // id: 'mapbox.light'
// }).addTo(mymap);
// var mapboxTiles = L.tileLayer('https://api.mapbox.com/v4/mapbox.dark/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiYWFsaWJlcnRpIiwiYSI6ImNqZ2JqeWNoMTJyODUyd3JudGxnNmhocHYifQ.FlEvzCpCA2Muv2ECqboflQ');
// var mymap = L.map('mapid').addLayer(mapboxTiles).setView([37.0902, -95.7129], 4);

function onEachFeature(feature, layer) {
    let properties = [];
    const exclusions = {NEIGHBORS: 1};
    for (let property of Object.keys(feature.properties)) {
        if (!(property in exclusions)){
            properties.push(`${property}: ${feature.properties[property]}`);
        }
    }
    layer.bindPopup(properties.join("<br />"));
}

function mapFocus(state) {
    if (!state){
        return;
    }
    if (currentLayer)
        mymap.removeLayer(currentLayer);
    $.get(`/precincts/${state}`, function(data){
        currentLayer = L.geoJson(data, {
            onEachFeature: onEachFeature,
            style: precinctStyle,
        }).addTo(mymap);
    });
    mymap.flyTo(stateFocus[state], stateZoom[state]);
}

function getColor(d) {
    const COLORS = ['#00FFFF', '#660066', '#FF66FF', '#FFFF66', '#FF0000', '#00FF00', '#0000FF', '#FFFFFF'];
    return COLORS[d % 7];
}

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

$(document).ready(function(){
    $("#resetMap").click(() => {
        mapFocus($('#stateSelect').val());
    });
});
