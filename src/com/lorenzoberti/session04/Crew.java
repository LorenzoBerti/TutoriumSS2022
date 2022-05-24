package com.lorenzoberti.session04;

/**
 * @author Lorenzo Berti
 *
 */
public class Crew {

	private String name;
	private String role;

	public Crew(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}