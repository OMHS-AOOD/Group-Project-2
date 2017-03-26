package gamefiles;
import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Card implements Serializable{

	protected boolean flipped;
	protected String name;
	protected boolean wasCF;
	public Card(String n) {
		name = n;
		flipped = false;
		wasCF = false;

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
			return new ImageIcon(getClass().getResource("Back.jpg"));
		}
		return new ImageIcon(getClass().getResource(name + ".png"));
	}
	public void beCF(){
		wasCF = true;
	}
	public boolean getCF(){
		return wasCF;
	}
}
