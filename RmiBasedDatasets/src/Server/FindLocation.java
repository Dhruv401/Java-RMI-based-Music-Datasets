package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// create this class to retrive location from file according to artistname
public class FindLocation {
	
	
	static FileInputStream inputstream = null;
	
	static Scanner scanner = null;
	
	// Declare static so we can call from server class
	public static String searchlocation(String artistname) throws IOException
	{
		
		String location = null;
		
		try{
		
		// Read from the file using FileInputStream and Scanner
		inputstream = new FileInputStream("src/Server/Table2.in");
		
		scanner = new Scanner(inputstream,"UTF-8");
		
		while(scanner.hasNext())
		{
			
			String line = scanner.nextLine();
			
			String fileelements[] = line.split("<SEP>");
			
			if(fileelements[2].equals(artistname))
			{
				location = fileelements[3];
				
				
			}
			
			
		}
		
		if(scanner.ioException() != null)
		{
			throw scanner.ioException();
		}
		
		}
		finally{
			
			if(inputstream != null)
			{
				inputstream.close();
			}
			if(scanner != null)
			{
				scanner.close();
			}
			
		}
		return location;
	}
	
/*	public static void main(String args[])
	{
		FindLocation f1 = new FindLocation();
		
		try {
			String result = f1.searchlocation("6th Sense");
			
			System.out.println(result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  */

}


