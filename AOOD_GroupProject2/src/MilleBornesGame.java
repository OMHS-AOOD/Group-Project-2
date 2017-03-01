
public class MilleBornesGame {
	private Deck deck;
	private CardStack discard;
	private Player player;
	private ComputerPlayer cpu;
	private GameBoard gb;
	public MilleBornesGame(){
		deck = new Deck();
		discard = new CardStack();
		player = new Player("Jonah");
		cpu = new ComputerPlayer();
		gb = new GameBoard();
		for(int i = 0; i < 6; i++){
			player.addCardToHand(deck.removeCard());
			cpu.addCardToHand(deck.removeCard());
		}
		gb.add(new DraggableCard(player.getCard(0)), 0, 0, 85, 110);
		
	}
	
}
