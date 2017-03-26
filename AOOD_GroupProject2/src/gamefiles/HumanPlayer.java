package gamefiles;

import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;

public class HumanPlayer implements Serializable {
	protected String name;
	protected CardStack battle, distance, safety, limit;
	protected ArrayList<Card> hand;
	protected ArrayList<DraggableCard> visibleCards;
	protected int maxPointsToWin;
	protected int used200s;
	protected ArrayList<Integer> validWinningConditions;

	public HumanPlayer(String n) {
		name = n;
		battle = new CardStack("Battle", name, Color.GREEN);
		distance = new CardStack("Distance", name, Color.GREEN);
		safety = new CardStack("Safety", name, Color.GREEN);
		limit = new CardStack("Limit", name, Color.green);
		hand = new ArrayList<Card>();
		visibleCards = new ArrayList<DraggableCard>();
		maxPointsToWin = 1000;
		used200s = 0;
		validWinningConditions = new ArrayList<Integer>();
		validWinningConditions.add(1000);

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

	public int getCurrentPoints() {
		int points = 0;
		for (Card c : distance.getStack()) {
			points += ((DistanceCard) c).getValue();
		}
		return points;
	}

	public int getNeededDistance() {
		return maxPointsToWin - this.getCurrentPoints();
	}

	public boolean hasWon(HumanPlayer other) {
		if (other.getCurrentPoints() == 0 && this.getCurrentPoints() > 900) {
			return true;
		}

		for (Integer i : validWinningConditions) {
			if (getCurrentPoints() == i.intValue()) {
				return true;
			}
		}
		return false;
	}

	public boolean canMoveNormally() {
		int numOfEOLCards = 0;
		int numOfLCards = 0;
		for (Card c : safety.getStack()) {
			if (((SafetyCard) c).getType() == 's') {
				return true && canMove();
			}
		}
		for (DraggableCard dCard : limit.getVisibleStack()) {
			if (dCard.getCard() instanceof LimitCard) {
				numOfLCards++;
			}
			if (dCard.getCard() instanceof EoLimitCard) {
				numOfEOLCards++;
			}
		}

		if (numOfLCards > numOfEOLCards) {
			return false;
		}
		return true && canMove();

	}

	public boolean canMove() {

		int numOfRolls = 0;
		int numOfRepairs = 0;
		int numOfGasoline = 0;
		int numOfSpares = 0;
		int numOfService = 0;

		int numOfStops = 0;
		int numOfAccidents = 0;
		int numOfOuts = 0;
		int numOfFlats = 0;

		int stopsResolved = 0;
		int accidentsResolved = 0;
		int outsResolved = 0;
		int flatsResolved = 0;

		for (Card c : battle.getStack()) {
			if (c instanceof RemedyCard) {
				if (((RemedyCard) c).getType() == 's') {
					numOfRolls++;
				}
				if (((RemedyCard) c).getType() == 'a') {
					numOfRepairs++;
				}
				if (((RemedyCard) c).getType() == 'o') {
					numOfGasoline++;
				}
				if (((RemedyCard) c).getType() == 'f') {
					numOfSpares++;
				}
				if (((RemedyCard) c).getType() == '*') {
					numOfService++;
				}

			} else if (c instanceof HazardCard) {
				if (((HazardCard) c).getType() == 's') {
					numOfStops++;
				}
				if (((HazardCard) c).getType() == 'a') {
					numOfAccidents++;
				}
				if (((HazardCard) c).getType() == 'o') {
					numOfOuts++;
				}
				if (((HazardCard) c).getType() == 'f') {
					numOfFlats++;
				}

			}
		}
		for (Card c : safety.getStack()) {
			if (((SafetyCard) c).getType() == 's') {
				numOfStops = 0;
			}
			if (((SafetyCard) c).getType() == 'a') {
				numOfAccidents = 0;
			}
			if (((SafetyCard) c).getType() == 'o') {
				numOfOuts = 0;
			}
			if (((SafetyCard) c).getType() == 'f') {
				numOfFlats = 0;
			}
		}
		if (numOfRolls == 0) {
			return false;
		}
		int totalHazards = numOfStops + numOfAccidents + numOfOuts + numOfFlats;

		stopsResolved = numOfStops - numOfRolls;
		accidentsResolved = numOfAccidents - numOfRepairs;
		outsResolved = numOfOuts - numOfGasoline;
		flatsResolved = numOfFlats - numOfSpares;

		int totalRemainingHazards = stopsResolved + accidentsResolved + outsResolved + flatsResolved - numOfService;

		if (totalRemainingHazards > 0) {
			return false;
		}
		if(name.equals("CPU")){
			if (totalHazards > numOfRolls) {
				return false;
			}
		}
		else{
			if (totalHazards+1 > numOfRolls) {
				return false;
			}
		}
		

		return true;
	}

	public boolean needsRoll() {
		int numOfRolls = 0;
		int numOfRepairs = 0;
		int numOfGasoline = 0;
		int numOfSpares = 0;
		int numOfService = 0;

		int numOfStops = 0;
		int numOfAccidents = 0;
		int numOfOuts = 0;
		int numOfFlats = 0;

		int stopsResolved = 0;
		int accidentsResolved = 0;
		int outsResolved = 0;
		int flatsResolved = 0;

		for (Card c : battle.getStack()) {
			if (c instanceof RemedyCard) {
				if (((RemedyCard) c).getType() == 's') {
					numOfRolls++;
				}
				if (((RemedyCard) c).getType() == 'a') {
					numOfRepairs++;
				}
				if (((RemedyCard) c).getType() == 'o') {
					numOfGasoline++;
				}
				if (((RemedyCard) c).getType() == 'f') {
					numOfSpares++;
				}
				if (((RemedyCard) c).getType() == '*') {
					numOfService++;
				}

			} else if (c instanceof HazardCard) {
				if (((HazardCard) c).getType() == 's') {
					numOfStops++;
				}
				if (((HazardCard) c).getType() == 'a') {
					numOfAccidents++;
				}
				if (((HazardCard) c).getType() == 'o') {
					numOfOuts++;
				}
				if (((HazardCard) c).getType() == 'f') {
					numOfFlats++;
				}

			}
		}
		for (Card c : safety.getStack()) {
			if (((SafetyCard) c).getType() == 's') {
				numOfStops = 0;
			}
			if (((SafetyCard) c).getType() == 'a') {
				numOfAccidents = 0;
			}
			if (((SafetyCard) c).getType() == 'o') {
				numOfOuts = 0;
			}
			if (((SafetyCard) c).getType() == 'f') {
				numOfFlats = 0;
			}
		}
		

		int totalHazards = numOfStops + numOfAccidents + numOfOuts + numOfFlats;
		stopsResolved = numOfStops - numOfRolls;
		accidentsResolved = numOfAccidents - numOfRepairs;
		outsResolved = numOfOuts - numOfGasoline;
		flatsResolved = numOfFlats - numOfSpares;

		int totalRemainingHazards = stopsResolved + accidentsResolved + outsResolved + flatsResolved - numOfService;

		if(totalRemainingHazards == stopsResolved){
			return true;
		}
		if (totalRemainingHazards > 0) {
			return false;
		}
		if(numOfRolls == 0){
			return true;
		}


		if(name.equals("CPU")){
			if (totalHazards > numOfRolls) {
				return false;
			}
		}
		else{
			if (totalHazards+1 > numOfRolls) {
				return false;
			}
		}
		return false;
	}

	public ArrayList<DraggableCard> getHand() {
		return visibleCards;
	}

	public int getUsed200s() {
		return used200s;
	}

	public void add200() {
		used200s++;
	}

	public boolean willTakeBattleCard() {
		return canMove();
	}

	public boolean willTakeRemedyCard(DraggableCard dc) {
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

		for (int i = 0; i < safChars.size(); i++) {
			for (int j = 0; j < hazChars.size(); j++) {
				if (safChars.get(i).charValue() == hazChars.get(j).charValue()) {
					hazChars.remove(j);
					j--;
					break;
				}
			}
		}
		for (int i = 0; i < remChars.size(); i++) {
			for (int j = 0; j < hazChars.size(); j++) {
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
			for (int i = 0; i < remChars.size(); i++) {
				for (int j = 0; j < hazChars.size(); j++) {
					if (remChars.get(i).charValue() == '*') {
						hazChars.remove(j);
						remChars.remove(i);
						i--;
						j--;
						break;
					}
				}
			}
		}

		if (hazChars.size() == 0) {
			return false;
		}
		if (((RemedyCard) dc.getCard()).getType() == '*') {
			return true;
		}

		for (Character c : hazChars) {
			if (c.charValue() == ((RemedyCard) dc.getCard()).getType()) {
				return true;
			}
		}

		return false;

	}

	public ArrayList<String> getCurrentHazards() {
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

		for (int i = 0; i < safChars.size(); i++) {
			for (int j = 0; j < hazChars.size(); j++) {
				if (safChars.get(i).charValue() == hazChars.get(j).charValue()) {
					hazChars.remove(j);
					j--;
				}
			}
		}
		for (int i = 0; i < remChars.size(); i++) {
			for (int j = 0; j < hazChars.size(); j++) {
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
			for (int i = 0; i < remChars.size(); i++) {
				for (int j = 0; j < hazChars.size(); j++) {
					if (remChars.get(i).charValue() == '*') {
						hazChars.remove(j);
						remChars.remove(i);
						i--;
						j--;
						break;
					}
				}
			}
		}

		ArrayList<String> output = new ArrayList<String>();
		for (Character h : hazChars) {
			if (h == 's') {
				output.add("Stop");
			}
			if (h == 'a') {
				output.add("Accident");
			}
			if (h == 'o') {
				output.add("Out of Gas");
			}
			if (h == 'f') {
				output.add("Flat Tire");
			}
		}

		if (willTakeEoLimitCard()) {
			output.add("Speed Limit");
		}

		return output;
	}

	public boolean handContainsSafetyCard() {
		for (Card c : hand) {
			if (c instanceof SafetyCard) {
				return true;
			}
		}
		return false;
	}

	public void coupFourre() {
		validWinningConditions.add(validWinningConditions.get(validWinningConditions.size() - 1) - 25);
	}

	public CardStack getLimit() {
		return limit;
	}

	public boolean willTakeLimitCard() {
		int numOfEOLCards = 0;
		int numOfLCards = 0;
		for (DraggableCard dCard : limit.getVisibleStack()) {
			if (dCard.getCard() instanceof LimitCard) {
				numOfLCards++;
			}
			if (dCard.getCard() instanceof EoLimitCard) {
				numOfEOLCards++;
			}
		}

		if (numOfLCards > numOfEOLCards) {
			return false;
		}
		return true;
	}

	public boolean willTakeEoLimitCard() {
		int numOfEOLCards = 0;
		int numOfLCards = 0;
		for (Card c : safety.getStack()) {
			if (((SafetyCard) c).getType() == 's') {
				return false;
			}
		}
		for (DraggableCard dCard : limit.getVisibleStack()) {
			if (dCard.getCard() instanceof LimitCard) {
				numOfLCards++;
			}
			if (dCard.getCard() instanceof EoLimitCard) {
				numOfEOLCards++;
			}
		}

		if (numOfLCards > numOfEOLCards) {
			return true;
		}
		return false;
	}

	public int getNumOfRolls() {
		int numOfRolls = 0;

		for (Card c : battle.getStack()) {
			if (c instanceof RollCard) {

				numOfRolls++;

			}

		}
		return numOfRolls;
	}

}
