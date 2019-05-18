
public class Card {
	String name, description;
	
	public Card(String s)
	{
		name=s;
		description="";
	}
	public Card(String s, String d)
	{
		name=s;
		description=d;
	}
	
	public String getFormatted()
	{
		return name.replaceAll(" - ", "*");
	}
	
	public String toString()
	{
		return name;
	}
}