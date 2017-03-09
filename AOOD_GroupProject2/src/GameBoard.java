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
	private ArrayList<CardStack> playerPiles, cpuPiles;
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
		if(j instanceof CardStack && ((CardStack) j).getOwner().equals("Player")){
			
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
			int targetX = (currentCardInt * 120) + 10;
			int targetY = 475;
			int width = currentCardClicked.getWidth();
			int height = currentCardClicked.getHeight();
			double multiplier = .1;
			/*
			if(!validSpace){
				while(currentCardClicked.getX() != targetX || currentCardClicked.getY() != targetY){
					int distX = currentCardClicked.getX() - (targetX);
					int distY = currentCardClicked.getY() - (targetY);

					if(distX < 10 && distX > 0){
						distX = 10;
					}
					else if(distX > -10 && distX < 0){
						distX = -10;
					}
					if(distY < 10 && distY > 0){
						distY = 10;
					}
					else if(distY > -10 && distY < 0){
						distY = -10;
					}
					int newPosX = (int)(currentCardClicked.getX() - distX*multiplier);
					int newPosY = (int)(currentCardClicked.getY() - distY*multiplier);
					System.out.println(newPosX + " " + newPosY);
					currentCardClicked.setBounds(newPosX, newPosY, width, height);
					
					if(Math.abs(distX) ==1){
						currentCardClicked.setBounds(targetX , currentCardClicked.getY(), 100, 153);
					}
					if(Math.abs(distY) ==1){
						currentCardClicked.setBounds(currentCardClicked.getX(), targetY, 100, 153);
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
				}
			}
			*/
			if(!validSpace){
				currentCardClicked.setBounds(targetX, targetY, width, height);

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
