package main.display.leftPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.display.GroupExecutionJCheckBox;
import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class ShapeColorSelectionPanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	ShapeDrawSpecifications specs;
	PreviewCanvas preview;
	public ShapeColorSelectionPanel(ShapeDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel colorSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorSelectLabelContainer.setMaximumSize(new Dimension(10000, 50));
		JLabel colorSelectLabel = new JLabel("Select desired colors below:");		
		colorSelectLabelContainer.add(colorSelectLabel);
		this.add(colorSelectLabelContainer);

		JPanel colorSelectPanel = new JPanel();
		colorSelectPanel.setMaximumSize(new Dimension(10000, 150));
		this.add(colorSelectPanel);
		colorSelectPanel.setLayout(new GridLayout(2, 5));
		colorSelectPanel.add(createOptionForColorSelect(true, "Red", Color.red));
		colorSelectPanel.add(createOptionForColorSelect(true, "Orange", Color.orange));
		colorSelectPanel.add(createOptionForColorSelect(false, "Yellow", Color.yellow));
		colorSelectPanel.add(createOptionForColorSelect(false, "Green", Color.green));
		colorSelectPanel.add(createOptionForColorSelect(false, "Blue", Color.blue));
		colorSelectPanel.add(createOptionForColorSelect(false, "Purple", Color.magenta));
		colorSelectPanel.add(createOptionForColorSelect(true, "White", Color.white));
		colorSelectPanel.add(createOptionForColorSelect(true, "Light Gray", Color.LIGHT_GRAY));
		colorSelectPanel.add(createOptionForColorSelect(false, "Dark Gray", Color.DARK_GRAY));
		colorSelectPanel.add(createOptionForColorSelect(false, "Black", Color.black));
	}
	
	private JPanel createOptionForColorSelect(boolean selected, String labelText, Color color) {
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel(labelText);
		option.add(label);
		option.add(new GroupExecutionJCheckBox(selected) {

			@Override
			public void performBeforeGroupUpdate() {
				specs.clearShapeColors();
			}

			@Override
			public void performUpdate() {
				specs.addShapeColor(color);
			}

			@Override
			public void performAfterGroupUpdate() {
				specs.clearImage();
				preview.setShapesToDraw(specs.getImageForDesktop());
				preview.baseColor = specs.baseColor;
				preview.repaint();
			}
			
		});
		return option;
	}
	
}
