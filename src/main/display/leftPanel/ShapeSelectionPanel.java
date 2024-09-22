package main.display.leftPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.display.GroupExecutionJCheckBox;
import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class ShapeSelectionPanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	ShapeDrawSpecifications specs;
	PreviewCanvas preview;
	public ShapeSelectionPanel(ShapeDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel shapeSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		shapeSelectLabelContainer.setMaximumSize(new Dimension(10000, 50));
		JLabel shapeSelectLabel = new JLabel("Select desired shapes below:");
		shapeSelectLabelContainer.add(shapeSelectLabel);
		this.add(shapeSelectLabelContainer);

		JPanel shapeSelectPanel = new JPanel();
		shapeSelectPanel.setMaximumSize(new Dimension(10000, 150));
		this.add(shapeSelectPanel);
		shapeSelectPanel.setLayout(new GridLayout(2, 5));
		shapeSelectPanel.add(createOptionForShapeSelect(true, "Triangle", false, 3));
		shapeSelectPanel.add(createOptionForShapeSelect(true, "Square", false, 4));
		shapeSelectPanel.add(createOptionForShapeSelect(true, "Pentagon", false, 5));
		shapeSelectPanel.add(createOptionForShapeSelect(true, "Hexagon", false, 6));
		shapeSelectPanel.add(createOptionForShapeSelect(false, "Octagon", false, 8));
		shapeSelectPanel.add(createOptionForShapeSelect(false, "3-Star", true, 3));
		shapeSelectPanel.add(createOptionForShapeSelect(false, "4-Star", true, 4));
		shapeSelectPanel.add(createOptionForShapeSelect(false, "5-Star", true, 5));
		shapeSelectPanel.add(createOptionForShapeSelect(false, "6-Star", true, 6));
		shapeSelectPanel.add(createOptionForShapeSelect(false, "8-Star", true, 8));
	}
	
	private JPanel createOptionForShapeSelect(boolean selected, String labelText, boolean stellated, int sides) {
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel(labelText);
		option.add(label);
		option.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		option.add(new GroupExecutionJCheckBox(selected) {

			@Override
			public void performBeforeGroupUpdate() {
				specs.clearBaseShapes();
			}

			@Override
			public void performUpdate() {
				if(stellated) {
					specs.addStellatedPolygon(sides);
				}
				else {
					specs.addRegularPolygon(sides);
				}
			}

			@Override
			public void performAfterGroupUpdate() {
				specs.clearImage();
				preview.setShapesToDraw(specs.getImage());
				preview.baseColor = specs.baseColor;
				preview.repaint();
			}
			
		});
		return option;
	}
}
