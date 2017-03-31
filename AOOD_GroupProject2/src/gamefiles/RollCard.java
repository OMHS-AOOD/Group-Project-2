package gamefiles;

import java.io.Serializable;

public class RollCard extends RemedyCard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RollCard(String n, char t, int iv){
		super(n, t, iv);

	}

	public RollCard(Card card) {
		super(card.getName(), ((RollCard) card).getType(), card.getInternalVal());
	}

}
