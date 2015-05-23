
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RMInterface extends Remote{

	public String createAccount(String m_firstName,String m_lastName,String m_emailAddress,String m_phoneNumber,String m_username,String m_password,String m_educationalInstitution) throws RemoteException;
	public String reserveBook(String m_username,String m_password,String m_bookName,String m_author)throws RemoteException;
	public String checkUser(String m_username,String m_password,String m_educationalInstitution)throws RemoteException;
	public String searchBook(String m_bookName)throws RemoteException;
}

