
public class RemedyCard extends Card {
	private char type;
	//'s' = Go
	//'a' = Repairs
	//'l' = End of Limit
	//'o' = Gasoline
	//'f' = Spare Tire
	//'*' = Road Service
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
