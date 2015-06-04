package rmi.Client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Scanner;

import rmi.Interface.AdminInterface;

public class AdminClient extends Client {
	

	public static void showMenu()
	{
		System.out.println("DRMS Admin Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Get Non-Returner.");
		System.out.println("2. Exit");
	}
	

	
	public static void main(String[] args)
	{
		try{

			AdminClient objClient = new AdminClient();
			//initialize the connections to registry
			objClient.InitializeServer();
			AdminInterface objServer = null;
			Scanner keyboard = new Scanner(System.in);
			//to which server you want to connect
			objServer = (AdminInterface) objClient.ServerValidation(keyboard);
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
					System.out.println("User Name: ");
					userName = objClient.InputStringValidation(keyboard);
					System.out.println("Password: ");
					password = objClient.InputStringValidation(keyboard);
					System.out.println("No Of Days: ");
					int numOfDays = objClient.InputIntValidation(keyboard);
					//TODO what to do with institute name
					
					objClient.logger.info("Non Returner retrieved on :"+ System.currentTimeMillis());
					objServer.getNonReturners(userName, password, objServer.toString(), numOfDays);

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
