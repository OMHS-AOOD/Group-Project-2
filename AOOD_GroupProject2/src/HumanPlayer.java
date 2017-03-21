import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer {
	protected String name;
	protected CardStack battle, distance, safety;
	protected ArrayList<Card> hand;
	protected ArrayList<DraggableCard> visibleCards;
	protected int pointsToWin;
	protected int used200s;

	public HumanPlayer(String n) {
		name = n;
		battle = new CardStack("Battle", name, Color.GREEN);
		distance = new CardStack("Distance", name, Color.GREEN);
		safety = new CardStack("Safety", name, Color.GREEN);
		hand = new ArrayList<Card>();
		visibleCards = new ArrayList<DraggableCard>();
		pointsToWin = 1000;
		used200s = 0;

	}

	public void addCardToHand(DraggableCard dc) {
		visibleCards.add(dc);
		hand.add(dc.getCard());
	}

	public String handToString() {
		String out = "Player " + name + "\n";
		for (Card c : hand) {
			out += "\n" + c.getName();
		}
		return out;
	}

	public DraggableCard getCard(int i) {
		return visibleCards.get(i);
	}

	public Card playCard(int i) {
		return hand.remove(i);
	}

	public String getName() {
		return name;
	}

	public CardStack getBattle() {
		return battle;
	}

	public CardStack getDistance() {
		return distance;
	}

	public CardStack getSafety() {
		return safety;
	}

	public void adjustPointsToWin(int i) {
		pointsToWin = pointsToWin - i;
	}

	public int getCurrentPoints() {
		int points = 0;
		for (Card c : distance.getStack()) {
			points += ((DistanceCard) c).getValue();
		}
		return points;
	}

	public int getNeededDistance() {
		return pointsToWin - this.getCurrentPoints();
	}

	public boolean hasWon(HumanPlayer other) {
		if(other.getCurrentPoints() == 0 && this.getCurrentPoints()>900){
			return true;
		}
		if (getCurrentPoints() == pointsToWin) {
			return true;
		}
		return false;
	}

	public boolean canMoveNormally() {

		ArrayList<Character> remChars = new ArrayList<Character>();
		ArrayList<Character> hazChars = new ArrayList<Character>();
		ArrayList<Character> safChars = new ArrayList<Character>();
		for (Card c : battle.getStack()) {
			if (c instanceof RemedyCard) {
				remChars.add(((RemedyCard) c).getType());
			} else if (c instanceof HazardCard) {
				hazChars.add(((HazardCard) c).getType());
			}
		}
		for (Card c : safety.getStack()) {
			safChars.add(((SafetyCard) c).getType());
		}
		for(int i = 0; i < safChars.size(); i++){
			for(int j = 0; j < hazChars.size(); j++){	
				if (safChars.get(i).charValue() == hazChars.get(j).charValue()) {
					hazChars.remove(j);
					j--;
				}
			}
		}
		for(int i = 0; i < remChars.size(); i++){
			for(int j = 0; j < hazChars.size(); j++){	
				if (remChars.get(i).charValue() == hazChars.get(j).charValue()) {
					hazChars.remove(j);
					remChars.remove(i);
					i--;
					j--;
					break;
				}
			}
		}
		
		if (hazChars.size() > 0) {
			for(int i = 0; i < remChars.size(); i++){
				for(int j = 0; j < hazChars.size(); j++){	
					if (remChars.get(i).charValue() == '*') {
						hazChars.remove(j);
						remChars.remove(i);
						i--;
						j--;
					}
				}
			}
		}
		
		if (hazChars.size() > 0) {
			return false;
		}
		return true;

	}

	public boolean canMove() {
		ArrayList<Character> remChars = new ArrayList<Character>();
		ArrayList<Character> hazChars = new ArrayList<Character>();
		ArrayList<Character> safChars = new ArrayList<Character>();
		for (Card c : battle.getStack()) {
			if (c instanceof RemedyCard) {
				remChars.add(((RemedyCard) c).getType());
			} else if (c instanceof HazardCard) {
				hazChars.add(((HazardCard) c).getType());
			}
		}
		for (Card c : safety.getStack()) {
			safChars.add(((SafetyCard) c).getType());
		}


		
		for(int i = 0; i < safChars.size(); i++){
			for(int j = 0; j < hazChars.size(); j++){	
				if (safChars.get(i).charValue() == hazChars.get(j).charValue()) {
					hazChars.remove(j);
					j--;
				}
			}
		}
		for(int i = 0; i < remChars.size(); i++){
			for(int j = 0; j < hazChars.size(); j++){	
				if (remChars.get(i).charValue() == hazChars.get(j).charValue()) {
					hazChars.remove(j);
					remChars.remove(i);
					i--;
					j--;
					break;
				}
			}
		}

		if (hazChars.size() > 0) {
			for(int i = 0; i < remChars.size(); i++){
				for(int j = 0; j < hazChars.size(); j++){	
					if (remChars.get(i).charValue() == '*') {
						hazChars.remove(j);
						remChars.remove(i);
						i--;
						j--;
					}
				}
			}
		}
		
		if (hazChars.size() > 1) {
			return false;
		}
		if (hazChars.size() == 1 && !hazChars.get(0).equals('l')) {
			return false;
		}
		return true;
	}

	public ArrayList<DraggableCard> getHand() {
		return visibleCards;
	}
	public int getUsed200s(){
		return used200s;
	}
	public void add200(){
		used200s++;
	}

}
