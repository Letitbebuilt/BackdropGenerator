package main.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PreviewCanvas extends JPanel{
	private static final long serialVersionUID = 3297155955034907974L;
	
	BufferedImage shapesToDraw;
	public Color baseColor = Color.black;
	Dimension dimensions = new Dimension(600, 600);
	public PreviewCanvas() {
		super();
		this.setPreferredSize(dimensions);
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
        g2.dispose();
    }
}
