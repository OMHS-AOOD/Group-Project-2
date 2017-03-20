import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MilleBornesGame {
	private Deck deck;
	private CardStack discard;
	private HumanPlayer player;
	private ComputerPlayer cpu;
	private GameBoard gb;

	public MilleBornesGame() {
		deck = new Deck();
		discard = new CardStack("Discard", "None", Color.BLACK);
		String name = JOptionPane.showInputDialog("Enter a username(10 characters or less)");
		if (name == null) {
			player = new HumanPlayer("Player");
		} else if(name.length() < 10) {
			player = new HumanPlayer(name);
		}
		else{
			player = new HumanPlayer("Player");
		}
		cpu = new ComputerPlayer();
		gb = new GameBoard(this, player, cpu);
		for (int i = 0; i < 6; i++) {
			player.addCardToHand(deck.removeCard());
			cpu.addCardToHand(deck.removeCard());
		}
		System.out.println(player.handToString() + "\n");
		System.out.println(cpu.handToString());

		gb.add(player.getDistance(), 150, 270, 120, 173);
		gb.add(player.getSafety(), 290, 270, 120, 173);
		gb.add(player.getBattle(), 430, 270, 120, 173);
		gb.add(cpu.getDistance(), 850, 270, 120, 173);
		gb.add(cpu.getSafety(), 710, 270, 120, 173);
		gb.add(cpu.getBattle(), 570, 270, 120, 173);

		for (int i = 0; i < 6; i++) {
			DraggableCard dc = new DraggableCard(player.getCard(i), player.getName(), (i * 120) + 10, 475);
			DraggableCard dc2 = new DraggableCard(cpu.getCard(i), cpu.getName(), (i * 120) + 10, 10);
			gb.add(dc, dc.getWantedX(), dc.getWantedY(), 100, 153);
			gb.add(dc2, dc2.getWantedX(), dc2.getWantedY(), 100, 153);
			dc2.flipCard();
		}

	}
	public void gameWon(){
		
	}
	
	public void drawCardForPlayer(HumanPlayer p){
		Card c = deck.removeCard();
		p.addCardToHand(c);
		DraggableCard dc = new DraggableCard(c, p.getName(), (5 * 120) + 10, 475);
		gb.add(dc, dc.getWantedX(), dc.getWantedY(), 100, 153);
		gb.pack();
		gb.resetSize();
	}
	
	
	

}
