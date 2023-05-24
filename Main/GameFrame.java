package Main;

import javax.swing.*;

public class GameFrame extends JFrame {
    public static final int SCREEN_WIDTH = 1920, SCREEN_HEIGHT = 1080;
    public static final int SCREEN_WIDTH_CENTER = SCREEN_WIDTH / 2;
    public static final int SCREEN_HEIGHT_CENTER = SCREEN_HEIGHT / 2;

    public static GamePanel gamePanel;
    public static GameMenu menuPanel;

    GameFrame() {
        gamePanel = new GamePanel();
        menuPanel = new GameMenu();

        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops program when programe is closed
        this.setUndecorated(true); // Remove the top bar of the program

        this.add(menuPanel);
        this.pack();

        this.setResizable(false); // Prevent screen from being resized
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        this.setVisible(true); // Make the program visible
    }
}
