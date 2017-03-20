
public class SafetyCard extends Card {
	private char type;
	//'s' = Go
	//'a' = Repairs
	//'l' = End of Limit
	//'o' = Gasoline
	//'f' = Spare Tire
	//'*' = Road Service
	public SafetyCard(String n, char t){
		super(n);
		type = t;
	}

	public char getType(){
		return type;
	}
}
