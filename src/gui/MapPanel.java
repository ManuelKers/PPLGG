package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import pplgg.Map;
import pplgg.PPLGG.Terrain;


// Extend our ball class from Canvas
public class MapPanel extends Canvas implements MouseListener {

    boolean selected;
    Map myMap;
    private static final int blockWidth = 7;
    private static final int blockHeight = 7;

    public MapPanel(Map map) {
        this.setSize( blockWidth*map.getWidth(), blockHeight*map.getHeight() );
        myMap = map;
        selected = false;
        this.addMouseListener(this);
    }


    public MapPanel() {
        // TODO Auto-generated constructor stub
    }


    public void paint(Graphics g) {
        drawMap(g);
    }

    private void drawMap(Graphics graphics) {
        BufferedImage buffer = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB );
        Graphics g = buffer.getGraphics();
        g.setColor( Color.white);
        g.fillRect( 0, 0, getWidth(), getHeight() );
        g.setColor( Color.black );
        if (myMap != null) {
            for (int x=0; x<myMap.getWidth(); x++) {
                for (int y=0; y<myMap.getHeight(); y++) {
                    if (myMap.getTerrain( x, y ) == Terrain.SOLID) {
                        g.fillRect( x*blockWidth, y*blockHeight, blockWidth, blockHeight );
                    }
                }   
            }
        }
        
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        g.setColor( Color.black );
        if (!selected)
            g.drawRect( 0, 0, panelWidth-1, getHeight()-1 );
        else {
            g.drawRect( 0, 0, panelWidth-1, panelHeight-1 );
            g.drawRect( 1, 1, panelWidth-3, panelHeight-3 );
            g.drawRect( 2, 2, panelWidth-5, panelHeight-5 );
        }
        Graphics2D graphics2d = (Graphics2D)graphics;
        graphics2d.drawImage(buffer,null,0,0);
    }


    public void setMap( Map newMap ) {
        myMap = newMap;
        this.setSize( blockWidth*newMap.getWidth(), blockHeight*newMap.getHeight() );
        repaint();
    }
    
    @Override
    public void mouseEntered( MouseEvent arg0 ) {
        selected = true;
        repaint();
        
    }
    @Override
    public void mouseExited( MouseEvent arg0 ) {
       selected = false;
       repaint();
        
    }
    @Override
    public void mousePressed( MouseEvent arg0 ) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased( MouseEvent arg0 ) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void mouseClicked( MouseEvent e ) {
        // TODO Auto-generated method stub
        
    }

}

