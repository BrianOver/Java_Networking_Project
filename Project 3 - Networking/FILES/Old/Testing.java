

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;

import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class Testing {
	
	static Scanner k = new Scanner(System.in);
	static ArrayList<Socket> socks = new ArrayList<>();
	static ArrayList<BufferedReader> input = new ArrayList<>();
	static ArrayList<PrintWriter> output = new ArrayList<>();
	static ServerSocket host  = null;
	
	public static void main(String[] args)  throws IOException 
	{
		//ApplesToApples game = new ApplesToApples();
		//game.createDeck();
		//build decks
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
			    			output.get(z).println(socks.get(i).toString().substring(socks.get(i).toString().indexOf("/")+1, socks.get(i).toString().indexOf(",")));
		    for(int i=0;i<socks.size();i++)
		    	output.get(i).println("*");
		    while(true)
		    {}
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
		        while ((inputLine = input.get(0).readLine()) != null) 
		        {
                  System.out.println("!");
                  System.out.println(inputLine);
                  Socket gen = new Socket(inputLine,5001);//THIS IS THE PROBLEM
                  System.out.println("Created Socket");
                	//socks.add(new Socket(inputLine, 5001));
                  socks.add(gen);
                  System.out.println("Added Socket");
                  System.out.println("After Socket add");
			        output.add(new PrintWriter(socks.get(socks.size()-1).getOutputStream(), true));
                 System.out.println("After PrintWiter add");
			        input.add(new BufferedReader(new InputStreamReader(socks.get(socks.size()-1).getInputStream())));
                 System.out.println("After BuffReader add");
		        }
              System.out.println("After while loop");

		    } 
		    catch (UnknownHostException e) {
		        System.err.println("Don't know about host: " + serverHostname);
		        System.exit(1);
		    }
		    catch (IOException e) {
		        System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
		        System.exit(1);
		    }
          
          catch (Exception e)
          {
            System.err.println("CEAHT");
            System.exit(1);
           } 
		}
	}
}
