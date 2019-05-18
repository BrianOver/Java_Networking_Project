import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.util.concurrent.*;

public class Server 
{   
    ArrayList<Socket> SocketList = new ArrayList<>();
    
    public static void main(String[] args) 
    {
          new Server().startServer();
    }

    public void startServer() 
    {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    ServerSocket serverSocket = new ServerSocket(5001);
                    System.out.println("This computer is: + " + InetAddress.getLocalHost());
                    System.out.println("Waiting for clients to connect...");
                    while (true) 
                    {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                        SocketList.add(clientSocket);
                       
                    }
                } 
                catch (IOException e) 
                {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private class ClientTask implements Runnable 
    {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) 
        {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() 
        {
            System.out.println("Got a client !"); //Notifies for each connection
 
            try
            {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
                BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 
   
                String inputLine; 
           
               while ((inputLine = in.readLine()) != null) //Client input
               { 
                   if(inputLine.indexOf("WINNER") > -1) //Checking for if game is over
                   {
                     for(Socket item : SocketList) 
                     {
                       if(!item.equals(clientSocket)) 
                       {
                         PrintWriter outs = new PrintWriter(item.getOutputStream(), true);  
                         outs.println(inputLine+"\nGame Over!!");                                
                       }
                      }
                      break; 
                    }
                   
                   if(!inputLine.equals("Caleb: ") && !inputLine.equals("Ben: ") && !inputLine.equals("Brian: "))
                     System.out.println(inputLine);
                           
                   if(SocketList.size() < 1) 
                     System.out.println("SocketList is "+SocketList.size()); //TroubleShooting code 
                   
                   //Sends information of each socket to the other clients
                   for(Socket item : SocketList) 
                   {
                     if(!item.equals(clientSocket)) //Makes sure its not the current client thread
                     {
                        PrintWriter outs = new PrintWriter(item.getOutputStream(), true); //Creates reader from other clients
                        if(!inputLine.equals("Caleb: ") && !inputLine.equals("Ben: ") && !inputLine.equals("Brian: ") )
                           outs.println(inputLine); //Sends the client info out
                        else
                           outs.println(""); //Troubleshooting                     
                     }
                   }                                
               } 
            }
            catch(IOException e){}
            
		    	try 
            {
                clientSocket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}