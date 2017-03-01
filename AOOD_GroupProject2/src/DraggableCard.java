import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class DraggableCard extends JComponent{
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

	}
	
}
