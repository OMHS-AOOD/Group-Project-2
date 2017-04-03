package gamefiles;

import java.io.Serializable;

public class EoLimitCard extends Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EoLimitCard(String n, int iv) {
		super(n, iv); 

	}

	public EoLimitCard(Card card) {
		super(card.getName(), card.getInternalVal());
	}

}
