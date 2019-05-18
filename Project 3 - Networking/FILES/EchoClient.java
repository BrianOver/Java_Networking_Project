import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;

public class EchoClient 
{
   public static void main(String[] args) throws IOException 
   {
      ApplesToApples Ape = new ApplesToApples();
   
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
   
      System.out.print ("Hit Enter");
      boolean startGreen = false; //If true, start green (only one player selects this)
      int WinCount = 0; //If starting green, change to "-1"
      while ((userInput = stdIn.readLine()) != null) //Checks for user input
      {
          boolean choose = false; //loop iterator used throughout my code
          
          if(userInput.equals("") || userInput.equals(" ")) //If user input is blank, send blank (Server class code)
            out.println("Brian: ");
          else
            out.println("Brian: "+userInput); //Else send user input
          
          String inputs;
          if(startGreen == false) //If red card player, waiting for the green card to be sent
          {     
             while((inputs = in.readLine()).indexOf("**") < 0)
             {
             }
              System.out.println(inputs);    
          }
          else //If green card player
          {
            inputs = ("Chooser: Brian");//Redundant code
          }
         
          int c = 0; 
          choose = false;
          if(inputs.indexOf("Chooser: Brian") < 1 && startGreen == false) //Red card chooser
          {
             choose = false;
             while(choose == false)
             {
                System.out.println(""); //Chooseing which red card from hand
                Ape.printHand();
                System.out.print("What card? : ");
                String temp = keyboard.nextLine();
                if(temp.charAt(0) < 56 && temp.charAt(0) > 48)
                {
                   c = Integer.parseInt(temp);
                   choose = true;
                }
                else
                {
                   System.out.println("");
                   System.out.print("Try again : ");
                }
              }
             
              String t = Ape.playCard(c-1); 
              System.out.println("(Echo) "+t);
              out.println("Brian: $$"+t); //Sends red card 
                
              choose = false;
              String tempt2;
              while((tempt2 = in.readLine()).indexOf("Chooser:") <0) //Waits for Green to select winner
                  System.out.println(tempt2);
              if(tempt2.indexOf("Chooser: Brian") > -1) //Checks if you won
              {
                inputs = tempt2;
                startGreen = true;
              }
            } 
         
          if(inputs.indexOf("Chooser: Brian") > -1  || startGreen == true) //Green card chooser
          {
             WinCount++;
             System.out.println("Score: "+WinCount);
             if(WinCount == 10)
             {
               out.println("WINNER: Brian");
             }
             
             System.out.print("Choose green? (y/n)"); //Delay for better visuals
             while(choose == false)  
             {
                String temp = keyboard.nextLine();
                if(temp.equals("y") || temp.equals("Y"))
                {
                   String[] tempArray =  Ape.getNewGreen();
                   String output = tempArray[0];
                   System.out.println("(Echo) "+output);
                   out.println("Brian: **"+output); //Sends out Green Card
                   choose = true;
                }
                else
                {
                   System.out.println("");
                   System.out.print("Try again : ");
                }
             }
                        
             String tempt;
             while((tempt = in.readLine()).indexOf("$$") <0) {}
               //System.out.println(tempt);
             System.out.println(in.readLine());  
             System.out.println(tempt);      
  
             System.out.println("");
             System.out.print("Which player wins? (Type Chooser: __)");  //Chooses which red player wins
 
             while(choose == false)
             {
               String winner = keyboard.nextLine(); 
               if(winner.equals("Chooser: Caleb") || winner.equals("Chooser: Ben")) 
               {
                 out.println(winner); //Sends which red player won
                 choose = true;
               }
               else
               {
                 System.out.println("");
                 System.out.print("Try again");
               }
             }        
             startGreen = false; 
           }      
        System.out.print("Hit Enter");
      }
  }
}