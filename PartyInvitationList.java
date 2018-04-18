import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PartyInvitationList {

	private static final double EARTH_RADIUS = 6371;

	public static void main(String[] args) throws FileNotFoundException {

		String fileName = "gistfile1.txt";
		
		List<JSONObject> jsonObject = ReadSingleLineOfJsonFile(fileName);

		List<DetailsInvitedPeople> listOfInvitedPeople = new ArrayList<DetailsInvitedPeople>();
		listOfInvitedPeople = listInvitedPeople(jsonObject);

		DisplayInvitedCustomers(listOfInvitedPeople);

	}


	public static void DisplayInvitedCustomers(List<DetailsInvitedPeople> listOfInvitedPeople) {

		Collections.sort(listOfInvitedPeople, new Comparator<DetailsInvitedPeople>() {

			@Override
			public int compare(DetailsInvitedPeople LHS, DetailsInvitedPeople RHS) {
				return LHS.getUserId().compareTo(RHS.getUserId());
			}
		});
		Iterator<DetailsInvitedPeople> iterator = listOfInvitedPeople.iterator();

		while (iterator.hasNext()) {
			DetailsInvitedPeople displayDetailsInvitedPeople = iterator.next();
			System.out.println(displayDetailsInvitedPeople.getUserId() + "\t" + displayDetailsInvitedPeople.getName());
		}

	}

	public static List<DetailsInvitedPeople> listInvitedPeople(List<JSONObject> jsonObject) {

		List<DetailsInvitedPeople> listOfInvitedPeople = new ArrayList<DetailsInvitedPeople>();
		double lattitudeDublinOffice = 53.3381985;
		double longitudeDublinOffice = -6.2592576;

		Iterator<JSONObject> iterator = jsonObject.iterator();

		while (iterator.hasNext()) {
			JSONObject jsonChildObject = (JSONObject) iterator.next();
			String longitudeCutomer = (String) jsonChildObject.get("longitude");
			String latitudeCutomer = (String) jsonChildObject.get("latitude");
			double doubleLongitudeCutomer = Double.parseDouble(longitudeCutomer);
			double doubleLatitudeCutomer = Double.parseDouble(latitudeCutomer);

			double differenceLongitudes = Math.abs(doubleLongitudeCutomer - longitudeDublinOffice);
			double distance = (Math.sin(Math.toRadians(lattitudeDublinOffice))
					* Math.sin(Math.toRadians(doubleLatitudeCutomer)))
					+ (Math.cos(Math.toRadians(lattitudeDublinOffice)) * Math.cos(Math.toRadians(doubleLatitudeCutomer))
							* Math.cos(Math.toRadians(differenceLongitudes)));

			double centralAngle = Math.acos(distance);

			double absoluteDistance = EARTH_RADIUS * centralAngle;

			if (absoluteDistance < 100) {
				DetailsInvitedPeople tempObjInvitePerson = new DetailsInvitedPeople();
				tempObjInvitePerson.setName((String) jsonChildObject.get("name"));
				tempObjInvitePerson.setUserId((Long) jsonChildObject.get("user_id"));
				listOfInvitedPeople.add(tempObjInvitePerson);
			}
		}

		return listOfInvitedPeople;
	}

	public static List<JSONObject> ReadSingleLineOfJsonFile(String fileName) throws FileNotFoundException {

		FileReader getFullJsonFile;
		Object obj;
		BufferedReader getJsonFileBuffer = null;
		JSONParser parser = new JSONParser();
		List<JSONObject> jsonObject = new ArrayList<JSONObject>();
		try {
			String sCurrentLine;
			getFullJsonFile = new FileReader(fileName);
			getJsonFileBuffer = new BufferedReader(getFullJsonFile);

			while ((sCurrentLine = getJsonFileBuffer.readLine()) != null) {
				obj = parser.parse(sCurrentLine);
				jsonObject.add((JSONObject) obj);
			}

			getJsonFileBuffer.close();
		} catch (IOException e) {
			System.err.println(" File not found. Please verify the File name or location");
		} catch (ParseException e) {
			System.err.println(" JSON file is not in proper format");
		}
		return jsonObject;
	}
}
