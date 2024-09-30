package test;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.shapes.Polygon;

public class PolygonTest {
	Polygon testSquareOriginCenter;
	Polygon testSquareAltCenter;
	double acceptableFloatingPointErrorMargin = 0.0001;
	@BeforeEach
	public void setup() {
		testSquareOriginCenter = new Polygon(
			new Point2D.Double(-1, -1),
			new Point2D.Double(1, -1),
			new Point2D.Double(1, 1),
			new Point2D.Double(-1, 1)
			);
		testSquareAltCenter = new Polygon(
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
		Polygon hexagon = Polygon.getRegularPolygon(6, 5, new Point2D.Double(10, 10));
		int[] expectedXCoords = {15, 13, 8, 5, 7, 12}; //remember, drawing points are rounded
		int[] expectedYCoords = {10, 14, 14, 10, 6, 6};
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, hexagon.getXCoordinatesForShapeDraw());
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, hexagon.getYCoordinatesForShapeDraw());
	}
	
	@Test
	public void getStellatedPolygon_hexagon_pointsWhereExpected() {
		Polygon star = Polygon.getStellatedPolygon(5, 10, 5, new Point2D.Double(10, 10));
		int[] expectedXCoords = {20, 14, 13, 8, 2, 5, 2, 8, 13, 14}; 
		int[] expectedYCoords = {10, 13, 20, 15, 16, 10, 4, 5, 0, 7}; 
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, star.getXCoordinatesForShapeDraw());
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, star.getYCoordinatesForShapeDraw());
		
		
	}
	
	
	@Test
	public void rotateAroundCenter_squareRotated90Degrees_newSquareCenterSamePointsRotated() {
		Polygon newSquare = (Polygon) testSquareOriginCenter.rotateAroundCenter(90);
		checkCenterPositionCorrect(new Point2D.Double(0, 0), newSquare.getCenter());
		
		int[] expectedXCoords = {1, 1, -1, -1};
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, newSquare.getXCoordinatesForShapeDraw());
		int[] expectedYCoords = {-1, 1, 1, -1};		
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, newSquare.getYCoordinatesForShapeDraw());
		
		newSquare = (Polygon) testSquareAltCenter.rotateAroundCenter(90);
		checkCenterPositionCorrect(new Point2D.Double(1, 1), newSquare.getCenter());
	}
	
	@Test
	public void moveCenterTo_centerMoved_pointsUpdatedRelativeToNewCenter() {
		Polygon newSquare = (Polygon) testSquareOriginCenter.moveCenterTo(new Point2D.Double(12, 12));
		int[] expectedXCoords = {11, 13, 13, 11};
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, newSquare.getXCoordinatesForShapeDraw());
		int[] expectedYCoords = {11, 11, 13, 13};
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, newSquare.getYCoordinatesForShapeDraw());
	}
	
	
	@Test
	public void scale_scaledDownByHalf_pointsMovedCenterConstant() {
		Polygon testSquare = Polygon.getRegularPolygon(4, 2, new Point2D.Double(2,2));
		Polygon newSquare = (Polygon) testSquare.scale(0.5);
		int[] expectedXCoords = {3, 2, 1, 2}; 
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, newSquare.getXCoordinatesForShapeDraw());
		int[] expectedYCoords = {2, 3, 2, 1};
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, newSquare.getYCoordinatesForShapeDraw());
		checkCenterPositionCorrect(new Point2D.Double(2, 2), newSquare.getCenter());
	}
	
	@Test
	public void scale_scaledDownByNegative_PointConfigurationInverted() {
		Polygon testSquare = Polygon.getRegularPolygon(4, 2, new Point2D.Double(2,2));
		Polygon newSquare = (Polygon) testSquare.scale(-1);
		int[] expectedXCoords = {0, 2, 4, 2}; 
		checkExpectedPointCoordinatesAgainstActual(expectedXCoords, newSquare.getXCoordinatesForShapeDraw());
		int[] expectedYCoords = {2, 0, 2, 4};
		checkExpectedPointCoordinatesAgainstActual(expectedYCoords, newSquare.getYCoordinatesForShapeDraw());
		checkCenterPositionCorrect(new Point2D.Double(2, 2), newSquare.getCenter());

	}
	
	@Test
	public void createCopy_copyIndependantOfOriginal() {
		Polygon testSquare = Polygon.getRegularPolygon(4, 2, new Point2D.Double(2,2));
		Polygon newSquare = (Polygon) testSquare.createCopy();
		checkExpectedPointCoordinatesAgainstActual(testSquare.getXCoordinatesForShapeDraw(), newSquare.getXCoordinatesForShapeDraw());
		checkExpectedPointCoordinatesAgainstActual(testSquare.getYCoordinatesForShapeDraw(), newSquare.getYCoordinatesForShapeDraw());
		assert(testSquare.getXCoordinatesForShapeDraw() != newSquare.getXCoordinatesForShapeDraw());
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
