import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.GridLayout;

public class GameGrid {

	JPanel frame = new JPanel();
	JButton[][] grid;
	
	public JPanel returnFrame() {
		return frame;
	}
	
	public JButton[][] returnGrid(){
		return grid;
	}

	public GameGrid(int width, int length, int liveRate){ // creates the grid using buttons (since they scale), using a 2D array of the size inputted by the player
		frame.setLayout(new GridLayout(width, length));
		grid = new JButton[width][length];
		for(int y = 0; y < length; y++){
			for(int x = 0; x < width; x++){
				grid[x][y] = new JButton("");
				double rand = Math.random() * 100;
				if(rand <= liveRate) {
					grid[x][y].setBackground(new Color(255, 255, 255));
					grid[x][y].setForeground(new Color(255, 255, 255));
				}else {
					grid[x][y].setBackground(new Color(0, 0, 0));
					grid[x][y].setForeground(new Color(0, 0, 0));
				}
				grid[x][y].setBorderPainted(false);
				grid[x][y].setSize(5, 5);
				frame.add(grid[x][y]);
			}
		}
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setSize(540, 540);
	}
	
//	public static void main(String[] args) {
//		new GameGrid(25, 25);
//	}
	
}
