package test;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.shapes.Shape;

public class ShapeTest {
	Shape testSquareOriginCenter;
	Shape testSquareAltCenter;

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
		int[] actualCoords = testSquareOriginCenter.getXCoordinatesForShapeDraw();
		
		assert(expectedCoords.length == actualCoords.length);
		for(int i = 0; i<expectedCoords.length; i++) {
			assert(expectedCoords[i] == actualCoords[i]);
		}
	}
	
	@Test
	public void getYCoordinatesForShapeDraw_returnCoords() {
		int[] expectedCoords = {-1, -1, 1, 1};
		int[] actualCoords = testSquareOriginCenter.getYCoordinatesForShapeDraw();
		
		assert(expectedCoords.length == actualCoords.length);
		for(int i = 0; i<expectedCoords.length; i++) {
			assert(expectedCoords[i] == actualCoords[i]);
		}
	}
	
	@Test
	public void getCenter_squareCenteredOnOrigin_returnCenter() {
		assert(testSquareOriginCenter.getCenter().equals(new Point2D.Double(0,0)));
	}
	
	
	
	@Test
	public void rotateAroundCenter_squareRotated90Degrees_newSquareCenterSamePointsRotated() {
		Shape newSquare = testSquareOriginCenter.rotateAroundCenter(90);
		assert(Math.abs(newSquare.getCenter().x) < 0.001);  
		assert(Math.abs(newSquare.getCenter().y) < 0.001);  
		
		int[] expectedXCoords = {1, 1, -1, -1};
		int[] actualXCoords = newSquare.getXCoordinatesForShapeDraw();
		for(int i = 0; i<expectedXCoords.length; i++) {
			assert(Math.abs(expectedXCoords[i]-actualXCoords[i]) < 0.001);
		}
		
		int[] expectedYCoords = {-1, 1, 1, -1};
		int[] actualYCoords = newSquare.getYCoordinatesForShapeDraw();
		for(int i = 0; i<expectedYCoords.length; i++) {
			assert(Math.abs(expectedYCoords[i]-actualYCoords[i]) < 0.001);
		}
		
		newSquare = testSquareAltCenter.rotateAroundCenter(90);
		assert(Math.abs(1-newSquare.getCenter().x) < 0.001);  
		assert(Math.abs(1-newSquare.getCenter().y) < 0.001);  
		
	}
}
