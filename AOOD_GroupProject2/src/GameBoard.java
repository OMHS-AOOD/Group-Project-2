import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JFrame{
	private JPanel board;
	private JLabel test;
	private ArrayList<DraggableCard> cardsOnBoard;
	public GameBoard(){
		super("Mille Bornes");
		
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
		if(j instanceof DraggableCard){
			cardsOnBoard.add((DraggableCard) j);
		}
		board.add(j);
		j.setBounds(x, y, w ,h);
	}
	
	public ArrayList<DraggableCard> getUsersHand(){
		return cardsOnBoard;
	}
}
