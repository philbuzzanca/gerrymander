'use strict';
var currentLayer;
var mymap = L.map('mapid').setView([37.0902, -95.7129], 4);
const stateFocus = {
    'nm': new L.LatLng(34, -105.87),
    'va': new L.LatLng(37.7, -79.5),
    'ut': new L.LatLng(39.3, -111.1)
};
const stateZoom = {
    'nm': 7,
    'va': 8,
    'ut': 7
};
// New token: pk.eyJ1IjoiYWFsaWJlcnRpIiwiYSI6ImNqZ2JqeWNoMTJyODUyd3JudGxnNmhocHYifQ.FlEvzCpCA2Muv2ECqboflQ
// Old token: pk.eyJ1IjoicGhpbGJ1enphbmNhIiwiYSI6ImNqZTB1eGIzYzY0YWsyeHFoaDRwamlxcXoifQ.EzbAaOMVsKV5_OIks8_67w
L.tileLayer('https://api.tiles.mapbox.com/styles/v1/mapbox/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoiYWFsaWJlcnRpIiwiYSI6ImNqZ2JqeWNoMTJyODUyd3JudGxnNmhocHYifQ.FlEvzCpCA2Muv2ECqboflQ', {
    id: 'dark-v9'
}).addTo(mymap);

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

// L.geoJSON(vaGeoData, {
//     onEachFeature: onEachFeature,
//     style: precinctStyle
// }).addTo(mymap);
// L.geoJSON(nm, {
//     onEachFeature: onEachFeature,
//     style: precinctStyle
// }).addTo(mymap);

$(document).ready(function(){
    $("#resetMap").click(() => {
        mapFocus($('#stateSelect').val());
    });
});
