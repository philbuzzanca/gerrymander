package com.orioles.model;

import com.orioles.constants.Party;
import com.orioles.security.PasswordUtility;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	private Party party;
	@Transient
	private List<Usermoves> previousRedistrictings;
	private boolean admin;

	public User() {}

	public User(String username, String password) {
		this.username = username;
		this.password = PasswordUtility.encode(password);
		this.party = Party.OTHER;
		this.previousRedistrictings = new ArrayList<>();
		this.admin = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
        
	public void setAllRedistrictings(List<Usermoves> redistrictingList){
		this.previousRedistrictings = redistrictingList;
	}

	public List<Usermoves> getAllRedistrictings(){
		return this.previousRedistrictings;
	}

	public void addRedistricting(Usermoves moveSet){
		previousRedistrictings.add(moveSet);
	}

	public List<Move> getRedistricting(int index) {
		return null;
//		return previousRedistrictings.get(index).getMoves();
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
