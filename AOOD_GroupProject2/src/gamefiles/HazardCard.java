package gamefiles;

public class HazardCard extends Card {
	private char type;
	//'s' = Stop
	//'a' = Accident
	//'l' = Speed Limit
	//'o' = Out of Gas
	//'f' = Flat Tire
	public HazardCard(String n,char t){
		super(n);
		type=t;
	}
	public char getType(){
		return type;
	}
}
