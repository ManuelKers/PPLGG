package pplgg;

import ga.GeneticAlgorithm;
import java.io.IOException;
import pplgg.ga.GeneratorIndividual;

public class PPLGG {

    private static int width = 80;
    private static int height = 20;
    
    public enum Terrain {
        EMPTY,
        SOLID;
    }

    public static void main (String[] args) {
        Generator.width = width;
        Generator.height = height;
        
       /* Generator resultGenerator = runGA();
        System.out.println("Press enter in console to show an endless amount of its levels!");
        try {
            System.in.read();
        }
        catch (IOException e) {}
        loopGenerator(resultGenerator, 1000);*/
        
        runPPLGG();
    }

    public static Generator runGA() {
        
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        GeneratorIndividual fittestInd = (GeneratorIndividual) geneticAlgorithm.getFittestIndividual();
        
        System.out.println("Best generator has a fitness of: "+fittestInd.getFitness());
        return fittestInd.getGenerator();
    }
    
    //timeToWait = 0 means you don't get to see the output
    public static void loopGenerator(Generator gen, int timeToWait) {
        int mapsCreated = 0;
        while (true) {
            //generate and show a map
            Map map = gen.generateMap(00);
            if (timeToWait > 0) {
                System.out.println(map.toString());
            }
            mapsCreated++;
            System.out.println("Map nr.: " + mapsCreated);
            
            try {
                Thread.sleep( timeToWait );
            }
            catch (InterruptedException e) {}
            
        }
    }

    public static void runPPLGG() {
        
        Generator levelGen;
        levelGen = Generator.randomGenerator(); 
        
        /*
        ArrayList<AgentParams> agentComposition = new ArrayList<AgentParams>();
        
        final AgentParams params1 = new AgentParams();
        params1.agentClass = RandomCrawlAgent.class;
        params1.pos = new Position((int) (width*Math.random()), (int) (height*Math.random()));
        params1.spawnRadius = 10;
        params1.tokens = (int) (1+40*Math.random());
        agentComposition.add(params1);
        
        final AgentParams params2 = new AgentParams();
        params2.agentClass = RandomCrawlAgent.class;
        params2.pos = new Position((int) (width*Math.random()), (int) (height*Math.random()));
        params2.spawnRadius = 5;
        params2.tokens = (int) (1+30*Math.random());
        agentComposition.add(params1);
        levelGen = new Generator(agentComposition);
        */
        
        loopGenerator(levelGen, 1000);
    }

    public static Generator retrieveMarioGenerator(int w, int h) {
        Generator.width = 150;
        Generator.height = 15;
        
        return runGA();
    }

}
