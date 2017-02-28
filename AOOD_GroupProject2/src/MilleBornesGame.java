
public class MilleBornesGame {
	private Deck deck;
	private CardStack discard;
	private Player player;
	private ComputerPlayer cpu;
	public MilleBornesGame(){
		deck = new Deck();
		discard = new CardStack();
		player = new Player("Jonah");
		cpu = new ComputerPlayer();
		for(int i = 0; i < 6; i++){
			player.addCardToHand(deck.removeCard());
			cpu.addCardToHand(deck.removeCard());
		}
		System.out.println();
		
	}
	
}
