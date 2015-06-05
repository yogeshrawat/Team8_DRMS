package TestCases;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.Scanner;

import org.junit.Test;

import Utility.ValidateInput;
import rmi.Client.AdminClient;
import rmi.Client.Client;
import rmi.Interface.AdminInterface;
import rmi.Server.RMIServer;

;

// TODO: Auto-generated Javadoc
/**
 * The Class Test1.
 */
public class Test3 {

	/**
	 * Test.
	 * 
	 * @throws RemoteException
	 */
	@SuppressWarnings("static-access")
	@Test
	public void test() throws RemoteException {
		
		AdminClient a = new AdminClient();
		//a.InitializeServer();
	
		assertNotNull(a.LocateServer("Concordia"));

		assertNull(a.LocateServer("Mcgill"));

		
	}

}
