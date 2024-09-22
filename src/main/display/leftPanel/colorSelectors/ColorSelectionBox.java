package main.display.leftPanel.colorSelectors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;

import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class ColorSelectionBox extends JPanel{ 
	private static final long serialVersionUID = 4500249599732469493L;
	
	int tintGradient = 3;
	int tintMaxModifier = 180;
	int colorGradiant = 5;
	int selectionBoxSize = 10;
	int width = selectionBoxSize * colorGradiant * 6;
	int height = selectionBoxSize*(tintGradient*2+1);
    BufferedImage selfReferentialImage;
	ArrayList<Color> colorBaseSteps = new ArrayList<>(List.of(
    		new Color(255, 0, 0),
    		new Color(255, 255, 0),
    		new Color(0, 255, 0),
    		new Color(0, 255, 255),
    		new Color(0, 0, 255),
    		new Color(255, 0, 255)
    	));
	
	public ColorSelectionBox(Consumer<Color> action) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Point mousPos = e.getPoint();
				selfReferentialImage = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
				Graphics2D g2 = (Graphics2D) selfReferentialImage.createGraphics();
		        g2.setClip(mousPos.x, mousPos.y , 1, 1);
		        printAll(g2);
		        g2.dispose();
		        Color color = new Color(selfReferentialImage.getRGB(mousPos.x, mousPos.y), true);
				action.accept(color);
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

    	double monochromaticModifier = 1;
        for(int i = 0; i<tintGradient*2; i++) {
        	g2.setColor(new Color((int)(255 * monochromaticModifier), (int)(255 * monochromaticModifier), (int)(255 * monochromaticModifier)));
			g2.fillRect(0, i*selectionBoxSize, selectionBoxSize, selectionBoxSize);
			monochromaticModifier -= (double)1/(tintGradient*2);
        }
        g2.setColor(Color.black);
		g2.fillRect(0, selectionBoxSize*tintGradient*2, selectionBoxSize, selectionBoxSize);
		
        int xMod = selectionBoxSize;
        for(int color = 0; color<colorBaseSteps.size(); color++) {
        	double colorModForGradiant = 1/(double)colorGradiant;
        	for(int cGradiant = 0; cGradiant < colorGradiant; cGradiant++) {
        		Color firstColor = colorBaseSteps.get(color);
        		Color secondColor = colorBaseSteps.get((color+1) %colorBaseSteps.size());
        		Color columnColor = new Color(
        				getWeightedColorValue(firstColor.getRed(), secondColor.getRed(), 1d-cGradiant*colorModForGradiant),
        				getWeightedColorValue(firstColor.getGreen(), secondColor.getGreen(), 1d-cGradiant*colorModForGradiant),
        				getWeightedColorValue(firstColor.getBlue(), secondColor.getBlue(), 1d-cGradiant*colorModForGradiant)
        			);
        		int yMod = 0;
        		
        		for(int tintGrad = tintGradient; tintGrad >0; tintGrad--) {
        			int tintToApply = tintMaxModifier*tintGrad / tintGradient;
        			g2.setColor(new Color(getSafeColorSumValue(columnColor.getRed(), tintToApply),
        					getSafeColorSumValue(columnColor.getGreen(), tintToApply),
        					getSafeColorSumValue(columnColor.getBlue(), tintToApply)
        				));
        			g2.fillRect(xMod, yMod, selectionBoxSize, selectionBoxSize);
        			yMod+=selectionBoxSize;
        		}
        		g2.setColor(columnColor);
    			g2.fillRect(xMod, yMod, selectionBoxSize, selectionBoxSize);
    			yMod+=selectionBoxSize;
    			
        		for(int tintGrad = 0; tintGrad < tintGradient; tintGrad++) {
        			int tintToApply = tintMaxModifier*tintGrad / tintGradient;
        			g2.setColor(new Color(getSafeColorSumValue(columnColor.getRed(), -tintToApply),
        					getSafeColorSumValue(columnColor.getGreen(), -tintToApply),
        					getSafeColorSumValue(columnColor.getBlue(), -tintToApply)
        				));
        			g2.fillRect(xMod, yMod, selectionBoxSize, selectionBoxSize);
        			yMod+=selectionBoxSize;
        		}
        		
        		xMod+=selectionBoxSize;
        	}
        }
    }
	
	
	private int getWeightedColorValue(int one, int two, double ratio) {
		return getSafeColorSumValue((int)(one*ratio), (int)(two*(1-ratio)));
	}
	
	private int getSafeColorSumValue(int one, int two) {
		return (int) Math.max(0, Math.min(255, one + two));
	}
}
