package circularmotion;

public class Point {
	private int x, y;

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int[] getXY(){
		int[] xyCoord = {x,y};
		return xyCoord;
	}

	@Override
	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}
