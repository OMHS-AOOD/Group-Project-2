package gamefiles;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean flipped;
	protected String name;
	protected boolean wasCF;
	protected int internalVal;

	public Card(String n, int iV) {
		name = n;
		flipped = false;
		wasCF = false;
		internalVal = iV;
	}

	public String getName() {
		return name;
	}

	public void setFlipped(boolean b) {
		flipped = b;
	}

	public void flip() {
		flipped = !flipped;
	}

	public ImageIcon getImage() {
		if (flipped) {
			return new ImageIcon(this.getClass().getResource("Back.jpg"));
		}

		return new ImageIcon(this.getClass().getResource(name + ".png"));
	}
	public void beCF(){
		wasCF = true;
	}
	public boolean getCF(){
		return wasCF;
	}
	public int getInternalVal(){
		return internalVal;
	}

}
