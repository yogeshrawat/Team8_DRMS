package rmi.Client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Scanner;

import rmi.Interface.AdminInterface;

public class AdminClient {

	static AdminInterface ConcordiaServer;
	static AdminInterface OttawaServer;
	static AdminInterface WaterlooServer;

	static final String Concordia ="Concordia", Ottawa="Ottawa", Waterloo="Waterloo";
	protected String instituteName;


	public static void showMenu()
	{
		System.out.println("DRMS Admin Clietn System \n");
		System.out.println("Please select an option:");
		System.out.println("1. Get Non Returner.");
		System.out.println("2. Exit");
	}

	//To lookup server
	public void registryLookup() throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		ConcordiaServer = (AdminInterface)Naming.lookup("rmi://127.0.0.1:1099/"+Concordia);		
		OttawaServer = (AdminInterface)Naming.lookup("rmi://127.0.0.1:1099/"+Ottawa);
		WaterlooServer = (AdminInterface)Naming.lookup("rmi://127.0.0.1:1099/"+Waterloo);	
	}

	//Get Server object 
	public AdminInterface locateServer(String instituteName) {
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

	//get server connection
	public AdminInterface getServer(Scanner keyboard)
	{
		Boolean valid = false;
		AdminInterface server = null;
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

	public static void main(String args[])
	{
		try {
			AdminClient objclient = new AdminClient();
			objclient.registryLookup();
			AdminInterface objServer = null;
			int userInput=0;
			Scanner keyboard = new Scanner(System.in);
			objServer = objclient.getServer(keyboard);
			showMenu();
			String userName, password;
			//admin logger

			while(true)
			{
				userInput = Integer.parseInt(objclient.InputStringValidation(keyboard));
				switch(userInput)
				{
				case 1: 
					System.out.println("User Name: ");
					userName = objclient.InputStringValidation(keyboard);
					System.out.println("Password: ");
					password = objclient.InputStringValidation(keyboard);
					System.out.println("No Of Days: ");
					int numOfDays = Integer.parseInt(objclient.InputStringValidation(keyboard));
					objServer.getNonReturners(userName, password, objServer.toString(), numOfDays);
					showMenu();
					break;
				case 2:
					System.out.println("Thank you");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid Input");
				}
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
