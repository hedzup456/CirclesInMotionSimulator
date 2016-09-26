/**
 * 
 */
package circularmotion;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.naming.InterruptedNamingException;
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
	public static void main(String[] a){
		SingleParticle sp = new SingleParticle(0, true, 1, 450);
		MainClass f = new MainClass(sp.calculateLocationInSecotionsOfT(1000));
		f.setSize(1000,1000);
		f.setVisible(true);		
	}
	private void drawOval(Graphics g, Point2D.Double ctr, double rad){
	
	}
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		drawOval(g, sp.getCentre.getX(), sp.getCentre.getY(), sp.getRadius());
	}
}
