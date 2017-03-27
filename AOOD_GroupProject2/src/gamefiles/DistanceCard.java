package gamefiles;

import java.io.Serializable;

public class DistanceCard extends Card implements Serializable {
	private int value;
	public DistanceCard(String n, int val, int iv){
		super(n, iv);
		value = val;
	}
	public DistanceCard(Card card) {
		super(card.getName(), card.getInternalVal());
		value = ((DistanceCard) card).getValue();
	}
	public int getValue(){
		return value;
	}
}
