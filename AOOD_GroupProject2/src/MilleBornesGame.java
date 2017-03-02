import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MilleBornesGame {
	private Deck deck;
	private CardStack discard;
	private HumanPlayer player;
	private ComputerPlayer cpu;
	private GameBoard gb;
	public MilleBornesGame(){
		deck = new Deck();
		discard = new CardStack();
		String name = JOptionPane.showInputDialog("Enter a username");
		if(name == null){
			player = new HumanPlayer("Player");
		}
		else{
			player = new HumanPlayer(name);
		}
		cpu = new ComputerPlayer();
		gb = new GameBoard();
		for(int i = 0; i < 6; i++){
			player.addCardToHand(deck.removeCard());
			cpu.addCardToHand(deck.removeCard());
		}
		System.out.println(player.handToString() + "\n");
		System.out.println(cpu.handToString());
		gb.add(new DraggableCard(player.getCard(0)), 0, 0, 85, 110);
		
	}
	
}
