package main.display.leftPanel.colorSelectors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class ShapeColorSelectionPanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	ShapeDrawSpecifications specs;
	PreviewCanvas preview;
	
	JPanel selectedColorButtonPanel;
	ArrayList<Color> selectedColors = new ArrayList<>();
	int buttonSize = 20;
	int maxRows = 3;
	int maxCols = 9;
	public ShapeColorSelectionPanel(ShapeDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ColorSelectionBox colorBox = new ColorSelectionBox(e -> addColorToList(e));
		
		
		JPanel colorSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorSelectLabelContainer.setPreferredSize(new Dimension(buttonSize*maxCols+maxCols*4+colorBox.width, 50));
		JLabel colorSelectLabel = new JLabel("Select desired colors below:");		
		colorSelectLabelContainer.add(colorSelectLabel);
		this.add(colorSelectLabelContainer);

		JPanel horizontalLayout = new JPanel();
		horizontalLayout.setLayout(new BoxLayout(horizontalLayout, BoxLayout.X_AXIS));
		horizontalLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(horizontalLayout);
		horizontalLayout.add(colorBox);
		selectedColorButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		horizontalLayout.add(selectedColorButtonPanel);
	}
	
	public void addColorToList(Color c) {
		if(selectedColors.size() < maxRows*maxCols) {
			specs.clearShapeColors();
			selectedColors.add(c);
			refreshSelectedColorPanel();
		}
	}
	
	private void resetPreview() {
		specs.clearShapeColors();
		selectedColors.stream().forEach(e -> specs.addShapeColor(e));
		specs.clearImage();
		preview.setShapesToDraw(specs.getImage());
		preview.baseColor = specs.baseColor;
		preview.repaint();
	}
	
	private void refreshSelectedColorPanel() {
		selectedColorButtonPanel.removeAll();
		//for some reason, GridLayout isn't working for selectedColorButtonPanel. So, manual layout it is
		int row = 0;
		int col = 0;
		for (Color c: selectedColors) {
			
			JButton colorRemovalButton = new JButton("x");
			colorRemovalButton.setFont(new Font("Arial", Font.BOLD, 12));
			colorRemovalButton.setSize(new Dimension (buttonSize, buttonSize));
			colorRemovalButton.setBackground(c);
			colorRemovalButton.setBorder(null);
			int colorSum = (c.getRed()+c.getGreen()+c.getBlue()) / 3;
			colorRemovalButton.setForeground(colorSum > 122? Color.BLACK: Color.white);
			colorRemovalButton.setLocation(col*buttonSize+col*2+2, row*buttonSize+row*2);
			colorRemovalButton.addActionListener(e -> {
				selectedColors.remove(c);
				refreshSelectedColorPanel();
			});
			selectedColorButtonPanel.add(colorRemovalButton);
			col++;
			if(col >= maxCols) {
				col = 0;
				row++;
			}
		}
		
		selectedColorButtonPanel.repaint();
		resetPreview();
	}
}
