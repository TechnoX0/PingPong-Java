package Main;

import Main.GameObjects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Main.GameFrame.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    boolean gameStart = false;
    public static Graphics2D g2D;

    Timer timer;

    public static Ball ball = new Ball(30);
    public static Paddle playerOne = new Paddle(20, 120, "Player one");
    public static Paddle playerTwo = new Paddle(20, 120, "Player two");

    GamePanel() {
        // Set panel dimension and background color
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x212529));

        // Set player starting position
        playerOne.setX(50);
        playerTwo.setX(1870);

        // Add key listener
        this.addKeyListener(this);
        focusPanel();

        // This sets the delay on when the game update
        timer = new Timer(16, this);
        timer.start();
    }

    public void focusPanel() {
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void paint(Graphics g) {
        super.paint(g); // Paint background
        g2D = (Graphics2D) g;

        g2D.setPaint(Color.white); // Set player, ball, and text color

        // Draw the center line
        drawCenterLine();

        // Draw Score
        g2D.setFont(new Font("Mono", Font.BOLD, 60));
        drawString(String.valueOf(playerOne.getScore()), "center", SCREEN_WIDTH_CENTER - (SCREEN_WIDTH_CENTER / 2), 120);
        drawString(String.valueOf(playerTwo.getScore()), "center", SCREEN_WIDTH_CENTER + (SCREEN_WIDTH_CENTER / 2), 120);

        // Draw players
        drawPlayer(playerOne); // Player one
        drawPlayer(playerTwo); // Player two

        // Draw the ball
        drawBall(ball);

        // Ball prediction
        g2D.setPaint(Color.RED);
        g2D.fillRect(ball.ray.exactX, ball.ray.prediction, 5, 5);

        g2D.setPaint(Color.GREEN);
        g2D.fillRect(ball.ray.exactX, ball.ray.exactY, 5, 5);

        if (!gameStart) {
            drawString("Pres SPACE to start!", "center", SCREEN_WIDTH_CENTER, SCREEN_HEIGHT_CENTER);
        }
    }

    public void drawPlayer(Paddle player) {
        g2D.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    public void drawBall(Ball ball) {
        g2D.fillOval(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
    }

    public void drawString(String str, String positioning, int posX, int posY) {
        int stringLen = (int) g2D.getFontMetrics().getStringBounds(str, g2D).getWidth();
        int newPos;

        positioning = positioning.toLowerCase();

        switch (positioning) {
            case "center" -> newPos = posX - stringLen / 2;
            case "left" -> newPos = posX + stringLen;
            default -> newPos = posX;
        }

        g2D.drawString(str, newPos, posY);
    }

    public void drawCenterLine() {
        g2D.setStroke(new BasicStroke(3)); // Line thickness

        int y = 0;
        int lineLen = 50, lineGap = 30;

        while (y < SCREEN_HEIGHT) {
            int dy = y + lineLen;
            g2D.drawLine(SCREEN_WIDTH_CENTER, y, SCREEN_WIDTH_CENTER, dy); // Draw the line
            y += lineLen + lineGap;
        }
    }

    public void startGame() {
        if (!gameStart) {
            gameStart = !gameStart;
            System.out.println("START");
        } else {
            gameStart = !gameStart;
            System.out.println("STOP");
        }
    }

    public void reset() {
        // Return players to center
        playerOne.setY((SCREEN_HEIGHT / 2) - playerOne.getHeight());
        playerTwo.setY((SCREEN_HEIGHT / 2) - playerTwo.getHeight());

        // Reset score
        playerOne.resetScore();
        playerTwo.resetScore();

        // Return ball to center
        ball.setPositionCenter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameStart) {
            return;
        }

        ball.move(); // Updates ball position
        ball.performChecks();

        // Player movement
        playerOne.movement();
        playerTwo.movement();

        if (playerOne.collided(ball)) {
            ball.setX(playerOne.getX() + playerOne.getWidth());
            ball.bounce(playerOne);
        } else if (playerTwo.collided(ball)) {
            ball.setX(playerTwo.getX() - ball.getSize());
            ball.bounce(playerTwo);
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("Key pressed " + e.getKeyChar() + " : " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case 87 -> playerOne.moveUp = true;
            case 83 -> playerOne.moveDown = true;
            case 38 -> playerTwo.moveUp = true;
            case 40 -> playerTwo.moveDown = true;
            case 66 -> ball.randomDXY(); // Press B to change ball direction and velocity
            // Toggle following ball
            case 49 -> playerOne.toggleFollow();
            case 50 -> playerTwo.toggleFollow();
            // Dash ability
            case 71 -> playerOne.dash();
            case 96 -> playerTwo.dash();
            // Reset game
            case 51 -> reset();
            case 32 -> startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 87 -> playerOne.moveUp = false;
            case 83 -> playerOne.moveDown = false;
            case 38 -> playerTwo.moveUp = false;
            case 40 -> playerTwo.moveDown = false;
        }
    }
}
