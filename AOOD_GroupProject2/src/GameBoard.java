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
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class GameBoard extends JFrame{
	private JPanel board;
	private JLabel test;
	private DraggableCard currentCard;
	private ArrayList<DraggableCard> cardsOnBoard;
	private int mouseX, mouseY;
	public GameBoard(){
		super("Mille Bornes");
		
		this.setSize(1200, 675);
		this.setVisible(true);
		this.setLocation(200, 112);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new JPanel();
		this.add(board);
		board.addMouseMotionListener(new BoardMouseDragger());
		board.setLayout(null);
		
		cardsOnBoard = new ArrayList<DraggableCard>();
	}

	public void add(JComponent j, int x, int y, int w, int h){
		if(j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals("Player")){
			cardsOnBoard.add((DraggableCard) j);
			cardsOnBoard.get(cardsOnBoard.size()-1).addMouseMotionListener(new CardDragger());
		}
		board.add(j);
		j.setBounds(x, y, w ,h);
	}
	
	public ArrayList<DraggableCard> getUsersHand(){
		return cardsOnBoard;
	}
	

	
	public boolean isOnCard(DraggableCard dc, MouseEvent e){
		System.out.println(e.getX() + " " + e.getY());
		System.out.println(dc.getX() + " " + dc.getY());
		System.out.println((dc.getX() + dc.getWidth()) + " " + (dc.getY()+ dc.getHeight()));
		System.out.println("=============================");
		if((e.getX() > dc.getX() && e.getX() < dc.getX() + dc.getWidth()) && (e.getY() > dc.getY() && e.getY() < dc.getY() + dc.getHeight())){
			System.out.println("asdasdsad");
			return true;
		}
		return false;
		
		
	}

	
	private class CardDragger extends MouseMotionAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println(currentCard);
			currentCard.setBounds(e.getX() - (currentCard.getWidth()/2), e.getY() - (currentCard.getHeight()/2), currentCard.getWidth(), currentCard.getHeight());
		}
		

	}
	
	
	private class BoardMouseDragger extends MouseMotionAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			boolean check = true;
			for(DraggableCard dc: cardsOnBoard){
				if(isOnCard(dc, e)){
					currentCard = dc;
					check = false;
				}
			}
			if(check){
				currentCard = null;
			}
		}
		

	}

}
