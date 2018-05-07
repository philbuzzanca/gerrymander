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
        private ArrayList<ArrayList<Move>> previousRedistrictings;

	public User() {}

	public User(String username, String password) {
		this.username = username;
		this.password = PasswordUtility.encode(password);
		this.party = Party.OTHER;
                this.previousRedistrictings = new ArrayList();
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
        
        public void setAllRedistrictings(ArrayList<ArrayList<Move>> redistrictingList){
            this.previousRedistrictings = redistrictingList;
        }
        
        public ArrayList<ArrayList<Move>> getAllRedistrictings(){
            return this.previousRedistrictings;
        }
        
        public void addRedistricting(ArrayList<Move> moveSet){
            previousRedistrictings.add(moveSet);
        }
        
        public ArrayList<Move> getRedistricting(int index){
            return previousRedistrictings.get(index);
        }
}
