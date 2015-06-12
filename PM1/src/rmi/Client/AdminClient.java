package rmi.Client;

import java.io.File;
import java.io.FileWriter;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Scanner;

import rmi.Interface.AdminInterface;
import rmi.Interface.StudentInterface;

public class AdminClient extends Client {

	static AdminInterface ConcordiaServer;
	static AdminInterface OttawaServer;
	static AdminInterface WaterlooServer;
	static final String Concordia = "Concordia", Ottawa = "Ottawa",
			Waterloo = "Waterloo";
	protected static String instituteName;

	public void InitializeServer() throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		ConcordiaServer = (AdminInterface) Naming
				.lookup("rmi://localhost:1099/Concordia");
		OttawaServer = (AdminInterface) Naming
				.lookup("rmi://localhost:1099/Ottawa");
		WaterlooServer = (AdminInterface) Naming
				.lookup("rmi://localhost:1099/Waterloo");
	}

	public AdminInterface ServerValidation(Scanner keyboard) {
		Boolean valid = false;
		AdminInterface server = null;
		System.out.println("Enter Institute Name");
		System.out.println("'Concordia' For Concordia University");
		System.out.println("'Ottawa' For Ottawa University");
		System.out.println("'Waterloo' For Waterloo University");
		while (!valid) {
			try {
				instituteName = keyboard.nextLine();
				server = LocateServer(instituteName);
				if (server != null) {
					valid = true;
				} else {
					System.out.println("Invalid Institute Name");
					keyboard.nextLine();
				}
			} catch (Exception e) {
				System.out.println("Invalid Institute Name");
				valid = false;
				keyboard.nextLine();
			}
		}
		// keyboard.nextLine();
		return server;
	}

	// Get Server Connection
	public static AdminInterface LocateServer(String instituteName) {
		if (instituteName.equals(Concordia)) {
			return ConcordiaServer;
		} else if (instituteName.equals(Ottawa)) {
			return OttawaServer;
		} else if (instituteName.equals(Waterloo)) {
			return WaterlooServer;
		}
		return null;
	}

	public static void showMenu() {
		System.out.println("DRMS Admin Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Get Non-Returner.");
		System.out.println("2. Exit");
	}

	public static void main(String[] args)
	{
		try{
			System.setProperty("java.security.policy","file:./security.policy");
			AdminClient objClient = new AdminClient();
			//initialize the connections to registry
			objClient.InitializeServer();
			AdminInterface objServer = null;
			Scanner keyboard = new Scanner(System.in);
			//to which server you want to connect
			objServer = objClient.ServerValidation(keyboard);
			Integer userInput = 0;
			showMenu();
			objClient.setLogger("admin", "logs/admin.txt");
			objClient.logger.info("admin login");

			userInput = Integer.parseInt(objClient.InputStringValidation(keyboard));
			String userName, password;
			boolean success = false;

			while(true)
			{
				switch(userInput)
				{
				case 1: 
					System.out.println("Admin userName: ");
					userName = objClient.InputStringValidation(keyboard);
					System.out.println("Password: ");
					password = objClient.InputStringValidation(keyboard);
					System.out.println("No Of Days: ");
					int numOfDays = objClient.InputIntValidation(keyboard);
					//TODO what to do with institute name
					
					objClient.logger.info("Non Returner retrieved on :"+ System.currentTimeMillis());
					String result = "";
					result = objServer.getNonReturners(userName, password, objServer.toString(), numOfDays);
					File nonReturners=new File(".\\src\\NonReturners.txt");
					FileWriter fw=new FileWriter(nonReturners);
					fw.write(result);
					System.out.println("File Written");
					showMenu();
					break;
				case 2:
					System.out.println("Have a nice day!");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid Input, please try again.");
				}
			}
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
