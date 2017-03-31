package gamefiles;

import java.io.Serializable;

public class SafetyCard extends Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char type;

	public SafetyCard(String n, char t, int iv){
		super(n, iv);
		type = t;
	}

	public SafetyCard(Card card) {
		super(card.getName(), card.getInternalVal());
		type=((SafetyCard) card).getType();
	}

	public char getType(){
		return type;
	}
}
