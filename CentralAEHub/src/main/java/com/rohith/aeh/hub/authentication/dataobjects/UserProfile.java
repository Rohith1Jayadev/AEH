package com.rohith.aeh.hub.authentication.dataobjects;

import java.util.List;

import com.rohith.aeh.hub.util.token.Scope;

public class UserProfile {

	private String userName;
	
	private String companyName;
	
	private String address;
	
	private String designation;

	private List<Scope> scopes;
	
	public String getUserName() {
		return userName;
	}

	public UserProfile setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public UserProfile setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public UserProfile setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getDesignation() {
		return designation;
	}

	public UserProfile setDesignation(String designation) {
		this.designation = designation;
		return this;
	}
	
	public List<Scope> getScopes() {
		return scopes;
	}

	public UserProfile setScopes(List<Scope> scopes) {
		this.scopes = scopes;
		return this;
	}
	
	
}
