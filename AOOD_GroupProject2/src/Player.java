
public class Player {
	private String name;
	private CardStack battle, speed, distance, safety, hand;
	public Player(String n){
		name = n;
		battle = new CardStack();
		speed = new CardStack();
		distance = new CardStack();
		safety = new CardStack();
		hand = new CardStack();
	}
}	
