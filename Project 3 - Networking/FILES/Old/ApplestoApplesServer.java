import java.net.*; 
import java.io.*; 
import java.util.*;

public class ApplestoApplesServer 
{ 
   ArrayList<Card> RedCard = new ArrayList<Card>();
   ArrayList<Card> GreenCard = new ArrayList<Card>();
   Scanner keyboard = new Scanner(System.in);
      
   public void FillRedCards()
   {  
      int count = 0;
      while(count <= 10)
      {
         System.out.print("Enter RedCard");
         RedCard.add(new Card(keyboard.next()));
         count++;
         System.out.println("");
      }   
   }
   
   public void FillGreenCards()
   {
      int count = 0;
      while(count <= 10)
      {
         System.out.print("Enter GreenCard");
         GreenCard.add(new Card(keyboard.next()));
         count++;
         System.out.println("");
      }
   }
   
   public void PreSet()
   {
      GreenCard.add(new Card("Pimpin'"));
      GreenCard.add(new Card("Fake"));
      GreenCard.add(new Card("Literaly dead rn ;)"));
      GreenCard.add(new Card("Cancer"));
      GreenCard.add(new Card("Ben tbh"));
      GreenCard.add(new Card("Same"));
      GreenCard.add(new Card("Bombin'"));
      GreenCard.add(new Card("Literaly Hitler"));
      
      RedCard.add(new Card("Ben"));
      RedCard.add(new Card("Goku"));
      RedCard.add(new Card("Sanic"));
      RedCard.add(new Card("Vultron"));    
   }
   
   public static void main(String[] args) throws IOException 
   { 
      ArrayList<Socket> SocketList = new ArrayList<Socket>(); 
      ArrayList<PrintWriter> outList = new ArrayList<PrintWriter>();
      ArrayList<BufferedReader> inList = new ArrayList<BufferedReader>();
     
      ApplestoApplesServer test = new ApplestoApplesServer();
      test.PreSet();
      
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
         SocketList.add(clientSocket);
        // System.out.println(clientSocket);

      } 
      catch (IOException e) 
      { 
         System.err.println("Accept failed."); 
         System.exit(1); 
      } 
   
      System.out.println ("Connection successful");
      System.out.println ("Waiting for input.....");
   
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
      outList.add(out);
      BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));   
      inList.add(in);
      String inputLine;   
      
         while(true)
         {
            System.out.println("Loop begins");
            
            try 
            { 
               clientSocket = serverSocket.accept(); 
            } 
            catch (IOException e) 
            { 
               System.out.println("Error at try catch");
            } 
            
            System.out.println("Try catch works");
           
            boolean ins  = false;
            for(Socket item : SocketList)
            {
               if(item.equals(clientSocket))
               {
                  ins = true;
                        //break;
               }
            }
            if(ins == false)
            {
               SocketList.add(clientSocket);
               out = new PrintWriter(clientSocket.getOutputStream(), true); 
               outList.add(out);
               in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));   
               inList.add(in);
             }

             System.out.println("Socket adding loop works");

             if(SocketList.size() < 1)
             {
               break;
             }
             for(int i = 0; i < SocketList.size(); i++)
             {
                   clientSocket = SocketList.get(i);
                   in = inList.get(i);
                   out = outList.get(i);
                   System.out.println (""+clientSocket+"  " +clientSocket.getPort()); 
                   out.println(in.readLine());
                   System.out.println("test");
                   
                   if(in.readLine().equals("Bye."))
                   {
                     inList.remove(i);
                     outList.remove(i);
                     SocketList.remove(i);
                   }
                   
                   if(SocketList.size() < 1)
                   {
                      break;
                   }
             }
        }    
    }
}