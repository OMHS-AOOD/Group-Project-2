import java.awt.Color;
import java.util.ArrayList;

public class Deck extends CardStack {
	public Deck() {
		super("Deck", "None", Color.BLACK);
		takesCard = false;
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.add(new SafetyCard("Driving Ace"));
		allCards.add(new SafetyCard("Extra Tank"));
		allCards.add(new SafetyCard("Puncture Proof"));
		allCards.add(new SafetyCard("Right of Way"));

		for (int i = 0; i < 5; i++) {
			allCards.add(new HazardCard("Stop",'s'));
		}
		for (int i = 0; i < 2; i++) {
			allCards.add(new HazardCard("Speed Limit",'l'));
			allCards.add(new HazardCard("Speed Limit",'l'));

			allCards.add(new RemedyCard("Roll",'s'));
			allCards.add(new RemedyCard("Roll",'s'));
			allCards.add(new RemedyCard("Roll",'s'));
			allCards.add(new RemedyCard("Roll",'s'));
			allCards.add(new RemedyCard("Roll",'s'));
			allCards.add(new RemedyCard("Roll",'s'));
			allCards.add(new RemedyCard("Roll",'s'));

			allCards.add(new DistanceCard("25 Miles", 25));
			allCards.add(new DistanceCard("25 Miles", 25));
			allCards.add(new DistanceCard("25 Miles", 25));
			allCards.add(new DistanceCard("25 Miles", 25));
			allCards.add(new DistanceCard("25 Miles", 25));

			allCards.add(new DistanceCard("50 Miles", 50));
			allCards.add(new DistanceCard("50 Miles", 50));
			allCards.add(new DistanceCard("50 Miles", 50));
			allCards.add(new DistanceCard("50 Miles", 50));
			allCards.add(new DistanceCard("50 Miles", 50));

			allCards.add(new DistanceCard("75 Miles", 75));
			allCards.add(new DistanceCard("75 Miles", 75));
			allCards.add(new DistanceCard("75 Miles", 75));
			allCards.add(new DistanceCard("75 Miles", 75));
			allCards.add(new DistanceCard("75 Miles", 75));

			allCards.add(new DistanceCard("200 Miles", 200));
			allCards.add(new DistanceCard("200 Miles", 200));

		}

		for (int i = 0; i < 3; i++) {
			allCards.add(new RemedyCard("Road Service",'*'));
			
			allCards.add(new HazardCard("Accident",'a'));
			allCards.add(new HazardCard("Out of Gas",'o'));
			allCards.add(new HazardCard("Flat Tire",'f'));

			allCards.add(new RemedyCard("Repairs",'a'));
			allCards.add(new RemedyCard("Repairs",'a'));
			allCards.add(new RemedyCard("Gasoline",'o'));
			allCards.add(new RemedyCard("Gasoline",'o'));
			allCards.add(new RemedyCard("Spare Tire",'f'));
			allCards.add(new RemedyCard("Spare Tire",'f'));
			allCards.add(new RemedyCard("End of Limit",'l'));
			allCards.add(new RemedyCard("End of Limit",'l'));

			allCards.add(new DistanceCard("100 Miles", 100));
			allCards.add(new DistanceCard("100 Miles", 100));
			allCards.add(new DistanceCard("100 Miles", 100));
			allCards.add(new DistanceCard("100 Miles", 100));

		}
		while(allCards.size() > 0){
			int index = (int)(Math.random()*allCards.size());
			stack.add(allCards.remove(index));
		}
	}
	
	public Card drawTopCard(){
		return super.removeCard();
	}
	
	
	public void reformDeck(CardStack discard){
		while(discard.getStack().size() > 0){
			int index = (int)(Math.random()*discard.getStack().size());
			stack.add(discard.getStack().remove(index));
		}
	}
}
