package mario;

import java.util.Random;

import pplgg.Generator;
import pplgg.PPLGG;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.PlayCustomized;
import dk.itu.mario.level.CustomizedLevel;
import dk.itu.mario.level.generator.CustomizedLevelGenerator;

public class MarioLevelGenerator extends CustomizedLevelGenerator {
    
    Generator myGenerator;
    
    public MarioLevelGenerator() {
        myGenerator = PPLGG.retrieveMarioGenerator(320, 15);
    }
    
    public LevelInterface generateLevel(GamePlay playerMetrics) {
        LevelInterface level = new MarioLevel(myGenerator.generateMap( 0 ));
        return level;
    }
}
