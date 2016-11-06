import java.util.InputMismatchException;
import java.util.Scanner;

public class Boundary {
	private double x; //x-coordinate of most upper-left point
	private double y; //y-coordinate of most upper-left point
	private double w; //width of boundary
	private double h; //height of boundary

	//Default constructor
	public Boundary(double x, double y, double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/// @brief Constructor from Scanner
	/// @param l Scanner
	public Boundary(Scanner l) {
		this.x = l.nextDouble();
		this.y = l.nextDouble();
		this.w = l.nextDouble();
		this.h = l.nextDouble();
	}

	/// @brief Boundary draws itself
	public void draw() {
		GUI.drawBoundary(x,y,w,h);
	}

	/// @brief Collision check linear motion of ball between two positions
	/// @return First collision
	public Collision checkCollisionBoundary(Vector p, Vector pnew) {
		//abstract boundary
		Collision c = new Collision(Double.POSITIVE_INFINITY, null);
		double f;
		if(pnew.x() > x + w - 2) {
			f = ((x + w - 2)-p.x())/(pnew.x()-p.x());
			c = new Collision(f, new Vector(-1, 0));
		}
		else if(pnew.x() < x + 2) {
			f = ((x + 2)-p.x())/(pnew.x()-p.x());
			if(f < c.f())
				c = new Collision(f, new Vector(1, 0));
		}
		if(pnew.y() > y - 2) {
			f = ((y - 2)-p.y())/(pnew.y()-p.y());
			if(f < c.f())
				c = new Collision(f, new Vector(0, -1));
		}
		else if(pnew.y() < y - h + 2) {
			f = (( y - h + 2)-p.y())/(pnew.y()-p.y());
			if(f < c.f())
				c = new Collision(f, new Vector(0, 1));
		}
		if(c.f() != Double.POSITIVE_INFINITY)
			return c;
		else
			return null;
	}
}
