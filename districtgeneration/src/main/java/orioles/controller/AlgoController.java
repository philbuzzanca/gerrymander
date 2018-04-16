package orioles.controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orioles.districtgeneration.Compactness;
import orioles.model.Algorithm;
import orioles.model.CongressionalDistrict;
import orioles.model.Measure;
import orioles.model.Precinct;

@RestController
public class AlgoController {
    @RequestMapping("/compactness")
    public Double compactnessTest(){
       CongressionalDistrict district = new CongressionalDistrict();
       Measure measure = new Compactness();
       double goodness = measure.calculateGoodness(district);
       return goodness;
    }
    
    @RequestMapping("/perimeter")
    public Double perimeter(){
        //Stubbed district info
        ArrayList<Point2D.Double> coordinates1 = setTestValues1();
        ArrayList<Point2D.Double> coordinates2 = setTestValues2();
        Precinct precinct1 = new Precinct();
        precinct1.setCoordinates(coordinates1);
        Precinct precinct2 = new Precinct();
        precinct2.setCoordinates(coordinates2);
        
        CongressionalDistrict district = new CongressionalDistrict();
        district.addToDistrict(precinct1);
        district.addToDistrict(precinct2);

        return district.calculatePerimeter();
    }
    
    @RequestMapping("/area")
    public String area(){
        return "OK";
    }
    
    @RequestMapping("/algorithm")
    public String startAlgo(){
        Algorithm algo = new Algorithm();
        CongressionalDistrict district1 = new CongressionalDistrict();
        CongressionalDistrict district2 = new CongressionalDistrict();
        return "OK";
    }
    
    
    
    
    public ArrayList<Point2D.Double> setTestValues1(){
        ArrayList<Point2D.Double> values = new ArrayList<>();
        values.add(new Point2D.Double(-77.860192445970085, 39.153000129599988));
        values.add(new Point2D.Double(-77.862840901004034, 39.145148506742501));
        values.add(new Point2D.Double(-77.873346649637909, 39.135546360339532));
        values.add(new Point2D.Double(-77.882493430145587, 39.13126749010371));
        values.add(new Point2D.Double(-77.890680842089836, 39.124104499358367));
        values.add(new Point2D.Double(-77.894846268673575, 39.12255812437617));
        values.add(new Point2D.Double(-77.901913921588758, 39.109258987450232));
        values.add(new Point2D.Double(-77.908329981503584, 39.105391702329115));
        values.add(new Point2D.Double(-77.922432270010589, 39.103689828404484));
        values.add(new Point2D.Double(-77.941270672299098, 39.09459701735851));
        values.add(new Point2D.Double(-77.964414641212088, 39.101559769527825));
        values.add(new Point2D.Double(-77.971541328575967, 39.101701318509427));
        values.add(new Point2D.Double(-77.979735427909077, 39.102271493126807));
        values.add(new Point2D.Double(-77.981985108969184, 39.108858052962042));
        values.add(new Point2D.Double(-77.978950654353426, 39.112789065136568));
        values.add(new Point2D.Double(-77.972639717892449, 39.12712705129421));
        values.add(new Point2D.Double(-77.974670307540165, 39.130077377118212));
        values.add(new Point2D.Double(-77.970110213216429, 39.136925859030271));
        values.add(new Point2D.Double(-77.974420500189183, 39.141822794124323));
        values.add(new Point2D.Double(-77.971542530283955, 39.143864989417992));
        values.add(new Point2D.Double(-77.972279957224089, 39.159294478547906));
        values.add(new Point2D.Double(-77.977362371961775, 39.160408577405875));
        values.add(new Point2D.Double(-77.967945976788215, 39.177382918855457));
        values.add(new Point2D.Double(-77.964248685985098, 39.180243390359387));
        values.add(new Point2D.Double(-77.950284399123774, 39.184643159184034));
        values.add(new Point2D.Double(-77.924340753474468, 39.188333689258108));
        values.add(new Point2D.Double(-77.919381539481677, 39.191192369034653));
        values.add(new Point2D.Double(-77.860192445970085, 39.153000129599988));
       
        return values;
    }
    
    public ArrayList<Point2D.Double> setTestValues2(){
        ArrayList<Point2D.Double> values = new ArrayList<>();
        values.add(new Point2D.Double(-77.860192445970085, 39.15300012959998));
        values.add(new Point2D.Double(-77.828292998532064, 39.132416754148437));
        values.add(new Point2D.Double(-77.828280321518847, 39.132408574203538));
        values.add(new Point2D.Double(-77.828326223685181, 39.132356344616042));
        values.add(new Point2D.Double(-77.83892474321199, 39.121858421492973));
        values.add(new Point2D.Double(-77.843815393037019, 39.119740053307041));
        values.add(new Point2D.Double(-77.845776346796839, 39.116067635459622));
        values.add(new Point2D.Double(-77.858832613282118, 39.117259691604616));
        values.add(new Point2D.Double(-77.879907874368911, 39.122431127549511));
        values.add(new Point2D.Double(-77.890680842089836, 39.124104499358367));
        values.add(new Point2D.Double(-77.882493430145587, 39.13126749010371));
        values.add(new Point2D.Double(-77.873346649637909, 39.135546360339532));
        values.add(new Point2D.Double(-77.862840901004034, 39.145148506742501));
        values.add(new Point2D.Double(-77.860192445970085, 39.153000129599988));
        return values;
    }
}

