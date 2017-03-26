package gamefiles;

import java.io.Serializable;

public class SafetyCard extends Card implements Serializable {
	private char type;

	public SafetyCard(String n, char t){
		super(n);
		type = t;
	}

	public char getType(){
		return type;
	}
}
