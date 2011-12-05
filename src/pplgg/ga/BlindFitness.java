package pplgg.ga;

import ga.Fitness;
import ga.Individual;
import pplgg.Generator;
import pplgg.Map;

public class BlindFitness extends Fitness {

    public BlindFitness(boolean[] generatorSelected) {
        
    }
    
    public double evaluate(Individual ind) {
        return 1;
    }
}
