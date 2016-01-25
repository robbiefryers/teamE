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
	private String room;
	private String time;
	private int size;
	//These are critical in being able to update the timetable display easily
	private int roomIndex=-1;


	//Default Constructor for Module
	public Module(String details) {

		String [] tokens = details.split(" ");
		moduleCode = tokens[0];
		name = tokens[1];
		year = moduleCode.charAt(2) - ONECHAR;
		time = tokens[2];
		room = tokens[3];
		size = Integer.parseInt(tokens[4]);

		if(room.charAt(0)!='?')
			roomIndex = room.charAt(0) - 'A';
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

	public String getRoom() {
		return room;
	}

	public int getSize() {
		return size;
	}
	

	public void setTime(String s) {
		time = s;
	}
	
	public void setRoomIndex(int i) {
		roomIndex = i;
	}


}
