package gamefiles;

import java.io.Serializable;

public class LimitCard extends Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LimitCard(String n, int iv) {
		super(n, iv);
	}

	public LimitCard(Card card) {
		super(card.getName(), card.getInternalVal());
	}

}
