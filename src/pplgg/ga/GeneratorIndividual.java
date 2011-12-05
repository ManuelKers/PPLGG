package pplgg.ga;

import agents.BouncerAgent;
import agents.BouncerRemover;
import agents.CirlceAgent;
import agents.CrossExtenderAgent;
import agents.GameOfLifeAgent;
import agents.GapAgent;
import agents.GroundRaiseAgent;
import agents.PlayablePathAgent;
import agents.RandomAgent;
import agents.RandomCrawlAgent;
import agents.RoomAgent;
import agents.StairAgent;
import agents.SwipingBombAgent;
import pplgg.AgentParams;
import pplgg.Generator;
import pplgg.Map;
import pplgg.Position;
import ga.Individual;

public class GeneratorIndividual extends Individual {

    Generator myGenerator;
    private static final int spawnRadiusMutation = 4;
    private static final int xPositionMutation = 100;
    private static final int yPositionMutation = 20;
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
        //pick an agent to mutate
        AgentParams toMutate = myGenerator.extractRandomAgent();
        switch((int)(Math.random()*3)) { //pick a parameter to mutate (adding/removing an agent has smaller possibility)
            case 0: 
                switch ((int) (Math.random()*12)) {
                    case 0:
                        toMutate.agentClass = RandomCrawlAgent.class;
                        break;
                    case 1:
                        toMutate.agentClass = PlayablePathAgent.class;
                        break;
                    case 2:
                        toMutate.agentClass = GapAgent.class;
                        break;
                    case 3:
                        toMutate.agentClass = RoomAgent.class;
                        break;
                    case 4:
                        toMutate.agentClass = RandomAgent.class;
                        break;
                    case 5:
                        toMutate.agentClass = SwipingBombAgent.class;
                        break;
                    case 6:
                        toMutate.agentClass = CirlceAgent.class;
                        break;
                    case 7:
                        toMutate.agentClass = StairAgent.class;
                        break;
                    case 8:
                        toMutate.agentClass = GroundRaiseAgent.class;
                        break;
                    case 9:
                        toMutate.agentClass = CrossExtenderAgent.class;
                        break;
                    case 10:
                        toMutate.agentClass = BouncerAgent.class;
                        break;
                    case 11:
                        toMutate.agentClass = BouncerRemover.class;
                        break;
                    case 12:
                        toMutate.agentClass = GameOfLifeAgent.class;
                        break;
                    default:
                        toMutate.agentClass = null;
                        break;
                }
                break;
            case 1: //mutate waiting time
                toMutate.waitingPeriod = (int)(1+(Generator.maximumWaitingTime-1)*Math.random());
                break;
            case 2: //add or remove an agent with equal chance (except if removing will make generator empty, thus void)
                if (Math.random()<0.5 && myGenerator.getNoAgents()>1)
                    myGenerator.extractRandomAgent();
                else {
                    myGenerator.addAgent( Generator.randomAgentParams() );
                }
                break;

        }
        //mutate spawn position
        Position pos = toMutate.pos; 
        int newX = pos.x + ((int)(2*xPositionMutation*Math.random()-xPositionMutation));
        newX = Math.min( Math.max( 0, newX), Generator.width-1 ); //clamp
        int newY = pos.y + ((int)(2*yPositionMutation*Math.random()-yPositionMutation));
        newY = Math.min( Math.max( 0, newY), Generator.height-1 ); //clamp
        toMutate.pos = new Position(newX, newY); 

        //mutate spawn radius
        toMutate.spawnRadius = Math.min(Generator.maximumSpawnRadius, Math.max( 0, toMutate.spawnRadius+2*spawnRadiusMutation*Math.random()-spawnRadiusMutation ));

        //mutate sequencing
        toMutate.spawnTime = Math.min(Generator.maximumSpawnTime, Math.max( 0, (int)(toMutate.spawnTime+2*spawnTimeMutation*Math.random()-spawnTimeMutation )));

        //mutate amount of tokens
        toMutate.tokens = Math.min(Generator.maximumTokens, Math.max( Generator.minimumTokens, (int)(toMutate.tokens+2*tokenMutation*Math.random()-tokenMutation )));

        //put the mutation back in the generator
        myGenerator.addAgent( toMutate );
    }

}
