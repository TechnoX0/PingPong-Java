package Main;

import javax.swing.*;
import java.awt.*;
import static Main.GameFrame.*;
import static Main.Main.gameFrame;

public class GameMenu extends JPanel {
    JButton startButton;

    GameMenu() {
        // Set panel dimension and background color
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x212529));

        startButton = new JButton("Start Game");

        startButton.setBounds(SCREEN_WIDTH_CENTER - 100, SCREEN_HEIGHT_CENTER - 25, 200, 50);
        startButton.addActionListener(e -> showGameScreen());
        startButton.setFocusPainted(false);
        startButton.setFocusable(false);

        this.setLayout(null);
        this.add(startButton);
    }

    private void showGameScreen() {
        gameFrame.remove(this);
        gameFrame.add(gamePanel);
        gameFrame.revalidate();
        gameFrame.repaint();

        gamePanel.focusPanel();
    }
}
