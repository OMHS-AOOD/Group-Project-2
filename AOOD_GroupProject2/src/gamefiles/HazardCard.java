package gamefiles;

import java.io.Serializable;

public class HazardCard extends Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char type;
	//'a' = Accident
	//'o' = Out of Gas
	//'f' = Flat Tire
	//'s' = Stop
	public HazardCard(String n,char t, int iv){
		super(n, iv);
		type=t;
	}
	public HazardCard(Card card) {
		super(card.getName(), card.getInternalVal());
		type=((HazardCard) card).getType();
	}
	public char getType(){
		return type;
	}
}
