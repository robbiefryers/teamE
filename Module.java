import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** Defines an object representing a single module
 */
public class Module  {

	//Class variable of ascii code for "1"
	private static final int ONECHAR = 48;

	//Declare Instance variables
	private String moduleCode;
	private String name;
	private int year;
	private char room;
	private String time;
	private int size;
	//These are critical in being able to update the timetable display easily
	private int roomIndex=-1;
	private int timeIndex=-1;


	//Default Constructor for Module
	public Module(String details) {

		String [] tokens = details.split(" ");
		moduleCode = tokens[0];
		name = tokens[1];
		year = moduleCode.charAt(2) - ONECHAR;
		time = tokens[2];
		room = tokens[3].charAt(0);
		size = Integer.parseInt(tokens[4]);

		if(room!='?')
			roomIndex = room - 'A';

		//To obtain the time index requires to check which day and then either morning or afternoon
		//Since the second letters of each day are different these are first used to determine the day
		//then whether the morning or afternoon can be obtained by checking if the 4th letter is either A or P
		char dayCH = time.charAt(1);
		char timeOfDayCH = time.charAt(3);

		if (dayCH!= '?'){
			switch (dayCH) {
			case 'o': timeIndex = 0; break;
			case 'u': timeIndex = 2; break;
			case 'e': timeIndex = 4; break;
			case 'h': timeIndex = 6; break;
			case 'r': timeIndex = 8; break;
			default: timeIndex = -1;
			}

			switch (timeOfDayCH) {
			case 'A': ; break;
			case 'P': timeIndex++; break;
			default: time = "?????";
			}

		}
	}


	public String getCode() {
		return moduleCode;
	}

	public int getYear() {
		return year;
	}

	public String getTime() {
		return time;
	}


	public int getRoomIndex() {
		return roomIndex;
	}

	public int getTimeIndex() {
		return timeIndex;
	}

	public char getRoom() {
		return room;
	}

	public int getSize() {
		return size;
	}

	public void setRoomInfo(int i) {
		roomIndex = i;
		if(i!= -1)
			room = (char) (i + 'A');
		else
			room = '?';

	}

	public void setTimeInfo(int i) {
		timeIndex = i;

		switch (i) {
		case 0: time = "MonAm"; break;
		case 1: time = "MonPM"; break;
		case 2: time = "TueAm"; break;
		case 3: time = "TuePM"; break;
		case 4: time = "WedAM"; break;
		case 5: time = "WedPM"; break;
		case 6: time = "ThuAm"; break;
		case 7: time = "ThuPM"; break;
		case 8: time = "FriAm"; break;
		case 9: time = "FriPM"; break;

		default: time = "?????";

		}
	}


}
