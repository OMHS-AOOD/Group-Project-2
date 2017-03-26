package gamefiles;

import java.io.Serializable;

public class RemedyCard extends Card implements Serializable {
	protected char type;
	//'s' = Go
	//'a' = Repairs
	//'o' = Gasoline
	//'f' = Spare Tire
	//'*' = Road Service
	public RemedyCard(String n, char t){
		super(n);
		type = t;
	}

	public char getType(){
		return type;
	}
}
