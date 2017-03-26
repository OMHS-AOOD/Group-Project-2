package gamefiles;

import java.io.Serializable;

public class HazardCard extends Card implements Serializable {
	private char type;
	//'a' = Accident
	//'o' = Out of Gas
	//'f' = Flat Tire
	//'s' = Stop
	public HazardCard(String n,char t){
		super(n);
		type=t;
	}
	public char getType(){
		return type;
	}
}
