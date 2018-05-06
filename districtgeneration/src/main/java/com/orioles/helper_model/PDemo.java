package com.orioles.helper_model;

import com.orioles.constants.Party;
import com.orioles.constants.Race;
import com.orioles.model.Stats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PDemo {
	@Id
	private int pid;
	private long hispanic;
	private long white;
	private long black;
	@Column(name = "Native")
	private long nativeamerican;
	private long asian;
	@Column(name = "Pacific")
	private long pacificislander;
	private long other;
	private long multiple;

	public long getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public long getHispanic() {
		return hispanic;
	}

	public void setHispanic(long hispanic) {
		this.hispanic = hispanic;
	}

	public long getWhite() {
		return white;
	}

	public void setWhite(long white) {
		this.white = white;
	}

	public long getBlack() {
		return black;
	}

	public void setBlack(long black) {
		this.black = black;
	}

	public long getNativeamerican() {
		return nativeamerican;
	}

	public void setNativeamerican(long nativeamerican) {
		this.nativeamerican = nativeamerican;
	}

	public long getAsian() {
		return asian;
	}

	public void setAsian(long asian) {
		this.asian = asian;
	}

	public long getPacificislander() {
		return pacificislander;
	}

	public void setPacificislander(long pacificislander) {
		this.pacificislander = pacificislander;
	}

	public long getOther() {
		return other;
	}

	public void setOther(long other) {
		this.other = other;
	}

	public long getMultiple() {
		return multiple;
	}

	public void setMultiple(long multiple) {
		this.multiple = multiple;
	}

	public Stats makeStat() {
		Map<Race, Long> races = new HashMap<>();
		races.put(Race.HISPANIC, hispanic);
		races.put(Race.WHITE, white);
		races.put(Race.BLACK, black);
		races.put(Race.NATIVE, nativeamerican);
		races.put(Race.ASIAN, asian);
		races.put(Race.PACIFIC, pacificislander);
		races.put(Race.OTHER, other);
		races.put(Race.MULTIPLE, multiple);

		Map<Party, Long> parties = new HashMap<>();
		for (Party p : Party.values()) {
			parties.put(p, 0L);
		}
		return new Stats(races, parties, hispanic + white + black
				+ nativeamerican + asian + pacificislander + other + multiple);
	}
}
