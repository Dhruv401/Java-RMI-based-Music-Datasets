package Client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument.LeafElement;

import Shared.connection_variable;

// client class which implements ActionListener interface
public class Client implements ActionListener{
	
	JFrame jf;
	
	JLabel heading,artistname,Selectchoice,displayresult,displaylocation;
	
	JTextField artsttextfield;
	
	JComboBox choicecombo;
	
	JButton display;
	
	JTextArea textArea;
	
	JScrollPane scrollpane;
	
	public String finalartistname;
	
	

	public Client(){
		
		jf= new JFrame("RMI Client");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(1000, 1000);
		//jf.setVisible(true);
		jf.setLayout(null);
		GUI();
	}
	
	
	// Contains All GUI code
	public void GUI()
	{
		
		heading = new JLabel("RMI-based Access to Hadoop/MapReduce-Processed Music Datasets");
		heading.setFont(new Font("Serif",Font.PLAIN, 30));
		heading.setBounds(80, 20,900, 40);
		jf.add(heading);
		
		artistname = new JLabel("Artist Name :");
		artistname.setBounds(100,90,250,20);
		jf.add(artistname);
		
		Selectchoice = new JLabel("Select Your Choice :");
		Selectchoice.setBounds(100,160,350,20);
		jf.add(Selectchoice);
		
		artsttextfield = new JTextField();
		artsttextfield.setBounds(270, 90, 250, 20);
		jf.add(artsttextfield);
		
		final DefaultComboBoxModel combobox = new DefaultComboBoxModel();
		combobox.addElement("Find Location");
		combobox.addElement("Find the List of songs");
		
		choicecombo = new JComboBox(combobox);
		choicecombo.setSelectedIndex(0);
		choicecombo.setBounds(270,150, 250, 30);
		jf.add(choicecombo);
		
		
		display = new JButton("DISPLAY");
		display.setBounds(320,240, 150,50);
		jf.add(display);
		display.addActionListener(this);
		
		displayresult = new JLabel();
		displayresult.setBounds(100,300,400,40);
	//	heading.setFont(new Font("Serif",Font.PLAIN, 30));
		jf.add(displayresult);
		displayresult.setVisible(false);
		
		displaylocation = new JLabel();
		displaylocation.setBounds(100, 350, 500, 20);
		jf.add(displaylocation);
		displaylocation.setVisible(false);
		
		textArea = new JTextArea(300,300);
		scrollpane = new JScrollPane(textArea);
		scrollpane.setBounds(100,350, 500, 300);
		jf.add(scrollpane);
		scrollpane.setVisible(false);
	
		
		jf.setVisible(true);
		
	}


	// button click event to send an artistname to server to get location or songs
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		Object src = arg0.getSource();
		
		if(src == display)
		{
			
			displayresult.setText(null);
			
			displaylocation.setText(null);
			
			textArea.setText(null);
			
			displayresult.setVisible(false);
			
			displaylocation.setVisible(false);
			
			scrollpane.setVisible(false);
		
			String artistname = artsttextfield.getText();
			
			System.out.println(artistname);
			
			
			// IF Location is selected then append 1 at the end off artistname
			if(choicecombo.getSelectedIndex() == 0)
			{
				finalartistname = artistname + "1";
			}
			// else append 2 for the songs
			else
			{
				finalartistname = artistname + "2";
				
			}
			
			
			try {
				// call sendtoserver method with requested artistname
				sendtoserver(finalartistname);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	// method to display result from server
	public void sendtoserver(String artistname) throws MalformedURLException, RemoteException, NotBoundException
	{
		// made a connection using localhost and port number
		Registry registry = LocateRegistry.getRegistry("localhost",connection_variable.RMI_PORT);
		
		// create a reference of an interface
		Shared.commoninterface common = (Shared.commoninterface)registry.lookup(connection_variable.RMI_ID);
		
		
	//	Shared.commoninterface common = (Shared.commoninterface) Naming.lookup("rmi://localhost:5000/dhruv");
		
		// call interface method
		String finalresult = common.sendartistname(artistname);
		
		System.out.println("Final Result :"+finalresult);
		
		// If it's location then display Location
		if(artistname.endsWith("1"))
		{
			displayresult.setText("Location of Artistname :"+artistname.substring(0, artistname.length()-1));
			
			displayresult.setVisible(true);
			
			displaylocation.setText(finalresult);
			
			
			displaylocation.setVisible(true);
			
			
		}
		// else it's display songs list in textarea
		else
		{
			displayresult.setText("List Of Songs of Artistname :"+artistname.substring(0, artistname.length()-1));
			
			displayresult.setVisible(true);
			
			String songs[] = finalresult.split("<I>");
			
			for(int i=0;i<songs.length;i++)
			{
				System.out.println("songs :"+songs[i]);
				
				textArea.append(songs[i]+"\n");
			}
			
			scrollpane.setVisible(true);

		}
		
		
	}
	// call Client class
	public static void main(String args[])
	{
		
		Client c1 = new Client();
		
	}

}
