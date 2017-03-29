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


	
	
	public int getLowestValuedCardIndex(){
		int lowestVal = 101;
		int index = 0;
		int cIndex = 0;
		for(DraggableCard dc: this.visibleCards){
			if(dc.getCard().getInternalVal() < lowestVal){
				index = cIndex;
				lowestVal = dc.getCard().getInternalVal();
			}
			cIndex++;
		}
		return index;
	}
}
