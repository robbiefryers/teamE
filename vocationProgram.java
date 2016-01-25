
/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class vocationProgram {

	//class constant
	private static final int NUMROOMS = 24;

	//instance variables
	private Module [] modules;
	private Object [][] rooms;
	private Object [][] timeIndex;
	private int arrayIndex = 0;
	private int headerIndex = 0;


	//Constructor Initialises vocation program
	public vocationProgram(int num) {
		modules = new Module[num];
		setRoomInfo();
		setTimeIndex();

	}


	public void newClass(Module c) {
		modules[arrayIndex] = c;
		arrayIndex++;
	}


	//Return fitness class given a integer representing index of fitness class, return null if no such class exists
	public Module getModule(int i) {
		return modules[i];

	}

	public int getClassNo(){
		return arrayIndex;
	}

	//Hard code room Information into instance variable
	private void setRoomInfo() {

		rooms = new Object[8][2];

		rooms[0][0] = 'A';
		rooms[0][1] = 100;
		rooms[1][0] = 'B';
		rooms[1][1] = 100;
		rooms[2][0] = 'C';
		rooms[2][1] = 60;
		rooms[3][0] = 'D';
		rooms[3][1] = 60;
		rooms[4][0] = 'E';
		rooms[4][1] = 60;
		rooms[5][0] = 'F';
		rooms[5][1] = 30;
		rooms[6][0] = 'G';
		rooms[6][1] = 60;
		rooms[7][0] = 'H';
		rooms[7][1] = 30;

	}
	
	//Hard code time slot Information into instance variable
	private void setTimeIndex() {

		timeIndex = new Object[10][2];

		timeIndex[0][0] = "MonAM";
		timeIndex[0][1] = 0;
		timeIndex[1][0] = "MonPM";
		timeIndex[1][1] = 1;
		timeIndex[2][0] = "TueAm";
		timeIndex[2][1] = 2;
		timeIndex[3][0] = "TuePM";
		timeIndex[3][1] = 3;
		timeIndex[4][0] = "WedAM";
		timeIndex[4][1] = 4;
		timeIndex[5][0] = "WedPM";
		timeIndex[5][1] = 5;
		timeIndex[6][0] = "ThuAM";
		timeIndex[6][1] = 6;
		timeIndex[7][0] = "ThuPM";
		timeIndex[7][1] = 7;
		timeIndex[8][0] = "FriAM";
		timeIndex[8][1] = 6;
		timeIndex[9][0] = "FriPM";
		timeIndex[9][1] = 7;

	}

	public Module getModuleByID(String s) {
		for (int i = 0; i < arrayIndex; i++){
			if (modules[i].getCode().equals(s))
				return modules[i];
		}
		return null;
	}
	public Object getRooms()[][] {

		return rooms;
	}
	
	public int getTimeIndex(String s) {
		int tI=0;
		for(int i = 0; i< timeIndex.length; i++){
			if(s.equals(timeIndex[i][0]))
				return tI;
			tI++;
		}
			return -1;
	}
	
	public String getTimeString(int index) {
		for(int i = 0; i< timeIndex.length; i++){
			if(index==((Integer)timeIndex[i][1]))
				return (String)timeIndex[i][0];
	
		}
			return null;
	}
	
	public int getRoomIndex(int index) {

		for(int i = 0; i< rooms.length; i++){
			if(index+65==((Integer)rooms[i][1]))
				return (Integer)rooms[i][0];
		
		}
			return -1;
	}

	public Object getRoomInfo (int i, int j) {

		return rooms[i][j];
	}
	
	public int getRoomSize(int i) {
		return (Integer)rooms[i][1];
	}

	public String timeTableHeader() {
		String s = "<html><center>"+rooms[headerIndex][0]+"<br>"+rooms[headerIndex][1]+"</center></html>";
		headerIndex++;
		return s;
	}
}