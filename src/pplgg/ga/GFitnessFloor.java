package pplgg.ga;

import pplgg.Map;
import pplgg.PPLGG.Terrain;
import ga.Fitness;
import ga.Individual;

public class GFitnessFloor extends GeneratorFitness {

    @Override
    public double evaluateMap( Map map ) {
        int w = map.getWidth();
        int h = map.getHeight();
        int noBottomSolid = 0;
        int noTopSolid = 0;
        for (int x=0; x<w; x++) 
            for (int y=0; y<h; y++)
                if (y>2*h/3)
                    noBottomSolid += (map.getTerrain( x, y ) == Terrain.SOLID) ? 1 : 0;
                else
                    noTopSolid += (map.getTerrain( x, y ) == Terrain.SOLID) ? 1 : 0;
        return (w*h + (noBottomSolid - noTopSolid));
    }

}
