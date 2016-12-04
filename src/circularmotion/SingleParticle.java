/**
 * 
 */
package circularmotion;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		3 Aug 2016
 *
 */
public class SingleParticle{
	private final double PI = Math.PI;	//	Save typing Math. every time.
	private double acceleration;
	private double force;
	private	double mass = 0;	// Zero by default
	private double period;
	private double radius;
	private Point centre = new Point(500,500);	// Point initialises as 0,0 given no arguments
	
	//	Getters
	/**
	 * @return the acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}
	/**
	 * @return the centre as a point
	 */
	public Point getCentre(){
		return centre;
	}	
	/**
	 * @return the force
	 * awakens
	 */
	public double getForce() {
		return force;
	}
	/**
	 * @return the frequency as a function of the period
	 */
	public double getFrequency() {
		return Math.pow(period, -1);
	}
	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}
	/**
	 * @return the period
	 */
	public double getPeriod() {
		return period;
	}
	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	//	Setters
	/**
	 * @param acceleration The acceleration that the object experiences.
	 */
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	/**
	 * @param frequency The force the object experiences
	 */
	public void setForce(double force) {
		this.force = force;
	}
	/**
	 * Set the frequency of the particle as a function of the perood.
	 * @param frequency The frequency of the object's rotation.
	 * 
	 */
	public void setFrequency(double frequency) {
		this.period = Math.pow(frequency, -1);
	}
	/**
	 * @param radius The radius of the object's motion
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	/**
	 * @param period The period of the object's rotation.
	 */
	public void setPeriod(double period) {
		this.period = period;
	}
	/**
	 * @param mass The mass of the object, only used in the Force and Energy calculations.
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}
	/**
	 * Set the particles' rotation centre
	 * @param centre
	 */
	public void setCentre(Point centre){
		this.centre = centre;
	}
	
	//	Constructors
	public SingleParticle (){
		// Empty constructor. Initalise with default values.
		this(1, true, 1, 1, new Point());
	}
	public SingleParticle (double acceleration, boolean isPeriod, double periodOrFrequency, double radius){
		setAcceleration(acceleration);
		if (isPeriod) setPeriod(periodOrFrequency);
		else setFrequency(periodOrFrequency);
		setRadius(radius);
	}
	public SingleParticle (double acceleration, boolean isPeriod, double periodOrFrequency, double radius, Point centre){
		this(acceleration, isPeriod, periodOrFrequency, radius);	// Reuse existing constructor
		setCentre(centre);
	}
	
	
	//	Calculation based methods.
	private double calculateSpeed(){
		double orbitalDistance = 2*PI*radius;
		double speed = orbitalDistance/period;
		return speed;
	}
	private double calculateAngularSpeed(){
		double speed = 2*PI/period;
		return speed;
	}
	private void calculateAcceleration(){
		double acceleration = Math.pow(calculateSpeed(), 2)/radius;
		setAcceleration(acceleration);
	}
	private double calculateForce(){
		return mass * acceleration;
	}
	
	public Point determineLocationGivenPartOfT(double progressIntoT){
		Point location;
		progressIntoT %= 1.0;	// It's irrelevant if the particle has done more than one full circle
		// Multiplying by 100 allows easy use of switch-case to sort out the four quarters of T.
		double x, y;
		switch( (int)(100*progressIntoT) ){
		case 0:
			x = 0;
			y = radius;
			break;
		case 25:
			x = radius;
			y =  0;
			break;
		case 50:
			x = 0;
			y = -radius;
			break;
		case 75:
			x = -radius;
			y = 0;
			break;
		default:
			double theta = calculateAngleFromPartOfT(progressIntoT);
			x = radius*Math.sin(theta);
			y = radius*Math.cos(theta);			
		}
		x += centre.getX();
		y += centre.getY();
		location = new Point(x, y);
		return location;
	}
	private double calculateAngleFromPartOfT(double progressIntoT){
		double angleInRadians = 2*PI*(progressIntoT/period);	// (t/T)*fullCircle
		return angleInRadians;
	}
	public Point[] calculateLocationInSecotionsOfT(int resolution){	// Resolution is how many points to make
		Point[] partsOfT = new Point[resolution];
		for (int part = 0; part < resolution; part++){
			partsOfT[part] = determineLocationGivenPartOfT(period*part/resolution);
		}
		return partsOfT;
	}
	
	/**
	 * Only for testing!
	 * @param args
	 */
	public static void main(String[] args) {
		//new SingleParticle(acceleration, isPeriod, periodOrFrequency, radius)
		SingleParticle iss = new SingleParticle(0.0, true, 1, 1);
		
		System.out.println(iss.getPeriod());
		System.out.println(iss.getFrequency());
		System.out.println(iss.calculateSpeed());
		iss.calculateAcceleration();
		System.out.println(iss.getAcceleration());
		System.out.println(iss.calculateForce());
		iss.setMass(1);
		System.out.println(iss.calculateForce());
		
		for (Point p: iss.calculateLocationInSecotionsOfT(1000)){
			System.out.println(p.getX() + ", " + p.getY());
		}
		
	}

}

