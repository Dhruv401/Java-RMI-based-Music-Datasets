package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


//create this class to retrive List of Songs from file according to artistname
public class GetSongs {
	
	
	
	static FileInputStream inputstream = null;
	
	static Scanner scanner = null;
	
	public static String searchlistofsongs(String artistname) throws IOException
	{
		
		String listofsongs = null;
		
		try{
		
		inputstream = new FileInputStream("src/Server/Table2.in");
		
		scanner = new Scanner(inputstream,"UTF-8");
		
		while(scanner.hasNext())
		{
			
			String line = scanner.nextLine();
			
			String fileelements[] = line.split("<SEP>");
			
			if(fileelements[2].equals(artistname))
			{
				listofsongs = fileelements[4];

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
		return listofsongs;
	}

}
