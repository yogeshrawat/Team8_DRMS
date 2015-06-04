package rmi.Server;
import java.rmi.Remote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rmi.Interface.AdminInterface;
import rmi.Interface.StudentInterface;
import rmi.LibraryObjects.Book;
import rmi.LibraryObjects.Student;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class RMIServer extends Thread implements StudentInterface, AdminInterface {

	private static HashMap<Character, ArrayList<Student>> tableStudents = new HashMap<Character, ArrayList<Student>>();
	//private static ArrayList<List<Student>> listStudents = new ArrayList<List<Student>>(); 
	private HashMap<String, Book> tableBooks   = new HashMap<String, Book>();
	private String instituteName;
	
	private Logger logger;
	
	public RMIServer(String instituteName)
	{
		this.instituteName = instituteName;
		this.setLogger("logs/library/"+instituteName+".txt");
	}
	
	public RMIServer(){
		
	}
	
	private void setLogger(String instituteName) {
		try{
			this.logger = Logger.getLogger(this.instituteName);
			FileHandler fileTxt 	 = new FileHandler(instituteName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
		}
		catch(Exception err) {
			System.out.println("Couldn't Initiate Logger. Please check file permission");
		}
	}



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
	public boolean createAccount(String strFirstName, String strLastName, String strEmailAddress, String strPhoneNumber, String strUsername,
			String strPassword, String strEducationalInstitution)
					throws RemoteException
	{
		Student objStudent = new Student(strUsername,strPassword,strEducationalInstitution);
		objStudent.setFirstName(strFirstName);
		objStudent.setLastName(strLastName);
		objStudent.setEmailAddress(strEmailAddress);
		objStudent.setPhoneNumber(strPhoneNumber);

		//Add student to HashTable 'tableStudents' with Lock
		synchronized(tableStudents) {
			ArrayList<Student> objNewStudent = tableStudents.get(strUsername.charAt(0));
			if(objNewStudent == null) {
				objNewStudent = new ArrayList<Student>();
				tableStudents.put(strUsername.charAt(0), objNewStudent);
			}
			objNewStudent.add(objStudent);
			logger.info("New User added to the library with username as : "+objStudent.getUserName());

		}
		return true;
	}

	@Override
	public boolean reserveBook(String strUsername, String strPassword, String strBookName, String strAuthor) throws RemoteException 
	{
		boolean success =false;
		int iLoginResult = 0;
		Student objStudent = null;
		objStudent = getStudent(strUsername);
		if(objStudent!=null)
		{
			iLoginResult = checkUser(strUsername, strPassword, objStudent.getInst());
			if(iLoginResult==1)
			{
				synchronized(tableBooks)
				{
					Book objBook = tableBooks.get(strBookName);
					if(objBook!= null)
					{
						//reserve the book
						if(objBook.getNumOfCopy()>0)
						{
							objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
							(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
							success = true;
							logger.info(strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of"+ strBookName+"is/are"+objBook.getNumOfCopy());

						}
						else
						{
							System.out.println("Required book not available currently");
						}
					}
					else
					{
						System.out.println("Required book not found");	
					}
				}
			}
			else
			{
				if(iLoginResult==2)
					System.out.println("Login is invalid!");
			}
		}
		else
		{
			System.out.println("Student "+strUsername+ " does not exist!");
		}
		return success;
	}

	@Override
	public int checkUser(String strUsername, String strPassword,String strEducationalInstitution) throws RemoteException 
	{
		// TODO Auto-generated method stub
		int login = 0;
		Student objStudent = null;
		ArrayList<Student> listStudent = tableStudents.get(strUsername.charAt(0));
		if(listStudent.size()>0)
		{
			for(Student student : listStudent)
			{
				if(student.getUserName().equals(strUsername))
				{
					if(student.getPassword().equals(strPassword))
					{
						login =1;
					}
					else
					{
						login = 2;
					}
				}
			}
		}

		return login;
	}

	//@Override
	//	public String searchBook(String strBookName) throws RemoteException {
	//		// TODO Auto-generated method stub
	//		
	//		
	//		return null;
	//	}

	public void exportServer() throws Exception
	{
		Remote objremote1 = UnicastRemoteObject.exportObject(this,2020);
		Registry registry1 = LocateRegistry.createRegistry(2020);
		registry1.bind("Institution", objremote1);
	}

	@Override
	public String getNonReturners(String AdminUsername, String AdminPassword,String InstitutionName, int NumDays) throws RemoteException 
	{
		StringBuilder sbStudentList = null;
		sbStudentList.append(instituteName+": \n");
		// TODO Auto-generated method stub
		if(AdminUsername.equalsIgnoreCase("admin")&&AdminPassword.equals("admin"))
		{
			Iterator<?> it = tableStudents.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry pair = (Map.Entry)it.next();
				ArrayList<Student> listStudent = (ArrayList<Student>) pair.getValue();
				if(!listStudent.isEmpty())
				{					
					for(Student objStudent : listStudent)
					{
						if(!objStudent.getReservedBooks().isEmpty())
						{
							Iterator<?> innerIterator = objStudent.getReservedBooks().entrySet().iterator();
							while(innerIterator.hasNext())
							{
								Map.Entry innerPair = (Map.Entry)innerIterator.next();
								
								if((int)innerPair.getValue()<=(14-NumDays))
								{
									sbStudentList.append(objStudent.getFirstName() +" "+objStudent.getLastName()+" "+objStudent.getPhoneNumber()+"\n");
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Student getStudent(String strUserName)
	{
		Student objStudent = null;
		ArrayList<Student> listStudent = tableStudents.get(strUserName.charAt(0));
		synchronized(tableStudents)
		{
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUserName))
					{
						objStudent = student;
					}
				}
			}
		}
		return objStudent;
	}
	
}
