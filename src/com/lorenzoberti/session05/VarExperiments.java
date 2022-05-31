/**
 * 
 */
package com.lorenzoberti.session05;

import java.util.List;

import com.lorenzoberti.session04.Crew;

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

		// Types of primitives:
		boolean b = true;
		byte by = 1; // 8 bits, values between -128 and 127
		short sh = 1; // 16 bits, values between -32768 and 32767
		int i = 1; // 32 bits, values between -2^31 and 2^31-1
		long l = 1l; // 64 bits, values between -2^63 and 2^63-1
		float f = 1.0f; // floating point number 32 bits
		double d = 1.0d; // floating point number 64 bits
		char c = 'c'; // represents a single character

		// Example of references
		String s = "String\n";
		Double D = 1.0d;

		Long ref1 = Long.valueOf(2);
		Long ref2 = Long.valueOf(2);
		Long ref3 = new Long(2); // DEPRECATED: this constructor is not recommended -> better choice: static
									// factory valueOf(long) is a better choice (for time and space)

		// ref1 and ref2 may point to the same object --> share memory
		// ref3 not because we use the constructor

		Long ref4 = 2L; // without L it complains

		Math.exp(D); // internally, it extracts D.doubleValue()
		Math.exp(D.doubleValue()); // it returns the primitve

		Double.isFinite(d); // it creates an object (a reference) which stores the value of the primitive
							// and uses the method
		D.isInfinite();

		Person Lorenzo = new Person("Lorenzo");

		System.out.println(Lorenzo); // it prints the type of the variable send an identifier created by Java
		// System.out.println(lorenzo.print()); // note that if you change the name of

		// you have an override and automatically prints (without calling it)
		// This is why the next example works
		System.out.println(d);
		System.out.println(D);

		System.out.println("------------------------");

		// The constructor creates an object Person whose birth year is set to 1996 and
		// whose
		// name is set to the value received as a parameter. The constructor returns a
		// reference
		Person lorenzo = new Person("Lorenzo");
		System.out.println(lorenzo);
		makeYounger(lorenzo);
		System.out.println(lorenzo);

		// The value of the variable lorenzo is copied to the variable andrea, i.e. the
		// value of the variable andrea becomes a reference to the already-existing
		// Person object.
		Person andrea = lorenzo;
		makeYounger(andrea);
		System.out.println(lorenzo);

		System.out.println("------------------------");

		System.out.println("check Person 1");
		if (lorenzo == andrea) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		System.out.println("------------------------");

		// Let's create a new "Lorenzo" and check if it is equal to the original one:
		// they
		// have same name and same birthday year
		Person lorenzo2 = new Person("Lorenzo");

		System.out.println("check Person 2");
		if (Lorenzo == lorenzo2) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		// In the first case they are equal because they have the same memory address,
		// in the second not.
		// The operator == compare the memory locations of the two objects

		System.out.println("------------------------");

		System.out.println("check Person 3");
		if (Lorenzo.equals(lorenzo2)) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		System.out.println("------------------------");

		// == checks if the objects are the same
		System.out.println("check 1");
		if (d == 1) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		System.out.println("------------------------");

		System.out.println("check 2");
		if (D == 1) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		System.out.println("------------------------");

		System.out.println("check 3");
		// this forces to create a new object and this check if the objects are the same
		if (D == new Double(1)) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		System.out.println("------------------------");

		System.out.println("check 4");
		// here I don't know if it is going to be true or not because I don't know if it
		// creates a new object or not: take careful!
		if (D == Double.valueOf(1)) {
			System.out.println("They are the same");
		} else
			System.out.println("No, they are not the same");

		System.out.println("------------------------");

		// Take careful with == working with reference because it checks if the objects
		// are the same
		// not if the value is the same --> better use equals method

		System.out.println("check 5");
		if (D.equals(new Double(1))) {
			System.out.println("They are equal");
		} else
			System.out.println("No, they are not equal");

		System.out.println("------------------------");

		System.out.println("Var experiments: \n");

		// Java 10
		// var type: can be used for local variable: the compiler deduces which type of
		// object it is
		int a = 10;
		var z = 10;

		if (a == z) {
			System.out.println("They are the same");
		} else
			System.out.println("They are not the same");

		System.out.println("------------------------");

		Double Dbis = 2.0;
		var varD = 2.0;

		if (Dbis.equals(varD)) {
			System.out.println("They are are equal");
		} else
			System.out.println("No, they are not equal");

		System.out.println("------------------------");

		System.out.println(D.getClass());

		// var prova = null; // it complains: it cannot be initialized with null
		// var[] array = new int[10]; // it complains
		var array2 = new int[10]; // it is fine

		// var and methods
		var m = sum(a, z);

		// var and lambda expression
		// DoubleBinaryOperator multiplier = (x,y) -> x*y;

//		var multiplier = (x,y) -> x*y;   //  it complains!
		// you have to help the compiler to understand at least the type of lambda
		// expression: cast
//		var multiplier = (DoubleBinaryOperator) (x,y) -> x*y;        

//		explicitTypes();
//		genericTypes();

	}

	public static void makeYounger(Person person) {
		person.setBirthYear(person.getBirthYear() + 1);
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
	public static double sum(double a, double b) {

		var sum = a + b;

		return sum;
	}

}
