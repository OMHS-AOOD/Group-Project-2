import java.awt.Graphics;
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
	public String getOwner() {
		return owner;
	}
	public String getName(){
		return name;
	}
	@Override
	public void paintComponent(Graphics g){
		g.drawRect(10, 10, this.getWidth()-20, this.getHeight()-20);
		g.drawString(name +"(" + owner + ")", 10, 10);
	}
}
