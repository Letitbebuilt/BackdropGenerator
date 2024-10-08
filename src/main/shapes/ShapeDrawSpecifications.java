package main.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShapeDrawSpecifications {
	private static final Random rand = new Random();
	public Color baseColor = Color.BLACK;
	private ArrayList<Color> shapeColors = new ArrayList<>();
	private ArrayList<Shape> baseShapes = new ArrayList<>();
	private double maxScaler = 3;
	private double minScaler = .33;
	public int baseSize = 15; 
	public Dimension imageScale = new Dimension(600, 600);
	public Point2D.Double centerPoint = new Point2D.Double(imageScale.width/2, imageScale.height/2);
	private int numGroups = (int)imageScale.getWidth()/baseSize;
	private int numPerGroup = 5;
	private int baseTransparency = 9;
	private int fadeIntervals = baseTransparency;
	private int fadeRate = baseTransparency/fadeIntervals;
	private int colorVariability = 15;
	private BufferedImage generatedImage = null;
	
	
	public ShapeDrawSpecifications() {
		shapeColors.addAll(List.of(Color.LIGHT_GRAY));
		baseShapes.addAll(List.of(
				Polygon.getRegularPolygon(3, baseSize, centerPoint),
				Polygon.getRegularPolygon(4, baseSize, centerPoint),
				Polygon.getRegularPolygon(5, baseSize, centerPoint),
				Polygon.getRegularPolygon(6, baseSize, centerPoint)
			));
	}
	public void clearBaseShapes() {
		baseShapes = new ArrayList<>();
	}
	
	public void addStellatedPolygon(int stellations) {
		baseShapes.add(Polygon.getStellatedPolygon(stellations, baseSize, (stellations < 5?   baseSize/3: baseSize/2), centerPoint));
	}
	
	public void addRegularPolygon(int sides) {
		baseShapes.add(Polygon.getRegularPolygon(sides, baseSize, centerPoint));
	}
	
	public void addCircle() {
		baseShapes.add(Circle.getCircle(centerPoint, baseSize));
	}
	
	public void clearShapeColors() {
		shapeColors = new ArrayList<>();
	}
	
	public void addShapeColor(Color c) {
		shapeColors.add(c);
	}
	
	public void setBackgroundColor(Color c) {
		baseColor = (c == null? Color.black:c);
	}
	
	public void clearImage() {
		generatedImage = null;
	}
	
	public BufferedImage getImage() {
		if(generatedImage != null) { 
			return generatedImage;
		}
		
		generatedImage = new BufferedImage((int)imageScale.getWidth(), (int)imageScale.getHeight(), BufferedImage.TYPE_INT_RGB);
		ArrayList<Shape> shapes = generateShapesMatchingSpecs();
		Graphics2D g2 = (Graphics2D) generatedImage.getGraphics();
        g2.setColor(baseColor);
        g2.fillRect(0, 0, generatedImage.getWidth(), generatedImage.getHeight());
        g2.setStroke(new BasicStroke(2));
        for(Shape shape: shapes) {
            g2.setColor(shape.color);
        	if(shape instanceof Polygon) {
        		Polygon polygon = (Polygon) shape;
            	g2.fillPolygon(polygon.getXCoordinatesForShapeDraw(), polygon.getYCoordinatesForShapeDraw(), polygon.getXCoordinatesForShapeDraw().length);
        	}
        	else if(shape instanceof Circle) {
        		Circle circle = (Circle) shape;
        		g2.fillOval((int)(circle.center.x - circle.radius), 
        				    (int)(circle.center.y - circle.radius),
        				    (int)(circle.radius*2), 
        				    (int)(circle.radius*2));
        	}
        	
        }
        g2.dispose();
        return generatedImage;
	}
	
	private ArrayList<Shape> generateShapesMatchingSpecs(){
		if(baseShapes.isEmpty() || shapeColors.isEmpty()) {
			return new ArrayList<>();
		}
		ArrayList<Shape> shapes = new ArrayList<>();
		for(int groups = 1; groups <= numGroups; groups++) {
	      	for(int numPerShape = 0; numPerShape < this.numPerGroup; numPerShape++) {
	      		Shape reference = baseShapes.get(rand.nextInt(baseShapes.size()));
	      		Shape drawTarget = reference.rotateAroundCenter(rand.nextInt(360))
	      				.scale(rand.nextDouble(minScaler, maxScaler))
	      				.moveCenterTo(new Point2D.Double(centerPoint.x + rand.nextInt(-groups*baseSize*3, groups*baseSize*3), centerPoint.y + rand.nextInt(-groups*baseSize*4, groups*baseSize*4)));
	      		Color baseColor = shapeColors.get(rand.nextInt(shapeColors.size()));
	      		Color translucentColor = new Color(getColorWithRandomMod(baseColor.getRed()), getColorWithRandomMod(baseColor.getGreen()), getColorWithRandomMod(baseColor.getBlue()), 30);
	      		drawTarget.color = translucentColor;
	      		shapes.add(drawTarget);
	      		for(int glow = 0; glow < fadeIntervals; glow++) {
	      			drawTarget = drawTarget.scale(1.02);
	          		translucentColor = new Color(translucentColor.getRed(), translucentColor.getGreen(), translucentColor.getBlue(), translucentColor.getAlpha()-fadeRate);
	          		drawTarget.color = translucentColor;
	          		shapes.add(drawTarget);
	      		}
	      	}
		}
		
		return shapes;
	}
	
	private int getColorWithRandomMod(int baseColor) {
		return Math.max(0, Math.min(255, baseColor+rand.nextInt(-colorVariability, colorVariability)));
	}
}
