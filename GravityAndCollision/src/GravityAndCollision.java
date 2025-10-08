import javax.swing.*; //for window/gui
import java.awt.*; //for graphics etc.
import java.awt.event.*; //for detecting events such as keys being pressed or mouse

public class GravityAndCollision extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    public boolean adding = false;
    public boolean removing = false;
    public int brushRadius = 30;

    public static int screenWidth = 1280;
    public static int screenHeight = 720;
    private Timer timer;
    private World world;

    public int mouseX;
    public int mouseY;

    public GravityAndCollision() { // when class starts (used for events in this case)
        timer = new Timer(20, this); // Timer to update ball position every 20 milliseconds
        timer.start(); // Start the timer
        setFocusable(true); // Allow panel to receive keyboard events
        addKeyListener(this); // Register the panel to listen for key events
        addMouseListener(this); // Register the panel to listen for mouse events
        addMouseMotionListener(this); // Register the panel to listen for mouse movements

        world = new World();
    }

    public static void main(String[] args) { // void setup basically
        JFrame frame = new JFrame("Gravity And Collision Simulation");
        GravityAndCollision panel = new GravityAndCollision();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set preferred size to width and height
        frame.getContentPane().add(panel); // Add the BouncingBall panel to the frame
        frame.pack(); // Pack the frame to ensure the correct size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when close button is pressed
        frame.setVisible(true); // make window visible
    }

    public void actionPerformed(ActionEvent actionEvent) { // void draw basically
        repaint(); // Redraw the panel
    }

    public void paintComponent(Graphics graphics) { // display on screen
        super.paintComponent(graphics);

        world.checkCollides();
        world.move();
        world.display(graphics);
        drawGui(graphics);

        if (adding == true) {
            world.addBallAtCursor(mouseX, mouseY);
        } else if (removing == true) {
            // Draw the ball
            graphics.setColor(Color.black);
            graphics.fillOval((int) mouseX - (int) world.rad, (int) mouseY - (int) world.rad, (int) brushRadius,
                    (int) brushRadius);

            world.removeBallAtCursor(mouseX, mouseY, brushRadius);
        }
    }

    private void drawGui(Graphics graphics) {
        graphics.setColor(Color.blue);

        graphics.drawString("Add balls with '+'; remove with '-' (numpad is supported)", 100, 55);
        graphics.drawString("Add balls with left click; remove with right click", 100, 70);
        graphics.drawString("Hold buttons to add/remove precisely", 100, 85);

        graphics.setColor(Color.black);

        // Convert the number of balls to a string
        String numBalls = "number of balls: " + String.valueOf(world.size());

        // Draw the number of balls at position (100, 100)
        graphics.drawString(numBalls, 100, 100);

        // Convert the number of collisons to a string
        String numCollisions = "number of collisions between balls: " + String.valueOf(world.numCollisions());

        // Draw the number of balls at position (100, 115)
        graphics.drawString(numCollisions, 100, 115);
    }

    // events:

    // keyboard:
    public void keyPressed(KeyEvent event) {
        System.out.println("key pressed");
        // Handle key presses
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_EQUALS) { // numpad plus or keyboard plus
            world.addBall();
        }

        if (keyCode == KeyEvent.VK_MINUS || keyCode == KeyEvent.VK_SUBTRACT) { // numpad minus or keyboard minus
            if (world.size() > 1) {
                world.killBall(); // remove one ball from the world.
            }
        }

        repaint(); // Redraw the panel after event
    }

    // mouse:
    public void mousePressed(MouseEvent e) {
        int mouseEvent = e.getButton();
        if (mouseEvent == MouseEvent.BUTTON1) { // Left click
            adding = true;
        } else if (mouseEvent == MouseEvent.BUTTON3) { // Right click
            removing = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        int mouseEvent = e.getButton();
        if (mouseEvent == MouseEvent.BUTTON1) { // Left click
            adding = false;
        } else if (mouseEvent == MouseEvent.BUTTON3) { // Right click
            removing = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // unused events (need to exist somewhere in the code)

    // key methods:
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    // mouse methods:
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}