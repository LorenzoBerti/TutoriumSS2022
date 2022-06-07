/**
 * 
 */
package com.lorenzoberti.session06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * @author Lorenzo Berti
 *
 */
public class GuestList implements HotelGuest {


	/**
	 * Import a guest list via CSV file.
	 *
	 * @param csv The file.
	 * @return The guest list
	 * @throws IOException
	 */
	public static HotelGuest getListFromCSV(File csv) throws IOException {

		// TODO

		BufferedReader csvReader = new BufferedReader(new FileReader(csv));
		String row;
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			// TODO You can use this reader. data[0] is room number as String, data[1] is
			// guest name.
		}
		csvReader.close();

		return null;
	}

	@Override
	public String getGuestName(int roomNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getGuests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Integer> getOccupiedRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkout(int roomNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkin(int roomNumber, String guestName) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}


}
