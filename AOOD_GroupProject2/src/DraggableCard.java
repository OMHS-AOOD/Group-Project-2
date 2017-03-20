import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DraggableCard extends JPanel{
	private Card thisCard;
	private ImageIcon currentImage;
	private JLabel image;
	private String owner;
	private boolean draggable;
	private int wantedX, wantedY;
	public DraggableCard(Card c, String o, int x, int y){
		thisCard = c;
		owner = o;
		
		image = new JLabel();
		this.add(image);
		
		currentImage = c.getImage();
		image.setIcon(currentImage);
		 
		draggable = true;
		wantedX = x;
		wantedY = y;
	}
	public void updateImage(){
		currentImage = thisCard.getImage();
		image.setIcon(currentImage);
	}
	public ImageIcon getImage(){
		return currentImage;
	}
	
	public Card getCard(){
		return thisCard;
	}
	
	public void flipCard(){
		thisCard.flip();
		updateImage();
	}
	public String getOwner(){
		return owner;
	}
	public void setOwner(String o){
		owner = o;
	}
	public boolean isDraggable(){
		return draggable;
	}
	public void setDraggable(boolean b){
		draggable = b;
	}
	public void updateWanted(int x, int y){
		wantedX = x;
		wantedY = y;
		
	}
	public int getWantedX(){
		return wantedX;
	}
	public int getWantedY(){
		return wantedY;
	}
}
