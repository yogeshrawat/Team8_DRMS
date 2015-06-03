package rmi.Client;
import java.io.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.management.remote.rmi.RMIIIOPServerImpl;

import rmi.Interface.RMInterface;

public class RMIClient{

	static RMInterface ConcordiaServer;
	static RMInterface OttawaServer;
	static RMInterface WaterlooServer;
	static final String Concordia ="concordia", Ottawa="ottawa", Waterloo="waterloo";
	static final String Institution = "Institution";
	protected static String instituteName;
	
	//Menu to display actions that are need to perform by student
	public static void showMenu()
	{
		System.out.println("DRMS Student Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Create An Account.");
		System.out.println("2. Reserve a Book");
		System.out.println("3. Exit");
	}

	//To intialize server
	public void InitializeServer() throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		ConcordiaServer = (RMInterface)Naming.lookup("rmi://127.0.0.1:2020/"+Institution);		
		OttawaServer = (RMInterface)Naming.lookup("rmi://127.0.0.1:2020/"+Institution);
		WaterlooServer = (RMInterface)Naming.lookup("rmi://127.0.0.1:2020/"+Institution);	
	}

	//Get Server Connection
	public static RMInterface LocateServer(String instituteName) {
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

	public RMInterface ServerValidation(Scanner keyboard)
	{
		Boolean valid = false;
		RMInterface server = null;
		System.out.println("Enter Institute Name");
		System.out.println("'concordia' For Concordia University");
		System.out.println("'ottawa' For Ottawa University");
		System.out.println("'waterloo' For Waterloo University");
		while(!valid)
		{
			try{
				instituteName = keyboard.nextLine();
				server = LocateServer(instituteName);
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
			
			RMIClient objClient = new RMIClient();
			//initialize the connections to registry
			objClient.InitializeServer();
			RMInterface objServer = null;
			Scanner keyboard = new Scanner(System.in);
			//to which server you want to connect
			objServer = objClient.ServerValidation(keyboard);
			Integer userInput = 0;
			showMenu();
			userInput = Integer.parseInt(objClient.InputStringValidation(keyboard));
			String userName, password, institution;
			boolean success = false;

			while(true)
			{
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
					System.out.println("Institution Name: ");
					institution= objClient.InputStringValidation(keyboard);
					success = objServer.createAccount(firstName, lastName, emailAddress, phoneNumber, userName, password, instituteName);
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
					showMenu();
					break;
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
