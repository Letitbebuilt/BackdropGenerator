package main.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import main.shapes.Shape;

public class PreviewCanvas extends JPanel{
	private static final Random rand = new Random();
	private static final long serialVersionUID = 3297155955034907974L;
	private Color baseColor = Color.BLACK;
	private ArrayList<Color> shapeColors = new ArrayList<>();
	private ArrayList<Shape> baseShapes = new ArrayList<>();
	private double maxScaler = 3;
	private double minScaler = .33;
	private int baseSize = 15;
	private Point2D.Double centerPoint = new Point2D.Double(300, 300);
	
	public PreviewCanvas() {
		super();
		this.setPreferredSize(new Dimension(600, 600));
		shapeColors.addAll(List.of(Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN));
		baseShapes.addAll(List.of(
				Shape.getStellatedPolygon(4, baseSize, baseSize/3, centerPoint),

				Shape.getStellatedPolygon(5, baseSize, baseSize/2, centerPoint),

				Shape.getStellatedPolygon(3, baseSize, baseSize/3, centerPoint)
			));
	}
	 
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(baseColor);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setStroke(new BasicStroke(2));
        for(int groups = 1; groups < 12; groups++) {
        	for(int numPerGroup = 0; numPerGroup < 5; numPerGroup++) {
        		Shape reference = baseShapes.get(rand.nextInt(baseShapes.size()));
        		Shape drawTarget = reference.rotateAroundCenter(rand.nextInt(360))
        				.scale(rand.nextDouble(minScaler, maxScaler))
        				.moveCenterTo(new Point2D.Double(centerPoint.x + rand.nextInt(-groups*baseSize*3, groups*baseSize*3), centerPoint.y + rand.nextInt(-groups*baseSize*4, groups*baseSize*4)));
        		Color baseColor = shapeColors.get(rand.nextInt(shapeColors.size()));
        		Color translucentColor = new Color(getColorWithRandomMod(baseColor.getRed()), getColorWithRandomMod(baseColor.getGreen()), getColorWithRandomMod(baseColor.getBlue()), 30);
        		g2.setColor(translucentColor);
        		g2.fillPolygon(drawTarget.getXCoordinatesForShapeDraw(), drawTarget.getYCoordinatesForShapeDraw(), drawTarget.getXCoordinatesForShapeDraw().length);
        		
        		for(int glow = 0; glow < 9; glow++) {
        			drawTarget = drawTarget.scale(1.05);
            		translucentColor = new Color(translucentColor.getRed(), translucentColor.getGreen(), translucentColor.getBlue(), translucentColor.getAlpha()-3);
            		g2.setColor(translucentColor);
            		g2.fillPolygon(drawTarget.getXCoordinatesForShapeDraw(), drawTarget.getYCoordinatesForShapeDraw(), drawTarget.getXCoordinatesForShapeDraw().length);
        		}

        	}
        }
        g2.dispose();
    }
	
	private int getColorWithRandomMod(int baseColor) {
		return Math.max(0, Math.min(255, baseColor+rand.nextInt(-50, 50)));
	}
}
