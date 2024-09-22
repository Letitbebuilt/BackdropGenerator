package main.display.leftPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.display.PreviewCanvas;
import main.shapes.ShapeDrawSpecifications;

public class ImageTypePanel extends JPanel{

	private static final long serialVersionUID = -9092168706431390569L;
	ShapeDrawSpecifications specs;
	PreviewCanvas preview;
	public ImageTypePanel(ShapeDrawSpecifications specs, PreviewCanvas preview) {
		super();
		this.specs = specs;
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
				specs.imageScale = dimension;
				specs.baseSize = dimension.height/40;
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
