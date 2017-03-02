import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JFrame{
	private JPanel board;
	private JLabel test;
	public GameBoard(){
		super("Mille Bornes");
		board = new JPanel();
		this.setSize(1200, 675);
		this.setVisible(true);
		this.setLocation(200, 112);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(board);
		test = new JLabel("FUCK");
		board.setLayout(null);
		board.add(test);
	}

	public void add(JComponent j, int x, int y, int w, int h){
		board.add(j);
		j.setBounds(x, y, w ,h);
	}
}
