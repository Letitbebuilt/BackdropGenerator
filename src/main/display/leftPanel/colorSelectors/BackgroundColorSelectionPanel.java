package main.display.leftPanel.colorSelectors;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class BackgroundColorSelectionPanel extends JPanel{
	private static final long serialVersionUID = -3993717702437763584L;
	ShapeDrawSpecifications specs;
	PreviewCanvas preview;
	public BackgroundColorSelectionPanel(ShapeDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel colorSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorSelectLabelContainer.setMaximumSize(new Dimension(10000, 150));
		JLabel colorSelectLabel = new JLabel("Select background color below:");		
		colorSelectLabelContainer.add(colorSelectLabel);
		this.add(colorSelectLabelContainer);


		JPanel colorSelectContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ColorSelectionBox colorSelectPanel = new ColorSelectionBox(e -> {
			specs.setBackgroundColor(e);
			specs.clearImage();
			preview.setShapesToDraw(specs.getImage());
			preview.baseColor = specs.baseColor;
			preview.repaint();
		});
		colorSelectContainer.add(colorSelectPanel);
		this.add(colorSelectContainer);
	}
	
	
}
