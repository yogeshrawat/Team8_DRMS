package TestCases;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import rmi.Server.RMIServer;;

// TODO: Auto-generated Javadoc
/**
 * The Class Test1.
 */
public class Test1 {

	/**
	 * Test.
	 * @throws RemoteException 
	 */
	@Test
	public void test() throws RemoteException {
		RMIServer c = new RMIServer();
		
		int valueReturnValue = c.checkUser("Student1", "password", "Concordia");
		assertEquals(valueReturnValue,1);
	} 

}
