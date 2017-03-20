import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer {
	protected String name;
	protected CardStack battle, distance, safety;
	protected ArrayList<Card> hand;
	protected ArrayList<DraggableCard> visibleCards;
	protected int pointsToWin;
	public HumanPlayer(String n){
		name = n;
		battle = new CardStack("Battle", name, Color.BLACK);
		distance = new CardStack("Distance", name, Color.BLACK);
		safety = new CardStack("Safety",name, Color.BLACK);
		hand = new ArrayList<Card>();
		visibleCards = new ArrayList<DraggableCard>();
		pointsToWin = 1000;

	}
	public void addCardToHand(DraggableCard dc){
		visibleCards.add(dc);
		hand.add(dc.getCard());
	}
	public String handToString(){
		String out = "Player " + name + "\n";
		for(Card c: hand){
			out += "\n" + c.getName();
		}
		return out;
	}
	public DraggableCard getCard(int i){
		return visibleCards.get(i);
	}
	public Card playCard(int i){
		return hand.remove(i);
	}
	public String getName(){
		return name;
	}
	public CardStack getBattle(){
		return battle;
	}
	public CardStack getDistance(){
		return distance;
	}
	public CardStack getSafety(){
		return safety;
	}
	public void adjustPointsToWin(int i){
		pointsToWin = pointsToWin-i;
	}
	public int getCurrentPoints(){
		int points = 0;
		for(Card c: distance.getStack()){
			points += ((DistanceCard) c).getValue();
		}
		return points;
	}
	public int getNeededDistance(){
		return pointsToWin - this.getCurrentPoints();
	}
	public boolean hasWon(){
		if(getCurrentPoints() == pointsToWin){
			return true;
		}
		return false;
	}
	
	public boolean canMoveNormally(){
		return true;
	}
	public boolean canMove(){
		return true;
	}
	
	public ArrayList<DraggableCard> getHand(){
		return visibleCards;
	}
	
	
}	
