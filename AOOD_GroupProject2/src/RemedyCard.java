
public class RemedyCard extends Card {
	private char type;
	public RemedyCard(String n, char t){
		super(n);
		type = t;
	}
	public char getType(){
		return type;
	}
}
