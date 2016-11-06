/// DID NOT COMMENT THIS CODE. BEWARE!

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI implements KeyListener {

	//size
	private static int width = 600;
	private static int height = 600;
	private static double xmin = -55, xmax = 55, ymin = -55, ymax = 55;

	//singleton for callbacks
	public static GUI jma = new GUI();

	// double buffered graphics
	private static BufferedImage offscreenImage, onscreenImage;
	private static Graphics2D offscreen, onscreen;


	// Add buttons to change time step, gravity, mass
	public static JButton timeButton = new JButton("Change Time: ");
	public static JButton gravityButton = new JButton("Change Gravity: ");
	public static JButton massButton = new JButton("Change Mass : ");

	// Add JTextField to input time step, gravity, mass
	public static JTextField timeChange = new JTextField("0");
	public static JTextField gravityChange = new JTextField("0");
	public static JTextField massChange = new JTextField("0");

	// the frame for drawing to the screen
	private static JFrame frame;

	private static long nextDraw = -1;

	private static final Font DEFAULT_FONT = new Font("Monospaced", Font.BOLD, 12);

	private GUI() {
	}

	static {
		Init();
	}

	private static void Init() {
		frame = new JFrame();
		offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		offscreen = offscreenImage.createGraphics();
		onscreen  = onscreenImage.createGraphics();
		clear();

		ImageIcon icon = new ImageIcon(onscreenImage);
		JLabel draw = new JLabel(icon);

		frame.setContentPane(draw);
		frame.addKeyListener(jma);    // JLabel cannot get keyboard focus
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
		frame.setTitle("Java Motion Applet");
		//frame.setJMenuBar(createMenuBar());
		frame.pack();
		timeButton.setBounds(10,20,150,30);
		timeChange.setBounds(15,60, 140, 30);
		gravityButton.setBounds(190,20,150,30);
		gravityChange.setBounds(195,60,140,30);
		massButton.setBounds(370,20,150,30);
		massChange.setBounds(375,60,140,30);
		frame.add(timeButton);
		frame.add(gravityButton);
		frame.add(massButton);
		frame.add(gravityChange);
		frame.add(massChange);
		frame.add(timeChange);
		frame.requestFocusInWindow();
		frame.setVisible(true);
	}

	public static void clear() {
		offscreen.setColor(Color.black);
		offscreen.fillRect(0, 0, width, height);
		//offscreen.setColor(Color.red);
		//drawCircle(0, 0, 2);
		offscreen.setColor(Color.white);
	}

	public static void pause(int t) {
		// sleep until the next time we're allowed to draw
		long millis = System.currentTimeMillis();
		if (millis < nextDraw) {
			try {
				Thread.sleep(nextDraw - millis);
			}
			catch (InterruptedException e) {
				System.out.println("Error sleeping");
			}
			millis = nextDraw;
		}

		// when are we allowed to draw again
		nextDraw = millis + t;
	}

	public static void show() {
		onscreen.drawImage(offscreenImage, 0, 0, null);
		frame.repaint();
		pause(16);
	}

	public void keyTyped(KeyEvent e) {
		//do nothing: Java is dumb.
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		default: break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		default: break;
		}
	}

	private static double scaleX(double x) { return (width  * (x - xmin) / (xmax - xmin)); }
	private static double scaleY(double y) { return (height * (ymax - y) / (ymax - ymin)); }
	private static double factorX(double w) { return (w * width  / Math.abs(xmax - xmin));  }
	private static double factorY(double h) { return (h * height / Math.abs(ymax - ymin));  }

	//Draw the ball at center (x, y) with radius r
	public static void drawCircle(double x, double y, double r) {
		double rx = factorX(2*r);
		double ry = factorY(2*r);
		offscreen.fill(new Ellipse2D.Double(
				scaleX(x) - rx/2, scaleY(y) - ry/2, rx, ry));
	}
	
	// Draw boundary with upper-left corner at (x,y), width w and height h
	public static void drawBoundary(double x, double y, double w, double h){
		offscreen.draw(new Rectangle2D.Double(scaleX(x), scaleY(y), factorX(w), factorY(h)));
	}

	public static void drawObstacle(int[] x, int[]y){
		offscreen.drawPolygon(x, y, x.length);
	}
	
	/// @brief Write text in the current font, left-aligned at (x, y).
	/// @param  x the x-coordinate of the text
	/// @param  y the y-coordinate of the text
	/// @param  text the text
	public static void text(double x, double y, String text) {
		if (text == null)
			throw new NullPointerException();

		offscreen.setFont(DEFAULT_FONT);
		FontMetrics metrics = offscreen.getFontMetrics();
		double xs = scaleX(x);
		double ys = scaleY(y);
		int hs = metrics.getDescent();
		offscreen.drawString(text, (float) xs, (float) (ys + hs));
	}
}




