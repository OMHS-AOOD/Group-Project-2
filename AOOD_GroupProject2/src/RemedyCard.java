
public class RemedyCard extends Card {
	private char type;
	public RemedyCard(String n, char t){
		super(n);
		type = t;
	}
	public RemedyCard(String n){
		super(n);

	}
	public char getType(){
		return type;
	}
}
