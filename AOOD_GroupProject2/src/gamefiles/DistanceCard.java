package gamefiles;

import java.io.Serializable;

public class DistanceCard extends Card implements Serializable {
	private int value;
	public DistanceCard(String n, int val){
		super(n);
		value = val;
	}
	public int getValue(){
		return value;
	}
}
