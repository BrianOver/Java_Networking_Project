import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class main {
	
	static Scanner k = new Scanner(System.in);
	static ArrayList<Socket> socks = new ArrayList<>();
	static ArrayList<BufferedReader> input = new ArrayList<>();
	static ArrayList<PrintWriter> output = new ArrayList<>();
	static ArrayList<Card> gdeck = new ArrayList<>();
	static ArrayList<Card> rdeck = new ArrayList<>();
	static ArrayList<Card> hand = new ArrayList<>();
	static ServerSocket host  = null;
	
	public static void main(String[] args)  throws IOException 
	{
		//build decks
		fillgreen();
		fillred();
		out.print("Do you want to host? (y/n) ");
		if(k.nextLine().equals("y"))
		{
			String user = "y";
			try {
				host = new ServerSocket(5001); 
			} 
			catch (IOException e) 
			{ 
				System.err.println("Could not listen on port: 5001."); 
				System.exit(1); 
			}
		    do{
		    	out.flush();
		    	System.out.println("This computer is: + " + InetAddress.getLocalHost());
			    System.out.println ("Waiting for connection on port 5001...");
			    try { 
			    	socks.add(host.accept());
			    	input.add(new BufferedReader(new InputStreamReader( socks.get(socks.size()-1).getInputStream())));
			    	output.add(new PrintWriter(socks.get(socks.size()-1).getOutputStream(), true));
			    } 
			    catch (IOException e) 
			    { 
			       System.err.println("Accept failed."); 
			       System.exit(1); 
			    } 
			    out.print("Do you want to accept more input? (y/n)");
		    }
		    while((user = k.nextLine()).equals("y"));
		    for(int i = 0;i<socks.size();i++)
		    	for(int z = 0;z<output.size();z++)
		    		if(i != z)
		    			output.get(z).println( );
		    for(int i = 0;i<socks.size();i++)
		    	output.get(i).println("*");
		    out.println("Done establishing connection, going to GREEN");
		    GREEN();
		}
		else
		{
			 System.out.println("This computer is: + " + InetAddress.getLocalHost());
		    System.out.print("Enter server IP: ");        
		    String serverHostname = k.nextLine();
		    System.out.println ("Attemping to connect to host " + serverHostname + " on port 5001.");
		    try {
		    	
		        socks.add(new Socket(serverHostname, 5001));
		        output.add(new PrintWriter(socks.get(socks.size()-1).getOutputStream(), true));
		        input.add(new BufferedReader(new InputStreamReader(socks.get(socks.size()-1).getInputStream())));
		        String inputLine; 
		        while ((inputLine = input.get(0).readLine()) != "*") 
		        {
		        	out.println(inputLine);
		        	socks.add(new Socket(inputLine, 5001));
			        output.add(new PrintWriter(socks.get(socks.size()-1).getOutputStream(), true));
			        input.add(new BufferedReader(new InputStreamReader(socks.get(socks.size()-1).getInputStream())));
		        }
		        out.println("Done connecting, going to RED");
		    } 
		    catch (UnknownHostException e) {
		        System.err.println("Don't know about host: " + serverHostname);
		        System.exit(1);
		    }
		    catch (IOException e) {
		        System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
		        System.exit(1);
		    }
		    RED();
		}
	}
	
	static void fillgreen() throws FileNotFoundException
	{
		Scanner file = new Scanner(new File("green"));
		String s;
		while(file.hasNextLine())
		{
			s = file.nextLine();
			gdeck.add(new Card(s));
		}
	}
	static void fillred() throws FileNotFoundException
	{
		Scanner file = new Scanner(new File("red"));
		String s;
		while(file.hasNextLine())
		{
			s = file.nextLine();
			rdeck.add(new Card(s));
		}
	}
	
	static void RED() throws FileNotFoundException
	{
		while(hand.size() < 7)
		{
			hand.add(rdeck.remove((int)(Math.random()*rdeck.size())));
			if(rdeck.size() == 0)
				fillred();
		}
		out.flush();
		for(int i = 0;i<hand.size();i++)
			out.println("("+i+") "+hand.get(i));
	}
	
	static void GREEN() throws FileNotFoundException
	{
		Card held = gdeck.remove((int)(Math.random()*gdeck.size()));
		if(gdeck.size() == 0)
			fillgreen();
		out.println(held);
      for(;;)
      {
      
      }
	}
}