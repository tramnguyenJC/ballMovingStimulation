import java.util.InputMismatchException;
import java.util.Scanner;

public class Boundary {
	private double x; //x-coordinate of most upper-left point
	private double y; //y-coordinate of most upper-left point
	private double w;  //width of boundary
	private double h; //height of boundary
	public Boundary(double x, double y, double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Boundary(Scanner l) {
		this.x = l.nextDouble();
		this.y = l.nextDouble();
		this.w = l.nextDouble();
		this.h = l.nextDouble();
	}
	public void draw() {
		GUI.drawBoundary(x,y,w,h);
	}

	public void x(double x0){
		x = x0;
	}
	public void y(double y0){
		y = y0;
	}
	public void w(double w0){
		w = w0;
	}
	public void h(double h0){
		h = h0;
	}
}
