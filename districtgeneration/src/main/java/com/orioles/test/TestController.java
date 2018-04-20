package com.orioles.test;

import com.orioles.districtgeneration.Coordinate;
import com.orioles.model.CongressionalDistrict;
import com.orioles.model.Precinct;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
	@RequestMapping("/perimeter")
	public Double perimeterTest(){
		List<Coordinate> coordinates1 = setTestValues1();
		List<Coordinate> coordinates2 = setTestValues1();
		Precinct precinct1 = new Precinct(coordinates1);
		Precinct precinct2 = new Precinct(coordinates2);

		List<Precinct> ps = new ArrayList<>();
		ps.add(precinct1);
		ps.add(precinct2);
		CongressionalDistrict district = new CongressionalDistrict(ps, 0);
		return district.getPerimeter();
	}

	private List<Coordinate> setTestValues1(){
		List<Coordinate> values = new ArrayList<>();
		values.add(new Coordinate(-77.860192445970085, 39.153000129599988));
		values.add(new Coordinate(-77.862840901004034, 39.145148506742501));
		values.add(new Coordinate(-77.873346649637909, 39.135546360339532));
		values.add(new Coordinate(-77.882493430145587, 39.13126749010371));
		values.add(new Coordinate(-77.890680842089836, 39.124104499358367));
		values.add(new Coordinate(-77.894846268673575, 39.12255812437617));
		values.add(new Coordinate(-77.901913921588758, 39.109258987450232));
		values.add(new Coordinate(-77.908329981503584, 39.105391702329115));
		values.add(new Coordinate(-77.922432270010589, 39.103689828404484));
		values.add(new Coordinate(-77.941270672299098, 39.09459701735851));
		values.add(new Coordinate(-77.964414641212088, 39.101559769527825));
		values.add(new Coordinate(-77.971541328575967, 39.101701318509427));
		values.add(new Coordinate(-77.979735427909077, 39.102271493126807));
		values.add(new Coordinate(-77.981985108969184, 39.108858052962042));
		values.add(new Coordinate(-77.978950654353426, 39.112789065136568));
		values.add(new Coordinate(-77.972639717892449, 39.12712705129421));
		values.add(new Coordinate(-77.974670307540165, 39.130077377118212));
		values.add(new Coordinate(-77.970110213216429, 39.136925859030271));
		values.add(new Coordinate(-77.974420500189183, 39.141822794124323));
		values.add(new Coordinate(-77.971542530283955, 39.143864989417992));
		values.add(new Coordinate(-77.972279957224089, 39.159294478547906));
		values.add(new Coordinate(-77.977362371961775, 39.160408577405875));
		values.add(new Coordinate(-77.967945976788215, 39.177382918855457));
		values.add(new Coordinate(-77.964248685985098, 39.180243390359387));
		values.add(new Coordinate(-77.950284399123774, 39.184643159184034));
		values.add(new Coordinate(-77.924340753474468, 39.188333689258108));
		values.add(new Coordinate(-77.919381539481677, 39.191192369034653));
		values.add(new Coordinate(-77.860192445970085, 39.153000129599988));
		return values;
	}
}
