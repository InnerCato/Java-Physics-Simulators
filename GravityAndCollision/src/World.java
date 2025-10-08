import java.util.ArrayList; //to be able to have as many balls as you want with each storing values such as location and velocity
import java.util.Random; //for random numbers
import java.awt.*; //for graphics etc.

public class World {

    Random random = new Random();

    private ArrayList<Ball> balls;
    private float diameter = 30;
    public float rad = diameter / 2;
    private int numCollisions;

    public World() {
        balls = new ArrayList<Ball>();
        this.rad = diameter / 2;
        this.numCollisions = 0;

        // Generating random float values for position and velocity within the screen
        // bounds
        float mouseX = rad + random.nextFloat() * (GravityAndCollision.screenWidth - 2 * rad);
        float mouseY = rad + random.nextFloat() * (GravityAndCollision.screenHeight - 2 * rad);
        float randomVx = -3 + random.nextInt(7); // Radom integer between -3 and 3
        float randomVy = -3 + random.nextInt(7); // Random integer between -3 and 3

        Ball ball = new Ball(mouseX, mouseY, randomVx, randomVy, diameter);
        balls.add(ball);
    }

    public void display(Graphics graphics) {
        for (Ball ball : balls) {
            ball.display(graphics);
        }
    }

    public void move() {
        for (Ball ball : balls) {
            ball.move();
        }
    }

    public void addBall() {
        // generate random x and y for ball:
        float mouseX = rad + random.nextFloat() * (GravityAndCollision.screenWidth - 2 * rad);
        float mouseY = rad + random.nextFloat() * (GravityAndCollision.screenHeight - 2 * rad);

        // Check if the new ball is too close to existing balls
        if (!isTooClose(mouseX, mouseY)) {
            Ball ball = null;

            // Create a new ball with specified position and random velocity
            if (mouseX >= rad && mouseX <= (GravityAndCollision.screenWidth - rad) && mouseY >= rad
                    && mouseY <= (GravityAndCollision.screenWidth - rad)) {
                // generate random part
                float randomVx = -3 + random.nextInt(7); // Random integer between -3 and 3
                float randomVy = -3 + random.nextInt(7); // Random integer between -3 and 3

                ball = new Ball(mouseX, mouseY, randomVx, randomVy, diameter);
            }

            // Add the new ball to the container if it was initialized
            if (ball != null) {
                balls.add(ball);
            }
        }
    }

    void killBall() {
        balls.remove(balls.size() - 1);
    }

    public void addBallAtCursor(int mouseX, int mouseY) {

        // Check if the new ball is too close to existing balls
        if (!isTooClose(mouseX - rad, mouseY - rad)) {
            Ball ball = null;

            // Create a new ball with specified position and random velocity
            if (mouseX >= rad && mouseX <= (GravityAndCollision.screenWidth - rad) && mouseY >= rad
                    && mouseY <= (GravityAndCollision.screenWidth - rad)) {
                // generate random part
                float randomVx = -3 + random.nextInt(7); // Random integer between -3 and 3
                float randomVy = -3 + random.nextInt(7); // Random integer between -3 and 3

                ball = new Ball(mouseX - rad, mouseY - rad, randomVx, randomVy, diameter);
            }

            // Add the new ball to the container if it was initialized
            if (ball != null) {
                balls.add(ball);
            }
        }
    }

    void removeBallAtCursor(float mouseX, float mouseY, float brushRadius) {
        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball ball = balls.get(i);
            float distance = (float) Math
                    .sqrt(Math.pow(((mouseX - rad) - ball.getX()), 2) + Math.pow(((mouseY - rad) - ball.getY()), 2));
            if (distance <= brushRadius + diameter / 2 && balls.size() > 1) {
                balls.remove(i);
            }
        }
    }

    public boolean isTooClose(float x, float y) {
        float minSeparation = diameter + (diameter / 4); // Minimum separation distance between balls

        for (Ball existingBall : balls) {
            float distance = (float) Math.sqrt((existingBall.getX() - x) * (existingBall.getX() - x)
                    + (existingBall.getY() - y) * (existingBall.getY() - y));
            if (distance < minSeparation) {
                // The new ball is too close to an existing ball
                return true;
            }
        }
        // The new ball is not too close to any existing ball
        return false;
    }

    void checkCollides() // checks collision each other by the balls.
    {

        for (int i = 0; i < balls.size(); i++) {

            for (int j = i + 1; j < balls.size(); j++) {
                if (balls.get(i).collide(balls.get(j))) {
                    numCollisions++;
                }
            }
        }
    }

    int size() // gets number of balls in the container.
    {
        return balls.size();
    }

    int numCollisions() {
        return numCollisions;
    }
}