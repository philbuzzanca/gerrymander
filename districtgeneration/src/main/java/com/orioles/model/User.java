package com.orioles.model;

import com.orioles.constants.Party;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String username;
    private String password;
    private String email;

    //@Column(name="is_activated")
    //private boolean isActivated;
    private Party party;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.party = Party.OTHER;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public boolean isIsActivated() {
        return isActivated;
    }*/

    /*public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }*/

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    @Override
    public String toString() {
        return String.format("User: {name: %s, password: %s, party: %s}", username, password, party);
    }
}
