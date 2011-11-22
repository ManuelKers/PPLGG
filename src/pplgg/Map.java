package pplgg;

import pplgg.PPLGG.Terrain;

public class Map {
    
    protected int width;
    protected int height;
    protected Terrain[][] mapxy;
    
    public Map(int w, int h) {
        width = w;
        height = h;
        mapxy = new Terrain[width][height];
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                mapxy[x][y] = Terrain.EMPTY;
            }
        }
        //mapxy[(int)(Math.random()*width)][(int)(Math.random()*height)] = Terrain.SOLID;
    }
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public Terrain getTerrain (int x, int y) {
        return mapxy[x][y];
    }
    public void setTerrain (int x, int y, Terrain newTerrain) {
        mapxy[x][y] = newTerrain;
    }
    
    public String toString() {
        String s = "";
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                char symbol;
                switch (mapxy[x][y]) {
                    case EMPTY:
                        symbol = '.'; 
                        break;
                    case SOLID:
                        symbol = 'x'; 
                        break;
                    default:
                        symbol = '?';
                        break;
                            
                }
                s += symbol;
            }
            s += '\n';
        }
        return s;
        
    }
    
}
