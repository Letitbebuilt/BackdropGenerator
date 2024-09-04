package main.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import main.shapes.Shape;

public class PreviewCanvas extends JPanel{
	
	private static final long serialVersionUID = 3297155955034907974L;

	public PreviewCanvas() {
		super();
		this.setPreferredSize(new Dimension(200, 200));
	}
	 
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        Shape hexagon = Shape.getRegularPolygon(6, 15, new Point2D.Double(100, 100));
        g2.drawPolygon(hexagon.getXCoordinatesForShapeDraw(), hexagon.getYCoordinatesForShapeDraw(), hexagon.getXCoordinatesForShapeDraw().length);
        g2.dispose();
    }
}
