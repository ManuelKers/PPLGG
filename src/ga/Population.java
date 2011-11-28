package ga;


import java.util.ArrayList;

import pplgg.Generator;
import pplgg.ga.GeneratorIndividual;

public class Population {
	ArrayList<Individual> individuals;
	int size;
	
	public Population(int size) {
		individuals = new ArrayList<Individual>(size);
		this.size = size;
	}
	
	public void initialize() {
		for (int i=0; i<size; i++) {
			individuals.add(new GeneratorIndividual(Generator.randomGenerator()));
		}
	}
	
	//returns average fitness
	public float evaluate(Fitness fitnessFunction) {
		float totalFit = 0;
		for (int i=0; i<size; i++) {
			Individual ind = individuals.get(i);
			double fit = fitnessFunction.evaluate(ind);
			ind.setFitness(fit);
			totalFit += fit;
		}
		return totalFit/size;
	}
	
	public void nextGeneration() {
		ArrayList<Individual> newPop = new ArrayList<Individual>(size);
		
		for (int i=0; i<size; i++) {
			Individual parent1 = Reproduction.tournamentSelection(individuals).copy();
			if (Math.random() >= Reproduction.crossOverRate) //new = copy of old individual
				newPop.add(parent1.copy()); 
			else { //new = offspring of two old individuals
				Individual parent2 = Reproduction.tournamentSelection(individuals).copy();
				newPop.add(Reproduction.mate(parent1,parent2));
			}
		}
		individuals = newPop;
		for (int i=0; i<size; i++) {
		    if (Math.random() < (Reproduction.mutationRate/size))
		        individuals.get(i).mutate();
		}
	}

    public Individual getFittestIndividual() {
        double bestFitness = -1; 
        Individual fittestInd = null;
        for (int i=0; i<size; i++) {
            double indFit = individuals.get(i).getFitness(); 
            if (indFit>bestFitness) {
                bestFitness = indFit;
                fittestInd = individuals.get(i);
            }
        }
        return fittestInd;
    }
	
}
