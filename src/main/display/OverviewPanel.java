package main.display;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.display.leftPanel.FocusSelectionPanel;
import main.display.leftPanel.ImageTypePanel;
import main.display.leftPanel.ShapeSelectionPanel;
import main.display.leftPanel.colorSelectors.BackgroundColorSelectionPanel;
import main.display.leftPanel.colorSelectors.ShapeColorSelectionPanel;
import main.shapes.ShapeDrawSpecifications;

public class OverviewPanel extends JPanel{
	private static final long serialVersionUID = -1079777852585779396L;
	private PreviewCanvas preview;
	OverviewPanel selfRef;
	ShapeDrawSpecifications shapeSpecs = new ShapeDrawSpecifications();
	public OverviewPanel() {
		super();
		preview = new PreviewCanvas();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.add(getLeftSidePanels());
		this.add(getRightSidePanels());
		selfRef = this;
	}

	private JPanel getLeftSidePanels() {
		int leftSideWidth = 450;
		JPanel leftSide = new JPanel();
		leftSide.setMaximumSize(new Dimension(450, 10000));
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		
		leftSide.add(new ShapeSelectionPanel(shapeSpecs, preview));
		leftSide.add(createVerticalPadding(10));	
		
		leftSide.add(new ShapeColorSelectionPanel(shapeSpecs, preview));
		leftSide.add(createVerticalPadding(10));
		
		leftSide.add(new FocusSelectionPanel(shapeSpecs, preview));
		leftSide.add(createVerticalPadding(10));

		leftSide.add(new ImageTypePanel(shapeSpecs, preview));
		leftSide.add(createVerticalPadding(10));
		
		leftSide.add(new BackgroundColorSelectionPanel(shapeSpecs, preview));
		leftSide.add(createVerticalPadding(10));
		
		leftSide.add(getButtonPanel());
		
		leftSide.add(createVerticalPadding(10));

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
	
	
	
	private JPanel getRightSidePanels() {
		JPanel rightSide = new JPanel();
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		
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
						
						ImageIO.write(shapeSpecs.getImage(), "png", file);
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
		shapeSpecs.clearImage();
		preview.setShapesToDraw(shapeSpecs.getImage());
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
