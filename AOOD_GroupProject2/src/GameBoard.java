import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class GameBoard extends JFrame{
	private JPanel board;
	private DraggableCard currentCardClicked;
	private ArrayList<DraggableCard> cardsOnBoard, cpuCardsOnBoard;
	private ArrayList<CardStack> playerPiles, cpuPiles;
	private int currentCardInt;
	private MilleBornesGame mbg;
	private HumanPlayer player;
	private ComputerPlayer cpu;
	public GameBoard(MilleBornesGame m, HumanPlayer p, ComputerPlayer c){
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
		cardsOnBoard = new ArrayList<DraggableCard>();
		playerPiles = new ArrayList<CardStack>();
		cpuPiles = new ArrayList<CardStack>();
		cpuCardsOnBoard = new ArrayList<DraggableCard>();

	}

	public void add(JComponent j, int x, int y, int w, int h){
		if(j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals(player.getName())){
			cardsOnBoard.add((DraggableCard) j);
			cardsOnBoard.get(cardsOnBoard.size()-1).addMouseMotionListener(new CardDrag());
			reorganizeCardGraphics();
		}
		if(j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals(cpu.getName())){
			cpuCardsOnBoard.add((DraggableCard) j);
			cpuCardsOnBoard.get(cardsOnBoard.size()-1).addMouseMotionListener(new CardDrag());
			reorganizeCpuCardGraphics();
		}
		if(j instanceof CardStack && ((CardStack) j).getOwner().equals(player.getName())){
			playerPiles.add((CardStack) j);
		}
		if(j instanceof CardStack && ((CardStack) j).getOwner().equals(cpu.getName())){
			cpuPiles.add((CardStack) j);
		}
		board.add(j);
		j.setBounds(x, y, w ,h);
	}
	
	public void returnToOriginalPos(){
		int targetX = currentCardClicked.getWantedX();
		int targetY = currentCardClicked.getWantedY();
		int width = currentCardClicked.getWidth();
		int height = currentCardClicked.getHeight();
		currentCardClicked.setBounds(targetX, targetY, width, height);

	}
	public ArrayList<DraggableCard> getUsersHand(){
		return cardsOnBoard;
	}
	
	public void reorganizeCardGraphics(){
		for(int i = 0; i < cardsOnBoard.size(); i++){
			DraggableCard dc = cardsOnBoard.get(i);
			if(dc.getMouseListeners().length != 0){
				dc.removeMouseListener(dc.getMouseListeners()[0]);
			}
			dc.addMouseListener(new CardClick(i));
			dc.setBounds((i * 120) + 10 , 475, 100, 153);
		}
	}
	public void reorganizeCpuCardGraphics(){
		for(int i = 0; i < cpuCardsOnBoard.size(); i++){
			DraggableCard dc = cpuCardsOnBoard.get(i);
			dc.setBounds((i * 120) + 10 , 10, 100, 153);
		}
	}

	private class CardClick extends MouseAdapter{
		private int cardInt;
		public CardClick(int cI){
			cardInt = cI;
		}
		@Override
		public void mousePressed(MouseEvent e){
			currentCardInt = cardInt;
		}
		
		
		
		@Override
		public void mouseReleased(MouseEvent e){
			decideStack();
			currentCardInt = -1;
			currentCardClicked = null;
		}
		
	}
	public void decideStack(){
		int xPos = currentCardClicked.getX();
		int yPos = currentCardClicked.getY();
		int width = currentCardClicked.getWidth();
		int height = currentCardClicked.getHeight();
		double maxTouch = 0;
		int maxTouchIndex = 0;
		int index = 0;
		for(CardStack c: playerPiles){
			Rectangle computedIntersection = SwingUtilities.computeIntersection(xPos, yPos, width, height, c.getBounds());
			double area = computedIntersection.getWidth() * computedIntersection.getHeight();
			if(area > maxTouch){
				maxTouchIndex = index;
				maxTouch = area;
			}
			index++;
		}
		CardStack target = playerPiles.get(maxTouchIndex);
		if(maxTouch > 10000 && target.canDropCardOn() && isValidMove(target)){
			int targetX = target.getX() + 10;
			int targetY = target.getY() + 10;
			int cwidth = currentCardClicked.getWidth();
			int cheight = currentCardClicked.getHeight();
			currentCardClicked.setBounds(targetX, targetY, cwidth, cheight);
			target.addCard(currentCardClicked);
			cardsOnBoard.remove(currentCardInt);
			this.reorganizeCardGraphics();
			currentCardClicked.removeMouseMotionListener(currentCardClicked.getMouseMotionListeners()[0]);
			currentCardClicked.removeMouseListener(currentCardClicked.getMouseListeners()[0]);
			this.cpuTurn();
		}
		else{
			returnToOriginalPos();
		}
	}
	private boolean isValidMove(CardStack c) {
		if(c.getName().equals("Distance") && currentCardClicked.getCard() instanceof DistanceCard){
			return true;
		}
		else if(c.getName().equals("Battle") && c.getOwner().equals(cpu.getName()) && currentCardClicked.getCard() instanceof HazardCard){
			return true;
		}
		else if(c.getName().equals("Battle") && c.getOwner().equals(player.getName()) && currentCardClicked.getCard() instanceof RemedyCard){
			return true;
		}
		else if(c.getName().equals("Safety") && currentCardClicked.getCard() instanceof SafetyCard){
			return true;
		}
		return false;
	}

	private class CardDrag extends MouseMotionAdapter{
		private int mouseX, mouseY;
		@Override
		public void mouseDragged(MouseEvent e){
			if(currentCardInt != -1){
				currentCardClicked = cardsOnBoard.get(currentCardInt);
				mouseX = currentCardClicked.getX() + e.getX();
				mouseY = currentCardClicked.getY() + e.getY();
				currentCardClicked.setBounds(mouseX - currentCardClicked.getWidth()/2, mouseY - currentCardClicked.getHeight()/2, currentCardClicked.getWidth(), currentCardClicked.getHeight());
			}
			
		}
	}
	
	public void cpuTurn() {

		if(player.hasWon()){
			mbg.gameWon();
		}
		else{
			if(cpu.canMoveNormally()){
				ArrayList<DraggableCard> allDists = new ArrayList<DraggableCard>();
				ArrayList<DraggableCard> allHazards = new ArrayList<DraggableCard>();
				ArrayList<DraggableCard> allSafes = new ArrayList<DraggableCard>();
				ArrayList<DraggableCard> allRemedy = new ArrayList<DraggableCard>();
				
				ArrayList<Integer> dInts = new ArrayList<Integer>();
				ArrayList<Integer> hInts = new ArrayList<Integer>();
				ArrayList<Integer> sInts = new ArrayList<Integer>();
				ArrayList<Integer> rInts = new ArrayList<Integer>();
				for(int i = 0; i < cpuCardsOnBoard.size(); i++){
					Card c = cpuCardsOnBoard.get(i).getCard();
					if(c instanceof DistanceCard){
						allDists.add(cpuCardsOnBoard.get(i));
						dInts.add(i);
					}
					if(c instanceof SafetyCard){
						allSafes.add(cpuCardsOnBoard.get(i));
						sInts.add(i);
					}
					if(c instanceof HazardCard){
						allHazards.add(cpuCardsOnBoard.get(i));
						hInts.add(i);
					}
					if(c instanceof RemedyCard){
						allRemedy.add(cpuCardsOnBoard.get(i));
						rInts.add(i);
					}
				}
				if(allHazards.size() > 0){
					CardStack target = player.getBattle();
					DraggableCard cpuCard = allHazards.get(0);
					int targetX = target.getX() + 10;
					int targetY = target.getY() + 10;
					int cwidth = cpuCard.getWidth();
					int cheight = cpuCard.getHeight();
					cpuCard.setBounds(targetX, targetY, cwidth, cheight);
					cpuCard.flipCard();
					target.addCard(cpuCard);
					cpuCardsOnBoard.remove(hInts.remove(0).intValue());
					this.reorganizeCpuCardGraphics();
				}
				
			}
			else{
				
			}
		}
		
	}
	
	


}
