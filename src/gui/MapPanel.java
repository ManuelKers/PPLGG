package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import pplgg.Map;
import pplgg.PPLGG.Terrain;


// Extend our ball class from Canvas
public class MapPanel extends Canvas {

    Map myMap;
    private static final int blockWidth = 7;
    private static final int blockHeight = 7;

    public MapPanel(Map map) {
        this.setSize( blockWidth*map.getWidth(), blockHeight*map.getHeight() );
        myMap = map;
    }


    public MapPanel() {
        // TODO Auto-generated constructor stub
    }


    public void paint(Graphics g) {
        drawMap(g);
    }

    private void drawMap(Graphics g) {
        if (myMap != null) {
            for (int x=0; x<myMap.getWidth(); x++) {
                for (int y=0; y<myMap.getHeight(); y++) {
                    if (myMap.getTerrain( x, y ) == Terrain.SOLID) {
                        g.fillRect( x*blockWidth, y*blockHeight, blockWidth, blockHeight );
                    }
                }   
            }
        }
    }


    public void setMap( Map newMap ) {
        myMap = newMap;
        this.setSize( blockWidth*newMap.getWidth(), blockHeight*newMap.getHeight() );
        repaint();
    }

}

