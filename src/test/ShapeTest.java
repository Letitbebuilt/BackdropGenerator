package test;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.shapes.Shape;

public class ShapeTest {
	Shape testSquareOriginCenter;
	Shape testSquareAltCenter;
	double acceptableFloatingPointErrorMargin = 0.0001;
	@BeforeEach
	public void setup() {
		testSquareOriginCenter = new Shape(
			new Point2D.Double(-1, -1),
			new Point2D.Double(1, -1),
			new Point2D.Double(1, 1),
			new Point2D.Double(-1, 1)
			);
		testSquareAltCenter = new Shape(
				new Point2D.Double(0, 2),
				new Point2D.Double(2, 2),
				new Point2D.Double(2, 0),
				new Point2D.Double(0, 0)
				);
	}
	
	
	
	@Test
	public void getXCoordinatesForShapeDraw_returnCoords() {
		int[] expectedCoords = {-1, 1, 1, -1};
		checkExpectedPointCoordinatesAgainstActual(expectedCoords, testSquareOriginCenter.getXCoordinatesForShapeDraw());
	}
	
	@Test
	public void getYCoordinatesForShapeDraw_returnCoords() {
		int[] expectedCoords = {-1, -1, 1, 1};
		checkExpectedPointCoordinatesAgainstActual(expectedCoords, testSquareOriginCenter.getYCoordinatesForShapeDraw());
	}
	
	@Test
	public void getCenter_squareCenteredOnOrigin_returnCenter() {
		checkCenterPositionCorrect(new Point2D.Double(0, 0), testSquareOriginCenter.getCenter());
	}
	
	
	
	@Test
	public void getRegularPolygon_hexagon_pointsWhereExpected() {
		Shape hexagon = Shape.getRegularPolygon(6, 5, new Point2D.Double(10, 10));
		int[] expectedXCoords = {15, 13, 8, 5, 7, 12};
		int[] expectedYCoords = {10, 14, 14, 10, 6, 6};
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, hexagon.getXCoordinatesForShapeDraw());
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, hexagon.getYCoordinatesForShapeDraw());
	}
	
	
	
	@Test
	public void rotateAroundCenter_squareRotated90Degrees_newSquareCenterSamePointsRotated() {
		Shape newSquare = testSquareOriginCenter.rotateAroundCenter(90);
		checkCenterPositionCorrect(new Point2D.Double(0, 0), newSquare.getCenter());
		
		int[] expectedXCoords = {1, 1, -1, -1};
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, newSquare.getXCoordinatesForShapeDraw());
		int[] expectedYCoords = {-1, 1, 1, -1};		
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, newSquare.getYCoordinatesForShapeDraw());
		
		newSquare = testSquareAltCenter.rotateAroundCenter(90);
		checkCenterPositionCorrect(new Point2D.Double(1, 1), newSquare.getCenter());
	}
	
	@Test
	public void moveCenterTo_centerMoved_pointsUpdatedRelativeToNewCenter() {
		Shape newSquare = testSquareOriginCenter.moveCenterTo(new Point2D.Double(12, 12));
		int[] expectedXCoords = {11, 13, 13, 11};
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, newSquare.getXCoordinatesForShapeDraw());
		int[] expectedYCoords = {11, 11, 13, 13};
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, newSquare.getYCoordinatesForShapeDraw());
	}
	
	
	public void checkExpectedPointCoordinatesAgainstActual(int[] expectedCoords, int[] actualCoords) {
		assert(expectedCoords.length == actualCoords.length);
		for(int i = 0; i<expectedCoords.length; i++) {
			assert(Math.abs(expectedCoords[i]-actualCoords[i]) < acceptableFloatingPointErrorMargin);
		}
	}
	
	public void checkCenterPositionCorrect(Point2D.Double expectedCenter, Point2D.Double actualCenter) {
		assert(Math.abs(expectedCenter.x-actualCenter.x) < acceptableFloatingPointErrorMargin);  
		assert(Math.abs(expectedCenter.y-actualCenter.y) < acceptableFloatingPointErrorMargin);  
	}
}
