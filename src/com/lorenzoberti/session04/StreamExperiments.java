package com.lorenzoberti.session04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Lorenzo Berti
 *
 */
public class StreamExperiments {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Stream creation

		// Create a Stream from an existing array or giving some values
		String[] array = new String[] { "a", "b", "c" };

		Stream<String> stream1 = Arrays.stream(array);
		Stream<String> stream2 = Stream.of(array);
		Stream<String> stream3 = Stream.of("a", "b", "c");

		// Using the method generate() we have to give also a limit, othertwise it
		// creates an "infinite" dimension stream (try to println without limit)
		Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);
		System.out.println(Arrays.toString(streamGenerated.toArray()));

		// Using the method iterate() we create an infinite stream starting from a
		// value and creating all the following elements performing some operation
		Stream<Integer> streamIterate = Stream.iterate(10, n -> n + 1).limit(10);
		System.out.println(Arrays.toString(streamIterate.toArray()));

		// For the primitive types int, long and double we have the interfaces
		// IntStream, LongStream and DoubleStream
		IntStream streamInt = IntStream.range(0, 10); // a way to create a stream of int
		System.out.println(Arrays.toString(streamInt.toArray()));
		DoubleStream streamDouble = DoubleStream.generate(() -> Math.random()).limit(10); // stream of uniform random
																							// numbers
		System.out.println(Arrays.toString(streamDouble.toArray()));

		// We can use also a Supplier to create a stream (from an existing Stream)
		// The supplier allows us to "reuse" our stream
		Stream<Double> streamDouble2 = Stream.generate(() -> Math.random()).limit(10);
		Supplier<Stream<Double>> streamSupplier = () -> streamDouble2;
		Stream<Double> randomStream = streamSupplier.get();
		System.out.println(randomStream.collect(Collectors.toList()));
		// Note: you cannot use the DoubleStream interface in this case
		// DoubleStream randomStream = streamSupplier.get();

		// Now let's see how to deal with Stream

		List<Crew> enterprise = new ArrayList<>();
		enterprise.add(new Crew("Kirk", "Captain"));
		enterprise.add(new Crew("Spock", "First Officer"));
		enterprise.add(new Crew("McCoy", "Doctor"));
		enterprise.add(new Crew("Scott", "Commander Liutenant"));
		enterprise.add(new Crew("Uhura", "Lieutenant"));
		enterprise.add(new Crew("Sulu", "Lieutenant"));

		// A list can be "streammed" with the method stream()
		// Stream<Crew> streamEnterprise = enterprise.stream();

		// Use the Supplier to create the stream
		Supplier<Stream<Crew>> enterpriseSupplier = () -> enterprise.stream();
		Stream<Crew> enterprise1 = enterpriseSupplier.get();

		// We want to see who are the lieutenants of the crew: we first "filter" the
		// data and then print
		enterprise1.filter(member -> member.getRole() == "Lieutenant")
				.forEach(member -> System.out.println(member.getName()));

		// Note: forEach is a final operation!
		// enterprise1.count();

		// Now we want the graduate our lieutenants
		Stream<Crew> enterprise2 = enterpriseSupplier.get();
		enterprise2.filter(member -> member.getRole() == "Lieutenant")
				.map(member -> new Crew(member.getName(), "Commander Lieutenant"))
				.forEach(member -> System.out.println(member.getName() + "\t" + member.getRole()));

		// But we lost the others!!
		Stream<Crew> enterprise3 = enterpriseSupplier.get();
		enterprise3.map(member -> {
			String role = member.getRole();
			if (role == "Lieutenant") {
				return new Crew(member.getName(), "Commander Lieutenant");
			} else
				return new Crew(member.getName(), role);
		}).collect(Collectors.toList())
				.forEach(member -> System.out.println(member.getName() + "\t" + member.getRole()));

		// Note the peek method: it is an intermediary operation (i.e. it returns a
		// stream)
		Stream<Crew> enterprise4 = enterpriseSupplier.get();
		List<Crew> enterpriseList = enterprise4.map(member -> {
			String role = member.getRole();
			if (role == "Lieutenant") {
				return new Crew(member.getName(), "Commander Lieutenant");
			} else
				return new Crew(member.getName(), role);
		}).peek(member -> System.out.println(member.getName() + "\t" + member.getRole())).collect(Collectors.toList());

		Stream<Crew> enterprise5 = enterpriseSupplier.get();
		Crew crew = enterprise5.filter(member -> member.getRole() == "Lieutenant").findFirst().orElse(null);
		System.out.println(crew.getName());

		Stream<Crew> enterprise6 = enterpriseSupplier.get();
		long numbers = enterprise6.filter(member -> member.getRole() == "Lieutenant").count();
		System.out.println(numbers);

	}

}