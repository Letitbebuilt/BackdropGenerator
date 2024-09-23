package main.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.shapes.ShapeDrawSpecifications;

public class PreviewCanvas extends JPanel{
	private static final long serialVersionUID = 3297155955034907974L;
	
	BufferedImage shapesToDraw;
	public Color baseColor = Color.black;
	Dimension dimensions = new Dimension(600, 600);
	ShapeDrawSpecifications specs;
	public PreviewCanvas(ShapeDrawSpecifications specs) {
		super();
		this.specs = specs;
		this.setPreferredSize(dimensions);
		
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				
				double xModRatio = specs.imageScale.getWidth() / dimensions.getWidth();
				double yModRatio = specs.imageScale.getWidth() / dimensions.getWidth();
				if(e.getPoint().x*xModRatio < specs.imageScale.getWidth() && e.getPoint().y*yModRatio < specs.imageScale.getHeight()) {
					specs.centerPoint = new Point2D.Double(e.getPoint().x*xModRatio, e.getPoint().y*yModRatio);
					specs.clearImage();
					setShapesToDraw(specs.getImage());
					baseColor = specs.baseColor;
					repaint();
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	public void setShapesToDraw(BufferedImage shapes) {
		shapesToDraw = shapes;
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setStroke(new BasicStroke(2));
        double widthHeightRatio = ((double)shapesToDraw.getHeight())/(double)shapesToDraw.getWidth();
        g2.drawImage(shapesToDraw, 0, 0, (int)dimensions.getWidth(), (int)(dimensions.getHeight()*widthHeightRatio), null);


		double xModRatio = dimensions.getWidth() / specs.imageScale.getWidth();
		double yModRatio = dimensions.getWidth() / specs.imageScale.getWidth() ;
		Point2D.Double p = new Point2D.Double(specs.centerPoint.x*xModRatio, specs.centerPoint.y*yModRatio);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.black);
        g2.drawLine((int)p.x-8, (int)p.y, (int)p.x+8, (int)p.y);
        g2.drawLine((int)p.x, (int)p.y-8, (int)p.x, (int)p.y+8);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.white);
        g2.drawLine((int)p.x-7, (int)p.y, (int)p.x+7, (int)p.y);
        g2.drawLine((int)p.x, (int)p.y-7, (int)p.x, (int)p.y+7);
        g2.dispose();
    }
}
