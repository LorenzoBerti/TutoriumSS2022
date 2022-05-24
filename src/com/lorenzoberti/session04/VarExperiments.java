package com.lorenzoberti.session04;

import java.util.List;

/**
 * @author Lorenzo Berti
 *
 */
public class VarExperiments {

	// You cannot use the var type as field of the class! It can be used only
	// locally
	// var generic;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Java 10
		// Var type
		int a = 10;
		var b = 10;

		if (a == b) {
			System.out.println("They are the same object");
		} else
			System.out.println("They are different");

		explicitTypes();
		genericTypes();

	}

	// Old Java
	public static void explicitTypes() {
		Crew kirk = new Crew("Kirk", "Captain");
		Crew spock = new Crew("Spock", "First Officer");
		Crew bones = new Crew("McCoy", "Doctor");
		Crew scott = new Crew("Scott", "Commander Liutenant");
		Crew uhura = new Crew("Uhura", "Lieutenant");
		Crew sulu = new Crew("Sulu", "Lieutenant");

		List<Crew> enterprise = List.of(kirk, spock, bones, scott, uhura, sulu);

		for (Crew member : enterprise) {
			System.out.println(member.getName() + " \t " + member.getRole());
		}

	}

	// New Java 10
	public static void genericTypes() {
		var kirk = new Crew("Kirk", "Captain");
		var spock = new Crew("Spock", "First Officer");
		var bones = new Crew("McCoy", "Doctor");
		var scott = new Crew("Scott", "Commander Liutenant");
		var uhura = new Crew("Uhura", "Lieutenant");
		var sulu = new Crew("Sulu", "Lieutenant");

		List<Crew> enterprise = List.of(kirk, spock, bones, scott, uhura, sulu);

		for (var member : enterprise) {
			System.out.println(member.getName() + " \t " + member.getRole());
		}

	}

	// You cannot give a parameter as var or set return type as var
	public double sum(double a, double b) {

		var sum = a + b;

		return sum;
	}

}
