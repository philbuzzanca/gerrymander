package com.orioles.model;

import com.orioles.constants.Party;
import com.orioles.security.PasswordUtility;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	private Party party;

	public User() {}

	public User(String username, String password) {
		this.username = username;
		this.password = PasswordUtility.encode(password);
		this.party = Party.OTHER;
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
}
