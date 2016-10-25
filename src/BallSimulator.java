import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/// @brief This program simulates the motion of a bouncing ball in a vacuum.
public class BallSimulator {
  public static void main(String args[]) throws FileNotFoundException {
	String inFileName = args[0];
	Scanner sc = new Scanner(new File(inFileName));

    // Input mass, time, gravity
    double m = sc.nextDouble();
    double t = sc.nextDouble();
    double g = sc.nextDouble();
    double wx = sc.nextDouble();
    double wy = sc.nextDouble();
    double d = sc.nextDouble();
    
    sc.close();

    // Make a simulator on the parameters and simulate
    Simulator simulator = new Simulator(m, t, g, wx, wy, d);
    simulator.simulate();
  }
}
