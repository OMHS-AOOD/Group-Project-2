import java.util.ArrayList;

public class Deck extends CardStack {
	public Deck(){
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.add(new SafetyCard("Driving Ace"));
		allCards.add(new SafetyCard("Extra Tank"));
		allCards.add(new SafetyCard("Puncture Proof"));
		allCards.add(new SafetyCard("Right of Way"));
		for(int i = 0; i < 2; i++){
			allCards.add(new HazardCard("Speed Limit"));
			allCards.add(new HazardCard("Speed Limit"));
			
			allCards.add(new RemedyCard("Go/Roll"));
			allCards.add(new RemedyCard("Go/Roll"));
			allCards.add(new RemedyCard("Go/Roll"));
			allCards.add(new RemedyCard("Go/Roll"));
			allCards.add(new RemedyCard("Go/Roll"));
			allCards.add(new RemedyCard("Go/Roll"));
			allCards.add(new RemedyCard("Go/Roll"));
			
			allCards.add(new DistanceCard("25 Miles"));
			allCards.add(new DistanceCard("25 Miles"));
			allCards.add(new DistanceCard("25 Miles"));
			allCards.add(new DistanceCard("25 Miles"));
			allCards.add(new DistanceCard("25 Miles"));
			
			allCards.add(new DistanceCard("50 Miles"));
			allCards.add(new DistanceCard("50 Miles"));
			allCards.add(new DistanceCard("50 Miles"));
			allCards.add(new DistanceCard("50 Miles"));
			allCards.add(new DistanceCard("50 Miles"));
			
			allCards.add(new DistanceCard("75 Miles"));
			allCards.add(new DistanceCard("75 Miles"));
			allCards.add(new DistanceCard("75 Miles"));
			allCards.add(new DistanceCard("75 Miles"));
			allCards.add(new DistanceCard("75 Miles"));

			allCards.add(new DistanceCard("200 Miles"));
			allCards.add(new DistanceCard("200 Miles"));

		}
		
		for(int i = 0; i < 3; i++){
			allCards.add(new HazardCard("Accident"));
			allCards.add(new HazardCard("Out of Gas"));
			allCards.add(new HazardCard("Flat Tire"));
			
			allCards.add(new RemedyCard("Repairs"));
			allCards.add(new RemedyCard("Repairs"));
			allCards.add(new RemedyCard("Gasoline"));
			allCards.add(new RemedyCard("Gasoline"));
			allCards.add(new RemedyCard("Spare Tire"));
			allCards.add(new RemedyCard("Spare Tire"));
			allCards.add(new RemedyCard("End of Limit"));
			allCards.add(new RemedyCard("End of Limit"));
			
			allCards.add(new DistanceCard("100 Miles"));
			allCards.add(new DistanceCard("100 Miles"));
			allCards.add(new DistanceCard("100 Miles"));
			allCards.add(new DistanceCard("100 Miles"));

		}
		
	}
}
