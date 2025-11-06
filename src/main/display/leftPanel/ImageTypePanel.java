package main.display.leftPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.display.PreviewCanvas;
import main.shapes.GeneralDrawSpecifications;
import main.shapes.LayerDrawSpecifications;

public class ImageTypePanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	ArrayList<LayerDrawSpecifications> specs;
	GeneralDrawSpecifications generalSpecs;
	PreviewCanvas preview;
	public ImageTypePanel(ArrayList<LayerDrawSpecifications> specs, GeneralDrawSpecifications generalSpecs, PreviewCanvas preview) {
		super();
		this.specs = specs;
		this.generalSpecs = generalSpecs;
		this.preview = preview;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel imageScaleLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		imageScaleLabelContainer.setMaximumSize(new Dimension(10000, 50));
		JLabel imageScaleLabel = new JLabel("Select desired image dimensions:");		
		imageScaleLabelContainer.add(imageScaleLabel);
		this.add(imageScaleLabelContainer);

		
		JPanel imageScaleButtons = new JPanel();
		ButtonGroup imageScaleGroup = new ButtonGroup();
		imageScaleButtons.setMaximumSize(new Dimension(10000, 150));
		this.add(imageScaleButtons);
		imageScaleButtons.setLayout(new GridLayout(1, 5));	
		imageScaleButtons.add(createRadioButtonForImageScaleSelect(true, "Square (600 x 600)", 
				new Dimension(600, 600), 
				imageScaleGroup));
		imageScaleButtons.add(createRadioButtonForImageScaleSelect(false, "Desktop ("+screenSize.width+" x "+screenSize.height+")", 
				screenSize, 
				imageScaleGroup));
	}
	
	private JPanel createRadioButtonForImageScaleSelect(boolean selected, String labelText, Dimension dimension, ButtonGroup group) {
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel(labelText);
		option.add(label);
		option.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		JRadioButton radioButton = new JRadioButton();
		radioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(LayerDrawSpecifications spec: specs) {
					Dimension oldImageScale = new Dimension((int)spec.imageScale.getWidth(), (int)spec.imageScale.getHeight());
					spec.imageScale = dimension;
					spec.baseSize = dimension.height/40;
					spec.centerPoint = new Point2D.Double(spec.centerPoint.x * dimension.getWidth()/oldImageScale.getWidth(), spec.centerPoint.y * dimension.getHeight()/oldImageScale.getHeight());
				}
				generalSpecs.imageScale = dimension;
				preview.repaint();
			}
			
		});
		option.add(radioButton);
		group.add(radioButton);
		radioButton.setSelected(selected);
		return option;
	}
}
