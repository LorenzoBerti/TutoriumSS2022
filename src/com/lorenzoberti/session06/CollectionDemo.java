/**
 * 
 */
package com.lorenzoberti.session06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lorenzoberti.session04.Crew;

/**
 * @author Lorenzo Berti
 *
 */
public class CollectionDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Collection of generic type. Instead of specify the type of object you can
		// simply put the generic <?>.
		Collection<?> collection;


		// Collection just contains objects, i.e. references. What you get from a
		// Collection is iteration: Collection is iterable
		Collection<Double> collectionDouble = null;

//		for (double i : collectionDouble) {
//			// do something
//		}

		// Also array are iterable!
		double[] array = new double[] { 1, 2, 3 };
		for (double i : array) {
			// do something
		}

		double d2 = array[1];

		// Types of collection, i.e. they extend the Collection interface and they are
		// kind of container of objects.

		// List is and ordered Collection (but may or may not be sorted)
		List<?> list;

		// Set it correspond closely to a mathematic set: not ordered but each element
		// is unique
		Set<?> set;
		
		// Map is not properly a Collection but it is based on Collections.
		// It specifies two elements: key and value-->Collection of pairs
		Map<?, ?> map;

		// Let's see more in deep List

		// Immutable list: once it is created it can never be changed
		List<Integer> intList = List.of(1, 2, 3);
		// intList.add(1); // It complains!

		double d = 1;
		Double D = 1D;
		List<Double> doubleList = List.of(d, D, Double.valueOf(3), 4.0);
		// here Java is creating a Double with the value 1, indeed if you take it you
		// see that is a Reference type
		System.out.println(doubleList.get(0).getClass());

		// We can be even more specific

		List<Double> arrayList = new ArrayList<>();
		// the ArrayList is modifiable; differently from an array, i.e. if I create an
		// array e.g. double[5] I can modify the entries but I cannot extend it, with
		// ArrayList I can

		// I can use a collection to create an ArrayList: I can use any Collection
		// that extends Double
		List<Double> arrayList2 = new ArrayList<>(doubleList); // Now it is going to be modifiable: 100% control on the
																// list
		// Note the difference
		int a = 10;
		// arrayList2.add(a); // it complains because we have a list of Double and we
		// are adding an Integer
		// but...
		List rawList = new ArrayList<>(doubleList);
		rawList.add(a); // It does not complain!
		// This is because of generic (we don't specify the type), but if you use a list
		// you want objects of the same type so be careful with generic
		System.out.println(rawList.get(4).getClass());

		System.out.println("---------------------------------");

		// Let's see Set
		Set<Crew> enterpriseSet1 = new HashSet<Crew>();

		List<Crew> enterprise1 = new ArrayList<>();

		Crew kirk = new Crew("Kirk", "Captain");
		Crew spock = new Crew("Spock", "First Officer");
		Crew mcCoy = new Crew("McCoy", "Doctor");
		Crew scott = new Crew("Scott", "Commander Liutenant");
		Crew uhura = new Crew("Uhura", "Lieutenant");
		Crew sulu = new Crew("Sulu", "Lieutenant");

		enterprise1.add(kirk);
		enterprise1.add(spock);
		enterprise1.add(mcCoy);
		enterprise1.add(scott);
		enterpriseSet1.addAll(enterprise1);

		// Note that if I add another kirk it will not be stored
		enterpriseSet1.add(kirk);
		enterpriseSet1.forEach(n -> System.out.println(n.getName()));
		System.out.println("---------------------------------");

		Set enterpriseSet2 = Set.of(kirk, spock, uhura, sulu); // Note: in this case it is immutable

		// Union
		Set<Crew> bigEnterprise = new HashSet<Crew>(enterpriseSet1);
		bigEnterprise.addAll(enterpriseSet2);
		System.out.print("Union of the two starships:\n");
		System.out.println(bigEnterprise);

		System.out.println("---------------------------------");

		// Intersection
		Set<Crew> smallEnterprise = new HashSet<Crew>(enterpriseSet1);
		smallEnterprise.retainAll(enterpriseSet2);
		System.out.print("Intersection of the two starships:\n");
		System.out.println(smallEnterprise);

		System.out.println("---------------------------------");

		// Difference
		Set<Crew> differenceEnteprise = new HashSet<Crew>(enterpriseSet1);
		differenceEnteprise.removeAll(enterpriseSet2);
		System.out.print("Difference of the two starships:\n");
		System.out.println(differenceEnteprise);

		System.out.println("---------------------------------");

		// HashMap<Key,Value>
		HashMap<String, Integer> hashMap = new HashMap<>();

		// Adding elements to the Map using put() method
		hashMap.put("Lorenzo", 25);
		hashMap.put("Andrea", 30);
		hashMap.put("Qiqi", 25);

		// Size of the Map
		System.out.println("Size of map is: " + hashMap.size());

		// Print elements in object of Map
		System.out.println(hashMap);

		// Check if a key is present and in case print the value
		if (hashMap.containsKey("Lorenzo")) {

			// Extract the value giving the key
			Integer age = hashMap.get("Lorenzo");
			// Print value of the corresponding key
			System.out.println("Value for key" + " \"Lorenzo\" is: " + age);
		}

		// Change an element. Note that a HashMap cannot have two identical keys
		hashMap.put("Lorenzo", 26);
		System.out.println(hashMap);

		System.out.println("-------------------------------------");

		// Since it is a Collection it is iterable
		for (String i : hashMap.keySet()) {
			System.out.println(i);
		}

		System.out.println("-------------------------------------");

		for (Integer i : hashMap.values()) {
			System.out.println(i);
		}

		System.out.println("-------------------------------------");

		for (String i : hashMap.keySet()) {
			System.out.println("Key: " + i + "\tValue: " + hashMap.get(i));
		}

		System.out.println("-------------------------------------");

		// Iterate through the pairs
		for (Map.Entry<String, Integer> e : hashMap.entrySet()) {
			System.out.println("Key: " + e.getKey() + "\tValue: " + e.getValue());
		}


	}
}