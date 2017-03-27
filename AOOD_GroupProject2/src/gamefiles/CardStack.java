package gamefiles;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CardStack extends JPanel implements Serializable {
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
	public CardStack(CardStack c2){
		takesCard = c2.canDropCardOn();
		name = c2.getName();
		owner = c2.getOwner();
		myColor = c2.getColor();
		stack = new ArrayList<Card>();
		for(Card c: c2.getStack()){
			if (c instanceof DistanceCard) {
				stack.add(new DistanceCard(c));
			} else if (c instanceof EoLimitCard) {
				stack.add(new EoLimitCard(c));
			} else if (c instanceof HazardCard) {
				stack.add(new HazardCard(c));
			} else if (c instanceof LimitCard) {
				stack.add(new LimitCard(c));
			} else if (c instanceof RemedyCard) {
				stack.add(new RemedyCard(c));
			} else if (c instanceof RollCard) {
				stack.add(new RollCard(c));
			} else if (c instanceof SafetyCard) {
				stack.add(new SafetyCard(c));
			}
		}
		visibleStack = new ArrayList<DraggableCard>();
		createDraggableCards();

		this.setLayout(null);
	}



	private Color getColor() {
		return myColor;
	}
	public void addCard(DraggableCard c) {
		visibleStack.add(c);
		stack.add(c.getCard());
	}

	public DraggableCard removeCard() {
		if(stack.size() == 0){
		}
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
		g.fillRect(10, 15, this.getWidth()-20, 15);
		g.setFont(new Font("OCR A Std", Font.PLAIN, 8));
		if (owner != "") {
			g.drawString(name + ": " + owner, 10, 10);

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
	
	public void createDraggableCards(){
		for(Card c: stack){
			DraggableCard drc = new DraggableCard(c, this.owner, this.getX() + 15, this.getY() + 35);
			visibleStack.add(drc);
			if(name.equals("Deck")){
				drc.setFlip(true);
			}
		}
	}

}
