var mymap = L.map('mapid').setView([37.7, -79.5], 8);
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoicGhpbGJ1enphbmNhIiwiYSI6ImNqZTB1eGIzYzY0YWsyeHFoaDRwamlxcXoifQ.EzbAaOMVsKV5_OIks8_67w',
        {
            id: 'mapbox.light'
        }).addTo(mymap);

function onEachFeature(feature, layer) {
    'use strict';
    let properties = [];
    for (let property of Object.keys(feature.properties)) {
        properties.push(`${property}: ${feature.properties[property]}`);
    }
    layer.bindPopup(properties.join("<br />"));
}

function mapFocus(state) {
    if (state == "nm") {
        mymap.setView(new L.LatLng(34, -105.87), 7);
    } else if (state == "va") {
        mymap.setView(new L.LatLng(37.7, -79.5), 8);
    } else if (state == "ut") {
        mymap.setView(new L.LatLng(39.3, -111.1), 7);
    }
}

function getColor(d) {
    return 	d % 7 == 6 ? '#00FFFF' :
            d % 7 == 5 ? '#660066' :
            d % 7 == 4 ? '#FF66FF' :
            d % 7 == 3 ? '#FFFF66' :
            d % 7 == 2 ? '#FF0000' :
            d % 7 == 1 ? '#00FF00' :
            d % 7 == 0 ? '#0000FF' : '#FFFFFF'
}
//Colors precinct map based on county
function precinctStyle(feature) {
    return {
        fillColor: getColor(feature.properties.COUNTY || feature.properties.uscong_dis),
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