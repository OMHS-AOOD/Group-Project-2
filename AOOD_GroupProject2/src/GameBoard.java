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
	private DraggableCard currentCardClicked;
	private ArrayList<DraggableCard> cardsOnBoard;
	private int currentCardInt;
	public GameBoard(){
		super("Mille Bornes");
		currentCardInt = -1;
		this.setSize(1200, 675);
		this.setVisible(true);
		this.setLocation(200, 112);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new JPanel();
		this.add(board);
		board.setLayout(null);
		
		cardsOnBoard = new ArrayList<DraggableCard>();
	}

	public void add(JComponent j, int x, int y, int w, int h){
		if(j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals("Player")){
			cardsOnBoard.add((DraggableCard) j);
			cardsOnBoard.get(cardsOnBoard.size()-1).addMouseListener(new CardClick(cardsOnBoard.size()-1));
			cardsOnBoard.get(cardsOnBoard.size()-1).addMouseMotionListener(new CardDrag());
		}
		board.add(j);
		j.setBounds(x, y, w ,h);
	}
	
	public ArrayList<DraggableCard> getUsersHand(){
		return cardsOnBoard;
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
