import java.net.*; 
import java.io.*; 

public class EchoServer { 
   public static void main(String[] args) throws IOException 
   { 
      ServerSocket serverSocket = null; 
   
      try { 
         serverSocket = new ServerSocket(5001); 
      } 
      catch (IOException e) 
      { 
         System.err.println("Could not listen on port: 5001."); 
         System.exit(1); 
      } 
   
      Socket clientSocket = null; 
      System.out.println("This computer is: + " + InetAddress.getLocalHost());
      System.out.println ("Waiting for connection on port 5001...");
   
      try { 
         clientSocket = serverSocket.accept(); 
      } 
      catch (IOException e) 
      { 
         System.err.println("Accept failed."); 
         System.exit(1); 
      } 
   
      System.out.println ("Connection successful");
      System.out.println ("Waiting for input.....");
   
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
      BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 
   
      String inputLine; 
   
      while ((inputLine = in.readLine()) != null) 
      { 
         System.out.println ("Server: " + inputLine); 
         out.println(inputLine); 
      
         if (inputLine.equals("Bye.")) 
            break; 
      } 
   
      out.close(); 
      in.close(); 
      clientSocket.close(); 
      serverSocket.close(); 
   } 
} 
