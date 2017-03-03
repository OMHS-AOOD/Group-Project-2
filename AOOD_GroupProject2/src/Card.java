import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Card{
	protected ImageIcon front, back;
	protected boolean flipped;
	protected String name;
	public Card(String n){
		name = n;
		flipped = false;
		URL location = MilleBornesGame.class.getProtectionDomain().getCodeSource().getLocation();
		File f = new File(location.getPath().substring(0, location.getPath().length() - 4) + "src/" +name +".png");
		File f2 = new File(location.getPath().substring(0, location.getPath().length() - 4) + "src/Back.png");
		front = new ImageIcon(f.getAbsolutePath());
		back = new ImageIcon(f.getAbsolutePath());

	}
	public String getName(){
		return name;
	}
	public void setFlipped(boolean b){
		flipped = b;
	}
	public void flip(){
		flipped = !flipped;
	}
	
	public ImageIcon getImage(){
		if(flipped){
			return back;
		}
		return front;
	}
}
