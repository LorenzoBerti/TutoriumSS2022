/**
 * 
 */
package com.lorenzoberti.session06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Lorenzo Berti
 *
 */
public class GuestList implements HotelGuest {

	private final Map<Integer, String> guestList = new HashMap<Integer, String>();


	/**
	 * Import a guest list via CSV file.
	 *
	 * @param csv The file.
	 * @return The guest list
	 * @throws IOException
	 */
	public static HotelGuest getListFromCSV(File csv) throws IOException {

		GuestList guests = new GuestList();

		BufferedReader csvReader = new BufferedReader(new FileReader(csv));
		String row;
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			guests.guestList.put(Integer.parseInt(data[0]), data[1]);
		}
		csvReader.close();

		return guests;
	}

	@Override
	public String getGuestName(int roomNumber) {
		// TODO Auto-generated method stub
		return guestList.get(roomNumber);
	}

	@Override
	public Collection<String> getGuests() {
		// TODO Auto-generated method stub
		return guestList.values();
	}

	@Override
	public Set<Integer> getOccupiedRooms() {
		// TODO Auto-generated method stub
		return guestList.keySet();
	}

	@Override
	public void checkout(int roomNumber) {
		// TODO Auto-generated method stub
		guestList.remove(roomNumber);

	}

	@Override
	public void checkin(int roomNumber, String guestName) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		if (guestList.containsKey(roomNumber)) {
			throw new IllegalArgumentException(
					"Cannot checkin to room " + roomNumber + ". The room is already occupied.");
		}

		guestList.put(roomNumber, guestName);

	}


}
