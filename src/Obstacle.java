import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Obstacle {
	private Vector[] points;
	private Vector[] normals;
	private Vector[] edges;

	public Obstacle(Obstacle obs) {
		this.points = obs.points;
		this.normals = obs.normals;
		this.edges = obs.edges;
	}
	public Obstacle(String fileName) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(fileName));
		int N = sc.nextInt();
		points = new Vector[N];
		for(int j = 0; j < N; j++) {
			int i = 0;
			while(sc.hasNextDouble()){
				points[i] = new Vector(sc.nextDouble(), sc.nextDouble());
				System.out.println(points[i].x() + "   "+ points[i].y());
				i++;
			}
		}
		
	}
	/// @brief Constructor from Scanner
	/// @param s Scanner
	
	public void draw(){
		int[] x = new int[points.length];
		int[] y = new int[points.length];
		for(int i=0; i < points.length; i++){
			x[i] = (int) points[i].x();
			y[i] = (int) points[i].y();
		}
		GUI.drawObstacle(x, y);
	}
}
