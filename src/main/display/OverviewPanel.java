package main.display;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.display.leftPanel.ImageTypePanel;
import main.display.leftPanel.colorSelectors.BackgroundColorSelectionPanel;
import main.display.leftPanel.layerPanel.LayerControlPanel;
import main.shapes.GeneralDrawSpecifications;
import main.shapes.LayerDrawSpecifications;

public class OverviewPanel extends JPanel{
	private static final long serialVersionUID = -1079777852585779396L;
	private PreviewCanvas preview;
	OverviewPanel selfRef;

	ArrayList<LayerControlPanel> layerControlPanels = new ArrayList<>();
	CardLayout layerPanelCards = new CardLayout();
	JPanel layerPanel;
	LayerControlPanel selectedLayerControlPanel;
	ArrayList<LayerDrawSpecifications> shapeSpecs = new ArrayList<>();
	LayerDrawSpecifications selectedSpecs = new LayerDrawSpecifications();
	GeneralDrawSpecifications generalSpecs = new GeneralDrawSpecifications();
	public OverviewPanel() {
		super();
		preview = new PreviewCanvas(shapeSpecs, generalSpecs);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.add(getLeftSidePanels());
		this.add(getRightSidePanels());
		selfRef = this;
	}

	private JPanel getLeftSidePanels() {
		JPanel leftSide = new JPanel();
		leftSide.setMaximumSize(new Dimension(450, 10000));
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		
		layerPanel = new JPanel(layerPanelCards);
		addLayer();
		setSelectedSpec(0);
		leftSide.add(layerPanel);
		
		leftSide.add(new ImageTypePanel(shapeSpecs, generalSpecs, preview));
		leftSide.add(createVerticalPadding(10));
		
		leftSide.add(new BackgroundColorSelectionPanel(generalSpecs, preview));
		leftSide.add(createVerticalPadding(10));

		
		leftSide.add(getButtonPanel());
		
		leftSide.add(createVerticalPadding(50));

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
	
	public void setSelectedSpec(int layer) {
		selectedLayerControlPanel = layerControlPanels.get(layer);
		preview.setSelectedSpec(layerControlPanels.get(layer).getSpecs());
		layerPanelCards.first(layerPanel);
		for(int i = 0; i<layer; i++) {
			layerPanelCards.next(layerPanel);
		}
	}
	
	public int getNumberOfLayers() {
		return shapeSpecs.size();
	}
	
	public void addLayer() {
		LayerControlPanel panel = new LayerControlPanel(preview);
		layerControlPanels.add(panel);
		shapeSpecs.add(panel.getSpecs());
		layerPanel.add(panel);
		setSelectedSpec(layerControlPanels.size()-1);
	}
	
	public void removeLayer(int layer) {
		if(shapeSpecs.size() > 1) {
			LayerControlPanel removalTarget = layerControlPanels.remove(layer);
			layerPanel.remove(removalTarget);
			shapeSpecs.remove(layer);
			setSelectedSpec(0);
		}
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
						
						ImageIO.write(preview.getImage(), "png", file);
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
