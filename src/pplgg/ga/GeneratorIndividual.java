package pplgg.ga;

import pplgg.AgentParams;
import pplgg.Generator;
import pplgg.Map;
import pplgg.Position;
import ga.Individual;

public class GeneratorIndividual extends Individual {

    Generator myGenerator;
    private static final int spawnRadiusMutation = 4;
    private static final int positionMutation = 20;
    private static final int spawnTimeMutation = 50;
    private static final int tokenMutation = 10;
    
    public GeneratorIndividual(Generator gen) {
        this.myGenerator = gen;
    }
    
    @Override
    public Individual copy() {
        Generator genCopy = myGenerator.copy();
        GeneratorIndividual copy = new GeneratorIndividual( genCopy );
        return copy;
    }

    public Generator getGenerator() {
        return myGenerator;
    }

    @Override
    public void mutate() {
        AgentParams toMutate = myGenerator.extractRandomAgent();
        switch((int)(1+Math.random()*5.1)) { //pick a parameter to mutate (adding/removing an agent has smaller possibility)
            case 0: 
                //TODO: change agent Class
                break;
            case 1: //mutate spawn position
                Position pos = toMutate.pos; 
                int newX = pos.x + ((int)(2*positionMutation*Math.random()-positionMutation));
                newX = Math.min( Math.max( 0, pos.x), Generator.width-1 ); //clamp
                int newY = pos.y + ((int)(2*positionMutation*Math.random()-positionMutation));
                newY = Math.min( Math.max( 0, pos.y), Generator.height-1 ); //clamp
                toMutate.pos = new Position(newX, newY); 
                break;
            case 2: //mutate spawn radius
                toMutate.spawnRadius = Math.min(Generator.maximumSpawnRadius, Math.max( 0, toMutate.spawnRadius+2*spawnRadiusMutation*Math.random()-spawnRadiusMutation ));
                break;
            case 3: //mutate sequencing
                toMutate.spawnTime = Math.min(Generator.maximumSpawnTime, Math.max( 0, (int)(toMutate.spawnTime+2*spawnTimeMutation*Math.random()-spawnTimeMutation )));
                break;
            case 4: //mutate amount of tokens
                toMutate.tokens = Math.min(Generator.maximumTokens, Math.max( Generator.minimumTokens, (int)(toMutate.tokens+2*tokenMutation*Math.random()-tokenMutation )));
                break;
            case 5: //mutate waiting time
                toMutate.waitingPeriod = (int)(1+(Generator.maximumWaitingTime-1)*Math.random());
                break;
            case 6: //add or remove an agent with equal chance (except if removing will make generator empty, thus void)
                if (Math.random()<0.5 && myGenerator.getNoAgents()>1)
                    myGenerator.extractRandomAgent();
                else
                    myGenerator.addAgent( Generator.randomAgentParams() );
                break;
                
        }
    }

}
