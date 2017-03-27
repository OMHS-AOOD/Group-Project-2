package gamefiles;

import java.io.Serializable;

public class RollCard extends RemedyCard implements Serializable{
	public RollCard(String n, char t, int iv){
		super(n, t, iv);

	}

	public RollCard(Card card) {
		super(card.getName(), ((RollCard) card).getType(), card.getInternalVal());
	}

}
