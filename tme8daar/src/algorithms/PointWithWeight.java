package algorithms;

import java.awt.Point;

public class PointWithWeight {
	
	private int weight;
	private Point p;
	
	public PointWithWeight(Point p, int weight) {
		this.weight = weight;
		this.p = p;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}
}
