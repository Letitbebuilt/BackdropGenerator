package main.shapes;

import java.awt.Color;
import java.awt.geom.Point2D;

public abstract class Shape {
	public Color color = Color.BLACK;
	protected Point2D.Double center;
	
	public abstract Shape rotateAroundCenter(int degrees);
	public abstract Shape createCopy();
	public abstract Shape moveCenterTo(Point2D.Double newCenter);
	public abstract Shape scale(double scaleModifier);
	public final Point2D.Double getCenter(){
		return new Point2D.Double(center.x, center.y);
	}
}
