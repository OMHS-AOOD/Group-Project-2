package gamefiles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class GameBoard extends JFrame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel board;
	private JTextArea noteBox;
	private JLabel playerScore, cpuScore, p200, c200, pMove, cMove;
	private DraggableCard currentCardClicked;
	private ArrayList<CardStack> playerPiles, cpuPiles;
	private int currentCardInt;
	private MilleBornesGame mbg;
	private HumanPlayer player;
	private ComputerPlayer cpu;
	private DraggableCard previousCpuCard;
	private JScrollPane pHaz, cHaz;
	private DefaultListModel<String> pDLM, cDLM;
	private JList<String> pList, cList;
	private JMenuBar jm;
	private JMenu options;
	private JMenuItem save, load, newG;
	private boolean hasDrawnCard, canCF, playedSafety;
	private int bWidth, bHeight;

	public GameBoard(MilleBornesGame m, HumanPlayer p, ComputerPlayer c) {
		super("Mille Bornes");
		mbg = m;
		hasDrawnCard = false;
		canCF = false;
		playedSafety = false;
		currentCardInt = -1;
		bWidth = 1450;
		bHeight = 700;
		this.setSize(bWidth, bHeight);
		this.setVisible(true);
		this.setLocation(25, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		board = new JPanel();
		this.add(board);
		board.setLayout(null);

		jm = new JMenuBar();
		this.setJMenuBar(jm);

		noteBox = new JTextArea();
		noteBox.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		noteBox.setForeground(Color.white);
		noteBox.setBackground(Color.black);
		noteBox.setWrapStyleWord(true);
		noteBox.setLineWrap(true);
		noteBox.setEditable(false);
		this.add(noteBox, 1300, 475, 130, 168);

		options = new JMenu("Options");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		newG = new JMenuItem("New");


		jm.add(options);
		options.add(newG);
		options.add(save);
		options.add(load);

		jm.setForeground(Color.green);
		jm.setBackground(Color.black);
		options.setForeground(Color.green);
		options.setBackground(Color.black);
		save.setForeground(Color.green);
		save.setBackground(Color.black);
		load.setForeground(Color.green);
		load.setBackground(Color.black);
		newG.setForeground(Color.green);
		newG.setBackground(Color.black);

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mbg.save();
			}
		});
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mbg.load();
			}
		});
		newG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mbg.newGame();
			}
		});


		player = p;
		cpu = c;
		playerPiles = new ArrayList<CardStack>();
		cpuPiles = new ArrayList<CardStack>();
		playerScore = new JLabel(player.getName() + ": " + player.getCurrentPoints() + " Miles");
		cpuScore = new JLabel(cpu.getName() + ": " + cpu.getCurrentPoints() + " Miles");
		p200 = new JLabel("200's used: " + player.getUsed200s());
		c200 = new JLabel("200's used: " + cpu.getUsed200s());
		pMove = new JLabel("Movement Status: Stopped");
		cMove = new JLabel("Movement Status: Stopped");

		pDLM = new DefaultListModel<String>();
		cDLM = new DefaultListModel<String>();
		pList = new JList<String>(pDLM);
		cList = new JList<String>(cDLM);
		pHaz = new JScrollPane(pList);
		cHaz = new JScrollPane(cList);

		pHaz.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		cHaz.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		pHaz.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cHaz.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(playerScore, 950, 475, 300, 30);
		this.add(cpuScore, 950, 10, 300, 30);

		this.add(p200, 950, 525, 300, 30);
		this.add(c200, 950, 35, 300, 30);

		this.add(pMove, 950, 500, 300, 30);
		this.add(cMove, 950, 60, 300, 30);

		this.add(pHaz, 950, 560, 300, 75);
		this.add(cHaz, 950, 95, 300, 75);

		playerScore.setForeground(Color.GREEN);
		cpuScore.setForeground(Color.RED);
		p200.setForeground(Color.GREEN);
		c200.setForeground(Color.RED);
		pMove.setForeground(Color.GREEN);
		cMove.setForeground(Color.RED);
		pList.setForeground(Color.GREEN);
		cList.setForeground(Color.RED);
		pList.setBackground(Color.BLACK);
		cList.setBackground(Color.BLACK);
		board.setBackground(Color.BLACK);

		pList.setCellRenderer(new MyCellRenderer());
		cList.setCellRenderer(new CpuCellRenderer());

		playerScore.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		cpuScore.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		p200.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		c200.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		pMove.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		cMove.setFont(new Font("OCR A Extended", Font.PLAIN, 16));
		pList.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
		cList.setFont(new Font("OCR A Extended", Font.PLAIN, 12));

		updateHazards();

	}

	public int getCurrentCardInt() {
		return currentCardInt;
	}

	public boolean getPlayedSafety() {
		return playedSafety;
	}

	public boolean getCanCF() {
		return canCF;
	}

	public boolean getHasDrawnCard() {
		return hasDrawnCard;
	}

	public void add(JComponent j, int x, int y, int w, int h) {

		if (j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals(player.getName())) {
			j.addMouseMotionListener(new CardDrag());
			reorganizeCardGraphics();
		}
		if (j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals(cpu.getName())) {
			reorganizeCpuCardGraphics();
		}
		if (j instanceof CardStack && !((CardStack) j).getName().equals("Deck")
				&& !((CardStack) j).getName().equals("Discard")) {
			j.addMouseListener(new StackClick((CardStack) j));
		}
		if (j instanceof CardStack && ((CardStack) j).getOwner().equals(player.getName())) {
			playerPiles.add((CardStack) j);
		}
		if (j instanceof CardStack && ((CardStack) j).getOwner().equals(cpu.getName())) {
			cpuPiles.add((CardStack) j);
		}
		if (j instanceof Deck) {
			j.addMouseListener(new DeckClick());
		}
		board.add(j);
		j.setBounds(x, y, w, h);
	}

	public void placeCard(DraggableCard j, int x, int y, int w, int h) {
		if (j.getOwner().equals(player.getName())) {
			if (j.getMouseMotionListeners().length == 0) {
				j.addMouseMotionListener(new CardDrag());
			} else {
				j.removeMouseMotionListener(j.getMouseMotionListeners()[0]);
				j.addMouseMotionListener(new CardDrag());

			}

			reorganizeCardGraphics();
		}
		if (j.getOwner().equals(cpu.getName())) {
			reorganizeCpuCardGraphics();
		}
		j.setBounds(x, y, w, h);
	}

	public void placeCardAgain(DraggableCard j, int x, int y, int w, int h) {
		if (j.getOwner().equals(player.getName())) {
			if (j.getMouseMotionListeners().length == 0) {
				j.addMouseMotionListener(new CardDrag());
			} else {
				j.removeMouseMotionListener(j.getMouseMotionListeners()[0]);
				j.addMouseMotionListener(new CardDrag());

			}

			reorganizeCardGraphics();
		}
		if (j.getOwner().equals(cpu.getName())) {
			reorganizeCpuCardGraphics();
		}
		j.setBounds(x, y, w, h);
		board.add(j);
	}

	public void returnToOriginalPos() {
		int targetX = currentCardClicked.getWantedX();
		int targetY = currentCardClicked.getWantedY();
		int width = currentCardClicked.getWidth();
		int height = currentCardClicked.getHeight();
		currentCardClicked.setBounds(targetX, targetY, width, height);

	}

	public void reorganizeCardGraphics() {
		int i = 0;
		for (DraggableCard dc : player.getHand()) {
			if (dc.getMouseListeners().length != 0) {
				dc.removeMouseListener(dc.getMouseListeners()[0]);
			}
			dc.addMouseListener(new CardClick(i));
			dc.updateWanted((i * 120) + 10, 475);
			dc.setBounds(dc.getWantedX(), dc.getWantedY(), 100, 153);
			i++;
		}
	}

	public void reorganizeCpuCardGraphics() {
		for (int i = 0; i < cpu.getHand().size(); i++) {
			DraggableCard dc = cpu.getHand().get(i);
			dc.setBounds((i * 120) + 10, 10, 100, 153);
			dc.setFlip(true);
		}
	}

	public void resetSize() {
		this.setSize(bWidth, bHeight);
	}

	private class DeckClick extends MouseAdapter {


		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == 1) {
				if (hasDrawnCard == false) {

					noteBox.setText("");

					mbg.drawCardForPlayer(player);
					hasDrawnCard = true;
					player.getCard(player.getHand().size() - 1).addMouseMotionListener(new CardDrag());
					reorganizeCardGraphics();
				}
			}
			if (e.getButton() == 3) {
				UIManager.put("OptionPane.messageForeground", Color.blue);
				UIManager.put("Button.foreground", Color.blue);
				JOptionPane.showMessageDialog(null, "Number of cards left: " + mbg.getDeck().getCurrentSize(), "Deck",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	private class StackClick extends MouseAdapter {
		private CardStack myStack;


		public StackClick(CardStack s) {
			myStack = s;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			String display = "";
			if (myStack.getVisibleStack().size() != 0) {
				for (DraggableCard dc : myStack.getVisibleStack()) {
					display = display + dc.getCard().getName();
					if (dc.getCard().getCF()) {
						display = display + ": was a coup-fourre";
					}
					display = display + "\n";
				}

				if (myStack.getOwner().equals(player.getName())) {
					UIManager.put("OptionPane.messageForeground", Color.green);
					UIManager.put("Button.foreground", Color.GREEN);

				} else if (myStack.getOwner().equals(cpu.getName())) {
					UIManager.put("OptionPane.messageForeground", Color.red);
					UIManager.put("Button.foreground", Color.red);

				} else {
					UIManager.put("OptionPane.messageForeground", Color.blue);
					UIManager.put("Button.foreground", Color.blue);

				}
				JOptionPane.showMessageDialog(null, display, myStack.getName() + ": " + myStack.getOwner(),
						JOptionPane.INFORMATION_MESSAGE);

			}

		}
	}

	private class CardClick extends MouseAdapter {
		private int cardInt;

		public CardClick(int cI) {
			cardInt = cI;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			currentCardInt = cardInt;
			JOptionPane.showMessageDialog(null, currentCardInt, "yo",
					JOptionPane.INFORMATION_MESSAGE);
			if (currentCardInt != -1) {
				currentCardClicked = player.getCard(currentCardInt);
				reorderComponents(currentCardClicked);
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			decideStack();
			currentCardInt = -1;
			currentCardClicked = null;
		}

	}

	public void decideStack() {
		if (currentCardClicked != null) {
			int xPos = currentCardClicked.getX();
			int yPos = currentCardClicked.getY();
			int width = currentCardClicked.getWidth();
			int height = currentCardClicked.getHeight();
			double maxTouch = 0;
			int maxTouchIndex = 0;
			int index = 0;
			for (CardStack c : playerPiles) {
				Rectangle computedIntersection = SwingUtilities.computeIntersection(xPos, yPos, width, height,
						c.getBounds());
				double area = computedIntersection.getWidth() * computedIntersection.getHeight();
				if (area > maxTouch) {
					maxTouchIndex = index;
					maxTouch = area;
				}
				index++;
			}

			CardStack discard = mbg.getDiscard();
			CardStack target = null;

			Rectangle computedIntersection1 = SwingUtilities.computeIntersection(xPos, yPos, width, height,
					discard.getBounds());
			double area1 = computedIntersection1.getWidth() * computedIntersection1.getHeight();
			if (area1 > maxTouch) {
				target = discard;
				maxTouch = area1;
			} else {
				target = playerPiles.get(maxTouchIndex);
			}
			Rectangle computedIntersection2 = SwingUtilities.computeIntersection(xPos, yPos, width, height,
					cpu.getBattle().getBounds());
			double area2 = computedIntersection2.getWidth() * computedIntersection2.getHeight();
			if (area2 > maxTouch) {
				target = cpu.getBattle();
				maxTouch = area2;
			}
			Rectangle computedIntersection3 = SwingUtilities.computeIntersection(xPos, yPos, width, height,
					cpu.getLimit().getBounds());
			double area3 = computedIntersection3.getWidth() * computedIntersection3.getHeight();
			if (area3 > maxTouch) {
				target = cpu.getLimit();
				maxTouch = area3;
			}
			if ((canCF == false && !hasDrawnCard)
					|| (!(currentCardClicked.getCard() instanceof SafetyCard) && !hasDrawnCard)) {
				returnToOriginalPos();
				noteBox.setText("Must draw card first.");
				return;
			}
			if (canCF) {
				// TODO
				// Ask user if they want to coup fourre
			}
			if (maxTouch > 10000 && target.canDropCardOn()
					&& isValidMove(target, currentCardClicked, player, cpu, false)) {

				int targetX = target.getX() + 15;
				int targetY = target.getY() + 35;
				int cwidth = currentCardClicked.getWidth();
				int cheight = currentCardClicked.getHeight();
				currentCardClicked.setBounds(targetX, targetY, cwidth, cheight);
				if (target.getName().equals("Discard")) {
					currentCardClicked.setOwner("");
				}
				if (target.getName().equals("Battle") && target.getOwner().equals(player.getName())
						&& currentCardClicked.getCard() instanceof DistanceCard) {
					currentCardClicked.setCard(new RollCard("Roll", 's', 0));

				}

				if (currentCardClicked.getCard() instanceof SafetyCard) {
					playedSafety = true;
				}
				if (currentCardClicked.getCard() instanceof DistanceCard && target.getName().equals("Distance")) {
					if (((DistanceCard) currentCardClicked.getCard()).getValue() == 200) {
						player.add200();

					}
				}

				target.addCard(currentCardClicked);
				player.getHand().remove(currentCardInt);
				player.getCards().remove(currentCardInt);
				currentCardClicked.removeMouseMotionListener(currentCardClicked.getMouseMotionListeners()[0]);
				currentCardClicked.removeMouseListener(currentCardClicked.getMouseListeners()[0]);
				if (hasDrawnCard == false) {
					mbg.drawCardForPlayer(player);
					player.getCard(player.getHand().size() - 1).addMouseMotionListener(new CardDrag());
					player.coupFourre();
					canCF = false;
					currentCardClicked.getCard().beCF();
					noteBox.setText("Coup-Fourre!");
					markCards();

				} else {

					noteBox.setText("");
				}
				hasDrawnCard = false;
				this.reorganizeCardGraphics();
				updateHazards();
				this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
				if (!playedSafety) {
					this.cpuTurn();

					if (previousCpuCard != null && previousCpuCard.getCard() instanceof HazardCard
							&& currentCardClicked.getCard() instanceof SafetyCard) {
						if (((HazardCard) previousCpuCard.getCard())
								.getType() != ((SafetyCard) currentCardClicked.getCard()).getType()) {
							canCF = false;
						}
					}
				} else {
					canCF = false;
					playedSafety = false;
				}
				markCards();
				updateHazards();
				currentCardClicked.mark(false);
				mbg.checkIfDeckNeedsReforming();
			} else {
				returnToOriginalPos();
			}
		}

	}

	private boolean isValidMove(CardStack c, DraggableCard dc, HumanPlayer p, HumanPlayer cp, boolean grayTime) {

		if (c.getName().equals("Distance") && dc.getCard() instanceof DistanceCard) {
			if (p.needsRoll() && !grayTime) {
				if (p.getName().equals(player.getName())) {
					noteBox.setText("Currently need a roll card to move.");
				}
				return false;
			}
			if (!p.canMoveNormally() && p.canMove() && ((DistanceCard) dc.getCard()).getValue() > 50) {

				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Can't go over 50 miles while limited.");
				}

				return false;
			}
			if (p.getUsed200s() == 2 && ((DistanceCard) dc.getCard()).getValue() == 200) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Can only use 2 200s for distance.");
				}

				return false;
			}
			if (!p.canMove()) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Cannot move due to status.");
				}
				return false;
			}
			if (p.getNeededDistance() < ((DistanceCard) dc.getCard()).getValue()) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Can't go over 1000 miles total.");

				}

				return false;
			}

			return true;
		}
		if (c.getName().equals("Battle") && c.getOwner().equals(cp.getName()) && dc.getCard() instanceof HazardCard) {
			if (cp.isImmuneTo(dc.getCard())) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Player has immunity to this card.");
					return false;
				}
			}

			if (!cp.willTakeBattleCard(dc.getCard())) {

				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Battle pile contains unresolved card.");
				}
				if (p.getName().equals(player.getName()) && !grayTime && cp.getBattle().getCurrentSize() == 0) {
					noteBox.setText("Battle pile is empty.");
				}

				return false;
			}
			return true;
		}
		if (c.getName().equals("Battle") && c.getOwner().equals(p.getName()) && dc.getCard() instanceof RemedyCard) {
			if (p.needsRoll() && !(dc.getCard() instanceof RollCard)) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Currently need a roll card to move.");
				}
				return false;

			}
			if (!p.willTakeRemedyCard(dc)) {
				if (p.getName().equals(player.getName()) && !(dc.getCard() instanceof RollCard) && !grayTime) {
					noteBox.setText("Can't use that card without appropriate status.");
				}
				if (p.getName().equals(player.getName()) && (dc.getCard() instanceof RollCard) && !grayTime) {
					noteBox.setText("Can't use that card until hazard is resolved.");

				}

				return false;
			}
			return true;
		}
		if (c.getName().equals("Limit") && c.getOwner().equals(cp.getName()) && dc.getCard() instanceof LimitCard) {
			if (cp.isImmuneTo(new HazardCard("Stop", 's', 0))) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Player has Right of Way.");
				}
				return false;
			}
			if (!cp.willTakeLimitCard()) {

				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Limit pile can't take another limit.");

				}
				return false;
			}

			return true;
		}
		if (c.getName().equals("Limit") && c.getOwner().equals(p.getName()) && dc.getCard() instanceof EoLimitCard) {
			if (!p.willTakeEoLimitCard()) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Can't use that card without being limited first.");

				}
				return false;
			}
			return true;
		}
		if (c.getName().equals("Safety") && dc.getCard() instanceof SafetyCard) {
			return true;
		}
		if (dc.getCard() instanceof DistanceCard && c.getName().equals("Battle") && c.getOwner().equals(p.getName())) {
			DistanceCard temp = (DistanceCard) dc.getCard();
			if (temp.getValue() < 200) {
				if (p.getName().equals(player.getName()) && !grayTime) {

					noteBox.setText("Invalid card for that pile.");

				}
				return false;
			}
			if (!p.needsRoll()) {
				if (p.getName().equals(player.getName()) && !grayTime) {
					noteBox.setText("Do not currently need a roll card.");

				}
				return false;
			}
			if (temp.getValue() == 200) {
				return true;

			}

		}
		if (c.getName().equals("Discard")) {
			return true;
		}

		if (p.getName().equals(player.getName()) && !grayTime) {
			noteBox.setText("Invalid card for that pile.");
		}
		return false;
	}

	private class CardDrag extends MouseMotionAdapter {
		private int mouseX, mouseY;

		@Override
		public void mouseDragged(MouseEvent e) {
			if (currentCardInt != -1) {

				mouseX = currentCardClicked.getX() + e.getX();
				mouseY = currentCardClicked.getY() + e.getY();
				currentCardClicked.setBounds(mouseX - currentCardClicked.getWidth() / 2,
						mouseY - currentCardClicked.getHeight() / 2, currentCardClicked.getWidth(),
						currentCardClicked.getHeight());
				drawDecks();

			}

		}
	}

	public void cpuTurn() {
		canCF = false;
		boolean hasntMoved = true;
		if (player.hasWon(cpu)) {
			mbg.gameWon(player.getName());
		} else {
			mbg.drawCardForPlayer(cpu);
			this.reorganizeCpuCardGraphics();
			ArrayList<DraggableCard> allDists = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allHazards = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allSafes = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allRemedy = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allLimits = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allEnds = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allRolls = new ArrayList<DraggableCard>();
			ArrayList<Integer> dInts = new ArrayList<Integer>();
			ArrayList<Integer> hInts = new ArrayList<Integer>();
			ArrayList<Integer> sInts = new ArrayList<Integer>();
			ArrayList<Integer> rInts = new ArrayList<Integer>();
			ArrayList<Integer> lInts = new ArrayList<Integer>();
			ArrayList<Integer> eInts = new ArrayList<Integer>();
			ArrayList<Integer> rollInts = new ArrayList<Integer>();
			for (int i = 0; i < cpu.getHand().size(); i++) {
				Card c = cpu.getHand().get(i).getCard();
				if (c instanceof DistanceCard) {
					allDists.add(cpu.getHand().get(i));
					dInts.add(i);
				}
				if (c instanceof SafetyCard) {
					allSafes.add(cpu.getHand().get(i));
					sInts.add(i);
				}
				if (c instanceof HazardCard) {
					allHazards.add(cpu.getHand().get(i));
					hInts.add(i);
				}
				if (c instanceof RemedyCard) {
					allRemedy.add(cpu.getHand().get(i));
					rInts.add(i);
				}
				if (c instanceof LimitCard) {
					allLimits.add(cpu.getHand().get(i));
					lInts.add(i);
				}
				if (c instanceof LimitCard) {
					allEnds.add(cpu.getHand().get(i));
					eInts.add(i);
				}
				if (c instanceof RollCard) {
					allRolls.add(cpu.getHand().get(i));
					rollInts.add(i);
				}
			}
			if (cpu.needsRoll() && hasntMoved && allRolls.size() > 0) {
				CardStack target = cpu.getBattle();
				DraggableCard cpuCard = allRolls.get(0);
				if (isValidMove(target, cpuCard, cpu, player, false)) {
					int targetX = target.getX() + 15;
					int targetY = target.getY() + 35;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					reorderComponents(cpuCard);
					int tempI = rollInts.remove(0).intValue();
					cpu.getHand().remove(tempI);
					cpu.getCards().remove(tempI);
					hasntMoved = false;
					updateHazards();
					borderLastCpuCard(cpuCard);
					this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
					this.reorganizeCpuCardGraphics();
				}
			}
			if (allSafes.size() > 0 && hasntMoved) {
				CardStack target = cpu.getSafety();
				DraggableCard cpuCard = allSafes.get(0);
				if (isValidMove(target, cpuCard, cpu, player, false)) {
					int targetX = target.getX() + 15;
					int targetY = target.getY() + 35;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					reorderComponents(cpuCard);
					int tempI = sInts.remove(0).intValue();

					cpu.getHand().remove(tempI);
					cpu.getCards().remove(tempI);

					hasntMoved = false;

					updateHazards();
					borderLastCpuCard(cpuCard);
					this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
					this.reorganizeCpuCardGraphics();
					cpuTurn();
				}
			}
			if (allHazards.size() > 0 && hasntMoved) {
				CardStack target = player.getBattle();

				for (int i = 0; i < allHazards.size(); i++) {
					DraggableCard cpuCard = allHazards.get(0);
					if (isValidMove(target, cpuCard, cpu, player, false)) {
						int targetX = target.getX() + 15;
						int targetY = target.getY() + 35;
						int cwidth = cpuCard.getWidth();
						int cheight = cpuCard.getHeight();
						cpuCard.setBounds(targetX, targetY, cwidth, cheight);
						cpuCard.flipCard();
						target.addCard(cpuCard);
						reorderComponents(cpuCard);
						int tempI = hInts.remove(i).intValue();


						cpu.getHand().remove(tempI);
						cpu.getCards().remove(tempI);

						hasntMoved = false;

						updateHazards();
						borderLastCpuCard(cpuCard);
						this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
						this.reorganizeCpuCardGraphics();
						canCF = true;
						break;
					}
				}

			}
			if (allLimits.size() > 0 && hasntMoved) {
				CardStack target = player.getLimit();
				DraggableCard cpuCard = allLimits.get(0);
				if (isValidMove(target, cpuCard, cpu, player, false)) {
					int targetX = target.getX() + 15;
					int targetY = target.getY() + 35;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					reorderComponents(cpuCard);
					int tempI = lInts.remove(0).intValue();
					cpu.getHand().remove(tempI);
					cpu.getCards().remove(tempI);

					hasntMoved = false;
					updateHazards();
					borderLastCpuCard(cpuCard);
					this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
					this.reorganizeCpuCardGraphics();
				}
			}
			if (allEnds.size() > 0 && !cpu.canMoveNormally()) {
				CardStack target = cpu.getLimit();
				DraggableCard cpuCard = allEnds.get(0);
				if (isValidMove(target, cpuCard, cpu, player, false)) {
					int targetX = target.getX() + 15;
					int targetY = target.getY() + 35;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					reorderComponents(cpuCard);
					int tempI = eInts.remove(0).intValue();
					cpu.getHand().remove(tempI);
					cpu.getCards().remove(tempI);

					hasntMoved = false;
					updateHazards();
					borderLastCpuCard(cpuCard);
					this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
					this.reorganizeCpuCardGraphics();
				}
			}
			if (allDists.size() > 0 && hasntMoved && cpu.canMoveNormally()) {
				int minLeft = 1000;
				int ind = -1;
				for (int i = 0; i < allDists.size(); i++) {
					int tempVal = cpu.getNeededDistance() - ((DistanceCard) allDists.get(i).getCard()).getValue();
					if (tempVal >= 0 && tempVal < minLeft) {
						ind = i;
						minLeft = tempVal;
					}
				}
				if (ind != -1) {
					CardStack target = cpu.getDistance();
					DraggableCard cpuCard = allDists.get(ind);
					if (isValidMove(target, cpuCard, cpu, player, false)) {
						int targetX = target.getX() + 15;
						int targetY = target.getY() + 35;
						int cwidth = cpuCard.getWidth();
						int cheight = cpuCard.getHeight();
						cpuCard.setBounds(targetX, targetY, cwidth, cheight);
						cpuCard.flipCard();
						target.addCard(cpuCard);
						reorderComponents(cpuCard);
						int tempI =dInts.remove(ind).intValue();
						cpu.getHand().remove(tempI);
						cpu.getCards().remove(tempI);

						hasntMoved = false;
						if (cpuCard.getCard() instanceof DistanceCard && target.getName().equals("Distance")) {
							if (((DistanceCard) cpuCard.getCard()).getValue() == 200) {
								cpu.add200();

							}
						}
						updateHazards();
						borderLastCpuCard(cpuCard);
						this.updateLabels(cpuScore, c200, cMove, cpu, pMove, player);

						if (cpu.hasWon(player)) {
							mbg.gameWon(cpu.getName());
						}
						this.reorganizeCpuCardGraphics();
					}

				}

			}
			if (allDists.size() > 0 && hasntMoved && cpu.canMove()) {
				int minLeft = 1000;
				int ind = -1;
				for (int i = 0; i < allDists.size(); i++) {
					int tempVal = cpu.getNeededDistance() - ((DistanceCard) allDists.get(i).getCard()).getValue();
					if (tempVal >= 0 && tempVal < minLeft
							&& ((DistanceCard) allDists.get(i).getCard()).getValue() <= 50) {
						ind = i;
						minLeft = tempVal;
					}
				}
				if (ind != -1) {
					CardStack target = cpu.getDistance();
					DraggableCard cpuCard = allDists.get(ind);
					if (isValidMove(target, cpuCard, cpu, player, false)) {
						int targetX = target.getX() + 15;
						int targetY = target.getY() + 35;
						int cwidth = cpuCard.getWidth();
						int cheight = cpuCard.getHeight();
						cpuCard.setBounds(targetX, targetY, cwidth, cheight);
						cpuCard.flipCard();
						target.addCard(cpuCard);
						reorderComponents(cpuCard);
						int tempI = dInts.remove(ind).intValue();
						cpu.getHand().remove(tempI);
						cpu.getCards().remove(tempI);

						hasntMoved = false;

						updateHazards();
						borderLastCpuCard(cpuCard);
						this.updateLabels(cpuScore, c200, cMove, cpu, pMove, player);

						if (player.hasWon(cpu)) {
							mbg.gameWon(player.getName());
						}
						this.reorganizeCpuCardGraphics();
					}

				}

			}
			if (!cpu.canMove() && hasntMoved && allRemedy.size() > 0) {
				ArrayList<Character> remChars = new ArrayList<Character>();
				ArrayList<Character> hazChars = new ArrayList<Character>();
				ArrayList<Character> safChars = new ArrayList<Character>();
				for (Card c : cpu.getBattle().getStack()) {
					if (c instanceof RemedyCard) {
						remChars.add(((RemedyCard) c).getType());
					} else if (c instanceof HazardCard) {
						hazChars.add(((HazardCard) c).getType());
					}
				}
				for (Card c : cpu.getSafety().getStack()) {
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
							}
						}
					}
				}

				if (hazChars.size() > 0) {
					for (int i = 0; i < allRemedy.size(); i++) {
						for (Character hc : hazChars) {
							DraggableCard cpuCard = allRemedy.get(i);
							if (((RemedyCard) cpuCard.getCard()).getType() == hc.charValue() && hasntMoved) {
								CardStack target = cpu.getBattle();
								if (isValidMove(target, cpuCard, cpu, player, false)) {
									int targetX = target.getX() + 15;
									int targetY = target.getY() + 35;
									int cwidth = cpuCard.getWidth();
									int cheight = cpuCard.getHeight();
									cpuCard.setBounds(targetX, targetY, cwidth, cheight);
									cpuCard.flipCard();
									target.addCard(cpuCard);
									reorderComponents(cpuCard);
									int tempI = rInts.remove(i).intValue();
									cpu.getHand().remove(tempI);
									cpu.getCards().remove(tempI);

									hasntMoved = false;

									updateHazards();
									borderLastCpuCard(cpuCard);
									this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
									this.reorganizeCpuCardGraphics();
								}
							}
						}
					}
				}

			}

			if (hasntMoved) {
				CardStack target = mbg.getDiscard();
				int tempI = cpu.getLowestValuedCardIndex();
				DraggableCard cpuCard = cpu.getHand().remove(tempI);
				cpu.getCards().remove(tempI);
				int targetX = target.getX() + 15;
				int targetY = target.getY() + 35;
				int cwidth = cpuCard.getWidth();
				int cheight = cpuCard.getHeight();
				cpuCard.setBounds(targetX, targetY, cwidth, cheight);
				cpuCard.flipCard();

				target.addCard(cpuCard);
				borderLastCpuCard(cpuCard);
				reorderComponents(cpuCard);
				hasntMoved = false;

				updateHazards();
				this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
				this.reorganizeCpuCardGraphics();

			}

		}

	}

	private void reorderComponents(Component topC) {

		Component[] components = board.getComponents();
		int i = 0;
		int index = -1;
		for (Component c : components) {
			if (c == topC) {
				index = i;
			}
			i++;
		}

		if (index != -1) {
			board.removeAll();
			Component temp = components[index];

			Component[] newComp = new Component[components.length];
			newComp[0] = temp;
			int tempI = 1;
			for (Component c : components) {
				if (c != temp) {
					newComp[tempI] = c;
					tempI++;
				}
			}

			for (Component comp : newComp) {
				board.add(comp);
			}
			board.validate();

		}

	}

	private void drawDecks() {
		for (CardStack c : playerPiles) {
			c.repaint();
		}
		for (CardStack c : cpuPiles) {
			c.repaint();
		}
		mbg.getDiscard().repaint();
		mbg.getDeck().repaint();
		noteBox.repaint();
	}

	private void borderLastCpuCard(DraggableCard dc) {
		if (previousCpuCard != null) {
			previousCpuCard.setBorder(null);
		}
		Border border = BorderFactory.createLineBorder(new Color(148, 0, 211), 10);
		dc.setBorder(border);
		previousCpuCard = dc;
	}

	private void updateMoveText(JLabel l, HumanPlayer p) {

		if (p.canMoveNormally()) {
			l.setText("Movement Status: Rolling");

		} else if (p.canMove()) {
			l.setText("Movement Status: Limited");

		} else {
			l.setText("Movement Status: Stopped");

		}
	}

	private void updateLabels(JLabel l1, JLabel l2, JLabel l3, HumanPlayer p, JLabel l4, HumanPlayer p2) {
		l1.setText(p.getName() + ": " + p.getCurrentPoints() + " Miles");
		l2.setText("200's used: " + p.getUsed200s());
		updateMoveText(l3, p);
		updateMoveText(l4, p2);

	}

	private void updateHazards() {
		ArrayList<String> temp = player.getCurrentHazards();
		pDLM.removeAllElements();
		for (String str : temp) {
			pDLM.addElement(str);
		}
		ArrayList<String> temp2 = cpu.getCurrentHazards();
		cDLM.removeAllElements();
		for (String str2 : temp2) {
			cDLM.addElement(str2);
		}

	}

	public class MyCellRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (isSelected) {
				c.setBackground(Color.WHITE);
				c.setForeground(Color.green);
			}
			return c;
		}
	}

	public class CpuCellRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (isSelected) {
				c.setBackground(Color.WHITE);
				c.setForeground(Color.red);
			}
			return c;
		}
	}

	public void resetup(GameData data) {


		for (CardStack c : playerPiles) {
			for (int i = c.getCurrentSize() - 1; i >= 0; i--) {
				DraggableCard dc = c.getVisibleStack().get(i);
				this.add(dc, c.getX() + 15, c.getY() + 35, 100, 153);
				dc.setFlip(false);
			}
		}
		for (CardStack c : cpuPiles) {
			for (int i = c.getCurrentSize() - 1; i >= 0; i--) {
				DraggableCard dc = c.getVisibleStack().get(i);
				this.add(dc, c.getX() + 15, c.getY() + 35, 100, 153);
				dc.setFlip(false);
			}
		}

		for (int i = mbg.getDiscard().getCurrentSize() - 1; i >= 0; i--) {
			DraggableCard dc = mbg.getDiscard().getVisibleStack().get(i);
			this.add(dc, mbg.getDiscard().getX() + 15, mbg.getDiscard().getY() + 35, 100, 153);
			dc.setFlip(false);
		}

		for (int i = mbg.getDeck().getCurrentSize() - 1; i >= 0; i--) {
			DraggableCard dc = mbg.getDeck().getVisibleStack().get(i);
			this.add(dc, mbg.getDeck().getX() + 15, mbg.getDeck().getY() + 35, 100, 153);
			dc.setFlip(true);
		}
		ArrayList<DraggableCard> c = player.getHand();
		for (int i = 0; i < c.size(); i++) {
			c.get(i).setOwner(player.getName());
			this.placeCardAgain(c.get(i), (i * 120) + 10, 475, 100, 153);
			c.get(i).setFlip(false);
		}
		ArrayList<DraggableCard> c2 = cpu.getHand();
		for (int i = 0; i < c2.size(); i++) {
			c2.get(i).setOwner(cpu.getName());
			this.placeCardAgain(c2.get(i), (i * 120) + 10, 10, 100, 153);
			c2.get(i).setFlip(true);

		}

		this.reorganizeCardGraphics();
		this.reorganizeCpuCardGraphics();
		hasDrawnCard = data.isHasDrawnCard();
		canCF = data.isCanCF();
		playedSafety = data.isPlayedSafety();
		currentCardInt = data.getCurrentCardInt();

		updateHazards();
		this.updateLabels(playerScore, p200, pMove, player, cMove, cpu);
		markCards();

	}

	public void markCards() {

		ArrayList<CardStack> allCardStacks = new ArrayList<CardStack>();
		for (CardStack c : playerPiles) {
			allCardStacks.add(c);
		}
		for (CardStack c : cpuPiles) {
			allCardStacks.add(c);
		}
		
		for (DraggableCard c : player.getHand()) {
			c.mark(true);

		}
		for (DraggableCard c : player.getHand()) {
			for (CardStack s : allCardStacks) {
				if (this.isValidMove(s, c, player, cpu, true)) {
					c.mark(false);
					break;
				}
			}
		}
		
		if (!(noteBox.getText().equals("Coup-Fourre!"))) {
			noteBox.setText("");
		}

	}



}
