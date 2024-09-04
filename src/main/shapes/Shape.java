package main.shapes;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Shape {
	private ArrayList<Point2D.Double> points = new ArrayList<>();
	private Point2D.Double center;
	
	public static Shape getRegularPolygon(int sides, int radius, Point2D.Double center) {
		double radiansPerSide = Math.PI*2 / (double) sides;
		Point2D.Double[] points = new Point2D.Double[sides];
		for(int i = 0; i<sides; i++) {
			points[i] = new Point2D.Double(Math.cos(radiansPerSide*i)*radius + center.x, Math.sin(radiansPerSide*i)*radius + center.y);
		}
		return new Shape(points);
	}
	
	public static Shape getStellatedPolygon(int stellations, int radius, int apothem, Point2D.Double center) {
		int sides = stellations*2;
		double radiansPerSide = Math.PI*2 / (double) sides;
		Point2D.Double[] points = new Point2D.Double[sides];
		for(int i = 0; i<sides; i++) {
			int scalar = i%2 == 0? radius: apothem;
			points[i] = new Point2D.Double(Math.cos(radiansPerSide*i)*scalar + center.x, Math.sin(radiansPerSide*i)*scalar + center.y);
		}
		return new Shape(points);
	}
	
	public Shape(Point2D.Double...pointsAsArray) {
		double xValCenter = 0;
		double yValCenter = 0;
		for(Point2D.Double point: pointsAsArray) {
			points.add(point);
			xValCenter += point.x;
			yValCenter += point.y;
		}
		
		xValCenter /= (double)pointsAsArray.length;
		yValCenter /= (double)pointsAsArray.length;
		center = new Point2D.Double(xValCenter, yValCenter);
	}
	
	public Shape rotateAroundCenter(int degrees) {
		Point2D.Double[] rotatedPoints = new Point2D.Double[points.size()];
		for(int i = 0; i<rotatedPoints.length; i++) {
			Point2D.Double baseShapePoint = points.get(i);
			
			//translate point be relative to (0, 0);
			Point2D.Double centeredPoint = new Point2D.Double(baseShapePoint.x - center.x, baseShapePoint.y - center.y);
			
			//obtain scalar and angle of point
			double scalar = Math.sqrt(Math.pow(centeredPoint.x, 2)+Math.pow(centeredPoint.y, 2));
			double directionInDegrees = Math.toDegrees(Math.atan(centeredPoint.y / centeredPoint.x));
			if(centeredPoint.x<0) {
				directionInDegrees+= 180;
			}
			//rotate
			double newDirectionInDegrees = directionInDegrees+degrees;
			Point2D.Double rotatedPointCenteredOrigin = new Point2D.Double(Math.cos(Math.toRadians(newDirectionInDegrees))*scalar, Math.sin(Math.toRadians(newDirectionInDegrees))*scalar);
			
			//re-center
			rotatedPoints[i] = new Point2D.Double(rotatedPointCenteredOrigin.x+center.x, rotatedPointCenteredOrigin.y+center.y);
		}
		
		return new Shape(rotatedPoints);
	}
	
	public Shape moveCenterTo(Point2D.Double newCenter) {
		Point2D.Double vectorForMove = new Point2D.Double(newCenter.x - center.x, newCenter.y - center.y);
		Point2D.Double[] translated = new Point2D.Double[points.size()];
		for(int i = 0; i<translated.length; i++) {
			translated[i] = new Point2D.Double(points.get(i).x+vectorForMove.x, points.get(i).y+vectorForMove.y);
		}
		
		return new Shape(translated);
	}
	
	public Shape scale(double scaleModifier) {
		Point2D.Double[] scaledPoints = new Point2D.Double[points.size()];
		for(int i = 0; i<scaledPoints.length; i++) {
			Point2D.Double baseShapePoint = points.get(i);
			
			//translate point be relative to (0, 0);
			Point2D.Double centeredPoint = new Point2D.Double(baseShapePoint.x - center.x, baseShapePoint.y - center.y);
			
			//obtain scalar and angle of point
			double scalar = Math.sqrt(Math.pow(centeredPoint.x, 2)+Math.pow(centeredPoint.y, 2));
			double directionInDegrees = Math.toDegrees(Math.atan(centeredPoint.y / centeredPoint.x));
			if(centeredPoint.x<0) {
				directionInDegrees+= 180;
			}
			//scale
			double newScalar = scalar * scaleModifier;
			Point2D.Double scaledPointCenterOrigin = new Point2D.Double(Math.cos(Math.toRadians(directionInDegrees))*newScalar, Math.sin(Math.toRadians(directionInDegrees))*newScalar);
			
			//re-center
			scaledPoints[i] = new Point2D.Double(scaledPointCenterOrigin.x+center.x, scaledPointCenterOrigin.y+center.y);
		}
		
		return new Shape(scaledPoints);
	}
	
	public int[] getXCoordinatesForShapeDraw() {
		int[] xCoords = new int[points.size()];
		for(int i = 0; i<xCoords.length; i++) {
			//note, typecast issue / overflow possible. add a check later
			xCoords[i] = (int) Math.round(points.get(i).x);
		}
		return xCoords;
	}
	
	public int[] getYCoordinatesForShapeDraw() {
		int[] yCoords = new int[points.size()];
		for(int i = 0; i<yCoords.length; i++) {
			//note, typecast issue / overflow possible. add a check later
			yCoords[i] = (int) Math.round(points.get(i).y);
		}
		return yCoords;
	}
	
	public Point2D.Double getCenter() {
		return new Point2D.Double(center.x, center.y);
	}
}
