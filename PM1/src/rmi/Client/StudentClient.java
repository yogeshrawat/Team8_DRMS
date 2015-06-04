package rmi.Client;
import java.io.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.management.remote.rmi.RMIIIOPServerImpl;

import rmi.Interface.StudentInterface;

public class StudentClient{

	static StudentInterface ConcordiaServer;
	static StudentInterface OttawaServer;
	static StudentInterface WaterlooServer;
	
	static final String Concordia ="Concordia", Ottawa="Ottawa", Waterloo="Waterloo";
	protected String instituteName;
	
	//Menu to display actions that are need to perform by student
	public static void showMenu()
	{
		System.out.println("DRMS Student Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Create new student account.");
		System.out.println("2. Reserve a book");
		System.out.println("3. Exit");
	}

	//To lookup server
	public void registryLookup() throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		ConcordiaServer = (StudentInterface)Naming.lookup("rmi://127.0.0.1:1099/"+Concordia);		
		OttawaServer = (StudentInterface)Naming.lookup("rmi://127.0.0.1:1099/"+Ottawa);
		WaterlooServer = (StudentInterface)Naming.lookup("rmi://127.0.0.1:1099/"+Waterloo);	
	}

	//Get Server Connection
	public StudentInterface locateServer(String instituteName) {
		if(instituteName.equals(Concordia)) {
			return ConcordiaServer;
		}
		else if(instituteName.equals(Ottawa)) {
			return OttawaServer;
		}
		else if(instituteName.equals(Waterloo)) {
			return WaterlooServer;
		}
		return null;
	}

	public String InputStringValidation(Scanner keyboard) {
		Boolean valid = false;
		String userInput = "";
		while(!valid)
		{
			try{
				userInput=keyboard.nextLine();
				valid=true;
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input, please enter an String");
				valid=false;
				keyboard.nextLine();
			}
		}
		return userInput;
	}

	public StudentInterface getServer(Scanner keyboard)
	{
		Boolean valid = false;
		StudentInterface server = null;
		System.out.println("Enter Institute Name");
		System.out.println("'Concordia' For Concordia University");
		System.out.println("'Ottawa' For Ottawa University");
		System.out.println("'Waterloo' For Waterloo University");
		while(!valid)
		{
			try{
				instituteName = keyboard.nextLine();
				server = locateServer(instituteName);
				if(server != null) {
					valid=true;
				}
				else {
					System.out.println("Invalid Institute Name");
					keyboard.nextLine();
				}
			}
			catch(Exception e)
			{
				System.out.println("Invalid Institute Name");
				valid=false;
				keyboard.nextLine();
			}
		}
		//keyboard.nextLine();
		return server;
	}

	public static void main(String[] args)
	{
		try{
			
			StudentClient objClient = new StudentClient();
			objClient.registryLookup();   //rmi registry lookup
			StudentInterface objServer = null;
			
			Integer userInput = 0;
			Scanner keyboard = new Scanner(System.in);
			
			//to which server you want to connect
			objServer = objClient.getServer(keyboard);
			showMenu();
			String userName, password, institution;
			boolean success = false;
			while(true)
			{
				userInput = Integer.parseInt(objClient.InputStringValidation(keyboard));
				switch(userInput)
				{
				case 1: 
					System.out.println("First Name: ");
					String firstName = objClient.InputStringValidation(keyboard);
					System.out.println("Last Name: ");
					String lastName = objClient.InputStringValidation(keyboard);
					System.out.println("Email: ");
					String emailAddress = objClient.InputStringValidation(keyboard);
					System.out.println("Phone No: ");
					String phoneNumber = objClient.InputStringValidation(keyboard);
					System.out.println("User Name: ");
					userName = objClient.InputStringValidation(keyboard);
					System.out.println("Password: ");
					password = objClient.InputStringValidation(keyboard);
					success = objServer.createAccount(firstName, lastName, emailAddress, phoneNumber, userName, password, objClient.instituteName);
					if(success){
						System.out.println("Success");
						//logger method for success
					}
					else{
						// logger method for failure
					}
					showMenu();
					break;
				case 2: 
					System.out.println("User Name: ");
					userName = objClient.InputStringValidation(keyboard);
					System.out.println("Password: ");
					password = objClient.InputStringValidation(keyboard);
					System.out.println("Book Name: ");
					String bookName = objClient.InputStringValidation(keyboard);
					System.out.println("Author: ");
					String authorName = objClient.InputStringValidation(keyboard);
					success = objServer.reserveBook(userName, password, bookName, authorName);
					if(success){
						System.out.println("Success");
						//logger method for success
					}
					else{
						// logger method for failure
					}
					showMenu();
					break;
				case 3: 
					System.out.println("Thank you");
					keyboard.close();
					System.exit(0);
				case 4: 
					showMenu();
					break;
				default:
					System.out.println("Invalid input");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
