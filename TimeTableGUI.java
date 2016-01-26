import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;

public class TimeTableGUI extends JFrame implements ActionListener {

	//Instance Variables
	//module is used to store the info for a module the user last clicked

	private Module moduleToChangeOne;
	private int lastRow, lastCol;
	private vocationProgram mit;
	private JTable currentTable;
	private JTable moduleList;
	private int numModules = 0;
	private boolean lastTableClicked;
	private int emptyRow, emptyColumn;


	public TimeTableGUI() {

		loadTimetable();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("MIT Timetable");
		setSize(1100, 600);
		setLocation(200, 0);

		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();

		//Header titles for the module list table
		String[] moduleColumnNames = {"Code", "Time", "Room", "Size"};

		//I will improve this with a method in the program class that will generate these automatically
		String[] timeColumnNames = {"<html><center> A <br> 100 </center></html>", "<html><center> B <br> 100 </center></html>",
				"<html><center> C <br> 60 </center></html>", "<html><center> D <br> 60 </center></html>",
				"<html><center> E <br> 60 </center></html>", "<html><center> F <br> 30 </center></html>",
				"<html><center> G <br> 60 </center></html>", "<html><center> H <br> 30 </center></html>",
		};


		//Set up a table for the current state of the timetable, passing the headers and a method that initially creates a blank display
		currentTable = new JTable(getBlank(),timeColumnNames);
		currentTable.setPreferredScrollableViewportSize(new Dimension(600,400));
		currentTable.setFillsViewportHeight(true);
		currentTable.setRowHeight(25);
		currentTable.setRowSelectionAllowed(false);
		JScrollPane js2 = new JScrollPane(currentTable);
		currentTable.setRowHeight(40);
		currentTable.addMouseListener(l);


		//Set up a table for the complete list of modules, passing the headers and a method that reads in module info from program class
		moduleList = new JTable(getContent(), moduleColumnNames);
		moduleList.setPreferredScrollableViewportSize(new Dimension(400,400));
		moduleList.setFillsViewportHeight(true);
		moduleList.setRowHeight(25);
		moduleList.addMouseListener(l);
		JScrollPane js = new JScrollPane(moduleList);

		//Save and exit button
		JButton b = new JButton("Save and Exit");

		//add the components to the GUI using the border layout
		//2 jpanels are used to place the timetables in the correct location

		add(b,BorderLayout.SOUTH);
		pan2.add(js);
		add(pan2, BorderLayout.EAST);

		pan1.add(js2);
		add(pan1, BorderLayout.WEST);

		//Populates the table with the classes that have a time
		refreshTimeTable();
	}

	//Deals with mouse click events from the user
	MouseListener l = new MouseAdapter() { 
		public void mousePressed(MouseEvent e) { 

			JTable target = (JTable)e.getSource();

			//Check to see which table was clicked
			if(target==currentTable){

				//get the row and column numbers of the cell that was clicked
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();

				//This means a swap has either just occurred or the the last click was on a blank space or it is the first click
				//Assign moduleToChange instance variable to the module of the cell that was clicked if it was occupied
				if(moduleToChangeOne==null){		
					if(target.getValueAt(row, column)!=null){
						moduleToChangeOne = mit.getModuleByID((String)target.getValueAt(row, column));
						lastRow = row;
						lastCol = column;
						//need some code here to highlight the cell that was clicked
					}
					//Set location of where module will be switched to
					else {
						emptyRow = row;
						emptyColumn = column;
					}
				}

				//This means a module is trying to be switched either inside the table or from the module list
				else {
					//Moving a class into a non blank slot
					if(target.getValueAt(row, column)!=null){

						//Case if the same module is placed back at its current location
						if((target.getValueAt(row, column).equals(moduleToChangeOne.getCode())))
							moduleToChangeOne=null;
						else{
							if(lastTableClicked==true){
								System.err.println("Double switch");
								moduleToChangeOne=null;
							}
							else{
								JOptionPane.showMessageDialog(null,"There is already a module there!", "Already Occupied",JOptionPane.ERROR_MESSAGE);
								moduleToChangeOne=null;
							}
						}
					}
					//moving a class into a blank slot
					else{
						System.err.println("move module " + moduleToChangeOne.getCode() +" to " + row + column);

						if(validateChange(moduleToChangeOne,row,column)){
							target.setValueAt("", moduleToChangeOne.getTimeIndex(), moduleToChangeOne.getRoomIndex());
							moduleToChangeOne.setRoomInfo(column);
							moduleToChangeOne.setTimeInfo(row);
							refreshTimeTable();
						}
						moduleToChangeOne=null;
					}
				}

				//Last table click is true when the last click was the timetable and false when the last click was the module list
				lastTableClicked = true;

			}

			//when module list table is clicked
			else {

				//This means the last table to be clicked was the main timetable and the current click is on the module list
				if(lastTableClicked==true){

					//This will be true if the last click was on a slot with a module inside it
					//We want to try to make a swap with the last module clicked and the one currently clicked in the module list
					if(moduleToChangeOne!=null){
						Module moduleFromList = mit.getModuleByID((String)target.getValueAt(target.getSelectedRow(), 0));
						System.err.println("move " + moduleFromList.getCode() + "into " + lastRow + lastCol);
						//need the room index and time index
						if(validateChange(moduleFromList, lastRow, lastCol)){
						moduleToChangeOne.setRoomInfo(-1);
						moduleToChangeOne.setTimeInfo(-1);
						moduleFromList.setTimeInfo(lastRow);
						moduleFromList.setRoomInfo(lastCol);
						moduleToChangeOne=null;
						refreshTimeTable();
						}
					}

					//If the last click was on the main table but on an empty slot then attempt to switch the module just clicked
					//into the position of the empty space last clicked
					else {
						//will do another check here to see if a swap has just occured once the value of the cell has been updated from a switch
						if (emptyRow!=0){
							moduleToChangeOne = mit.getModuleByID((String)target.getValueAt(target.getSelectedRow(), 0));
							validateChange( mit.getModuleByID((String)target.getValueAt(target.getSelectedRow(), 0)), emptyRow,emptyColumn);
							System.err.println("Switch "+ moduleToChangeOne.getCode() + " into " + emptyRow +","+ emptyColumn);
							moduleToChangeOne=null;
						}
						else
							moduleToChangeOne = mit.getModuleByID((String)target.getValueAt(target.getSelectedRow(), 0));

					}

				}

				//This means the last table to be clicked was the module list and the current click is on the module list
				//If this is the case simply reassign the module to be changed 
				else {
					moduleToChangeOne = mit.getModuleByID((String)target.getValueAt(target.getSelectedRow(), 0));
					System.err.println("Overwrite module to change to " + moduleToChangeOne.getCode());
				}

				//A false value indicates that the last table that was clicked was the module list
				lastTableClicked=false;
			}

		}

	};

	//This method will validate if the new location is acceptable
	private boolean validateChange(Module m, int tI, int rI){

		//First check if the size of the room will fit the size of the class, if not return value 1
		if(m.getSize()>mit.getRoomSize(rI)){
			JOptionPane.showMessageDialog(null,"Class Size of " +m.getSize() +" is too great for Room " + mit.getRoomInfo(rI, 0) , "Size Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		boolean clash=false;
		//Next check if there are any other modules on from the same year during that time
		for(int i = 0; i < mit.getClassNo(); i++) {
			if(m.getYear()==mit.getModule(i).getYear())
				if(tI==mit.getModule(i).getTimeIndex())
					if(mit.getModule(i).getCode().equals(m.getCode()))
						clash=false;
					else{
						JOptionPane.showMessageDialog(null,"Year " + m.getYear() + " have another class on then", "Schedule Error",JOptionPane.ERROR_MESSAGE);
						clash=true;	
					}
		}

		if(clash==true)
			return false;


		else
			return true;
	}


	//Compulsory actionPerformed method 

	public void actionPerformed(ActionEvent ae) {
		//psuedo code
	}

	//Method to load the timetable based on the input file
	private void loadTimetable() {

		FileReader reader;

		//This first try block is used to determine how many modules are in the read in file, once this is known
		//another method is called to create a vocationProgram object that will have an array of classes of the correct length
		try {
			//try opening the modulesIn text file and create a scanner object that will read the input line by line
			reader = new FileReader("ModulesIn.txt");
			Scanner details = new Scanner(reader);


			//A loop to simpy count the number of modules in the modulesIn file
			while (details.hasNext()) {
				numModules++;
				details.nextLine();
			}

			//create the vocationProgram with the correct number of modules
			createProgram(numModules);

			//close the files once the input has been dealt with
			details.close();
			reader.close();
		}

		catch (IOException x) {
			System.err.println("file error");
		}
	}

	//method to populate the timetable based on the number of modules in the modulesIn file
	private void createProgram(int n) {

		FileReader modules;
		try {
			modules = new FileReader("ModulesIn.txt");
			Scanner details = new Scanner(modules);

			//create the new vocationProgram which will set up a Module array with size = number of modules
			mit = new vocationProgram(n);

			//Create a new Module object and place it in the vocationProgram oject for each module read in
			while (details.hasNext()) {
				String s= details.nextLine();
				Module c = new Module(s);
				mit.newClass(c);
			}
		}
		catch (IOException x) {
			System.err.println("file error");
		}
	}

	private Object [][] getContent() {

		Object [][] data = new Object[mit.getClassNo()][4];

		for(int i=0; i<mit.getClassNo(); i++){
			data[i][0] = mit.getModule(i).getCode();
			data[i][1] = mit.getModule(i).getTime();
			data[i][2] = mit.getModule(i).getRoom();
			data[i][3] = mit.getModule(i).getSize();
		}
		return data;
	}

	private Object [][] getBlank() {

		Object [][] classes = new Object[10][mit.getRooms().length];
		return classes;
	}

	//Method that utilises the timeIndex and roomIndex properties of the Module class to update the relevant cells in the timetable
	//Any index values for time that are not -1 will contain an index value which will place them in the table according to what time
	//and which room they are in
	private void refreshTimeTable() {
		for(int i = 0; i < numModules; i++){
			if(mit.getModule(i).getTimeIndex()!=-1){
				currentTable.setValueAt(mit.getModule(i).getCode(), mit.getModule(i).getTimeIndex(), mit.getModule(i).getRoomIndex());
			}
		}
	}

	private void refreshModuleList() {
		for(int i = 0; i < numModules; i++){
			moduleList.setValueAt( mit.getModule(i).getCode(), i, 0);
			moduleList.setValueAt( mit.getModule(i).getTime(), i, 1);
			moduleList.setValueAt( mit.getModule(i).getRoom(), i, 2);
			moduleList.setValueAt( mit.getModule(i).getSize(), i, 3);
		}
	}
}
