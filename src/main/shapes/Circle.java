package main.shapes;

import java.awt.geom.Point2D;

public class Circle extends Shape {
	public double radius;
	
	public static Circle getCircle(Point2D.Double center, double radius) {
		return new Circle(new Point2D.Double(center.x, center.y), radius);
	}
	
	private Circle(Point2D.Double center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	public Shape rotateAroundCenter(int degrees) {
		return this.createCopy(); //rotating a circle does nothing visually
	}

	@Override
	public Shape moveCenterTo(Point2D.Double newCenter) {
		return new Circle(new Point2D.Double(newCenter.x, newCenter.y), radius);
	}

	@Override
	public Shape scale(double scaleModifier) {
		return new Circle(new Point2D.Double(center.x, center.y), radius*scaleModifier);
	}

	@Override
	public Shape createCopy() {
		return new Circle(new Point2D.Double(center.x, center.y), radius);
	}

}
