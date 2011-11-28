package pplgg.ga;

import ga.Fitness;
import ga.Individual;
import pplgg.Map;
import pplgg.PPLGG.Terrain;

public class GFitnessFill extends GeneratorFitness {

    private double searchedRatio;
    
    public GFitnessFill(double ratio) {
        this.searchedRatio = ratio;
    }
    
    @Override
    public double evaluateMap ( Map map ) {
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