package gamefiles;

import java.io.Serializable;

public class LimitCard extends Card implements Serializable{

	public LimitCard(String n, int iv) {
		super(n, iv);
	}

	public LimitCard(Card card) {
		super(card.getName(), card.getInternalVal());
	}

}
