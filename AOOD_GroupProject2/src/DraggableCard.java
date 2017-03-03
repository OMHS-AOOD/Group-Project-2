import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DraggableCard extends JPanel{
	private Card thisCard;
	private ImageIcon currentImage;
	private JLabel image;
	public DraggableCard(Card c){
		thisCard = c;
		
		image = new JLabel();
		this.add(image);
		
		currentImage = c.getImage();
		image.setIcon(currentImage);
		 
		
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
	
}
