package main.display.leftPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class FocusSelectionPanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	ShapeDrawSpecifications specs;
	PreviewCanvas preview;
	public FocusSelectionPanel(ShapeDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel focusSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		focusSelectLabelContainer.setMaximumSize(new Dimension(10000, 50));
		JLabel focusSelectLabel = new JLabel("Focus can be changed by clicking on the preview to the right.");		
		focusSelectLabelContainer.add(focusSelectLabel);
		this.add(focusSelectLabelContainer);
	}
}
