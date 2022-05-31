package com.lorenzoberti.session05;

/**
 * @author Lorenzo Berti
 *
 */
public class Person {

	private String name;

	private int birthYear;

	public Person(String name) {
		super();
		this.name = name;
		this.birthYear = 1996;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getBirthYear() {
		return this.birthYear;
	}

	@Override
	public String toString() {
		return name + "(" + birthYear + ")";
	}

//	public String print() {
//		return name + "(" + birthYear + ")";
//	}

}
