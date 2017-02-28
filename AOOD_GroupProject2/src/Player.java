
public class Player {
	protected String name;
	protected CardStack battle, speed, distance, safety, hand;
	public Player(String n){
		name = n;
		battle = new CardStack();
		speed = new CardStack();
		distance = new CardStack();
		safety = new CardStack();
		hand = new CardStack();
	}
	public void addCardToHand(Card c){
		hand.addCard(c);
	}
	public String handToString(){
		String out = "Player " + name + "\n";
		for(Card c: hand.getStack()){
			out += "\n" + c.getName();
		}
		return out;
	}
}	
