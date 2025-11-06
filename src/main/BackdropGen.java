package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.display.OverviewPanel;

public class BackdropGen {
	static OverviewPanel panel = new OverviewPanel();
	private static void createAndShowGUI() {
		//Create and set up the window.
        JFrame frame = new JFrame("Backdrop Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        initializeJMenu(frame);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Display the window.
        frame.pack();
        frame.setLocation(new Point(screenSize.width/2-frame.getWidth()/2, screenSize.height/2-frame.getHeight()/2));
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("resources/logo.png").getImage());
        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	private static void initializeJMenu(JFrame frame) {
		JMenuBar bar = new JMenuBar();
		JMenu layers = new JMenu("Layers");
		createLayerMenu(layers);
		bar.add(layers);
		frame.setJMenuBar(bar);
	}
	
	private static void createLayerMenu(JMenu layers) {
		layers.removeAll();
		for(int i = 0; i<panel.getNumberOfLayers(); i++) {
			int layerNum = i;
			JMenu layerMenu = new JMenu("Layer "+(layerNum+1));
			JMenuItem select = new JMenuItem("Select");
			select.addActionListener(e -> panel.setSelectedSpec(layerNum));
			JMenuItem remove = new JMenuItem("Remove");
			remove.addActionListener(e -> {
				panel.removeLayer(layerNum);
				createLayerMenu(layers);
			});
			layerMenu.add(select);
			layerMenu.add(remove);
			layers.add(layerMenu);
		}

		JMenuItem add = new JMenuItem("Add Layer");
		add.addActionListener(e -> {
			panel.addLayer();
			createLayerMenu(layers);
		});
		layers.add(add);
	}
}
