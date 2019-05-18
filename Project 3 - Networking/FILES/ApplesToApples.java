import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplesToApples {
	ArrayList<String> red;
	ArrayList<String> green;
	ArrayList<String> redDetail;
	ArrayList<String> greenDetail;
	ArrayList<Card> hand;
	public ApplesToApples()
	{
		red= new ArrayList<String>();
		green= new ArrayList<String>();
		redDetail= new ArrayList<String>();
		greenDetail= new ArrayList<String>();
		hand= new ArrayList<Card>();
		createDeck();
	}
	
	public void createDeck()
	{
		Scanner redFile = null, greenFile = null;
		try {
			redFile= new Scanner(new File("red.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			greenFile= new Scanner(new File("green.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(redFile.hasNextLine())
		{
			String line=redFile.nextLine();
			String name=line.substring(0,line.indexOf(" - ")-1);
			String detail=line.substring(line.indexOf(" - ")+1);
			red.add(redFile.nextLine());
			redDetail.add(detail);
		}
		while(greenFile.hasNextLine())
		{
			String line=greenFile.nextLine();
  			String name=line.substring(0,line.indexOf(" - ")-1);
			String detail=line.substring(line.indexOf(" - ")+1);
			green.add(greenFile.nextLine());
			greenDetail.add(detail);
		}
		fillHand();
	}
	
	public void fillHand()
	{
		for(int i=0;i<7;i++)
		{
			int r = (int)(Math.random()*red.size()-1);
			hand.add(new Card(red.remove(r),redDetail.remove(r)));
		}
	}
	
	public String[] getNewGreen()
	{
		int r =(int) (Math.random()*green.size());
		return new String[] {green.remove(r),greenDetail.remove(r)};
	}
	
	public String playCard(int index)
	{
      String s = hand.remove(index).toString();
      int temp = (int)(Math.random()*red.size());
      hand.add(new Card(red.remove(temp), redDetail.remove(temp)));
		return s;
	}
	
	public void printHand()
	{
		for(int i=0;i<hand.size();i++)
			System.out.println(i+1+" :: "+hand.get(i).toString());
	}
}