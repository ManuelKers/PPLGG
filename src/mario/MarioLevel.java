package mario;

import pplgg.Map;
import pplgg.PPLGG.Terrain;
import dk.itu.mario.level.Level;

public class MarioLevel extends Level {
    
    public MarioLevel(Map pplggMap) {
        super(pplggMap.getWidth(), pplggMap.getHeight()+1);
        
        for (int x=0; x<width; x++) {
            for (int y=1; y<height; y++) {
                if (pplggMap.getTerrain( x, y-1 ) == Terrain.SOLID)
                    setBlock( x, y, ROCK );
            }
        }
        xExit = width-10;
        yExit = height/2;
        
    }
    
}
