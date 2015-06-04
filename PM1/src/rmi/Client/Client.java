package rmi.Client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import rmi.Interface.StudentInterface;

public class Client {
	
	static StudentInterface ConcordiaServer;
	static StudentInterface OttawaServer;
	static StudentInterface WaterlooServer;
	static final String Concordia ="concordia", Ottawa="ottawa", Waterloo="waterloo";
	static final String Institution = "Institution";
	protected static String instituteName;
	protected Logger logger;

	public void setLogger(String username, String fileName) {
		try{
			this.logger = Logger.getLogger(username);
			FileHandler fileTxt 	 = new FileHandler(fileName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
		}
		catch(Exception err) {
			System.out.println("Couldn't create Logger. Please check file permission");
		}
	}
	
	public void InitializeServer() throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		ConcordiaServer = (StudentInterface)Naming.lookup("rmi://127.0.0.1:2020/"+Institution);		
		OttawaServer = (StudentInterface)Naming.lookup("rmi://127.0.0.1:2020/"+Institution);
		WaterlooServer = (StudentInterface)Naming.lookup("rmi://127.0.0.1:2020/"+Institution);	
	}
	
	public StudentInterface ServerValidation(Scanner keyboard)
	{
		Boolean valid = false;
		StudentInterface server = null;
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

	
	//Get Server Connection
	public static StudentInterface LocateServer(String instituteName) {
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
	
	
	public int InputIntValidation(Scanner keyboard) {
		Boolean valid = false;
		int userInput = 0;
		while(!valid)
		{
			try{
				userInput=keyboard.nextInt();
				valid=true;
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input, please enter an Integer");
				valid=false;
				keyboard.nextLine();
			}
		}
		return userInput;
	}
}
