package main.display.leftPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.display.PreviewCanvas;
import main.shapes.LayerDrawSpecifications;

public class FocusSelectionPanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	LayerDrawSpecifications specs;
	PreviewCanvas preview;
	JLabel currentLayerLabel;
	public FocusSelectionPanel(LayerDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel flowContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel labelContainer = new JPanel();
		labelContainer.setLayout(new BoxLayout(labelContainer, BoxLayout.Y_AXIS));
		labelContainer.setMaximumSize(new Dimension(10000, 50));
		JLabel focusSelectLabel = new JLabel("Focus can be changed by clicking on the preview to the right.");	
		currentLayerLabel = new JLabel("Current Layer: ");	
		labelContainer.add(focusSelectLabel);
		labelContainer.add(currentLayerLabel);
		flowContainer.add(labelContainer);
		this.add(flowContainer);
	}
	
	public void setCurrentLayer(String currentLayer) {
		currentLayerLabel.setText("Current Layer: "+currentLayer);
	}
}
