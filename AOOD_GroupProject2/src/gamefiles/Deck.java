package gamefiles;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Deck extends CardStack implements Serializable {
	public Deck() {
		super("Deck", "", Color.BLUE);
		takesCard = false;
		myColor = Color.BLUE;
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.add(new SafetyCard("Driving Ace", 'a', 99));
		allCards.add(new SafetyCard("Extra Tank", 'o', 99));
		allCards.add(new SafetyCard("Puncture Proof", 'f', 99));
		allCards.add(new SafetyCard("Right of Way", 's', 100));

		for (int i = 0; i < 5; i++) {
			allCards.add(new HazardCard("Stop", 's', 50));
		}
		for (int i = 0; i < 2; i++) {
			allCards.add(new LimitCard("Speed Limit", 25));
			allCards.add(new LimitCard("Speed Limit", 25));

			allCards.add(new RollCard("Roll", 's', 40));
			allCards.add(new RollCard("Roll", 's', 40));
			allCards.add(new RollCard("Roll", 's', 40));
			allCards.add(new RollCard("Roll", 's', 40));
			allCards.add(new RollCard("Roll", 's', 40));
			allCards.add(new RollCard("Roll", 's', 40));
			allCards.add(new RollCard("Roll", 's', 40));

			allCards.add(new DistanceCard("25 Miles", 25, 10));
			allCards.add(new DistanceCard("25 Miles", 25, 10));
			allCards.add(new DistanceCard("25 Miles", 25, 10));
			allCards.add(new DistanceCard("25 Miles", 25, 10));
			allCards.add(new DistanceCard("25 Miles", 25, 10));

			allCards.add(new DistanceCard("50 Miles", 50, 20));
			allCards.add(new DistanceCard("50 Miles", 50, 20));
			allCards.add(new DistanceCard("50 Miles", 50, 20));
			allCards.add(new DistanceCard("50 Miles", 50, 20));
			allCards.add(new DistanceCard("50 Miles", 50, 20));

			allCards.add(new DistanceCard("75 Miles", 75, 30));
			allCards.add(new DistanceCard("75 Miles", 75, 30));
			allCards.add(new DistanceCard("75 Miles", 75, 30));
			allCards.add(new DistanceCard("75 Miles", 75, 30));
			allCards.add(new DistanceCard("75 Miles", 75, 30));

			allCards.add(new DistanceCard("200 Miles", 200, 90));
			allCards.add(new DistanceCard("200 Miles", 200, 90));

		}

		for (int i = 0; i < 3; i++) {
			allCards.add(new RemedyCard("Road Service", '*', 70));

			allCards.add(new HazardCard("Accident", 'a', 60));
			allCards.add(new HazardCard("Out of Gas", 'o', 60));
			allCards.add(new HazardCard("Flat Tire", 'f', 60));

			allCards.add(new RemedyCard("Repairs", 'a', 35));
			allCards.add(new RemedyCard("Repairs", 'a', 35));
			allCards.add(new RemedyCard("Gasoline", 'o', 35));
			allCards.add(new RemedyCard("Gasoline", 'o', 35));
			allCards.add(new RemedyCard("Spare Tire", 'f', 35));
			allCards.add(new RemedyCard("Spare Tire", 'f', 35));
			allCards.add(new EoLimitCard("End of Limit", 30));
			allCards.add(new EoLimitCard("End of Limit", 30));

			allCards.add(new DistanceCard("100 Miles", 100, 70));
			allCards.add(new DistanceCard("100 Miles", 100, 70));
			allCards.add(new DistanceCard("100 Miles", 100, 70));
			allCards.add(new DistanceCard("100 Miles", 100, 70));

		}
		while (allCards.size() > 0) {
			int index = (int) (Math.random() * allCards.size());
			stack.add(allCards.remove(index));
		}
		createDraggableCards();
	}

	public Deck(ArrayList<Card> d) {
		super("Deck", "", Color.BLUE);
		takesCard = false;
		myColor = Color.BLUE;
		for (Card c : d) {
			if (c instanceof DistanceCard) {
				stack.add(new DistanceCard(c));
			} else if (c instanceof EoLimitCard) {
				stack.add(new EoLimitCard(c));
			} else if (c instanceof HazardCard) {
				stack.add(new HazardCard(c));
			} else if (c instanceof LimitCard) {
				stack.add(new LimitCard(c));
			} else if (c instanceof RollCard) {
				stack.add(new RollCard(c));
			} else if (c instanceof RemedyCard) {
				stack.add(new RemedyCard(c));

			} else if (c instanceof SafetyCard) {
				stack.add(new SafetyCard(c));
			}
		}
		createDraggableCards();
	}

	public void reformDeck(CardStack discard) {
		while (discard.getVisibleStack().size() > 0) {
			int index = (int) (Math.random() * (discard.getVisibleStack().size() - 1));
			stack.add(discard.getStack().remove(index));
			visibleStack.add(discard.getVisibleStack().remove(index));
		}
		for (DraggableCard dc : visibleStack) {
			dc.setBounds(this.getX() + 15, this.getY() + 35, dc.getWidth(), dc.getHeight());
			dc.setOwner("");
			dc.mark(false);
			dc.setFlip(true);
		}
	}

}
