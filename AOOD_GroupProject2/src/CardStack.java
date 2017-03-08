import java.util.ArrayList;

public class CardStack {
	protected boolean takesCard;
	protected ArrayList<Card> stack;
	public CardStack(){
		stack = new ArrayList<Card>();
		takesCard = true;
	}
	public void addCard(Card c){
		stack.add(c);
	}
	public Card removeCard(){
		return stack.remove(stack.size()-1);
	}
	public ArrayList<Card> getStack(){
		return stack;
	}
	public boolean canDropCardOn(){
		return takesCard;
	}
}
