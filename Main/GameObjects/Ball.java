package Main.GameObjects;

import javax.sound.sampled.*;
import java.io.File;
import static Main.GameFrame.*;
import static Main.GamePanel.*;
import static Main.Main.random;

public class Ball {
    private int x = 0, y = 0;
    private int dx = 0, dy = 0;
    public final int maxDX = 60, maxDY = 30;
    private final int size;

    public Raycast ray;

    File file = new File("src/Main/Audio/plastic.wav");
    AudioInputStream audioStream;
    Clip clip;

    public Ball(int size)  {
        ray = new Raycast(this);
        this.size = size;

        randomDXY();
        setPositionCenter();
    }

    public void randomDXY() {
        int veloX = random(5) + 15;
        int veloY = random(25);

        if (random(10) >= 5) {
            veloX *= -1;
        }

        if (random(10) >= 5) {
            veloY *= -1;
        }

        setDx(veloX);
        setDy(veloY);
    }

    public void setPositionCenter() {
        setX(SCREEN_WIDTH_CENTER - (size / 2));
        setY(SCREEN_HEIGHT_CENTER - (size / 2));
    }

    public void setPosition(int x) {
        setX(x);
        setY(random(SCREEN_HEIGHT));
    }

    public void move() {
        int radius = size / 2;
        int ny = (y + radius) + dy;

        if (ny + radius >= SCREEN_HEIGHT || ny - radius <= 0) {
            dy *= -1;
        }

        this.x += dx;
        this.y += dy;
    }

    public void increaseVelocityX() {
        dx *= 1.1;

        if (dx > maxDX) {
            dx = maxDX;
        } else if (dx < -maxDX) {
            dx = -maxDX;
        }
    }

    public void increaseVelocityY(int increase) {
        dy += increase;

        if (dy > maxDY) {
            dy = maxDY;
        } else if (dy < -maxDY) {
            dy = -maxDY;
        }
    }

    public void bounce(Paddle player) {
        int displacement = this.getCenterYPosition() - player.getCenterYPosition();
        displacement *= 1.4;

        dx *= -1;
        increaseVelocityX();
        increaseVelocityY(displacement);

        stopSound();
        playSound();
    }

    void playSound() {
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Checks
    public void performChecks() {
        if (this.hitLeftWall()) {
            playerTwo.addScore();
        }

        if (this.hitRightWall()) {
            playerOne.addScore();
        }

        // Check if ball goes out of the left or right wall
        if (this.hasHitWall()) {
            this.setPositionCenter();
            this.randomDXY();
        }
    }

    public boolean hitLeftWall() {
        return this.x + this.size < 0;
    }

    public boolean hitRightWall() {
        return this.x > SCREEN_WIDTH;
    }

    public boolean hasHitWall() {
        return hitLeftWall() || hitRightWall();
    }

    // Getters and Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getSize() {
        return size;
    }

    public int getCenterYPosition() {
        return getY() + (getSize() / 2);
    }
}
