import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Obstacle {
	private Vector[] points;
	private Vector[] normals;
	private Vector[] edges;

	// @brief: Constructor of Obstacle
	// @param fileName: Name of file input that contains information of obstacles

	public Obstacle(String fileName) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(fileName));
		int N = sc.nextInt();
		points = new Vector[N];
		for(int i = 0; i < N; i++) {
			points[i] = new Vector(sc.nextDouble(), sc.nextDouble());
			System.out.println(points[i].x() + "  " + points[i].y());
		}

		int N2 = sc.nextInt();
		normals = new Vector[N2];
		for(int i = 0; i < N2; i++) {
			normals[i] = new Vector(sc.nextDouble(), sc.nextDouble()).norm();
			System.out.println(normals[i].x() + " " + normals[i].y());
		}
		
		int N3 = sc.nextInt();
		edges = new Vector[N3];
		for(int i = 0; i < N3; i++) {
			edges[i] = new Vector(points[sc.nextInt()].multiply(-1).add(points[sc.nextInt()]));
			sc.nextInt();
			System.out.println(edges[i].x() + "  " + edges[i].y());
		}

	}
	/// @brief detect Collision with Obstacle
	/// @param p, pNew: position and new position of ball
	// ball equation: p + us, 0 <= u <= 1 means collision
	// obstacle edge equation: q + tr, 0 <= t <= 1 means collision

	public Collision checkCollisionObstacle(Vector p, Vector pnew) {
		Collision c = new Collision(Double.POSITIVE_INFINITY, null);
		Vector s = new Vector(pnew.sub(p));
		for (int i=0; i < edges.length; i++){
			Vector r = new Vector(edges[i]);
			double rcs = r.cross(s);
			double u = ((p.sub(points[i])).sub(new Vector(-2,-2)).cross(r))/rcs;
			double t = ((p.sub(points[i])).sub(new Vector(-2,-2)).cross(s))/rcs;
			if (rcs!=0 && 0 <= t && t <= 1 && 0 <= u && u <= 1){
				c = new Collision(u, normals[i]);
			}
		}
		if(c.f() != Double.POSITIVE_INFINITY)
			return c;
		else
			return null;
	}

	// @brief Obstacle draw itself
	public void draw(){
		GUI.drawObstacle(points);
	}
}
