package main.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.shapes.ShapeDrawSpecifications;

public class OverviewPanel extends JPanel{
	private static final long serialVersionUID = -1079777852585779396L;
	private PreviewCanvas preview;
	OverviewPanel selfRef;
	ShapeDrawSpecifications shapeSpecs = new ShapeDrawSpecifications();
	BufferedImage shapesToDraw = shapeSpecs.getImageForDesktop();
	public OverviewPanel() {
		super();
		shapeSpecs.imageScale = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.add(getLeftSidePanels());
		this.add(getRightSidePanels());
		selfRef = this;
	}

	private JPanel getLeftSidePanels() {
		int leftSideWidth = 450;
		JPanel leftSide = new JPanel();
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		
		JPanel shapeSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		shapeSelectLabelContainer.setMaximumSize(new Dimension(leftSideWidth, 50));
		JLabel shapeSelectLabel = new JLabel("Select desired shapes below:");
		shapeSelectLabelContainer.add(shapeSelectLabel);
		leftSide.add(shapeSelectLabelContainer);

		JPanel shapeSelectPanel = new JPanel();
		shapeSelectPanel.setMaximumSize(new Dimension(leftSideWidth, 150));
		leftSide.add(shapeSelectPanel);
		shapeSelectPanel.setLayout(new GridLayout(2, 5));
		shapeSelectPanel.add(createOptionForShapeSelect("Triangle", false, 3));
		shapeSelectPanel.add(createOptionForShapeSelect("Square", false, 4));
		shapeSelectPanel.add(createOptionForShapeSelect("Pentagon", false, 5));
		shapeSelectPanel.add(createOptionForShapeSelect("Hexagon", false, 6));
		shapeSelectPanel.add(createOptionForShapeSelect("Octagon", false, 8));
		shapeSelectPanel.add(createOptionForShapeSelect("3-Star", true, 3));
		shapeSelectPanel.add(createOptionForShapeSelect("4-Star", true, 4));
		shapeSelectPanel.add(createOptionForShapeSelect("5-Star", true, 5));
		shapeSelectPanel.add(createOptionForShapeSelect("6-Star", true, 6));
		shapeSelectPanel.add(createOptionForShapeSelect("8-Star", true, 8));

		leftSide.add(createVerticalPadding(50));	
		
		JPanel colorSelectLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorSelectLabelContainer.setMaximumSize(new Dimension(leftSideWidth, 50));
		JLabel colorSelectLabel = new JLabel("Select desired colors below:");		
		colorSelectLabelContainer.add(colorSelectLabel);
		leftSide.add(colorSelectLabelContainer);

		JPanel colorSelectPanel = new JPanel();
		colorSelectPanel.setMaximumSize(new Dimension(leftSideWidth, 150));
		leftSide.add(colorSelectPanel);
		colorSelectPanel.setLayout(new GridLayout(2, 5));
		colorSelectPanel.add(createOptionForColorSelect("Red", Color.red));
		colorSelectPanel.add(createOptionForColorSelect("Orange", Color.orange));
		colorSelectPanel.add(createOptionForColorSelect("Yellow", Color.yellow));
		colorSelectPanel.add(createOptionForColorSelect("Green", Color.green));
		colorSelectPanel.add(createOptionForColorSelect("Blue", Color.blue));
		colorSelectPanel.add(createOptionForColorSelect("Purple", Color.magenta));
		colorSelectPanel.add(createOptionForColorSelect("White", Color.white));
		colorSelectPanel.add(createOptionForColorSelect("Light Gray", Color.LIGHT_GRAY));
		colorSelectPanel.add(createOptionForColorSelect("Dark Gray", Color.DARK_GRAY));
		colorSelectPanel.add(createOptionForColorSelect("Black", Color.black));

		leftSide.add(createVerticalPadding(200));
		
		leftSide.add(getButtonPanel());
		
		leftSide.add(createVerticalPadding(200));

		JPanel legal = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel legalVert = new JPanel();
		legalVert.setLayout(new BoxLayout(legalVert, BoxLayout.Y_AXIS));
		legalVert.add(new JLabel("Application created by Samuel Vega"));
		legalVert.add(new JLabel("Distributed under CC BY-NC-SA 4.0."));
		legalVert.add(new JLabel("https://creativecommons.org/licenses/by-nc-sa/4.0/"));
		legal.add(legalVert);
		leftSide.add(legal);
		return leftSide;
	}
	
	private JPanel createOptionForShapeSelect(String labelText, boolean stellated, int sides) {
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel(labelText);
		option.add(label);
		option.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		option.add(new GroupExecutionJCheckBox() {

			@Override
			public void performBeforeGroupUpdate() {
				shapeSpecs.clearBaseShapes();
			}

			@Override
			public void performUpdate() {
				if(stellated) {
					shapeSpecs.addStellatedPolygon(sides);
				}
				else {
					shapeSpecs.addRegularPolygon(sides);
				}
			}

			@Override
			public void performAfterGroupUpdate() {
				refreshImage();
			}
			
		});
		return option;
	}
	
	private JPanel createOptionForColorSelect(String labelText, Color color) {
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel(labelText);
		option.add(label);
		option.add(new GroupExecutionJCheckBox() {

			@Override
			public void performBeforeGroupUpdate() {
				shapeSpecs.clearBaseColors();
			}

			@Override
			public void performUpdate() {
				shapeSpecs.addColor(color);
			}

			@Override
			public void performAfterGroupUpdate() {
				refreshImage();
			}
			
		});
		return option;
	}
	
	
	
	private JPanel getRightSidePanels() {
		JPanel rightSide = new JPanel();
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		
		preview = new PreviewCanvas();
		refreshImage();
		rightSide.add(preview);
		return rightSide;
	}
	
	
	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton redrawButton = new JButton("Re-Draw");
		redrawButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				refreshImage();
			} 
		});
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
			  	JFileChooser fileChooser = new JFileChooser();
			  	FileNameExtensionFilter filter = new FileNameExtensionFilter(
			            "pdf files (*.pdf)", "pdf");
			  	fileChooser.setFileFilter(filter);
				if (fileChooser.showSaveDialog(selfRef) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  file = new File(file.getParent(), file.getName()+".png");
				  if(!file.exists()) {
					  try {
						file.createNewFile();
						
						ImageIO.write(shapesToDraw, "png", file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				  }
				}
			} 
		});
		buttonPanel.add(redrawButton);
		buttonPanel.add(createHorizontalPadding(10));
		buttonPanel.add(saveButton);
		return buttonPanel;
	}
	
	private void refreshImage() {
		shapesToDraw = shapeSpecs.getImageForDesktop();
		preview.setShapesToDraw(shapesToDraw);
		preview.baseColor = shapeSpecs.baseColor;
		preview.repaint();
	}
	
	private JPanel createVerticalPadding(int size) {
		JPanel padding = new JPanel();
		padding.setPreferredSize(new Dimension(0, size));
		return padding;
	}
	
	private JPanel createHorizontalPadding(int size) {
		JPanel padding = new JPanel();
		padding.setPreferredSize(new Dimension(size, 0));
		return padding;
	}
}
