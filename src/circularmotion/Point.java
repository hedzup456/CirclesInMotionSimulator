package circularmotion;

public class Point {
	private double x, y;
	
	/**
	 * Constructor for the Point class.
	 * <p>
	 * This constructor takes two double coordinates and sets them directly.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public Point( double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor for the Point class.
	 * <p>
	 * This constructor takes two int coordinates converts them to doubles,
	 * then calls the constructor for the Point class with double arguments.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public Point( int x, int y){
		this( (double) x, (double) y);	// Call the Point constructor with ints cast to doubles.
	}

	/**
	 * Constructor for the Point class.
	 * <p>
	 * Constructor with no arguments, defaulting to a point of (0,0) This is 
	 * done by calling the Point constructor with arguments 0 0.
	 */
	public Point(){
		this( 0.0, 0.0);
	}
	
	// Getters and setters.
	
	/**
	 * Get the X coordinate.
	 * 
	 * @return the X coordinate of the Point object.
	 */
	public double getX(){
		return x;
	}
	/**
	 * Get the Y coordinate.
	 * 
	 * @return the Y coordinate of the Point object.
	 */
	public double getY(){
		return y;
	}
	/**
	 * set the X coordinate of the Point object.
	 * 
	 * @param x The X coordinate to change to.
	 */
	public void setX( double x){
		this.x = x;
	}
	/**
	 * Set the Y coordinate of the Point object.
	 * 
	 * @param y The Y coordinate to change to.
	 */
	public void setY( double y){
		this.y = y;
	}
	/**
	 * Set both X and Y coordinates of the Point class simultaneously.
	 * 
	 * @param x The X coordinate to set to.
	 * @param y The Y coordinate to set to.
	 */
	public void setXY ( double x, double y){
		this.setX(x);
		this.setY(y);		
	}
	
	@Override
	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}
