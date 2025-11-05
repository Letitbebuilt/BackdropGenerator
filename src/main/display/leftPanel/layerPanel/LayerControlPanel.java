package main.display.leftPanel.layerPanel;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.display.PreviewCanvas;
import main.display.leftPanel.FocusSelectionPanel;
import main.display.leftPanel.ShapeSelectionPanel;
import main.display.leftPanel.colorSelectors.ShapeColorSelectionPanel;
import main.shapes.LayerDrawSpecifications;

public class LayerControlPanel extends JPanel{

	private static final long serialVersionUID = -7464722965760324654L;
	LayerDrawSpecifications shapeSpecs = new LayerDrawSpecifications();
	public LayerControlPanel(PreviewCanvas preview) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new FocusSelectionPanel(shapeSpecs, preview));
		this.add(createVerticalPadding(10));
		
		this.add(new ShapeSelectionPanel(shapeSpecs, preview));
		this.add(createVerticalPadding(10));	
		
		this.add(new ShapeColorSelectionPanel(shapeSpecs, preview));
		this.add(createVerticalPadding(10));
	}
	
	private JPanel createVerticalPadding(int size) {
		JPanel padding = new JPanel();
		padding.setPreferredSize(new Dimension(0, size));
		return padding;
	}
	
	public LayerDrawSpecifications getSpecs() {
		return shapeSpecs;
	}
}
