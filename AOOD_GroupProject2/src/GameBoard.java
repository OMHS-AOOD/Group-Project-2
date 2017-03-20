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
	private ArrayList<DraggableCard> cardsOnBoard;
	private ArrayList<CardStack> playerPiles, cpuPiles;
	private int currentCardInt;
	private MilleBornesGame mbg;
	public GameBoard(MilleBornesGame m){
		super("Mille Bornes");
		mbg = m;
		currentCardInt = -1;
		this.setSize(1200, 675);
		this.setVisible(true);
		this.setLocation(200, 112);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new JPanel();
		this.add(board);
		board.setLayout(null);
		
		cardsOnBoard = new ArrayList<DraggableCard>();
		playerPiles = new ArrayList<CardStack>();
	}

	public void add(JComponent j, int x, int y, int w, int h){
		if(j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals("Player")){
			cardsOnBoard.add((DraggableCard) j);
			cardsOnBoard.get(cardsOnBoard.size()-1).addMouseMotionListener(new CardDrag());
			reorganizeCardGraphics();
		}
		if(j instanceof CardStack && ((CardStack) j).getOwner().equals("Player")){
			playerPiles.add((CardStack) j);
		}
		if(j instanceof CardStack && ((CardStack) j).getOwner().equals("CPU")){
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
		if(maxTouch > 10000){
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
		}
		else{
			returnToOriginalPos();
		}
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
	
	
	
	


}
