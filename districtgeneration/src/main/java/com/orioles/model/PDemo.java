package com.orioles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PDemo {
	@Id
	private int pid;
	private int hispanic;
	private int white;
	private int black;
	@Column(name = "Native")
	private int nativeamerican;
	private int asian;
	@Column(name = "Pacific")
	private int pacificislander;
	private int other;
	private int multiple;
	private int population;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getHispanic() {
		return hispanic;
	}

	public void setHispanic(int hispanic) {
		this.hispanic = hispanic;
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getNativeamerican() {
		return nativeamerican;
	}

	public void setNativeamerican(int nativeamerican) {
		this.nativeamerican = nativeamerican;
	}

	public int getAsian() {
		return asian;
	}

	public void setAsian(int asian) {
		this.asian = asian;
	}

	public int getPacificislander() {
		return pacificislander;
	}

	public void setPacificislander(int pacificislander) {
		this.pacificislander = pacificislander;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
