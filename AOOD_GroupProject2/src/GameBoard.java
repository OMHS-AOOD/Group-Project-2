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
	private boolean validSpace;
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
		validSpace = false;
	}

	public void add(JComponent j, int x, int y, int w, int h){
		if(j instanceof DraggableCard && ((DraggableCard) j).getOwner().equals("Player")){
			cardsOnBoard.add((DraggableCard) j);
			cardsOnBoard.get(cardsOnBoard.size()-1).addMouseMotionListener(new CardDrag());
			for(int i = 0; i < cardsOnBoard.size(); i++){
				DraggableCard dc = cardsOnBoard.get(i);
				if(dc.getMouseListeners().length != 0){
					dc.removeMouseListener(dc.getMouseListeners()[0]);
				}
				dc.addMouseListener(new CardClick(i));
				dc.setBounds((i * 120) + 10 , 475, 100, 153);
			}

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
		
		@Override
		public void mouseReleased(MouseEvent e){
   
			if(!validSpace){
				while(currentCardClicked.getX() != (currentCardInt * 120) + 10 && currentCardClicked.getY() != 775){
					
					int distX = currentCardClicked.getX() - ((currentCardInt * 120) + 10);
					int distY = currentCardClicked.getY() - 475;
					System.out.println(currentCardClicked.getX() + "-" + currentCardClicked.getY());
					System.out.println(distX + " " + distY);
					System.out.println((int)(currentCardClicked.getX() - distX*.99) + " " + (int)(currentCardClicked.getY() - distY*.99));
					currentCardClicked.setBounds((int)(currentCardClicked.getX() - distX*.99) , (int)(currentCardClicked.getY() - distY*.99), 100, 153);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					if(Math.abs(distX) ==1){
						currentCardClicked.setBounds((currentCardInt * 120) + 10 , currentCardClicked.getY(), 100, 153);
					}
					if(Math.abs(distY) ==1){
						currentCardClicked.setBounds(currentCardClicked.getX(), 475, 100, 153);

					}

				}
			}
			currentCardInt = -1;
			currentCardClicked = null;
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
