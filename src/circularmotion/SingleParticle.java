/**
 * 
 */
package circularmotion;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		3 Aug 2016
 *
 */
public class SingleParticle {
	private final double PI = Math.PI;	//	Save typing Math. every time.
	private double acceleration;
	private double force;
	private	double mass = 0;	// Zero by default
	private double period;
	private double radius;
	
	
	//	Getters
	/**
	 * @return the acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}
	/**
	 * @return the force
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
	 * @param frequency The frequency of the object's rotation.
	 * 
	 * This method doesn't set a value directly, instead storing frequency as a function of period
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
	
	//	Constructors
	public SingleParticle (double acceleration, boolean isPeriod, double periodOrFrequency, double radius){
		setAcceleration(acceleration);
		if (isPeriod) setPeriod(periodOrFrequency);
		else setFrequency(periodOrFrequency);
		setRadius(radius);
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
	
	
	/**
	 * Only for testing!
	 * @param args
	 */
	public static void main(String[] args) {
		SingleParticle iss = new SingleParticle(0.0, true, 5561.4, (412.5 + 6371)*1000);
		
		System.out.println(iss.getPeriod());
		System.out.println(iss.getFrequency());
		System.out.println(iss.calculateSpeed());
		iss.calculateAcceleration();
		System.out.println(iss.getAcceleration());
	}

}
