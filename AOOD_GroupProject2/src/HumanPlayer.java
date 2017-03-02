import java.util.ArrayList;

public class HumanPlayer {
	protected String name;
	protected CardStack battle, speed, distance, safety;
	protected ArrayList<Card> hand;
	public HumanPlayer(String n){
		name = n;
		battle = new CardStack();
		speed = new CardStack();
		distance = new CardStack();
		safety = new CardStack();
		hand = new ArrayList<Card>();
	}
	public void addCardToHand(Card c){
		hand.add(c);
	}
	public String handToString(){
		String out = "Player " + name + "\n";
		for(Card c: hand){
			out += "\n" + c.getName();
		}
		return out;
	}
	public Card getCard(int i){
		return hand.get(i);
	}
	public Card playCard(int i){
		return hand.remove(i);
	}
}	
