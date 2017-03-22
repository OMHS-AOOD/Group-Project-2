import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class GameBoard extends JFrame {
	private JPanel board;
	private JLabel playerScore, cpuScore, p200, c200;
	private DraggableCard currentCardClicked;
	private ArrayList<CardStack> playerPiles, cpuPiles;
	private int currentCardInt;
	private MilleBornesGame mbg;
	private HumanPlayer player;
	private ComputerPlayer cpu;

	public GameBoard(MilleBornesGame m, HumanPlayer p, ComputerPlayer c) {
		super("Mille Bornes");
		mbg = m;
		currentCardInt = -1;
		this.setSize(1200, 675);
		this.setVisible(true);
		this.setLocation(200, 112);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		board = new JPanel();
		this.add(board);
		board.setLayout(null);
		player = p;
		cpu = c;
		playerPiles = new ArrayList<CardStack>();
		cpuPiles = new ArrayList<CardStack>();
		playerScore = new JLabel(player.getName() + ": " + player.getCurrentPoints());
		cpuScore = new JLabel(cpu.getName() + ": " + cpu.getCurrentPoints());
		p200 = new JLabel("200's used: " + player.getUsed200s());
		c200 = new JLabel("200's used: " + cpu.getUsed200s());
		this.add(playerScore, 800, 475, 300, 30);
		this.add(cpuScore, 800, 505, 300, 30);
		this.add(p200, 800, 490, 300, 30);
		this.add(c200, 800, 520, 300, 30);
		
		playerScore.setForeground(Color.GREEN);
		cpuScore.setForeground(Color.RED);
		p200.setForeground(Color.GREEN);
		c200.setForeground(Color.RED);
		board.setBackground(Color.BLACK);
		
	}

	public void add(JComponent j, int x, int y, int w, int h) {

		if (j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals(player.getName())) {
			j.addMouseMotionListener(new CardDrag());
			reorganizeCardGraphics();
		}
		if (j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals(cpu.getName())) {
			reorganizeCpuCardGraphics();
		}
		if (j instanceof CardStack && ((CardStack) j).getOwner().equals(player.getName())) {
			playerPiles.add((CardStack) j);
			j.addMouseListener(new StackClick((CardStack) j));
		}
		if (j instanceof CardStack && ((CardStack) j).getOwner().equals(cpu.getName())) {
			cpuPiles.add((CardStack) j);
			j.addMouseListener(new StackClick((CardStack) j));
		}
		board.add(j);
		j.setBounds(x, y, w, h);
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
			dc.updateWanted((i * 120), 475);
			dc.setBounds(dc.getWantedX(), dc.getWantedY(), 100, 153);
			i++;
		}
	}

	public void reorganizeCpuCardGraphics() {
		for (int i = 0; i < cpu.getHand().size(); i++) {
			DraggableCard dc = cpu.getHand().get(i);
			dc.setBounds((i * 120) + 10, 10, 100, 153);
		}
	}

	public void resetSize() {
		this.setSize(1200, 675);
	}

	private class StackClick extends MouseAdapter{
		private CardStack myStack;
		int i = 0;
		public StackClick(CardStack s) {
			myStack = s;
		}
		@Override
		public void mousePressed(MouseEvent e) {
			if(myStack.getVisibleStack().size() !=0){
				if(i > myStack.getVisibleStack().size()-1){
					i = 0;
				}
				DraggableCard dc = myStack.getVisibleStack().get(i);
				reorderComponents(dc);
				i++;
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
			if(currentCardInt != -1){
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

			if (maxTouch > 10000 && target.canDropCardOn() && isValidMove(target, currentCardClicked, player, cpu)) {
				int targetX = target.getX() + 15;
				int targetY = target.getY() + 30;
				int cwidth = currentCardClicked.getWidth();
				int cheight = currentCardClicked.getHeight();
				currentCardClicked.setBounds(targetX, targetY, cwidth, cheight);
				if (target.getName().equals("Discard")) {
					currentCardClicked.setOwner("");
				}
				target.addCard(currentCardClicked);
				player.getHand().remove(currentCardInt);
				currentCardClicked.removeMouseMotionListener(currentCardClicked.getMouseMotionListeners()[0]);
				currentCardClicked.removeMouseListener(currentCardClicked.getMouseListeners()[0]);
				mbg.drawCardForPlayer(player);
				player.getCard(player.getHand().size() - 1).addMouseMotionListener(new CardDrag());
				this.reorganizeCardGraphics();
				playerScore.setText(player.getName() + ": " + player.getCurrentPoints());
				p200.setText("200's used: " + player.getUsed200s());
				this.cpuTurn();
			} else {
				returnToOriginalPos();
			}
		}

	}

	private boolean isValidMove(CardStack c, DraggableCard dc, HumanPlayer p, HumanPlayer cp) {
		if (c.getName().equals("Distance") && dc.getCard() instanceof DistanceCard) {
			if (!p.canMoveNormally() && p.canMove() && ((DistanceCard) currentCardClicked.getCard()).getValue() > 50) {
				return false;
			}
			if (p.getUsed200s() == 2 && ((DistanceCard) dc.getCard()).getValue() == 200) {
				return false;
			}
			if (!p.canMove()) {
				return false;
			}
			if (p.getNeededDistance() < ((DistanceCard) dc.getCard()).getValue()) {
				return false;
			}

			if (((DistanceCard) dc.getCard()).getValue() == 200) {
				p.add200();
			}

			return true;
		} else if (c.getName().equals("Battle") && c.getOwner().equals(cp.getName())
				&& dc.getCard() instanceof HazardCard) {
			if (c.containsCard(dc.getCard().getName())) {
				return false;
			}
			return true;
		} else if (c.getName().equals("Battle") && c.getOwner().equals(p.getName())
				&& dc.getCard() instanceof RemedyCard) {
			return true;
		} else if (c.getName().equals("Safety") && dc.getCard() instanceof SafetyCard) {
			if (c.containsCard(dc.getCard().getName())) {
				return false;
			}
			return true;
		} else if (c.getName().equals("Discard")) {
			return true;
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
		boolean hasntMoved = true;
		if (player.hasWon(cpu)) {
			mbg.gameWon(player.getName());
		} else {
			ArrayList<DraggableCard> allDists = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allHazards = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allSafes = new ArrayList<DraggableCard>();
			ArrayList<DraggableCard> allRemedy = new ArrayList<DraggableCard>();

			ArrayList<Integer> dInts = new ArrayList<Integer>();
			ArrayList<Integer> hInts = new ArrayList<Integer>();
			ArrayList<Integer> sInts = new ArrayList<Integer>();
			ArrayList<Integer> rInts = new ArrayList<Integer>();
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
			}
			if (allSafes.size() > 0 && hasntMoved) {
				CardStack target = cpu.getSafety();
				DraggableCard cpuCard = allSafes.get(0);
				if (isValidMove(target, cpuCard, cpu, player)) {
					int targetX = target.getX() + 15;
					int targetY = target.getY() + 30;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					reorderComponents(cpuCard);
					cpu.getHand().remove(sInts.remove(0).intValue());
					hasntMoved = false;
					mbg.drawCardForPlayer(cpu);
					this.reorganizeCpuCardGraphics();
				}
			}
			if (cpu.canMove() && allHazards.size() > 0 && hasntMoved) {
				CardStack target = player.getBattle();
				DraggableCard cpuCard = allHazards.get(0);
				if (isValidMove(target, cpuCard, cpu, player)) {
					int targetX = target.getX() + 15;
					int targetY = target.getY() + 30;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					reorderComponents(cpuCard);
					cpu.getHand().remove(hInts.remove(0).intValue());
					hasntMoved = false;
					mbg.drawCardForPlayer(cpu);
					this.reorganizeCpuCardGraphics();
				}

			}
			if (allDists.size() > 0 && hasntMoved && cpu.canMoveNormally()) {
				int minLeft = 1000;
				int ind = -1;
				for (int i = 0; i < allDists.size(); i++) {
					int tempVal = cpu.getNeededDistance() - ((DistanceCard) allDists.get(i).getCard()).getValue();
					if (tempVal > 0 && tempVal < minLeft) {
						ind = i;
						minLeft = tempVal;
					}
				}
				if (ind != -1) {
					CardStack target = cpu.getDistance();
					DraggableCard cpuCard = allDists.get(ind);
					if (isValidMove(target, cpuCard, cpu, player)) {
						int targetX = target.getX() + 15;
						int targetY = target.getY() + 30;
						int cwidth = cpuCard.getWidth();
						int cheight = cpuCard.getHeight();
						cpuCard.setBounds(targetX, targetY, cwidth, cheight);
						cpuCard.flipCard();
						target.addCard(cpuCard);
						reorderComponents(cpuCard);
						cpu.getHand().remove(dInts.remove(ind).intValue());
						hasntMoved = false;
						mbg.drawCardForPlayer(cpu);
						cpuScore.setText(cpu.getName() + ": " + cpu.getCurrentPoints());
						c200.setText("200's used: " + cpu.getUsed200s());
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
					if (tempVal > 0 && tempVal < minLeft
							&& ((DistanceCard) allDists.get(i).getCard()).getValue() <= 50) {
						ind = i;
						minLeft = tempVal;
					}
				}
				if (ind != -1) {
					CardStack target = cpu.getDistance();
					DraggableCard cpuCard = allDists.get(ind);
					if (isValidMove(target, cpuCard, cpu, player)) {
						int targetX = target.getX() + 15;
						int targetY = target.getY() + 30;
						int cwidth = cpuCard.getWidth();
						int cheight = cpuCard.getHeight();
						cpuCard.setBounds(targetX, targetY, cwidth, cheight);
						cpuCard.flipCard();
						target.addCard(cpuCard);
						reorderComponents(cpuCard);
						cpu.getHand().remove(dInts.remove(ind).intValue());
						hasntMoved = false;
						mbg.drawCardForPlayer(cpu);
						cpuScore.setText(cpu.getName() + ": " + cpu.getCurrentPoints());
						c200.setText("200's used: " + cpu.getUsed200s());

						if (player.hasWon(cpu)) {
							mbg.gameWon(player.getName());
						}
						this.reorganizeCpuCardGraphics();
					}

				}

			}
			if (!cpu.canMove() && hasntMoved && allRemedy.size() > 0) {
				ArrayList<DraggableCard> enemyHazards = cpu.getBattle().getVisibleStack();
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
							if (((RemedyCard) cpuCard.getCard()).getType() == hc.charValue()) {
								CardStack target = cpu.getBattle();
								if (isValidMove(target, cpuCard, cpu, player)) {
									int targetX = target.getX() + 15;
									int targetY = target.getY() + 30;
									int cwidth = cpuCard.getWidth();
									int cheight = cpuCard.getHeight();
									cpuCard.setBounds(targetX, targetY, cwidth, cheight);
									cpuCard.flipCard();
									target.addCard(cpuCard);
									reorderComponents(cpuCard);
									cpu.getHand().remove(rInts.remove(i).intValue());
									hasntMoved = false;
									mbg.drawCardForPlayer(cpu);
									this.reorganizeCpuCardGraphics();
								}
							}
						}
					}
				}

			}
			if (hasntMoved) {
				CardStack target = mbg.getDiscard();
				DraggableCard cpuCard = cpu.getHand().remove((int) (Math.random() * 6));

				int targetX = target.getX() + 15;
				int targetY = target.getY() + 30;
				int cwidth = cpuCard.getWidth();
				int cheight = cpuCard.getHeight();
				cpuCard.setBounds(targetX, targetY, cwidth, cheight);
				cpuCard.flipCard();
				target.addCard(cpuCard);
				reorderComponents(cpuCard);
				hasntMoved = false;
				mbg.drawCardForPlayer(cpu);
				this.reorganizeCpuCardGraphics();

			}

		}

	}

	
	private void reorderComponents(Component topC){

		Component[] components = board.getComponents();
		int i = 0;
		int index = -1;
		for(Component c: components){
			if(c.equals(topC)){
				index = i;
			}
			i++;
		}
		if(index != -1){
			board.removeAll();		
			Component temp = components[index];
			components[index] = components[0];
			components[0] = temp;

			for (Component comp : components) {
			    board.add(comp);
			}
			board.validate();
		}
		
	}
	private void drawDecks(){
		for(CardStack c: playerPiles){
			c.repaint();
		}
		for(CardStack c: cpuPiles){
			c.repaint();
		}
		mbg.getDiscard().repaint();
		mbg.getDeck().repaint();
	}
}
