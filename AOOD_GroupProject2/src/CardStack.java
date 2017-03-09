import java.util.ArrayList;

import javax.swing.JPanel;

public class CardStack extends JPanel{
	protected boolean takesCard;
	protected ArrayList<Card> stack;
	protected String name;
	protected String owner;
	public CardStack(String n, String o){
		stack = new ArrayList<Card>();
		takesCard = true;
		name = n;
		owner = o;
		this.setLayout(null);
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
	public void drawPile(){
		this.getGraphics().drawRect(10, 10, this.getWidth()-20, this.getHeight()-20);
		this.getGraphics().drawString(name, 10, 0);
	}
	public String getOwner() {
		return owner;
	}
	public String getName(){
		return name;
	}
}
