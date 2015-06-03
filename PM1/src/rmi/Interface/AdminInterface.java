package rmi.Interface;

import java.rmi.RemoteException;

public interface AdminInterface {
	
	public String getNonReturners(String AdminUsername, String strPassword, String InstitutionName, int NumDays)throws RemoteException;	
}
