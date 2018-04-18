'use strict';
var mymap = L.map('mapid').setView([37.7, -79.5], 8);
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoicGhpbGJ1enphbmNhIiwiYSI6ImNqZTB1eGIzYzY0YWsyeHFoaDRwamlxcXoifQ.EzbAaOMVsKV5_OIks8_67w',
        {
            id: 'mapbox.light'
        }).addTo(mymap);

function onEachFeature(feature, layer) {
    let properties = [];
    for (let property of Object.keys(feature.properties)) {
        properties.push(`${property}: ${feature.properties[property]}`);
    }
    layer.bindPopup(properties.join("<br />"));
}

function mapFocus(state) {
    switch(state) {
        case "nm":
            mymap.setView(new L.LatLng(34, -105.87), 7);
            break;
        case "va":
            mymap.setView(new L.LatLng(37.7, -79.5), 8);
            break;
        case "ut":
            mymap.setView(new L.LatLng(39.3, -111.1), 7);
            break;
        default:
            mymap.setView(new L.LatLng(37.0902, 95.7129), 4);
    }
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