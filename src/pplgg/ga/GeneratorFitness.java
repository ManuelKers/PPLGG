package pplgg.ga;

import ga.Fitness;
import ga.Individual;
import pplgg.Generator;
import pplgg.Map;

public abstract class GeneratorFitness extends Fitness {

    private static int mapSamples = 40; 

    public abstract double evaluateMap(Map map);

    @Override
	public double evaluate(Individual ind) {
        GeneratorIndividual genInd = (GeneratorIndividual) ind;
        Generator gen = genInd.getGenerator();

        double fitness = 0;
        for (int k=0; k<mapSamples; k++) {
            Map sampleMap = gen.generateMap(0);
            fitness += evaluateMap( sampleMap ) ;
        }
        fitness /= mapSamples;
        System.out.println(fitness);
        //System.out.println("Press enter to show an endless amount of fit levels!");
        return fitness;
    }
}
