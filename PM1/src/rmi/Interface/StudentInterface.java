package rmi.Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface StudentInterface extends Remote{

	public boolean createAccount(String m_firstName,String m_lastName,String m_emailAddress,String m_phoneNumber,String m_username,String m_password,String m_educationalInstitution) throws RemoteException;
	public boolean reserveBook(String m_username,String m_password,String m_bookName,String m_author)throws RemoteException;
	public int checkUser(String m_username,String m_password,String m_educationalInstitution)throws RemoteException;
	//public String searchBook(String strBookName)throws RemoteException;
}

