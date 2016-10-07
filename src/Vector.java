/// @brief Denotes a mathematical 2d point/vector (x, y)
///
/// Denotes a mathematical 2d point/vector. This class supports common
/// operations of vectors, e.g., adding and scalar multiplication for use with
/// Euler integration.
public class Vector {
  private double x, y; ///< Positional data

  /// @brief Default constructor for (0, 0)
  public Vector() {
    x = 0;
    y = 0;
  }

  /// @brief Constructor for (x, y) of arbitrary points/vectors
  /// @param _x X coordinate
  /// @param _y Y coordinate
  public Vector(double _x, double _y) {
    x = _x;
    y = _y;
  }

  /// @brief Construction from another Vector (x, y)
  /// @param v Vector to copy data from
  public Vector(Vector v) {
    x = v.x;
    y = v.y;
  }

  /// @return X coordinate
  public double X() {return x;}
  /// @return Y coordinate
  public double Y() {return y;}
  /// @param _x new X coordinate
  public void X(double _x) {x = _x;}
  /// @param _y new Y coordinate
  public void Y(double _y) {y = _y;}

  /// @brief Vector addition
  /// @param v Vector
  ///
  /// (x, y) becomes (x + v.x, y + v.y)
  public void add(Vector v) {
    x += v.x;
    y += v.y;
  }

  /// @brief Vector scale
  /// @param d Scale
  /// @return Newly computed vector (d*x, d*y)
  public Vector scalarMultiply(double d) {
    return new Vector(x*d, y*d);
  }
}
