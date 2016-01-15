package Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Shared.commoninterface;
import Shared.connection_variable;

// create a server which exends RMI UnicastRemoteObject and implements commoninterface
class AddServer extends UnicastRemoteObject implements commoninterface{

	protected AddServer() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	}

	// implements interface method in server side
	@Override
	public String sendartistname(String artistname) {
		// TODO Auto-generated method stub
		
		String result = null;
		
		//call location class
		if(artistname.endsWith("1"))
		{
			
			String originalartistname = artistname.substring(0, artistname.length()-1);
		
		try {
			
			result = FindLocation.searchlocation(originalartistname);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		// call getsongs class
		else
		{
			
			String originalname = artistname.substring(0,artistname.length()-1);
			
			try {
				
				result = GetSongs.searchlistofsongs(originalname);
				
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			
		}
		
		return result;
		
	}
	

}

public class Server{
	public static void main(String args[]) throws RemoteException, MalformedURLException, AlreadyBoundException
	{
		AddServer add = new AddServer();
		
	
		// create a Registry and bind RMI ID and AddServer object 
		Registry registry = LocateRegistry.createRegistry(connection_variable.RMI_PORT);
		
		registry.bind(connection_variable.RMI_ID, add);
		
		
	//	String result = add.sendartistname("40 Glocc2");
		
	//	System.out.println(result);
		
	//	Naming.rebind("rmi://localhost:5000/dhruv", add);
		
		System.out.println("Server is Started ready to accept task from client");
		
			
		
	}
}
