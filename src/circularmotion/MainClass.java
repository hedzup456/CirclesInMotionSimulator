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
	Point2D.Double[] aa;
	public MainClass(Point2D.Double[] a){
		super();
		aa = a;
	}
	public static void main(String[] a){
		SingleParticle sp = new SingleParticle(0, true, 1, 450);
		MainClass f = new MainClass(sp.calculateLocationInSecotionsOfT(100000));
		f.setSize(1000,1000);
		f.setVisible(true);		
	}
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < aa.length; i++){
			if (i == aa.length-1){
				g2d.draw(new Line2D.Float(aa[i], aa[0]));
			} else {
				g2d.draw(new Line2D.Float(aa[i], aa[i+1]));
			}
		}
	}
}