package Main.GameObjects;

import java.util.Random;

import static Main.GameFrame.SCREEN_HEIGHT;
import static Main.GameFrame.SCREEN_WIDTH;
import static Main.GamePanel.*;

public class Raycast {
    public int x, y, dx, dy;
    public int exactX = 0, exactY = 0;
    public int prediction;
    public int since;

    public Raycast(Ball ball) {
        this.x = ball.getX();
        this.y = ball.getY();
        this.dx = ball.getDx();
        this.dy = ball.getDy();

        update(ball);
    }

    public void move(Ball ball) {
        int radius = ball.getSize() / 2;
        int ny = y + dy;

        if (ny + radius > SCREEN_HEIGHT || ny - radius < 0) {
            dy *= -1;
        }

        x += dx;
        y += dy;
    }

    public void update(Ball ball) {
        int radius = ball.getSize() / 2;

        // Set ray-cast position to center of the ball
        this.x = ball.getX() + radius;
        this.y = ball.getY() + radius;

        // Set ray-cast speed
        this.dx = ball.getDx();
        this.dy = ball.getDy();
    }

    public Raycast prediction(Ball ball, Paddle paddle) {
        if (dx == ball.getDx() && dy == ball.getDy()) {
            return this;
        }

        int left = playerOne.getX() + playerOne.getWidth();
        int right = playerTwo.getX();
        int radius = ball.getSize() / 2;

        // Update coordinate and speed
        update(ball);

        int nx = x + dx;

        // Ray-cast
        while (nx - radius >= left && nx + radius <= right) {
            nx = x + dx;
            move(ball);
        }

        // Exact coordinates where the ball will hit
        exactX = x;
        exactY = y;

        // Ai predicted coordinates
        double c = (ball.getDx() > 0) ? ball.getX() - paddle.right : ball.getX() - paddle.left;
        double closeness = c / SCREEN_WIDTH;
        int error = (int) (200 * closeness);

        prediction = y + randInt(-error, error);

        // Update coordinate and speed
        update(ball);

        return this;
    }

    public int randInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
