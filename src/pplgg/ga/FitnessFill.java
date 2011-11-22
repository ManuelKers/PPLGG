package pplgg.ga;

import pplgg.Map;
import pplgg.PPLGG.Terrain;

public class FitnessFill extends Fitness {

    private double searchedRatio;
    
    public FitnessFill(double ratio) {
        this.searchedRatio = ratio;
    }
    
    @Override
    public double evaluate( Map map ) {
        int w = map.getWidth();
        int h = map.getHeight();
        int noSolid = 0;
        for (int x=0; x<w; x++) 
            for (int y=0; y<h; y++)
                noSolid += (map.getTerrain( x, y ) == Terrain.SOLID) ? 1 : 0;
        double solidRatio = (double)noSolid / (double)(w*h);
        return (1-(Math.abs(searchedRatio-solidRatio)));
    }
}