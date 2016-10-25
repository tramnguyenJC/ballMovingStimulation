/// @brief A class encapsulating a state for our simulation
///
/// A Ball is the State of the simulator. It contains an intrinsic mass. It
/// contains position and velocity data. It contains operations to access, modify,
/// and integrate the state.
public class Ball {
	private static Vector pos; ///< Position/velocity
	private static Vector vel;
	private double mass;     ///< Mass
	
	/// @brief Construcor to initialize position/velocity to (0,0) and mass to m
	/// @param m Mass
	public Ball(double m) {
		setMass(m);
		pos = new Vector();
		vel = new Vector();
	}

	/// @brief Apply force to the ball for a small time step using Euler
	///        Integration
	/// @param force Force to apply
	/// @param t Time step
	///
	/// x = x + t*v;
	/// v = v + t*a;
	public void applyForce(Vector force, double t) {
		Vector acc = force.scalarMultiply(1/getMass()); //mass of 1
		pos.add(vel.scalarMultiply(t));
		vel.add(acc.scalarMultiply(t));	
	}

	/// @brief Draw the ball as a circle
	public void draw() {
		GUI.drawCircle(pos.X(), pos.Y(), 2);
	}
	
	//@ brief: get velocity
	public static Vector vel(){
		return vel;
	}
	
	//@ brief: get position
	public static Vector pos(){
		return pos;
	}
	
	//@ brief: get mass
	public double getMass() {
		return mass;
	}

	//@ brief: set mass
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	//@ brief: test if ball collided
	public static boolean Collided (){
		return (pos.Y() < -36 || pos.Y() > 36 || pos.X() > 36 || pos.X() < -36);
	}
}
