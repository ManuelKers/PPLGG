package ga;

import java.util.ArrayList;

import pplgg.AgentParams;
import pplgg.Generator;
import pplgg.ga.GeneratorIndividual;

public class Reproduction {
    public static int tournamentSize = 5;
    public static float mutationRate = 0.05f;
    public static float crossOverRate = 0.9f;

    public static Individual tournamentSelection(ArrayList<Individual> genePool) {

        //select competitors at random, possibly selecting the same more than once
        ArrayList<Individual> competitors = new ArrayList<Individual>();
        for (int i=0; i<tournamentSize; i++) {
            competitors.add(genePool.get((int)(Math.random()*genePool.size())));
        }

        //find the fittest and return it
        double bestFit = -1;
        Individual bestInd = null;
        for (int i=0; i<competitors.size(); i++) {
            double indFit = competitors.get(i).getFitness();
            if (indFit > bestFit) {
                bestFit = indFit;
                bestInd = competitors.get(i);
            }
        }
        if (bestInd == null) {
            int sda=0;
        }
        return bestInd;
    }

    public static Individual mate(Individual p1, Individual p2) {
        //copy the parents, so we can do with them whatever we want
        GeneratorIndividual parent1 = (GeneratorIndividual) p1.copy();
        GeneratorIndividual parent2 = (GeneratorIndividual) p2.copy();
        int size1 = parent1.getGenerator().getNoAgents();
        int size2 = parent2.getGenerator().getNoAgents();
        ArrayList<AgentParams> childAgentComposition = new ArrayList<AgentParams>();
        
        //pick random amount of agents from both parents (at least 1 from the first parent (so we assume first parent is non-empty))
        int amountFromParent1 = (int)(1+Math.random()*(size1));
        int amountFromParent2 = (int)(Math.random()*(size2+1));
        for (int i=0; i<amountFromParent1; i++)
            childAgentComposition.add(parent1.getGenerator().extractRandomAgent());
        for (int i=0; i<amountFromParent2; i++)
            childAgentComposition.add(parent2.getGenerator().extractRandomAgent());
        //construct the new individual
        return new GeneratorIndividual(new Generator(childAgentComposition));
    }
}
