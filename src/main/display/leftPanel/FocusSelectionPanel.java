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
		JLabel focusSelectLabel = new JLabel("Select desired focus Point:");		
		focusSelectLabelContainer.add(focusSelectLabel);
		this.add(focusSelectLabelContainer);

		
		JPanel focusSelectButtons = new JPanel();
		ButtonGroup focusSelectGroup = new ButtonGroup();
		focusSelectButtons.setMaximumSize(new Dimension(10000, 150));
		this.add(focusSelectButtons);
		focusSelectButtons.setLayout(new GridLayout(1, 5));	
		focusSelectButtons.add(createRadioButtonForFocusSelect(true, "Top Left", 1d/6d, 1d/6d, focusSelectGroup));
		focusSelectButtons.add(createRadioButtonForFocusSelect(false, "Top Right", 5d/6d, 1d/6d, focusSelectGroup));
		focusSelectButtons.add(createRadioButtonForFocusSelect(false, "Center", .5, .5, focusSelectGroup));
		focusSelectButtons.add(createRadioButtonForFocusSelect(false, "Bottom Left", 1d/6d, 5d/6d, focusSelectGroup));
		focusSelectButtons.add(createRadioButtonForFocusSelect(false, "Bottom Right", 5d/6d, 5d/6d, focusSelectGroup));
		
	}
	private JPanel createRadioButtonForFocusSelect(boolean selected, String labelText, double widthMod, double heightMod, ButtonGroup group) {
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel(labelText);
		option.add(label);
		option.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		JRadioButton radioButton = new JRadioButton();
		radioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				specs.centerMod = new Point2D.Double(widthMod, heightMod);
				specs.recalculateFocus();
				specs.clearImage();
				preview.setShapesToDraw(specs.getImage());
				preview.baseColor = specs.baseColor;
				preview.repaint();
			}
			
		});
		option.add(radioButton);
		group.add(radioButton);
		radioButton.setSelected(selected);
		return option;
	}
}
