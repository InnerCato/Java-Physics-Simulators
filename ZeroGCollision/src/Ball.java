import java.awt.*;                      //for graphics etc.

public class Ball{
    private float ballX;
    private float ballY;
    private float xVel;
    private float yVel;
    private float diameter;
  
    Ball (float ballX, float ballY, float xVel, float yVel, float diameter)   {
        this.ballX = ballX;
        this.ballY = ballY;
        this.xVel = xVel;
        this.yVel = yVel;
        this.diameter = diameter;
    }

    public void move() {
        // Update ball position
        ballX += xVel;
        ballY += yVel;
        // Check for collision with walls
        // Horizontal walls
        if (ballX <= 0) {
           ballX = 0;
            xVel *= -1;
        } else if (ballX + diameter >= ZeroGCollision.screenWidth) {
            ballX = ZeroGCollision.screenWidth - diameter;
            xVel *= -1;
        }

        // Vertical walls
        if (ballY <= 0) {
            ballY = 0;
            yVel *= -1;
        } else if (ballY + diameter >= ZeroGCollision.screenHeight) {
            ballY = ZeroGCollision.screenHeight - diameter;
            yVel *= -1;
        }
    }

    public void display(Graphics graphics) {
        // Draw the ball
        graphics.setColor(Color.RED);
        graphics.fillOval((int) ballX, (int) ballY, (int) diameter, (int) diameter);

        // Draw the walls
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, ZeroGCollision.screenWidth - 1, ZeroGCollision.screenHeight - 1);
    }



    // some getter and setter methods.
    public float getX()
    {
        return ballX;
    }

    public float getY()
    {
        return ballY;
    }

    float getXVel()
    {
        return xVel;
    }

    float getYVel()
    {
        return yVel;
    }

    void setXVel(float xV)
    {
        xVel = xV;
    }

    void setYVel(float yV)
    {
        yVel = yV;
    }
    
    void setX(float xL)
    {
        ballX = xL;
    }

    void setY(float yL)
    {
        ballY = yL;
    }


    boolean collide(Ball otherBall) 
    { 
        // Velocities of the current ball before collision
        float myXVel = xVel;
        float myYVel = yVel;
        
        // Velocities of the other ball before collision
        float otherXVel = otherBall.getXVel();
        float otherYVel = otherBall.getYVel();

        // Masses of the balls
        float myMass = 10; // Replace with the actual mass of the current ball
        float otherMass = 10; // Replace with the actual mass of the other ball

        // Calculate the distance between the balls
        double distance = Math.sqrt(Math.pow(otherBall.getX() - ballX, 2) + Math.pow(otherBall.getY() - ballY, 2));

        // Check if the balls are colliding
        if (distance <= diameter) {
            // Calculate new velocities using elastic collision formulas
            // Calculate overlap
            float overlap = diameter - (float) distance;
    
            // Move balls away from each other proportionally
            float dx = otherBall.getX() - ballX;
            float dy = otherBall.getY() - ballY;
            float distanceFactor = (float) Math.sqrt(dx*dx + dy*dy);

            if (distanceFactor == 0) distanceFactor = 0.1f; // prevent divide by zero

            float moveX = dx / distanceFactor * overlap / 2;
            float moveY = dy / distanceFactor * overlap / 2;

            // Apply separation
            ballX -= moveX;
            ballY -= moveY;
            otherBall.setX(otherBall.getX() + moveX);
            otherBall.setY(otherBall.getY() + moveY);
            // Velocity components for the current ball after collision
            xVel = (myXVel * (myMass - otherMass) + 2 * otherMass * otherXVel) / (myMass + otherMass);
            yVel = (myYVel * (myMass - otherMass) + 2 * otherMass * otherYVel) / (myMass + otherMass);

            // Velocity components for the other ball after collision
            otherBall.setXVel((otherXVel * (otherMass - myMass) + 2 * myMass * myXVel) / (myMass + otherMass));
            otherBall.setYVel((otherYVel * (otherMass - myMass) + 2 * myMass * myYVel) / (myMass + otherMass));

            // Collision occurred
            return true;
        } else {
            // No collision occurred
            return false;
        }
    }
}