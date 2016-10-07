/// @brief This program simulates the motion of a bouncing ball in a vacuum.
public class BallSimulator {
  public static void main(String args[]) {
    if(args.length != 5) {
      System.out.println("Usage: java BallSimulation <mass> <time step> <gravity magnitude in down-y direction> <wind force magnitude in x-right position> <wind force magnitude in y-down position>");
      System.exit(1);
    }

    // Input mass, time, gravity
    double m = Double.parseDouble(args[0]);
    double t = Double.parseDouble(args[1]);
    double g = Double.parseDouble(args[2]);
    double wx = Double.parseDouble(args[3]);
    double wy = Double.parseDouble(args[4]);

    // Make a simulator on the parameters and simulate
    Simulator simulator = new Simulator(m, t, g, wx, wy);
    simulator.simulate();
  }
}
