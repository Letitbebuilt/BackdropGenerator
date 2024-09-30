package main.display.leftPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.display.GroupExecutionJCheckBox;
import main.display.PreviewCanvas;
import main.shapes.Circle;
import main.shapes.Polygon;
import main.shapes.Shape;
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
		shapeSelectPanel.setLayout(new GridLayout(1, 10));
		shapeSelectPanel.add(createCircleOptionForShapeSelect(false));
		shapeSelectPanel.add(createPolygonOptionForShapeSelect(true, "Triangle", 3));
		shapeSelectPanel.add(createPolygonOptionForShapeSelect(true, "Square", 4));
		shapeSelectPanel.add(createPolygonOptionForShapeSelect(true, "Pentagon", 5));
		shapeSelectPanel.add(createPolygonOptionForShapeSelect(true, "Hexagon", 6));
		shapeSelectPanel.add(createPolygonOptionForShapeSelect(false, "Octagon", 8));
		shapeSelectPanel.add(createStellatedOptionForShapeSelect(false, "3 Point Star", 3));
		shapeSelectPanel.add(createStellatedOptionForShapeSelect(false, "4 Point Star", 4));
		shapeSelectPanel.add(createStellatedOptionForShapeSelect(false, "5 Point Star", 5));
		shapeSelectPanel.add(createStellatedOptionForShapeSelect(false, "6 Point Star", 6));
		shapeSelectPanel.add(createStellatedOptionForShapeSelect(false, "8 Point Star", 8));
	}
	
	private JPanel createPolygonOptionForShapeSelect(boolean selected, String labelText, int sides) {
		int imageSize = 30;
		Polygon shapeToDraw = Polygon.getRegularPolygon(sides, (imageSize-2)/2, new Point2D.Double(imageSize/2, imageSize/2));
		Polygon shapeRotated = (Polygon) shapeToDraw.rotateAroundCenter(-90);
		BufferedImage img = getIconForCheckbox(imageSize, shapeRotated, selected);
		ImageIcon icon = new ImageIcon(img);
		
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		option.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		GroupExecutionJCheckBox checkbox = new GroupExecutionJCheckBox(selected) {

			@Override
			public void performBeforeGroupUpdate() {
				specs.clearBaseShapes();
			}

			@Override
			public void performUpdate() {
				specs.addRegularPolygon(sides);
			}

			@Override
			public void performAfterGroupUpdate() {
				specs.clearImage();
				preview.setShapesToDraw(specs.getImage());
				preview.baseColor = specs.baseColor;
				preview.repaint();
				icon.setImage(getIconForCheckbox(imageSize, shapeRotated, this.isSelected()));
			}
			
		};
		checkbox.setIcon(icon);
		checkbox.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				Polygon scaledShape = (Polygon) shapeRotated.scale(1.05);
				scaledShape.color = Color.GRAY;
				icon.setImage(getIconForCheckbox(imageSize, scaledShape, checkbox.isSelected()));
			}

			public void mouseExited(MouseEvent e) {
				icon.setImage(getIconForCheckbox(imageSize, shapeRotated, checkbox.isSelected()));
			}
		});
		option.add(checkbox);
		checkbox.setToolTipText(labelText);
		return option;
	}
	
	private JPanel createStellatedOptionForShapeSelect(boolean selected, String labelText, int sides) {
		int imageSize = 30;
		int radius = (imageSize-2)/2;
		Polygon shapeToDraw = Polygon.getStellatedPolygon(sides, radius, (sides < 5?   radius/3: radius/2), new Point2D.Double(radius, radius));
		Polygon shapeRotated = (Polygon) shapeToDraw.rotateAroundCenter(-90);
		BufferedImage img = getIconForCheckbox(imageSize, shapeRotated, selected);
		ImageIcon icon = new ImageIcon(img);
		
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		GroupExecutionJCheckBox checkbox = new GroupExecutionJCheckBox(selected) {

			@Override
			public void performBeforeGroupUpdate() {
				specs.clearBaseShapes();
			}

			@Override
			public void performUpdate() {
				specs.addStellatedPolygon(sides);
			}

			@Override
			public void performAfterGroupUpdate() {
				specs.clearImage();
				preview.setShapesToDraw(specs.getImage());
				preview.baseColor = specs.baseColor;
				preview.repaint();
				icon.setImage(getIconForCheckbox(imageSize, shapeRotated, this.isSelected()));
			}
			
		};
		checkbox.setIcon(icon);
		checkbox.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				Polygon scaledShape = (Polygon) shapeRotated.scale(1.05);
				scaledShape.color = Color.GRAY;
				icon.setImage(getIconForCheckbox(imageSize, scaledShape, checkbox.isSelected()));
			}

			public void mouseExited(MouseEvent e) {
				icon.setImage(getIconForCheckbox(imageSize, shapeRotated, checkbox.isSelected()));
			}
		});
		option.add(checkbox);
		checkbox.setToolTipText(labelText);
		return option;
	}
	
	private JPanel createCircleOptionForShapeSelect(boolean selected) {
		int imageSize = 26;
		int radius = (imageSize-2)/2;
		Circle circle = Circle.getCircle(new Point2D.Double(radius,radius), radius);
		BufferedImage img = getIconForCheckbox(imageSize, circle, selected);
		ImageIcon icon = new ImageIcon(img);
		
		JPanel option = new JPanel();
		option.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		GroupExecutionJCheckBox checkbox = new GroupExecutionJCheckBox(selected) {

			@Override
			public void performBeforeGroupUpdate() {
				specs.clearBaseShapes();
			}

			@Override
			public void performUpdate() {
				specs.addCircle();
			}

			@Override
			public void performAfterGroupUpdate() {
				specs.clearImage();
				preview.setShapesToDraw(specs.getImage());
				preview.baseColor = specs.baseColor;
				preview.repaint();
				icon.setImage(getIconForCheckbox(imageSize, circle, this.isSelected()));
			}
			
		};
		checkbox.setIcon(icon);
		checkbox.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				Circle scaledShape = (Circle) circle.scale(1.05);
				scaledShape.color = Color.GRAY;
				icon.setImage(getIconForCheckbox(imageSize, scaledShape, checkbox.isSelected()));
			}

			public void mouseExited(MouseEvent e) {
				icon.setImage(getIconForCheckbox(imageSize, circle, checkbox.isSelected()));
			}
		});
		option.add(checkbox);
		checkbox.setToolTipText("Circle");
		return option;
	}
	
	private BufferedImage getIconForCheckbox(int imageSize, Shape shape, boolean selected) {
		BufferedImage img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D paint = (Graphics2D)(img.getGraphics().create());
		paint.setColor(shape.color);        
		if(shape instanceof Polygon) {
			Polygon polygon = (Polygon) shape;
			if(selected) {
				paint.fillPolygon(polygon.getXCoordinatesForShapeDraw(), polygon.getYCoordinatesForShapeDraw(), polygon.getYCoordinatesForShapeDraw().length);
			}
			else {
				paint.drawPolygon(polygon.getXCoordinatesForShapeDraw(), polygon.getYCoordinatesForShapeDraw(), polygon.getYCoordinatesForShapeDraw().length);
			}
		}
		else if(shape instanceof Circle) {
			Circle circle = (Circle) shape;
			if(selected) {
				paint.fillOval((int)(circle.getCenter().x - circle.radius), 
    				           (int)(circle.getCenter().y - circle.radius),
    				           (int)(circle.radius*2), 
    				           (int)(circle.radius*2));
			}
			else {
				paint.drawOval((int)(circle.getCenter().x - circle.radius), 
				           (int)(circle.getCenter().y - circle.radius),
				           (int)(circle.radius*2), 
				           (int)(circle.radius*2));
			}
		}
		
		
		return img;
	}
}
