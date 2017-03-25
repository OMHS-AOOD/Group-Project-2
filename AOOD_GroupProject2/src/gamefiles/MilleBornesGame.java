package gamefiles;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MilleBornesGame implements Serializable {
	private Deck deck;
	private CardStack discard;
	private HumanPlayer player;
	private ComputerPlayer cpu;
	private GameBoard gb;
	private MilleBornesContainer mbc;

	public MilleBornesGame(MilleBornesContainer m) {
		Font myFont = new Font("OCR A Std", Font.PLAIN, 12);
		UIManager.put("OptionPane.background", Color.black);
		UIManager.put("OptionPane.messageForeground", Color.green);
		UIManager.put("OptionPane.messageDialogTitle", Color.black);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("OptionPane.messageFont", myFont);
		UIManager.put("Button.background", Color.black);
		UIManager.put("Button.foreground", Color.GREEN);
		UIManager.put("Button.select", Color.white);
		UIManager.put("Button.font", myFont);
		UIManager.put("Menu.selectionBackground", Color.white);
		UIManager.put("Menu.selectionForeground", Color.green);
		UIManager.put("MenuItem.selectionBackground", Color.white);
		UIManager.put("MenuItem.selectionForeground", Color.green);
		UIManager.put("MenuItem.font", myFont);
		UIManager.put("Menu.font", myFont);
		
		UIManager.put("ComboBox.background", Color.BLACK);
	    UIManager.put("ComboBox.foreground", Color.GREEN);
	    UIManager.put("ComboBox.font", myFont);
	    
	    UIManager.put("TextField.background", Color.BLACK);
	    UIManager.put("TextField.foreground", Color.GREEN);
	    UIManager.put("TextField.font", myFont);
	    UIManager.put("Label.background", Color.BLACK);
	    UIManager.put("Label.foreground", Color.GREEN);
	    UIManager.put("Label.font", myFont);
	    
	    UIManager.put("Viewport.background", Color.BLACK);
	    UIManager.put("Viewport.foreground", Color.GREEN);


		mbc = m;
		
		deck = new Deck();
		discard = new CardStack("Discard", "", Color.BLUE);
		String name = JOptionPane.showInputDialog("Enter a username(10 characters or less)");
		if (name == null || name.equals("")) {
			player = new HumanPlayer("Player");
		} else if(name.length() <= 10) {
			player = new HumanPlayer(name.trim());
		}
		else{
			player = new HumanPlayer("Player");
		}
		cpu = new ComputerPlayer();
		gb = new GameBoard(this, player, cpu);
		

		gb.add(player.getDistance(), 150, 225, 130, 198);
		gb.add(player.getSafety(), 290, 225, 130, 198);
		gb.add(player.getBattle(), 430, 225, 130, 198);
		gb.add(cpu.getDistance(), 850, 225, 130, 198);
		gb.add(cpu.getSafety(), 710, 225, 130, 198);
		gb.add(cpu.getBattle(), 570, 225, 130, 198);
		gb.add(deck, 10, 225, 130, 198);
		
		discard.setColor(Color.BLUE);
		gb.add(discard, 990, 225, 130, 198);

		
		for(DraggableCard dc: deck.getVisibleStack()){
			gb.add(dc, deck.getX()+15, deck.getY()+35, 100, 153);
		}
		
		for (int i = 0; i < 6; i++) {
			DraggableCard dc1 = deck.removeCard();
			DraggableCard dc2 = deck.removeCard();
			dc1.setOwner(player.getName());
			dc2.setOwner(cpu.getName());
			player.addCardToHand(dc1);
			cpu.addCardToHand(dc2);
			
		}
		//System.out.println(player.handToString() + "\n");
		//System.out.println(cpu.handToString());
		
		for (int i = 0; i < 6; i++) {
			DraggableCard dc = player.getCard(i);
			DraggableCard dc2 = cpu.getCard(i);
			gb.placeCard(dc, (i * 120) + 10, 475, 100, 153);
			gb.placeCard(dc2, (i * 120) + 10, 10, 100, 153);
			dc.flipCard();
		}
		
		gb.pack();
		gb.resetSize();

	}
	public void gameWon(String winner){
		if(winner.equals(cpu.getName())){
			UIManager.put("OptionPane.messageForeground", Color.red);
			UIManager.put("Button.foreground", Color.RED);
			UIManager.put("Button.foreground", Color.RED);


		}
		else{
			UIManager.put("OptionPane.messageForeground", Color.green);
			UIManager.put("Button.foreground", Color.green);
			UIManager.put("Button.foreground", Color.GREEN);

		}
		JOptionPane.showMessageDialog(null, "The winner is " + winner + "!", "Winner" , JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);

	}
	
	public void drawCardForPlayer(HumanPlayer p){
		checkIfDeckNeedsReforming();
		DraggableCard dc = deck.removeCard();
		p.addCardToHand(dc);
		dc.updateWanted(6*120,  475);
		dc.setOwner(p.getName());
		if(dc.getOwner().equals(player.getName())){
			dc.setFlip(false);
		}



	}
	private void checkIfDeckNeedsReforming(){
		if(deck.getVisibleStack().size() == 0 && discard.getVisibleStack().size()>0){
			deck.reformDeck(discard);
		}
		else if(deck.getVisibleStack().size() == 0 && discard.getVisibleStack().size()==0){
			gameWon("neither player");
		}
	}
	
	public CardStack getDiscard(){
		return discard;
	}
	public Deck getDeck() {
		return deck;
	}
	
	public void save(){
		mbc.save();
	}
	public void load(){
		mbc.load();
	}

}
