package ga;

import pplgg.ga.GFitnessFloor;

public class GeneticAlgorithm {
	Population population;
	Fitness fitnessFunction;
	Individual fittestIndividual;
	
	public static void main(String[] args) {
		new GeneticAlgorithm();
	}
	
	public GeneticAlgorithm() {
		population = new Population(20);
		fitnessFunction = new GFitnessFloor();
		population.initialize();
		runGA();
		
	}

    private void runGA() {
        int generation = 1;
        while (generation<=20) {
            System.out.println(generation);
            population.evaluate(fitnessFunction);
            Individual populationFittest = population.getFittestIndividual();
            if (generation == 1)
                fittestIndividual = populationFittest; 
            else if (populationFittest.getFitness() > fittestIndividual.getFitness())
                fittestIndividual = populationFittest;
            population.nextGeneration();
            generation++;
        }
    }

    public Individual getFittestIndividual() {
        return fittestIndividual;
    }
}
