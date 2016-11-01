import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.Scanner;
import java.util.InputMismatchException;
/// @brief Simulator for a ball.
///
/// The simulator owns t he ball and determines the overall forces for an object.
/// It also determines the simulation loop of the code - clear, update, draw.
public class Simulator  implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == GUI.gravityButton){
			double g = Double.parseDouble(GUI.gravityChange.getText());
			gravity.y(g);
		}
		if (e.getSource() == GUI.timeButton){
			timeStep = Double.parseDouble(GUI.timeChange.getText());
		}
		if (e.getSource() == GUI.massButton){
			//m = Double.parseDouble(GUI.massChange.getText());
		}
	}

	private double timeStep; /// Timestep - time passed in virtual world per frame
	private Ball ball;       /// Ball to simulate
	private Vector gravity;  /// Gravity force
	private Vector wind;     /// Wind force
	private static Boundary boundary;/// Boundary

	private static final int SAMPLES = 100;

	/// @brief Constructor from Scanner
	/// @param s Scanner
	public Simulator(Scanner s) {
		while(s.hasNextLine()) {
			String line = s.nextLine();
			Scanner l = new Scanner(line);
			String tag = l.next();
			switch(tag) {
			case "TimeStep":
				System.out.println("Reading timestep");
				timeStep = l.nextDouble();
				break;
			case "Boundary":
				System.out.println("Reading boundary");
				boundary = new Boundary(l);
				break;	
			case "Gravity":
				System.out.println("Reading gravity");
				gravity = new Vector(l);
				break;
			case "Wind":
				System.out.println("Reading wind");
				wind = new Vector(l);
				break;
			case "Ball":
				System.out.println("Reading agent");
				ball = new Ball(s);
				break;
			default:
				throw new InputMismatchException("Unknown tag " + tag);
			}
			l.close();
		}
	}

	/// @brief Simulation loop
	///
	/// Clear, update, draw
	public void simulate() {
		long updateTimes[] = new long[SAMPLES], drawTimes[] = new long[SAMPLES];
		long frame = 0;
		long updateSum = 0, drawSum = 0;

		while(true) {
			int i = (int)(frame%SAMPLES);

			//Update
			long startUpdate = System.nanoTime();

			update();

			long endUpdate = System.nanoTime();
			updateSum -= updateTimes[i];
			updateTimes[i] = endUpdate - startUpdate;
			updateSum += updateTimes[i];

			//Draw
			long startDraw = System.nanoTime();

			GUI.clear();

			draw();
			drawFrameRate(updateSum, drawSum);

			GUI.show();

			long endDraw = System.nanoTime();
			drawSum -= drawTimes[i];
			drawTimes[i] = endDraw - startDraw;
			drawSum += drawTimes[i];

			frame++;
		}
	}

	/// @brief Update the position of the ball
	private void update() {
		if(ball.rest())
			return;

		double tr = timeStep;
		while(tr > 0) {
			Ball ballnew = new Ball(ball);
			Vector force = determineForces();
			ballnew.applyForce(force, tr);

			Collision c = boundary.checkCollisionBoundary(ball.pos(), ballnew.pos());
			if(c != null) {
				ball.applyForce(force, tr*c.f());
				resolveCollision(c);
				tr -= tr*c.f();
			}
			else {
				ball = ballnew;
				tr = 0;
			}
		}
	}

	/// @brief Determine forces on the ball
	private Vector determineForces() {
		Vector fg = gravity.multiply(ball.mass()); //F_g = m*g where g is acceleration due to gravity
		Vector fw = wind.multiply(ball.drag());    //F_w = d*w where w is wind vector
		Vector fa = ball.vel().multiply(-ball.drag()); //F_a = -d*v where v is velocity of ball

		//F_tot = F_g + F_w + F_a
		return fg.add(fw.add(fa));
	}


	private void resolveCollision(Collision c) {
		Vector vn = c.n().multiply(c.n().dot(ball.vel()));
		Vector vt = ball.vel().sub(vn);
		vn.multiplyeq(-ball.elasticity());
		vt.multiplyeq(1-ball.friction());
		ball.vel(vn.add(vt));

		//TODO remove hard constant for resting distance
		if(ball.vel().magnitude() < 1)
			ball.rest(true);
	}

	private void draw() {
		ball.draw();
		boundary.draw();
	}

	private void drawFrameRate(long updateSum, long drawSum) {
		double updateTime = updateSum / SAMPLES / 1e9;
		double drawTime = drawSum / SAMPLES / 1e9;
		double frameTime = updateTime + drawTime;

		GUI.text(-51, -50, String.format("Update Time: %5.3f", updateTime));
		GUI.text(-51, -47, String.format("  Draw Time: %5.3f", drawTime));
		GUI.text(-51, -44, String.format(" Frame Time: %5.3f", frameTime));
		GUI.text(-51, -41,
				String.format(" Frame Rate: %5.2f", Math.min(1./frameTime, 1/0.016)));
	}
}
