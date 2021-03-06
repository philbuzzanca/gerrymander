'use strict';
function getColor(d) {
    const COLORS = ['#00FFFF', '#660066', '#FF66FF', '#FFFF66', '#FF0000', '#00FF00', '#0000FF', '#FFFFFF'];
    return COLORS[d % 7];
}

function build(state, constraints, measures){
    $('#startAlgo').hide();
    $('#runningAlgo').show();
    let formData = {measure: measures, constraint: constraints, state : state};
    $.post("/startAlgo", formData, function (data) {
        console.log("Settings sent and received gerrymandered districts to load");
        console.log(data);
        let maxGoodness = 0; 
        for(var district in data){
            if(data[district] > maxGoodness) maxGoodness = data[district];
        }
        currentLayer.eachLayer(function(layer){
            let relativeGoodness = 255 - Math.floor(data[layer.feature.properties.CD] / maxGoodness * 255);
            let heat = "#" + relativeGoodness.toString(16) + "0000";
            console.log(heat);
            layer.setStyle({
                fillColor : heat,
                fillOpacity : 1
            });
        });
    });
}

var firstPlay = true;

$(document).ready(function(){
    $('#runningAlgo').hide();
    $('#pause').hide();
    $("#startAlgoBtn").click((event) => {
        event.preventDefault();

        console.log("Sending Settings!");
        let constraints = {
            community : $('#preserveExistingCommunitiesCheck').val(),
            contiguity : $('#contiguityCheck').val()
        };

        let measures = {
            compactness : $('#compactnessSlider').val(),
            equal_population : $('#equalPopSlider').val(),
            racial_fairness : $('#racialSlider').val(),
        };

        build($('#stateSelect').val(), constraints, measures);
    });
    $("#play").click(() => {
        if(firstPlay) {
            firstPlay = false;
            currentLayer.eachLayer(function(layer){
            layer.setStyle({
                fillColor : getColor(layer.feature.properties.CD),
                fillOpacity : 0.3
            });
        });
        }
        // $("#play").hide();
        // $("#pause").show();
        $.post("/runIteration").then(function(res){
            // res will contain the array of moves to make (array of Move objects)
            /**
             * Move contains:
             * sourceDistrict(int)
             * destDistrict(int)
             * precinct(int)
             */ 
            for(let i = 0; i < res.length; i++){
                let src = res[i].sourceDistrict;
                let dest = res[i].destDistrict;
                let precinct = res[i].precinct;
                currentLayer.eachLayer(function(layer){
                    if (parseInt(layer.feature.properties.CODE) === precinct){
                        layer.setStyle({
                            fillColor : getColor(dest)
                        });
                    }
                });
            }
        }).catch(() => console.log("ERR!"));
    });
    $("#pause").click(() => {
        $("#play").show();
        $("#pause").hide();
    });
    $("#stop").click(() => {
        $("#play").show();
        $("#pause").hide();
        $('#runningAlgo').hide();
        $('#startAlgo').show();
    });
});
