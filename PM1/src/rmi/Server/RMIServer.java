package rmi.Server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import rmi.Interface.AdminInterface;
import rmi.Interface.StudentInterface;
import rmi.LibraryObjects.Book;
import rmi.LibraryObjects.Student;


public class RMIServer implements StudentInterface, AdminInterface {

	private static HashMap<Character, ArrayList<Student>> Studentindex = new HashMap<Character, ArrayList<Student>>();
	private HashMap<String, Book> books   = new HashMap<String, Book>();
	private String instituteName;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			(new RMIServer()).exportServer();
			System.out.println("Server is up and running!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean createAccount(String m_firstName, String m_lastName,
			String m_emailAddress, String m_phoneNumber, String m_username,
			String m_password, String m_educationalInstitution)
			throws RemoteException
	{
		Student objstudent = new Student(m_username,m_password,m_educationalInstitution);
		objstudent.setFirstName(m_firstName);
		objstudent.setLastName(m_lastName);
		objstudent.setEmailAddress(m_emailAddress);
		objstudent.setPhoneNumber(m_phoneNumber);
		
		synchronized(Studentindex) {
			ArrayList<Student> newStudent = Studentindex.get(m_username.charAt(0));
			if(newStudent == null) {
				newStudent = new ArrayList<Student>();
				Studentindex.put(m_username.charAt(0), newStudent);
			}
			newStudent.add(objstudent);
		}
		return true;
	}

	@Override
	public boolean reserveBook(String m_username, String m_password,
			String m_bookName, String m_author) throws RemoteException {
		String reverip = "";
		for (int i=0; i< m_username.length();i++)
		{
			reverip = reverip + m_username.charAt((m_username.length()-1)-i);
		}
		return true;
	}

	@Override
	public String checkUser(String m_username, String m_password,
			String m_educationalInstitution) throws RemoteException {
		// TODO Auto-generated method stub
		return "true";
	}

	@Override
	public String searchBook(String m_bookName) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void exportServer() throws Exception
	{
		Remote objremote1 = UnicastRemoteObject.exportObject(this,2020);
		Registry registry1 = LocateRegistry.createRegistry(2020);
		registry1.bind("Institution", objremote1);
	}

	@Override
	public String getNonReturners(String AdminUsername, String strPassword,
			String InstitutionName, int NumDays) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
