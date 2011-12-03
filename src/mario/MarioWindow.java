package mario;

import gui.MapPanel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;

import dk.itu.mario.engine.MarioComponent;

public class MarioWindow extends JFrame {
    public MarioComponent mario;
    public MarioWindow(MapPanel mapPanel) {
        mario = new MarioComponent(640, 480,true, new MarioLevel(mapPanel.getMap()));
        setContentPane( mario );

        setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width-getWidth())/2, (screenSize.height-getHeight())/2);
        setVisible(true);
        this.setAlwaysOnTop( true );
        mario.start();
    }
}
