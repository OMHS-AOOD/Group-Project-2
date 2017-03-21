import java.awt.Color;

public class ComputerPlayer extends HumanPlayer {
	public ComputerPlayer(){
		super("CPU");
		safety.setDrop(false);
		distance.setDrop(false);
		battle.setColor(Color.RED);
		safety.setColor(Color.RED);
		distance.setColor(Color.RED);
	}
	

}
