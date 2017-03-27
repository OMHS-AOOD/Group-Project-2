package gamefiles;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DraggableCard extends JPanel implements Serializable {
	private Card thisCard;
	private Color grayed;
	private ImageIcon currentImage;
	private JLabel image;
	private String owner;
	private JPanel overlay;
	private int wantedX, wantedY;

	public DraggableCard(Card c, String o, int x, int y) {
		grayed = new Color(100, 100, 100, 200);
		thisCard = c;
		owner = o;
		image = new JLabel();
		overlay = new JPanel();
		this.add(overlay);
		this.add(image);
		
		
		this.setVisible(true);
		this.setLayout(null);
		image.setBounds(0, 0, 100, 153);
		overlay.setBounds(0, 0, 100, 153);
		overlay.setBackground(grayed);
		overlay.setVisible(false);
		currentImage = thisCard.getImage();
		image.setIcon(currentImage);

		wantedX = x;
		wantedY = y;

		this.setBackground(Color.BLACK);
		this.setOpaque(false);
	}

	public DraggableCard(DraggableCard dc) {
		if (dc.getCard() instanceof DistanceCard) {
			thisCard = new DistanceCard(dc.getCard());
		} else if (dc.getCard() instanceof EoLimitCard) {
			thisCard = new EoLimitCard(dc.getCard());
		} else if (dc.getCard() instanceof HazardCard) {
			thisCard = new HazardCard(dc.getCard());
		} else if (dc.getCard() instanceof LimitCard) {
			thisCard = new LimitCard(dc.getCard());
		} else if (dc.getCard() instanceof RemedyCard) {
			thisCard = new RemedyCard(dc.getCard());
		} else if (dc.getCard() instanceof RollCard) {
			thisCard = new RollCard(dc.getCard());
		} else if (dc.getCard() instanceof SafetyCard) {
			thisCard = new SafetyCard(dc.getCard());
		}
		else{
			System.out.println("Error");
			thisCard = null;
		}
		
		owner = dc.getOwner();
		image = new JLabel();
		this.add(image);
		this.setVisible(true);
		this.setLayout(null);
		image.setBounds(0, 0, 100, 153);
		currentImage = thisCard.getImage();

		image.setIcon(currentImage);

		wantedX = dc.getWantedX();
		wantedY = dc.getWantedY();

		this.setBackground(Color.BLACK);
		this.setOpaque(false);
	}

	public void updateImage() {
		currentImage = thisCard.getImage();
		image.setIcon(currentImage);
	}

	public ImageIcon getImage() {
		return currentImage;
	}

	public Card getCard() {
		return thisCard;
	}

	public void flipCard() {
		thisCard.flip();
		updateImage();
	}

	public void setFlip(boolean b) {
		thisCard.setFlipped(b);
		updateImage();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String o) {
		owner = o;
	}

	

	public void updateWanted(int x, int y) {
		wantedX = x;
		wantedY = y;

	}

	public int getWantedX() {
		return wantedX;
	}

	public int getWantedY() {
		return wantedY;
	}

	public void setCard(RemedyCard remedyCard) {
		thisCard = remedyCard;
		this.updateImage();

	}
	public void mark(boolean b){
		if(b){
			overlay.setVisible(true);
		}
		else{
			overlay.setVisible(false);
		}
	}
}
