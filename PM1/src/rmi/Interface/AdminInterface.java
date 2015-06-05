package rmi.Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdminInterface extends Remote {
	
	public String getNonReturners(String AdminUsername, String strPassword, String InstitutionName, int NumDays)throws RemoteException;	
}
