
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
	
	
	public int getRoomSize(int i) {
		return (Integer)rooms[i][1];
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
}