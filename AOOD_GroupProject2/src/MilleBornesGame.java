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
		discard = new CardStack("Discard", "", Color.BLUE);
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
		

		gb.add(player.getDistance(), 150, 270, 120, 173);
		gb.add(player.getSafety(), 290, 270, 120, 173);
		gb.add(player.getBattle(), 430, 270, 120, 173);
		gb.add(cpu.getDistance(), 850, 270, 120, 173);
		gb.add(cpu.getSafety(), 710, 270, 120, 173);
		gb.add(cpu.getBattle(), 570, 270, 120, 173);
		gb.add(deck, 10, 270, 120, 173);
		
		discard.setColor(Color.BLUE);
		gb.add(discard, 990, 270, 120, 173);

		
		for(DraggableCard dc: deck.getVisibleStack()){
			gb.add(dc, dc.getWantedX(), dc.getWantedY(), 100, 153);
		}
		
		for (int i = 0; i < 6; i++) {
			DraggableCard dc1 = deck.removeCard();
			DraggableCard dc2 = deck.removeCard();
			dc1.setOwner(player.getName());
			dc2.setOwner(cpu.getName());
			player.addCardToHand(dc1);
			cpu.addCardToHand(dc2);
			
		}
		System.out.println(player.handToString() + "\n");
		System.out.println(cpu.handToString());
		
		for (int i = 0; i < 6; i++) {
			DraggableCard dc = player.getCard(i);
			DraggableCard dc2 = cpu.getCard(i);
			gb.add(dc, (i * 120) + 10, 475, 100, 153);
			gb.add(dc2, (i * 120) + 10, 10, 100, 153);
			dc.flipCard();
		}
		
		gb.pack();
		gb.resetSize();

	}
	public void gameWon(String winner){
		JOptionPane.showMessageDialog(null, "The winner is " + winner + "!", "Winner" , JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);

	}
	
	public void drawCardForPlayer(HumanPlayer p){
		DraggableCard dc = deck.removeCard();
		p.addCardToHand(dc);
		dc.updateWanted(5*120,  475);
		gb.add(dc, dc.getWantedX(), dc.getWantedY(), 100, 153);
		dc.setOwner(p.getName());
		if(dc.getOwner().equals(player.getName())){
			dc.flipCard();
		}
		gb.pack();
		gb.resetSize();
	}
	
	
	public CardStack getDiscard(){
		return discard;
	}

}
