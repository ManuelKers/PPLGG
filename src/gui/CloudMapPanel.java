package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pplgg.Generator;
import pplgg.Map;
import pplgg.PPLGG.Terrain;


// Extend our ball class from Canvas
public class CloudMapPanel extends Canvas {

    Generator myGen;
    ArrayList<Map> sampleMaps;
    private static final int blockWidth = 7;
    private static final int blockHeight = 7;


    public CloudMapPanel() {
        sampleMaps = new ArrayList<Map>();
    }
    public void setGenerator (Generator gen) {
        myGen = gen;
    }
    

    public void addMaps(int noMaps) {
        for (int i=0; i<noMaps; i++) { 
            Map newMap = myGen.generateMap( 0 );
            if (sampleMaps.isEmpty()) 
                this.setSize( blockWidth*newMap.getWidth(), blockHeight*newMap.getHeight() );
            sampleMaps.add( newMap );
        }
        repaint();
    }

    public void paint(Graphics g) {
        drawCloudMap(g);
    }

    private void drawCloudMap(Graphics g) {

        if (!sampleMaps.isEmpty()) {
            int width = sampleMaps.get( 0 ).getWidth();
            int height = sampleMaps.get( 0 ).getHeight();
            double[][] grayScale = new double[width][height];
            for (int x=0; x<width; x++)
                for (int y=0; y<height; y++)
                    grayScale[x][y] = 1;
            int noMaps = sampleMaps.size(); 
            for (int m=0; m<sampleMaps.size(); m++) {
                Map map = sampleMaps.get(m); 
                for (int x=0; x<width; x++) {
                    for (int y=0; y<height; y++) {
                        if (map.getTerrain( x, y ) == Terrain.SOLID) {
                            grayScale[x][y] -= (double)1/noMaps;
                        }
                    }   
                }
            }
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    int gVal = (int)(255*grayScale[x][y]);
                    g.setColor( new Color (gVal,gVal,gVal) );
                    g.fillRect( x*blockWidth, y*blockHeight, blockWidth, blockHeight );
                }
            }   
        }
    }

    public void clear() {
        sampleMaps.clear();
    }
}

