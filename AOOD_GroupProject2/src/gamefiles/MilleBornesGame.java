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
		Font myFont = new Font("OCR A Extended", Font.PLAIN, 12);
		UIManager.put("OptionPane.background", Color.black);
		UIManager.put("OptionPane.messageForeground", Color.green);
		UIManager.put("OptionPane.messageDialogTitle", Color.black);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("OptionPane.messageFont", myFont);
		UIManager.put("Button.background", Color.black);
		UIManager.put("Button.foreground", Color.GREEN);
		UIManager.put("Button.select", Color.white);
		UIManager.put("Button.font", myFont);
		UIManager.put("Menu.selectionBackground", Color.GREEN);
		UIManager.put("Menu.selectionForeground", Color.BLACK);
		UIManager.put("MenuItem.selectionBackground", Color.GREEN);
		UIManager.put("MenuItem.selectionForeground", Color.BLACK);
		UIManager.put("MenuItem.font", myFont);
		UIManager.put("Menu.font", myFont);

		UIManager.put("ComboBox.background", Color.BLACK);
		UIManager.put("ComboBox.foreground", Color.GREEN);
		UIManager.put("ComboBox.font", myFont);

		UIManager.put("ComboBox.selectionBackground", Color.GREEN);
		UIManager.put("ComboBox.selectionForeground", Color.BLACK);
		
		UIManager.put("TextField.background", Color.BLACK);
		UIManager.put("TextField.foreground", Color.GREEN);
		UIManager.put("TextField.font", myFont);
		UIManager.put("Label.background", Color.BLACK);
		UIManager.put("Label.foreground", Color.GREEN);
		UIManager.put("Label.font", myFont);

		UIManager.put("Viewport.background", Color.BLACK);
		UIManager.put("Viewport.foreground", Color.GREEN);

		UIManager.put("TextField.selectionBackground", Color.GREEN);
		UIManager.put("TextField.selectionForeground", Color.BLACK);
		
		UIManager.put("ToolTip.foreground", Color.GREEN);
		UIManager.put("ToolTip.background", Color.BLACK);
		
		mbc = m;

		deck = new Deck();
		discard = new CardStack("Discard", "", Color.BLUE);
		String name = JOptionPane.showInputDialog("Enter a username(10 characters or less)");
		if (name == null || name.equals("")) {
			player = new HumanPlayer("Player");
		} else if (name.length() <= 10) {
			player = new HumanPlayer(name.trim());
		} else {
			player = new HumanPlayer("Player");
		}
		cpu = new ComputerPlayer();
		gb = new GameBoard(this, player, cpu);

		gb.add(player.getDistance(), 150, 225, 130, 198);
		gb.add(player.getSafety(), 290, 225, 130, 198);
		gb.add(player.getLimit(), 430, 225, 130, 198);
		gb.add(player.getBattle(), 570, 225, 130, 198);

		gb.add(cpu.getDistance(), 1130, 225, 130, 198);
		gb.add(cpu.getSafety(), 990, 225, 130, 198);
		gb.add(cpu.getLimit(), 850, 225, 130, 198);
		gb.add(cpu.getBattle(), 710, 225, 130, 198);
		gb.add(deck, 10, 225, 130, 198);

		discard.setColor(Color.BLUE);
		gb.add(discard, 1270, 225, 130, 198);

		for (DraggableCard dc : deck.getVisibleStack()) {
			gb.add(dc, deck.getX() + 15, deck.getY() + 35, 100, 153);
		}

		for (int i = 0; i < 6; i++) {
			DraggableCard dc1 = deck.removeCard();
			DraggableCard dc2 = deck.removeCard();
			dc1.setOwner(player.getName());
			dc2.setOwner(cpu.getName());
			player.addCardToHand(dc1);
			cpu.addCardToHand(dc2);

		}

		for (int i = 0; i < 6; i++) {
			DraggableCard dc = player.getCard(i);
			DraggableCard dc2 = cpu.getCard(i);
			gb.placeCard(dc, (i * 120) + 10, 475, 100, 153);
			gb.placeCard(dc2, (i * 120) + 10, 10, 100, 153);
			dc.flipCard();
		}

		gb.pack();
		gb.resetSize();

		int goesFirst = (int) (Math.random() * 2);
		if (goesFirst >= 1) {
			gb.cpuTurn();
		}
		gb.markCards();

	}

	public void gameWon(String winner) {
		if (winner.equals(cpu.getName())) {
			UIManager.put("OptionPane.messageForeground", Color.red);
			UIManager.put("Button.foreground", Color.RED);
			UIManager.put("Button.foreground", Color.RED);

		} else {
			UIManager.put("OptionPane.messageForeground", Color.green);
			UIManager.put("Button.foreground", Color.green);
			UIManager.put("Button.foreground", Color.GREEN);

		}
		JOptionPane.showMessageDialog(null, "The winner is " + winner + "!", "Winner", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);

	}

	public void drawCardForPlayer(HumanPlayer p) {
		checkIfDeckNeedsReforming();
		DraggableCard dc = deck.removeCard();
		p.addCardToHand(dc);
		dc.updateWanted(6 * 120, 475);
		dc.setOwner(p.getName());
		if (dc.getOwner().equals(player.getName())) {
			dc.setFlip(false);
		}
		gb.markCards();

	}

	public void checkIfDeckNeedsReforming() {
		if (deck.getVisibleStack().size() == 0) {
			if(discard.getCurrentSize() > 0){
				deck.reformDeck(discard);
			}
			DraggableCard topOfP1 = player.getBattle().removeLast();
			DraggableCard topOfP2 = player.getLimit().removeLast();
			DraggableCard topOfCP1 = cpu.getBattle().removeLast();
			DraggableCard topOfCP2 = cpu.getLimit().removeLast();
			
			CardStack pBattle = player.getBattle();
			CardStack pLimit = player.getLimit();
			CardStack cBattle = cpu.getBattle();
			CardStack cLimit = cpu.getLimit();
			
			
			if(pBattle.getCurrentSize()>0){
				deck.reformDeck(pBattle);
				player.getBattle().addCard(topOfP1);
				topOfP1.setBounds(pBattle.getX()+15, pBattle.getY() + 35, 100, 153);
			}
			if(pLimit.getCurrentSize()>0){
				deck.reformDeck(pLimit);
				player.getLimit().addCard(topOfP2);
				topOfP2.setBounds(pLimit.getX()+15, pLimit.getY() + 35, 100, 153);

			}
			if(cBattle.getCurrentSize()>0){
				deck.reformDeck(cBattle);
				cpu.getBattle().addCard(topOfCP1);
				topOfCP1.setBounds(cBattle.getX()+15, cBattle.getY() + 35, 100, 153);

			}
			if(cLimit.getCurrentSize()>0){
				deck.reformDeck(cLimit);
				cpu.getLimit().addCard(topOfCP2);
				topOfCP2.setBounds(cLimit.getX()+15, cLimit.getY() + 35, 100, 153);

			}
			




			
		}
	}

	public CardStack getDiscard() {
		return discard;
	}

	public Deck getDeck() {
		return deck;
	}

	public void save() {
		mbc.save();
	}

	public void load() {
		String check = JOptionPane.showInputDialog("Would you like to load(Current game will be lost)(Y/N)?");
		if(check != null && check.equalsIgnoreCase("Y")){
			mbc.load();
		}

	}



	public MilleBornesGame(GameData data, MilleBornesContainer milleBornesContainer) {
		Font myFont = new Font("OCR A Extended", Font.PLAIN, 12);
		UIManager.put("OptionPane.background", Color.black);
		UIManager.put("OptionPane.messageForeground", Color.green);
		UIManager.put("OptionPane.messageDialogTitle", Color.black);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("OptionPane.messageFont", myFont);
		UIManager.put("Button.background", Color.black);
		UIManager.put("Button.foreground", Color.GREEN);
		UIManager.put("Button.select", Color.white);
		UIManager.put("Button.font", myFont);
		UIManager.put("Menu.selectionBackground", Color.GREEN);
		UIManager.put("Menu.selectionForeground", Color.BLACK);
		UIManager.put("MenuItem.selectionBackground", Color.GREEN);
		UIManager.put("MenuItem.selectionForeground", Color.BLACK);
		UIManager.put("MenuItem.font", myFont);
		UIManager.put("Menu.font", myFont);

		UIManager.put("ComboBox.background", Color.BLACK);
		UIManager.put("ComboBox.foreground", Color.GREEN);
		UIManager.put("ComboBox.font", myFont);

		UIManager.put("ComboBox.selectionBackground", Color.GREEN);
		UIManager.put("ComboBox.selectionForeground", Color.BLACK);

		UIManager.put("TextField.background", Color.BLACK);
		UIManager.put("TextField.foreground", Color.GREEN);
		UIManager.put("TextField.font", myFont);
		UIManager.put("Label.background", Color.BLACK);
		UIManager.put("Label.foreground", Color.GREEN);
		UIManager.put("Label.font", myFont);

		UIManager.put("Viewport.background", Color.BLACK);
		UIManager.put("Viewport.foreground", Color.GREEN);

		UIManager.put("TextField.selectionBackground", Color.GREEN);
		UIManager.put("TextField.selectionForeground", Color.BLACK);
		
		UIManager.put("ToolTip.foreground", Color.GREEN);
		UIManager.put("ToolTip.background", Color.BLACK);
		mbc = milleBornesContainer;

		deck = new Deck(data.getDeck());
		discard = new CardStack(data.getDiscard(), "Discard", "", Color.blue, true);
		player = new HumanPlayer(data.getpName(), data.getpBattle(), data.getpDistance(), data.getpSafety(), data.getpLimit(), data.getpHand(), data.getpUsed200s(), data.getpWinCons());
		cpu = new ComputerPlayer(data.getcBattle(), data.getcDistance(), data.getcSafety(), data.getcLimit(), data.getcHand(), data.getcUsed200s(), data.getcWinCons());
		gb = new GameBoard(this, player, cpu);

		gb.add(player.getDistance(), 150, 225, 130, 198);
		gb.add(player.getSafety(), 290, 225, 130, 198);
		gb.add(player.getLimit(), 430, 225, 130, 198);
		gb.add(player.getBattle(), 570, 225, 130, 198);

		gb.add(cpu.getDistance(), 1130, 225, 130, 198);
		gb.add(cpu.getSafety(), 990, 225, 130, 198);
		gb.add(cpu.getLimit(), 850, 225, 130, 198);
		gb.add(cpu.getBattle(), 710, 225, 130, 198);
		gb.add(deck, 10, 225, 130, 198);


		gb.add(discard, 1270, 225, 130, 198);
		gb.resetup(data);
		gb.pack();
		gb.resetSize();
	}

	public GameBoard getBoard() {
		return gb;
	}

	public ComputerPlayer getCPU() {
		return cpu;
	}

	public HumanPlayer getPlayer() {
		return player;
	}

	public void dispose() {
		gb.dispose();

	}

	public void newGame() {
		String check = JOptionPane.showInputDialog("Would you like to start a new game(Current game will be lost)(Y/N)?");
		if(check != null && check.equalsIgnoreCase("Y")){
			mbc.newGame();
		}
		
	}

}
