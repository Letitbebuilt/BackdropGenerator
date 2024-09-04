package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import main.display.PreviewCanvas;

public class BackdropGen {
	private static void createAndShowGUI() {
		//Create and set up the window.
        JFrame frame = new JFrame("Backdrop Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PreviewCanvas(), BorderLayout.CENTER);
    	

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
}
