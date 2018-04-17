package com.orioles.model;

import com.orioles.constants.Party;
import com.orioles.constants.Race;

import java.util.Map;

public class Summary {
	public static void summarize(Map<Race, Long> races, Map<Party, Long> parties, Stats eachStat, Stats overallStats) {
		for (Race r : Race.values()) {
			races.put(r, races.get(r) + eachStat.getRaces().get(r));
		}

		for (Party p : Party.values()) {
			parties.put(p, parties.get(p) + eachStat.getParties().get(p));
		}

		overallStats.setPopulation(overallStats.getPopulation() + eachStat.getPopulation());
	}
}
