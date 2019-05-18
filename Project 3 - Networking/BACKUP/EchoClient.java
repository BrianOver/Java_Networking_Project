import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoClient 
{
   public static void main(String[] args) throws IOException 
   {
   
      Scanner keyboard = new Scanner(System.in);
      System.out.println("This computer is: + " + InetAddress.getLocalHost());
      System.out.print("Enter server IP: ");        
      String serverHostname = keyboard.nextLine();
   
      if (args.length > 0)
         serverHostname = args[0];
      System.out.println ("Attemping to connect to host " + serverHostname + " on port 5001.");
   
      Socket echoSocket = null;
      PrintWriter out = null;
      BufferedReader in = null;
   
      try {
         echoSocket = new Socket(serverHostname, 5001);
         out = new PrintWriter(echoSocket.getOutputStream(), true);
         in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
      } 
      catch (UnknownHostException e) {
         System.err.println("Don't know about host: " + serverHostname);
         System.exit(1);
      } 
      catch (IOException e) {
         System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
         System.exit(1);
      }
   
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
           String userInput;
   
      System.out.print ("input: ");
      while ((userInput = stdIn.readLine()) != null) {
         out.println(userInput);
         System.out.println("echo: " + in.readLine());
         
         String temp = "";   
         BufferedReader ins = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
         if( (temp = ins.readLine()) != null )
            System.out.println("Other Client : "+ins.readLine());
        
         System.out.print ("input: ");
      }
   
      out.close();
      in.close();
     // ins.close();
      stdIn.close();
      echoSocket.close();
   }
}

