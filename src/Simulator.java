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
	private Ball ball;       /// Ball to simulate
	private Vector gravity; /// Gravity force
	private Vector totalForce, dragForce;/// Total Forces acting on the ball
	private Vector wind;
	private Vector posNew;
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
				ball.applyForce(totalForce, timeStep);
				if(Ball.Collided()){
					
				}
			}
			ball.draw();
			GUI.draw();
		}
	}
	//
	//d is the drag coefficient

	public Vector getForces( double d, Vector vel){
		dragForce = new Vector(0,-d*vel.Y()); 
		dragForce.add(gravity);
		dragForce.add(wind);
		return dragForce; 

	}

}
