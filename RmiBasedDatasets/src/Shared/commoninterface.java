package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

// create an interface which extends RMI Remote interface
public interface commoninterface extends Remote{
	
	public String sendartistname(String artistname) throws RemoteException;

}
