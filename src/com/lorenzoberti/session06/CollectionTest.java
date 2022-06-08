/**
 * 
 */
package com.lorenzoberti.session06;

import java.io.File;
import java.io.IOException;

/**
 * @author Lorenzo Berti
 *
 */
public class CollectionTest {

	// Write the path of the file in File("path of the file",
	// "name_of_the_file.csv")
	private static File file = new File("/home/lorenzo/git/TutoriumSS2022/src/com/lorenzoberti/session06",
			"guestlist.csv");

	private static long seed = 1037L;
	private static int size = 10;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		HotelGuest guestList = GuestList.getListFromCSV(file);

		for (int room : guestList.getOccupiedRooms()) {
			System.out.println("Room: " + room + "\tName: " + guestList.getGuestName(room));
		}

		System.out.println("------------------------");

		RandomToList random = new RandomToList();
		random.getList(seed, size).forEach(i -> System.out.println(i));

	}

}
