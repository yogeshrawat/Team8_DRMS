import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RMIServer implements RMInterface {

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
	public String createAccount(String m_firstName, String m_lastName,
			String m_emailAddress, String m_phoneNumber, String m_username,
			String m_password, String m_educationalInstitution)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String reserveBook(String m_username, String m_password,
			String m_bookName, String m_author) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
		Remote objRemote = UnicastRemoteObject.exportObject(this,2020);
		Registry r = LocateRegistry.createRegistry(2020);
		r.bind("test",objRemote);
	}

}
