package com.orioles.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Usermoves implements Serializable {
	@Id
	private int uselessid;
	private String username;
	private String moves;

	public Usermoves() {}

	public int getUselessid() {
		return uselessid;
	}

	public void setUselessid(int uselessid) {
		this.uselessid = uselessid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMoves() {
		return moves;
	}

	public void setMoves(String moves) {
		this.moves = moves;
	}
}
