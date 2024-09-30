package test;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.shapes.Circle;
import main.shapes.Polygon;

public class CircleTest {
	Circle testCircle;
	double acceptableFloatingPointErrorMargin = 0.0001;
	@BeforeEach
	public void setup() {
		testCircle = Circle.getCircle(new Point2D.Double(2, 2), 2);
	}
	
	@Test
	public void rotateAroundCenter_clonesCircle() {
		Circle newCircle = (Circle) testCircle.rotateAroundCenter(12);
		assert(testCircle.radius == newCircle.radius);
		assert(testCircle.getCenter().equals(newCircle.getCenter()));
		assert(testCircle.getCenter() != (newCircle.getCenter()));
	}
	
	@Test
	public void moveCenterTo_centerMoved_newCoordsCorrect() {
		Circle newCircle = (Circle) testCircle.moveCenterTo(new Point2D.Double(3, 3));
		assert(newCircle.getCenter().x == 3 && newCircle.getCenter().y == 3);
		assert(Math.abs(newCircle.radius-2) < acceptableFloatingPointErrorMargin);
	}
	
	@Test
	public void scale_scaled_radiusAdjusted() {
		Circle newCircle = (Circle) testCircle.scale(1.5);
		assert(newCircle.getCenter().x == 2 && newCircle.getCenter().y == 2);
		assert(Math.abs(newCircle.radius-3) < acceptableFloatingPointErrorMargin);
	}
	
	@Test
	public void createCopy_copyMatches() {
		Circle newCircle = (Circle) testCircle.createCopy();
		assert(testCircle.radius == newCircle.radius);
		assert(testCircle.getCenter().equals(newCircle.getCenter()));
		assert(testCircle.getCenter() != (newCircle.getCenter()));
	}
}
