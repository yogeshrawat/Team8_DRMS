package TestCases;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import rmi.Server.RMIServer;

;

// TODO: Auto-generated Javadoc
/**
 * The Class Test1.
 */
public class Test1 {

	/**
	 * Test.
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test() throws RemoteException {
		

		RMIServer c2 = new RMIServer("Concordia",5050);
		boolean bcreateAccount = c2.createAccount("Test", "Test", "Test",
				"Test", "Test", "Test", "Concordia");
		assertTrue(bcreateAccount);
		
//		RMIServer c1 = new RMIServer("Concordia",5050);
		int valueReturnValue = c2
				.checkUser("Test", "Test", "Concordia");
		assertEquals(valueReturnValue, 1);
		
		int valueReturnValue2 = c2
				.checkUser("Test", "Test1", "Concordia");
		assertEquals(valueReturnValue2, 0);
	}

}
