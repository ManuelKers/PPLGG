package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import pplgg.Generator;
import pplgg.Map;
import pplgg.PPLGG.Terrain;


// Extend our ball class from Canvas
public class CloudMapPanel extends Canvas implements MouseListener {

    private enum State {
        IDLE,
        MOUSEOVER,
        INSPECTED,
        SELECTED;
    }

    State state;
    Generator myGen;
    ArrayList<Map> sampleMaps;
    double[][] grayScale;
    private int mapHeight;
    private int mapWidth;
    private PPLGGGUI gui;
    private static final int blockWidth = 4;
    private static final int blockHeight = 4;

    public CloudMapPanel(PPLGGGUI gui) {
        this.gui = gui;
        sampleMaps = new ArrayList<Map>();
        state = State.IDLE;
        this.setSize( blockWidth*Generator.width, blockHeight*Generator.height );
        mapWidth = Generator.width;
        mapHeight = Generator.height;
        grayScale = new double[mapWidth][mapHeight];
        for (int x=0; x<mapWidth; x++)
            for (int y=0; y<mapHeight; y++)
                grayScale[x][y] = 1;
        this.addMouseListener(this);
    }
    public void setGenerator (Generator gen) {
        myGen = gen;
    }


    public void addMaps(int noToAdd) {
        for (int i=0; i<noToAdd; i++) { 
            Map newMap = myGen.generateMap( 0 );
            sampleMaps.add( newMap );
        }

        int noMaps = sampleMaps.size();
        for (int x=0; x<mapWidth; x++)
            for (int y=0; y<mapHeight; y++) {
                double gs = 1-grayScale[x][y];
                gs *= (double)(noMaps-noToAdd)/(double)noMaps;
                grayScale[x][y] = 1-gs;
            }

        for (int m=sampleMaps.size()-noToAdd; m<sampleMaps.size(); m++) {
            Map map = sampleMaps.get(m); 
            for (int x=0; x<mapWidth; x++) {
                for (int y=0; y<mapHeight; y++) {
                    if (map.getTerrain( x, y ) == Terrain.SOLID) {
                        grayScale[x][y] -= (double)1/noMaps;
                    }
                }   
            }
        }


        repaint();
    }

    public void paint(Graphics g) {
        synchronized (this) {
            drawCloudMap(g);
        }
    }

    private void drawCloudMap(Graphics graphics) {
        BufferedImage buffer = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB );
        Graphics g = buffer.getGraphics();
        for (int x=0; x<mapWidth; x++) {
            for (int y=0; y<mapHeight; y++) {
                int gVal = (int)(255*grayScale[x][y]);
                if (state == State.SELECTED)
                    g.setColor( new Color ((int)(gVal/1.1),(int)(gVal/1.1),gVal) );
                else
                    g.setColor( new Color (gVal,gVal,gVal) );
                g.fillRect( x*blockWidth, y*blockHeight, blockWidth, blockHeight );
            }
        } 

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        g.setColor( Color.black );
        switch (state) {
            case SELECTED:
            case INSPECTED:
                g.setColor( Color.blue );
            case MOUSEOVER:
                g.drawRect( 1, 1, panelWidth-3, panelHeight-3 );
                g.drawRect( 2, 2, panelWidth-5, panelHeight-5 );
            case IDLE:
                g.drawRect( 0, 0, panelWidth-1, getHeight()-1 );
                break;
        }
        g.setColor( Color.black );
        Graphics2D graphics2d = (Graphics2D)graphics;
        graphics2d.drawImage(buffer,null,0,0);
    }

    public void clear() {
        sampleMaps.clear();

    }
    @Override
    public void mouseClicked( MouseEvent arg0 ) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseEntered( MouseEvent arg0 ) {
        if (!(state==State.SELECTED || state==State.INSPECTED)) {
            state = State.MOUSEOVER;
            repaint();
        }

    }
    @Override
    public void mouseExited( MouseEvent arg0 ) {
        if (!(state==State.SELECTED || state==State.INSPECTED)) {
            state = State.IDLE;
            repaint();
        }
    }
    @Override
    public void mousePressed( MouseEvent e ) {
        if (e.getButton()==MouseEvent.BUTTON1)
        switch (state) {
            case INSPECTED:
                state = State.SELECTED;
                gui.viewOverView();
                break;
            case SELECTED:
                state = State.MOUSEOVER;
                break;
            case MOUSEOVER:
                state = State.SELECTED;
                break;
        }
        else if (e.getButton()==MouseEvent.BUTTON3) {
            if (state == State.MOUSEOVER || state == State.SELECTED) {
                state = State.INSPECTED;
                gui.inspect(this);
            } else {
                gui.viewOverView();
                state = State.MOUSEOVER;
            }
        }
        repaint();
    }
    
    public boolean isSelected() {
        return state==State.SELECTED;
    }
    
    @Override
    public void mouseReleased( MouseEvent arg0 ) {
        // TODO Auto-generated method stub

    }
    public int getNoMaps() {
        // TODO Auto-generated method stub
        return sampleMaps.size();
    }
    public Generator getGenerator() {
        return myGen;
    }
    public void deselect() {
        state = State.IDLE;
    }
}

