package pplgg;

import java.awt.im.InputContext;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import pplgg.ga.Fitness;
import pplgg.ga.FitnessFill;

import agents.CrossExtenderAgent;
import agents.PlayablePathAgent;
import agents.RandomAgent;
import agents.RandomCrawlAgent;
import agents.RoomAgent;

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
        
        //findRatioFilled( .5 );
        runPPLGG();
    }

    public static void findRatioFilled(double ratio) {
        
        int genSamples = 100;
        int mapSamples = 50;
        
        double bestFitness = -1;
        Generator bestGenerator = null;
        
        Fitness fitnessFunction = new FitnessFill(ratio);
        for (int g=0; g<genSamples; g++) {
            System.out.print("Evaluating generator #"+g+". Fitness: ");
            Generator sampleGenerator = Generator.randomGenerator();
            double fillFitness = 0;
            for (int k=0; k<mapSamples; k++) {
                Map sampleMap = sampleGenerator.generateMap(0);
                double fitness = fitnessFunction.evaluate( sampleMap ) ;
                fillFitness += fitness;
            }
            fillFitness /= mapSamples;
            System.out.println(fillFitness);
            if (fillFitness > bestFitness) {
                bestFitness = fillFitness;
                bestGenerator = sampleGenerator;
            }
        }
        System.out.println("Best generator has a fitness of: "+bestFitness);
        System.out.println("Press enter to show an endless amount of fit levels!");
        try {
            System.in.read();
        }
        catch (IOException e) {}
        loopGenerator(bestGenerator, 1000);
    }
    
    //timeToWait = 0 means you don't get to see the output
    public static void loopGenerator(Generator gen, int timeToWait) {
        int mapsCreated = 0;
        while (true) {
            //generate and show a map
            Map map = gen.generateMap(0);
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
        
        loopGenerator(levelGen, 001);
    }

}
