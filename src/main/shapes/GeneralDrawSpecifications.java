package main.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GeneralDrawSpecifications {
	public Color baseColor = new Color(0f, 0f, 0f, 1f);
	public Dimension imageScale = new Dimension(600, 600);
	private BufferedImage generatedImage = null;
	
	
	public GeneralDrawSpecifications() {
		
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
		
		generatedImage = new BufferedImage((int)imageScale.getWidth(), (int)imageScale.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) generatedImage.getGraphics();
        g2.setColor(baseColor);
        g2.fillRect(0, 0, generatedImage.getWidth(), generatedImage.getHeight());
       
        g2.dispose();
        return generatedImage;
	}
}
