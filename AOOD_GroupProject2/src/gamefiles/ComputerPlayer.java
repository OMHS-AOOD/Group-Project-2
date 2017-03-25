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
	}
	@Override
	public boolean hasWon(HumanPlayer other) {
		if (other.getCurrentPoints() == 0 && this.getCurrentPoints() > 900) {
			return true;
		}
		if (getCurrentPoints() == maxPointsToWin) {
			return true;
		}
		return false;
	}
}
