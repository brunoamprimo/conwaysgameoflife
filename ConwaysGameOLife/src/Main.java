///////////////////////////////////

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.imageio.ImageIO;

// Conways Game Of Life (main)

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static Main mg;
	private static BufferedImage iconimg;
	
	private boolean gameStart = false;
	private int gameSize = 5;
	private int liveRate = 50;
	
	//
	
	private JPanel gridFrame;
	private JButton[][] gridButton;
	private GameGrid gg;
	
	private boolean isRunning = false;
	private JButton runOnceButton;
	private JButton runButton;
	
	private int cycleTick = 0;
	
	//
	
	private JPanel gamePanel;
	private JLabel gameTitle;
	private JButton playButton;
	
	private JLabel sizeST;
	private JTextField sizeSetting;
	
	private JLabel liveST;
	private JTextField liveSetting;
	
	private JLabel tickText;
	
	//
	
	public void sleep(int ms) { // intended to pause the program
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gameOfLife() { // the method to run the game of life a single time
		cycleTick++;
		tickText.setText("Tick: " + cycleTick);
		ArrayList<JButton> toLive = new ArrayList<JButton>();
		ArrayList<JButton> toDie = new ArrayList<JButton>();
		for(int r = 0; r < gridButton.length; r++) {
			for(int c = 0; c < gridButton[0].length; c++) {
				int aliveNeighbors = 0;
				boolean isAlive = false;
				if(gridButton[r][c].getBackground().equals(new Color(255, 255, 255))) {
					isAlive = true;
				}
				if(r - 1 >= 0) {
					if(gridButton[r - 1][c].getBackground().equals(new Color(255, 255, 255))){
						aliveNeighbors++;
					}
					if(c - 1 >= 0) {
						if(gridButton[r - 1][c - 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
					if(c + 1 < gridButton[0].length) {
						if(gridButton[r - 1][c + 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
				}
				if(r + 1 < gridButton.length) {
					if(gridButton[r + 1][c].getBackground().equals(new Color(255, 255, 255))){
						aliveNeighbors++;
					}
					if(c - 1 >= 0) {
						if(gridButton[r + 1][c - 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
					if(c + 1 < gridButton[0].length) {
						if(gridButton[r + 1][c + 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
				}
				if(c - 1 >= 0) {
					if(gridButton[r][c - 1].getBackground().equals(new Color(255, 255, 255))){
						aliveNeighbors++;
					}
					if(r - 1 >= 0) {
						if(gridButton[r - 1][c - 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
					if(r + 1 < gridButton.length) {
						if(gridButton[r + 1][c - 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
				}
				if(c + 1 < gridButton[0].length) {
					if(gridButton[r][c + 1].getBackground().equals(new Color(255, 255, 255))){
						aliveNeighbors++;
					}
					if(r - 1 >= 0) {
						if(gridButton[r - 1][c + 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
					if(r + 1 < gridButton.length) {
						if(gridButton[r + 1][c + 1].getBackground().equals(new Color(255, 255, 255))){
							aliveNeighbors++;
						}
					}
				}
				if(isAlive && (aliveNeighbors < 2 || aliveNeighbors > 3)) {
					if(gridButton[r][c].getBackground() != new Color(0, 0, 0)) {
						toDie.add(gridButton[r][c]);
					}
				}else if(aliveNeighbors == 3) {
					if(gridButton[r][c].getBackground() != new Color(255, 255, 255)) {
						toLive.add(gridButton[r][c]);
					}
				}
			}
		}
		for(int b = 0; b < toLive.size(); b++) {
			toLive.get(b).setBackground(new Color(255, 255, 255));
		}
		for(int b = 0; b < toDie.size(); b++) {
			toDie.get(b).setBackground(new Color(0, 0, 0));
		}
	}
	
	public void runLoop() { // loop the method so that the game of life continues on...
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(isRunning) {
					gameOfLife();
					sleep(50);
				}
			}
		}).start();
	}
	
	public void toggleLoop() { // toggles loop
		isRunning = !isRunning;
		if(isRunning) {
			runLoop();
		}
	}
	
	//
	
	public void startGame() { // sets up the game UI
		gameStart = true;
		//
		gameTitle.setVisible(false);
		playButton.setVisible(false);
		sizeST.setVisible(false);
		sizeSetting.setVisible(false);
		liveST.setVisible(false);
		liveSetting.setVisible(false);
		//
		gg = new GameGrid(gameSize, gameSize, liveRate);
		gridFrame = gg.returnFrame();
		gridButton = gg.returnGrid();
		//
		runOnceButton.setVisible(true);
		runButton.setVisible(true);
		tickText.setVisible(true);
		//
		gridFrame.setBounds(540/2, 25, 540, 540);
		gridFrame.setBackground(new Color(0, 0, 0));
		gridFrame.setVisible(true); gamePanel.add(gridFrame);
	}
	
	public void initgame() { // initalizes all the game buttons, including to play it and to run the program once/in a loop.
		final JButton[] buttonList = {playButton, runOnceButton, runButton};
		for(int i = 0; i < buttonList.length; i++) {
			final int ic = i + 1;
			buttonList[i].addActionListener(new ActionListener() {
				// When button is pressed
		    	public void actionPerformed(ActionEvent e) {
		    		if(ic == 1 && !gameStart) { // playButton
		    			int intUse = 5;
		    			int intUseLive = 50;
		    			boolean pass = true;
		    			try {
		    				intUse = Integer.parseInt(sizeSetting.getText());
		    				intUseLive = Integer.parseInt(liveSetting.getText());
		    			}
		    			catch (Exception b) {
		    				pass = false;
		    			}
		    			if(pass && intUse >= 5 && intUse <= 500 && intUseLive >= 1 && intUseLive <= 80) {
		    				gameSize = intUse;
		    				liveRate = intUseLive;
		    				startGame();
		    			}
		    		}else if(ic == 2 && gameStart) {
		    			gameOfLife();
		    		}else if(ic == 3 && gameStart) {
		    			toggleLoop();
		    		}
		    	}
		    	//
		    });
		}
	}
	
	public void setup() { // sets up the starting UI
		// Initalize window
		setTitle("Conway's Game of Life");
		setResizable(false);
		setIconImage(iconimg);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// Initalize panel
		gamePanel = new JPanel();
		gamePanel.setSize(1080, 720);
		gamePanel.setBounds(0, 0, 1080, 720);
	    gamePanel.setLayout(null);
	    gamePanel.setBackground(Color.black);
	    // Initalize Text/Buttons
	    //
		gameTitle = new JLabel("Conway's Game Of Life");
		gameTitle.setBounds(-10, 100, 1080, 60);
		gameTitle.setFont(new Font("Arial", Font.BOLD, 50));
		gameTitle.setVerticalAlignment(SwingConstants.TOP);
		gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gameTitle.setForeground(new Color(255, 255, 255));
		gameTitle.setVisible(true); gamePanel.add(gameTitle);
		//
		JLabel gameCredit = new JLabel("Made by Bruno Amprimo");
		gameCredit.setBounds(750, 640, 300, 20);
		gameCredit.setFont(new Font("Arial", Font.BOLD, 15));
		gameCredit.setForeground(new Color(255, 255, 255));
		gameCredit.setVerticalAlignment(SwingConstants.TOP);
		gameCredit.setHorizontalAlignment(SwingConstants.RIGHT);
		gameCredit.setVisible(true); gamePanel.add(gameCredit);
		//
		sizeST = new JLabel("Adjust size (min 5, max 500)");
		sizeST.setBounds((1080/2)-110, 230, 200, 30);
		sizeST.setFont(new Font("Arial", Font.BOLD, 15));
		sizeST.setVerticalAlignment(SwingConstants.TOP);
		sizeST.setHorizontalAlignment(SwingConstants.CENTER);
		sizeST.setForeground(new Color(255, 255, 255));
		sizeST.setVisible(true); gamePanel.add(sizeST);
		//
		sizeSetting = new JTextField("5");
		sizeSetting.setBounds((1080/2)-110, 250, 200, 30);
		sizeSetting.setHorizontalAlignment(SwingConstants.CENTER);
		sizeSetting.setFont(new Font("Arial", Font.BOLD, 25));
		sizeSetting.setVisible(true); gamePanel.add(sizeSetting);
		//
		liveST = new JLabel("Adjust alive rate by % (min 1, max 80)");
		liveST.setBounds((1080/2)-160, 330, 300, 30);
		liveST.setFont(new Font("Arial", Font.BOLD, 15));
		liveST.setVerticalAlignment(SwingConstants.TOP);
		liveST.setHorizontalAlignment(SwingConstants.CENTER);
		liveST.setForeground(new Color(255, 255, 255));
		liveST.setVisible(true); gamePanel.add(liveST);
		//
		liveSetting = new JTextField("50");
		liveSetting.setBounds((1080/2)-110, 350, 200, 30);
		liveSetting.setHorizontalAlignment(SwingConstants.CENTER);
		liveSetting.setFont(new Font("Arial", Font.BOLD, 25));
		liveSetting.setVisible(true); gamePanel.add(liveSetting);
		//
		playButton = new JButton("Start");
		playButton.setBounds((1080/2)-110, 450, 200, 30);
		playButton.setFont(new Font("Arial", Font.BOLD, 30));
		playButton.setForeground(new Color(255, 255, 255));
		playButton.setBackground(new Color(0, 0, 0));
		playButton.setVisible(true); gamePanel.add(playButton);
		//
		runOnceButton = new JButton("Run Once");
		runOnceButton.setBounds((1080/2)+40, 600, 200, 30);
		runOnceButton.setFont(new Font("Arial", Font.BOLD, 30));
		runOnceButton.setForeground(new Color(255, 255, 255));
		runOnceButton.setBackground(new Color(0, 0, 0));
		runOnceButton.setVisible(false); gamePanel.add(runOnceButton);
		//
		runButton = new JButton("Run");
		runButton.setBounds((1080/2)-260, 600, 200, 30);
		runButton.setFont(new Font("Arial", Font.BOLD, 30));
		runButton.setForeground(new Color(255, 255, 255));
		runButton.setBackground(new Color(0, 0, 0));
		runButton.setVisible(false); gamePanel.add(runButton);
		//
		tickText = new JLabel("Tick: 0");
		tickText.setBounds(25, 330, 300, 30);
		tickText.setFont(new Font("Arial", Font.BOLD, 15));
		tickText.setVerticalAlignment(SwingConstants.TOP);
		tickText.setHorizontalAlignment(SwingConstants.CENTER);
		tickText.setForeground(new Color(255, 255, 255));
		tickText.setVisible(false); gamePanel.add(tickText);
		//
		gamePanel.setVisible(true);
		// Finalize panel
		add(gamePanel, BorderLayout.CENTER);
	    setSize(1080, 720);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    initgame();
	}
	
	public Main() { // constructor
		setup();
	}
	
	public static void main(String [] args) { // necessary to start
//		try {
//			iconimg = ImageIO.read(Main.class.getResourceAsStream("/cgol.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		mg = new Main();
	}

}
