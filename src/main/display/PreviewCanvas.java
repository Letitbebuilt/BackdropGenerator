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
import java.util.ArrayList;

import javax.swing.JPanel;

import main.shapes.GeneralDrawSpecifications;
import main.shapes.LayerDrawSpecifications;

public class PreviewCanvas extends JPanel{
	private static final long serialVersionUID = 3297155955034907974L;
	
	public Color baseColor = Color.black;
	Dimension dimensions = new Dimension(600, 600);
	ArrayList<LayerDrawSpecifications> specs;
	LayerDrawSpecifications selectedSpec;
	GeneralDrawSpecifications generalSpecs;
	BufferedImage trueImage = null;
	public PreviewCanvas(ArrayList<LayerDrawSpecifications> specs, GeneralDrawSpecifications generalSpecs) {
		super();
		this.specs = specs;
		this.generalSpecs = generalSpecs;
		this.setPreferredSize(dimensions);
	}
	
	public void setSelectedSpec(LayerDrawSpecifications spec) {
		this.selectedSpec = spec;
		
		for(MouseListener listener: this.getMouseListeners()) {
			this.removeMouseListener(listener);
		}
		System.out.println(this.getMouseListeners().length);
		this.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {
				double width = generalSpecs.imageScale.getWidth();
				double height = generalSpecs.imageScale.getHeight();
				double ratio = generalSpecs.imageScale.getWidth() / dimensions.getWidth();
				
				double xMod = width > height ? 1: width / height;
				double yMod = height > width ? 1: height / width;
				
				if(e.getPoint().x/xMod < dimensions.getWidth() && e.getPoint().y/yMod < dimensions.getHeight()) {
					selectedSpec.centerPoint = new Point2D.Double(e.getPoint().x*ratio, e.getPoint().y*ratio);
					baseColor = generalSpecs.baseColor;
					repaint();
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D displayGraphcis = (Graphics2D) g.create();
        trueImage = new BufferedImage((int)generalSpecs.imageScale.getWidth(), (int)generalSpecs.imageScale.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = (Graphics2D) trueImage.getGraphics();
        
        displayGraphcis.setColor(Color.DARK_GRAY);
        displayGraphcis.fillRect(0, 0, this.getWidth(), this.getHeight());

        double widthHeightRatio = ((double)generalSpecs.imageScale.getHeight())/(double)generalSpecs.imageScale.getWidth();
        displayGraphcis.setColor(generalSpecs.baseColor);
        displayGraphcis.fillRect(0, 0, (int)dimensions.getWidth(), (int)(dimensions.getHeight()*widthHeightRatio));
        imageGraphics.setColor(generalSpecs.baseColor);
        imageGraphics.fillRect(0, 0, (int)generalSpecs.imageScale.getWidth(), (int)(generalSpecs.imageScale.getHeight()));
        
        
        displayGraphcis.setStroke(new BasicStroke(2));
        for(LayerDrawSpecifications spec: specs) {
        	BufferedImage image = spec.getImage();
        	displayGraphcis.drawImage(image, 0, 0, (int)dimensions.getWidth(), (int)(dimensions.getHeight()*widthHeightRatio), null);
            imageGraphics.drawImage(image, 0, 0, (int)image.getWidth(), (int)(image.getHeight()), null);
        }
        


		double xModRatio = dimensions.getWidth() / selectedSpec.imageScale.getWidth();
		double yModRatio = dimensions.getWidth() / selectedSpec.imageScale.getWidth() ;
		Point2D.Double p = new Point2D.Double(selectedSpec.centerPoint.x*xModRatio, selectedSpec.centerPoint.y*yModRatio);
        displayGraphcis.setStroke(new BasicStroke(4));
        displayGraphcis.setColor(Color.black);
        displayGraphcis.drawLine((int)p.x-8, (int)p.y, (int)p.x+8, (int)p.y);
        displayGraphcis.drawLine((int)p.x, (int)p.y-8, (int)p.x, (int)p.y+8);
        displayGraphcis.setStroke(new BasicStroke(2));
        displayGraphcis.setColor(Color.white);
        displayGraphcis.drawLine((int)p.x-7, (int)p.y, (int)p.x+7, (int)p.y);
        displayGraphcis.drawLine((int)p.x, (int)p.y-7, (int)p.x, (int)p.y+7);
        displayGraphcis.dispose();
    }
	
	public BufferedImage getImage() {
		return trueImage;
	}
	
}
