import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/// @brief Simulator for a ball.
///
/// The simulator owns the ball and determines the overall forces for an object.
/// It also determines the simulation loop of the code - clear, update, draw.
public class Simulator  implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == GUI.gravityButton){
			double g = Double.parseDouble(GUI.gravityChange.getText());
			gravity.Y(g);
		}
		if (e.getSource() == GUI.timeButton){
			timeStep = Double.parseDouble(GUI.timeChange.getText());
		}
		if (e.getSource() == GUI.massButton){
			//m = Double.parseDouble(GUI.massChange.getText());
		}
	}

	private double timeStep; /// Timestep - time passed in virtual world per frame
	private static Ball ball;       /// Ball to simulate
	private static Vector gravity; /// Gravity force
	private Vector totalForce;
	private static Vector dragForce;/// Total Forces acting on the ball
	private static Vector wind;
	private static Vector posNew;
	private double tR;
	/// @brief Constructor
	/// @param m Mass of the ball
	/// @param t Time step
	/// @param g Gravity magnitude
	public Simulator(double m, double t, double g, double wx, double wy) {
		GUI.gravityButton.addActionListener(this);
		GUI.timeButton.addActionListener(this);
		GUI.massButton.addActionListener(this);
		timeStep = t;
		ball = new Ball(m);
		gravity = new Vector(0, g); 
		wind = new Vector(wx,wy);
	}

	/// @brief Simulation loop
	///
	/// Clear, update, draw
	public void simulate() {
		while(true) {
			tR = timeStep;
			GUI.clear();
			while(tR > 0){
				totalForce = getForces(ball.d(), ball.vel());
				Vector acc = totalForce.scalarMultiply(1/ball.getMass());
				Vector posNew = new Vector(ball.pos().X(),ball.pos().Y()) ;
				Vector velNew =  new Vector(ball.vel().X(),ball.vel().Y());
				
				velNew.add(acc.scalarMultiply(tR));
				posNew.add(velNew.scalarMultiply(tR));
				if(Collided(posNew)){
					
					double slope = Slope(posNew.X(), posNew.Y(), ball.pos().X(), ball.pos().Y());
					double xCollided = 0, yCollided = 0;
					
					
					// Calculating position 
					if(posNew.X()>36){
						xCollided = 36;
						yCollided = slope*xCollided - slope*posNew.X() + posNew.Y();
						ball.vel().X(-ball.vel().X());
					}
					if(posNew.X()<-36){
						xCollided = -36;
						yCollided = slope*xCollided - slope*posNew.X() + posNew.Y();
						ball.vel().X(-ball.vel().X());
					}
					System.out.println(slope);
					if(posNew.Y()<-36){
						yCollided = -36;
						xCollided = (yCollided - posNew.Y())/slope + posNew.X();
						ball.vel().Y(-ball.vel().Y());
					}
					double distanceBeforeCollided = Math.sqrt(Math.pow(xCollided - ball.pos().X(), 2) + Math.pow(yCollided - ball.pos().Y(), 2));
					double distance = Math.sqrt(Math.pow(posNew.X() - ball.pos().X(), 2) + Math.pow(posNew.Y() - ball.pos().Y(), 2));
					double ratio = distanceBeforeCollided/ distance;
					System.out.println(posNew.X() + "   " + ball.pos().X() );
					ball.applyForce(totalForce, ratio*timeStep);
					System.out.println(distanceBeforeCollided);
					System.out.println(distance);
					System.out.println(ratio);
					tR -= ratio*tR;
				}
				else {
					ball.applyForce(totalForce, tR);
					tR = 0;
				}
				System.out.println(acc.X() + "  " + acc.Y());
				System.out.println(ball.vel().X() + "  " + ball.vel().Y());
				System.out.println(ball.pos().X() + "  " + ball.pos().Y());
				System.out.println(tR);
				System.out.println();
			}
			ball.draw();
			GUI.draw();
		}
	}
	
	public static double Slope (double x1, double y1, double x2, double y2){
		double slope = (y2-y1)/(x2-x1);
		return slope;
	}
	
	 
	/*public void simulate() {
		while(true) {
			GUI.clear();
			totalForce = getForces(ball.d(), ball.vel());
			ball.applyForce(totalForce, timeStep);
			ball.draw();
			GUI.draw();
		}
	}*/
	//
	//d is the drag coefficient

	public static Vector getForces( double d, Vector vel){
		dragForce = new Vector(0,-d*vel.Y()); 
		dragForce.add(gravity);
		dragForce.add(wind);
		return dragForce; 
	}
	public static boolean Collided(Vector pos){
		return (pos.Y() < -36 || pos.Y() > 36 || pos.X() > 36 || pos.X() < -36);
	}

}
