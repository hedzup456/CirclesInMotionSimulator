/**
 * DO NOT ACTUALLY USE ANY OF THIS YOU KNOB
 * 
 */
package circularmotion;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		15 Sep 2016
 *
 */
public class MainClass extends JFrame{
	SingleParticle sp;
	public MainClass(SingleParticle sp){
		super();
		this.sp = sp;
	}
	
	private void drawOval(Graphics g, Point2D.Double ctr, double rad){
		int[] accCentre = findActualCentreLocation(ctr, rad);
		g.drawOval(accCentre[0], accCentre[1], (int)rad, (int)rad);
	}
	private int[] findActualCentreLocation(Point2D.Double ctr, double rad){
		int[] actualCoords = new int[2];
		actualCoords[0] = (int)(ctr.getX() - rad);
		actualCoords[1] = (int)(ctr.getY() - rad);
		return actualCoords;	
	}
	
	public static void main(String[] a){
		SingleParticle sp = new SingleParticle(0, true, 1, 500);
		MainClass f = new MainClass(sp);
		f.setSize(1000,1000);
		f.setVisible(true);		
	}
	private void addTimeCross(Graphics g, SingleParticle sp){
		Point2D.Double = sp.determineLocationGivenPartOfT(1);
		
		
	}
	public void paint(Graphics g){
		//	Graphics2D g2d = (Graphics2D) g;
		drawOval(g, sp.getCentre(), sp.getRadius());
		addTimeCross(g, sp);
	}
}
