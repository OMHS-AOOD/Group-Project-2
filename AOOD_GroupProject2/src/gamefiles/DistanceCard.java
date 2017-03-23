package gamefiles;

public class DistanceCard extends Card {
	private int value;
	public DistanceCard(String n, int val){
		super(n);
		value = val;
	}
	public int getValue(){
		return value;
	}
}
