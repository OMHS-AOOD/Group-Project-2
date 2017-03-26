package gamefiles;
import java.awt.Color;
import java.io.Serializable;

public class ComputerPlayer extends HumanPlayer implements Serializable {
	public ComputerPlayer(){
		super("CPU");
		safety.setDrop(false);
		distance.setDrop(false);
		battle.setColor(Color.RED);
		safety.setColor(Color.RED);
		distance.setColor(Color.RED);
		limit.setColor(Color.red);
	}

}
