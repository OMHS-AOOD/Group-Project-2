import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CardStack extends JPanel {
	protected boolean takesCard;
	protected ArrayList<Card> stack;
	protected ArrayList<DraggableCard> visibleStack;
	protected String name;
	protected String owner;
	protected Color myColor;
	

	public CardStack(String n, String o, Color c) {
		stack = new ArrayList<Card>();
		visibleStack = new ArrayList<DraggableCard>();
		takesCard = true;
		name = n;
		owner = o;
		myColor = c;
		this.setLayout(null);
		

	}



	public void addCard(DraggableCard c) {
		visibleStack.add(c);
		stack.add(c.getCard());
	}

	public DraggableCard removeCard() {

		stack.remove(stack.size() - 1);
		return visibleStack.remove(visibleStack.size()-1);
	}

	public ArrayList<Card> getStack() {
		return stack;
	}

	public boolean canDropCardOn() {
		return takesCard;
	}

	public String getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(myColor);
		g.drawRect(10, 15, this.getWidth() - 20, this.getHeight() - 20);
		g.fillRect(10, 15, this.getWidth()-20, 20);
		if (owner != "") {
			g.drawString(name + "(" + owner + ")", 10, 10);

		} else {
			g.drawString(name, 10, 10);

		}
	}

	
	public void setOwner(String o) {
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
	public ArrayList<DraggableCard> getVisibleStack(){
		return visibleStack;
	}



	public boolean containsCard(String name2) {
		for(Card c: stack){
			if(c.getName().equals(name2)){
				return true;
			}
		}
		return false;
	}
}
