package gamefiles;

import java.io.Serializable;

public class RemedyCard extends Card implements Serializable {
	protected char type;
	//'s' = Go
	//'a' = Repairs
	//'o' = Gasoline
	//'f' = Spare Tire
	//'*' = Road Service
	public RemedyCard(String n, char t, int iv){
		super(n, iv);
		type = t;
	}

	public RemedyCard(Card card) {
		super(card.getName(),card.getInternalVal());
		type = ((RemedyCard) card).getType();
	}

	public char getType(){
		return type;
	}
}
