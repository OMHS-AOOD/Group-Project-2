import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CardStack extends JPanel{
	protected boolean takesCard;
	protected ArrayList<Card> stack;
	protected ArrayList<DraggableCard> visibleStack;
	protected String name;
	protected String owner;
	protected Color myColor;
	public CardStack(String n, String o, Color c){
		stack = new ArrayList<Card>();
		visibleStack = new ArrayList<DraggableCard>();
		takesCard = true;
		name = n;
		owner = o;
		myColor = c;
		this.setLayout(null);
	}
	public void addCard(Card c){
		stack.add(c);
	}
	public void addCard(DraggableCard c){
		visibleStack.add(c);
		stack.add(c.getCard());
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
		g.setColor(myColor);
		g.drawRect(10, 10, this.getWidth()-20, this.getHeight()-20);
		g.drawString(name +"(" + owner + ")", 10, 10);
	}
	public void setOwner(String o){
		owner = o;
	}
	public void setDrop(boolean b) {
		takesCard = b;
		
	}
	public void setColor(Color c) {
		myColor = c;
	}
	public int getCurrentSize() {
		return stack.size();
	}
	
}
