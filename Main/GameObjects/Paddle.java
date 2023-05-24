package Main.GameObjects;

import static Main.GameFrame.*;
import static Main.GamePanel.ball;

public class Paddle {
    private String name;
    private int x = 0, y = 0;
    public int dy = 25;
    private final int width, height;
    public int left, right;
    public boolean moveUp = false, moveDown = false;
    private boolean aiEnabled = false;
    private int score;

    public Paddle(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;

        this.left = x;
        this.right = x + width;

        setY((SCREEN_HEIGHT / 2) - getHeight()); // Set initial position to the center
    }

    // Movement
    public void movement() {
        if (aiEnabled) {
            ai(ball);
        } else {
            if (this.moveUp) {
                moveUp();
            }else if (this.moveDown) {
                moveDown();
            }
        }
    }

    public void moveUp() {
        if (y > 0) {
            y -= dy;
        }
    }

    public void moveDown() {
        if (y + height < SCREEN_HEIGHT) {
            y += dy;
        }
    }

    public void dash() {
        if (moveUp) {
            y -= height + (height / 2);
            if (y < 0) {
                y = 0;
            }
        }

        if (moveDown) {
            y += height + (height / 2);
            if (y + height > SCREEN_HEIGHT) {
                y = SCREEN_HEIGHT - height;
            }
        }
    }

    public void ai(Ball ball) {
        if ((ball.getX() < x) && (ball.getDx() < 0) ||
                (ball.getX() > x) && (ball.getDx() > 0)) {
            return;
        }

        int hy = height / 2;
        int top = (y + hy) - dy / 2;
        int bottom = (y + hy) + dy / 2;

        Raycast pd = ball.ray.prediction(ball, this);

        if (pd.prediction < top) {
            moveUp();
        } else if (pd.prediction > bottom) {
            moveDown();
        }
    }

    // Collision detection
    public boolean collided(Ball ball) {
        return ball.getX() + ball.getSize() > this.getX() && ball.getX() < this.getX() + this.getWidth() &&
                ball.getY() + ball.getSize() > this.getY() &&ball.getY() < this.getY() + this.getHeight();
    }

    public void toggleFollow() {
        aiEnabled = !aiEnabled;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

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

    public int getDy() {
        return this.dy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        this.score++;
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getCenterYPosition() {
        return getY() + (getHeight() / 2);
    }
}
