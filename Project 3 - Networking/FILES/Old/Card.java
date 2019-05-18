import java.net.*; 
import java.io.*; 
import java.util.*;

public class Card 
{ 
   private String name;
  
   public Card(String s)
   {
      name = "Brian: *";
      name += s;
   }
   
   public String getName()
   {
      return name;
   }
   
   public void setName(String s)
   {
      name = "Brian : *";
      name += s;
   }
   
   public String toString()
   {
      return name;
   }
}