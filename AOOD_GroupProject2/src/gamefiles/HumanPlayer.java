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

	public HumanPlayer(String n, ArrayList<Card> b,  ArrayList<Card> d, ArrayList<Card> s, ArrayList<Card> l, ArrayList<Card> hList, int u2, ArrayList<Integer> iList) {
		name = n;
		battle = new CardStack(b, "Battle", name, Color.green, true);
		distance = new CardStack(d, "Distance", name, Color.green, true);
		safety = new CardStack(s, "Safety", name, Color.green, true);
		limit = new CardStack(l, "Limit", name, Color.green, true);
		hand = new ArrayList<Card>();
		
		for(Card c: hList){
			hand.add(c);
		}
		visibleCards = new ArrayList<DraggableCard>();
		int ind = 0;
		for(Card c: hand){
			visibleCards.add(new DraggableCard(c, name, (ind*120)+10, 475));
			ind++;
		}



		maxPointsToWin = 1000;
		used200s = u2;
		validWinningConditions = iList;
	}

	private ArrayList<Integer> getValidWins() {
		return validWinningConditions;
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
		if(needsRoll()){
			return false;
		}
		if(this.getCurrentHazards().size() == 1 && this.getCurrentHazards().get(0).equals("Speed Limit")){
			return true;
		}
		if(this.getCurrentHazards().size() == 0){
			return true;
		}
		return false;
		
	}

	public boolean needsRoll() {
		for(Card s: safety.getStack()){
			if(((SafetyCard) s).getType() == 's'){
				return false;
			}
		}
		if (battle.getCurrentSize() == 0) {

			return true;
		}
		

		if(safety.getCurrentSize() != 0){
			Card c1 = battle.getStack().get(battle.getCurrentSize()-1);
			Card c2 = safety.getStack().get(safety.getCurrentSize()-1);
			if(c1 instanceof HazardCard && c2 instanceof SafetyCard){
				if(((HazardCard) c1).getType() == ((SafetyCard) c2).getType()){
					return true;
				}
			}
		}
		if((battle.getLast().getCard() instanceof RollCard)){
			return false;
		}
		if((battle.getStack().get(battle.getCurrentSize()-1) instanceof HazardCard)){
			HazardCard tempCard = (HazardCard) battle.getStack().get(battle.getCurrentSize()-1);
			if(tempCard.getType() == 's'){

				return true;
			}
			return false;
		}
		if((battle.getStack().get(battle.getCurrentSize()-1) instanceof RemedyCard)){

			return true;
		}
		return false;
	}

	public ArrayList<DraggableCard> getHand() {
		return visibleCards;
	}
	public ArrayList<Card> getCards() {
		return this.hand;
	}

	public int getUsed200s() {
		return used200s;
	}

	public void add200() {
		used200s++;
	}
	public boolean isImmuneTo(Card c){
		for (Card c2 : safety.getStack()) {
			if (((SafetyCard) c2).getType() == ((HazardCard) c).getType()) {
				return true;
			}
		}
		return false;
	}
	public boolean willTakeBattleCard(Card c) {
		if (battle.getCurrentSize() == 0) {
			return false;
		}
		for (Card c2 : safety.getStack()) {
			if (((SafetyCard) c2).getType() == ((HazardCard) c).getType()) {
				return false;
			}
		}
		if((battle.getStack().get(battle.getCurrentSize()-1) instanceof RemedyCard)){
			return true;
		}
		if((battle.getStack().get(battle.getCurrentSize()-1) instanceof HazardCard)){
			return true;
		}
		return false;
		
	}



	public boolean willTakeRemedyCard(DraggableCard dc) {
		RemedyCard rc = (RemedyCard) dc.getCard();
		ArrayList<String> hazards = this.getCurrentHazards();
		if (needsRoll() && !(rc instanceof RollCard)) {
			return false;
		}
		if(needsRoll() && (rc instanceof RollCard)){
			return true;
		}
		if(rc.getType() == '*' && hazards.size() == 1 && hazards.get(0).equals("Speed Limit")){
			return false;
		}
		if(rc.getType() == '*' && hazards.size() > 0){
			return true;
		}
		if (rc.getType() == 's' && hazards.contains("Stop")) {
			return true;
		}
		if (rc.getType() == 'a' && hazards.contains("Accident")) {
			return true;
		}
		if (rc.getType() == 'o' && hazards.contains("Out of Gas")) {
			return true;
		}
		if (rc.getType() == 'f' && hazards.contains("Flat Tire")) {
			return true;
		}
		return false;
	}

	public ArrayList<String> getCurrentHazards() {
		
		ArrayList<String> output = new ArrayList<String>();
		if(battle.getCurrentSize() > 0 && battle.getLast().getCard() instanceof HazardCard){
			HazardCard temp = (HazardCard) battle.getLast().getCard();
			if (temp.getType() == 's') {
				output.add("Stop");
			}
			if (temp.getType() == 'a') {
				output.add("Accident");
			}
			if (temp.getType() == 'o') {
				output.add("Out of Gas");
			}
			if (temp.getType() == 'f') {
				output.add("Flat Tire");
			}
		}
		if (willTakeEoLimitCard()) {
			output.add("Speed Limit");
		}
		if(this.needsRoll()){
			output.add("Needs Roll Card");
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

	public ArrayList<Integer> getWinCons() {
		return this.validWinningConditions;
	}
	

	

}
