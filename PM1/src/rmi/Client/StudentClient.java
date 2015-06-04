package rmi.Client;
import java.io.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.management.remote.rmi.RMIIIOPServerImpl;

import rmi.Interface.StudentInterface;

public class StudentClient extends Client{

	
	//Menu to display actions that are need to perform by student
	public static void showMenu()
	{
		System.out.println("DRMS Student Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Create An Account.");
		System.out.println("2. Reserve a Book");
		System.out.println("3. Exit");
	}
	

	
	public static void main(String[] args)
	{
		try{
			
			StudentClient objClient = new StudentClient();
			//initialize the connections to registry
			objClient.InitializeServer();
			StudentInterface objServer = null;
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
