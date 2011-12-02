package pplgg;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.PriorityQueue;

import agents.AbstractAgent;
import agents.BouncerAgent;
import agents.BouncerRemover;
import agents.GameOfLifeAgent;
import agents.CirlceAgent;
import agents.CrossExtenderAgent;
import agents.GapAgent;
import agents.GroundRaiseAgent;
import agents.PlayablePathAgent;
import agents.RandomAgent;
import agents.RandomCrawlAgent;
import agents.RoomAgent;
import agents.StairAgent;
import agents.SwipingBombAgent;




public class Generator {

    public static final float maximumSpawnRadius = 10;
    public static final int maximumSpawnTime = 200;
    public static final int minimumTokens = 10;
    public static final int maximumTokens = 80;
    public static final int maximumWaitingTime = 5;
    public static int width;
    public static int height;

    public static int[] testSubjects = {5,12};

    private ArrayList<AgentParams> agentComposition;

    public static Generator randomGenerator() {
        int noAgents = (int) (4+10 * Math.random());
        ArrayList<AgentParams> agentComposition = new ArrayList<AgentParams>(noAgents);
        for (int i=0; i<noAgents; i++) {
            agentComposition.add( i, Generator.randomAgentParams());
        }

        return new Generator(agentComposition);
    }


    public static AgentParams randomAgentParams() {
        AgentParams parameters = new AgentParams();
        //TODO: collect possible agents from agent package
      //switch ((int) (Math.random()*12)) {
           //switch(12){
           switch (testSubjects[(int)Math.random()*testSubjects.length]){
            case 0:
                parameters.agentClass = RandomCrawlAgent.class;
                break;
            case 1:
                parameters.agentClass = PlayablePathAgent.class;
                break;
            case 2:
                parameters.agentClass = GapAgent.class;
                break;
            case 3:
                parameters.agentClass = RoomAgent.class;
                break;
            case 4:
                parameters.agentClass = RandomAgent.class;
                break;
            case 5:
                parameters.agentClass = SwipingBombAgent.class;
                break;
            case 6:
                parameters.agentClass = CirlceAgent.class;
                break;
            case 7:
                parameters.agentClass = StairAgent.class;
                break;
            case 8:
                parameters.agentClass = GroundRaiseAgent.class;
                break;
            case 9:
                parameters.agentClass = CrossExtenderAgent.class;
                break;
            case 10:
            	parameters.agentClass = BouncerAgent.class;
                break;
            case 11:
            	parameters.agentClass = BouncerRemover.class;
                break;
            case 12:
            	parameters.agentClass = GameOfLifeAgent.class;
                break;
            default:
                parameters.agentClass = null;
                break;
        }
        parameters.pos = new Position((int) (width*Math.random()), (int) (height*Math.random()));
        parameters.spawnRadius = maximumSpawnRadius * Math.random();
        parameters.tokens = (int) (minimumTokens+(maximumTokens-minimumTokens)*Math.random());
        parameters.spawnTime = (int) (1+maximumSpawnTime*Math.random());
        parameters.waitingPeriod = (int) (1+(maximumWaitingTime-1)*Math.random());
        return parameters;
    }

    public Generator(ArrayList<AgentParams> agentComposition) {
        this.agentComposition = agentComposition;
    }

    public Generator() {
    }

    public Map generateMap(int timeToWait) {
        MapManager mapManager = new MapManager( width, height );
        ArrayList<AgentParams> spawnEvents = (ArrayList<AgentParams>) agentComposition.clone();
        ArrayList<AgentParams> removeList = new ArrayList<AgentParams>();
        int steps = 0;
        while (mapManager.agentsLeft() || spawnEvents.size()>0) {
            //spawn the agents according to their spawn time
            for (AgentParams agentPar : spawnEvents) {
                if (agentPar.spawnTime == steps) {
                    //pick a position inside the spawn circle
                    double direction = Math.random()*2*Math.PI;
                    double distance = Math.random()*2*Math.PI;
                    int xPos = Math.max( Math.min((int)(agentPar.pos.x + distance * Math.cos( direction )),width-1), 0 );
                    int yPos = Math.max( Math.min((int)(agentPar.pos.y + distance * Math.sin( direction )),height-1), 0 );
                    Position spawnPos = new Position(xPos,yPos);
                    //construct this agent with the mapmanager
                    try { 
                        Constructor<?> con = agentPar.agentClass.getConstructor(MapManager.class, Integer.TYPE, Position.class, Integer.TYPE);
                        con.newInstance(new Object[]{mapManager, agentPar.tokens, spawnPos, agentPar.waitingPeriod});
                    } catch (Exception e) {e.printStackTrace();};
                    removeList.add(agentPar);
                }
            }
            for (AgentParams toRemove : removeList) {
                spawnEvents.remove( toRemove );
            }
            removeList.clear();
            mapManager.performStep();

            if (timeToWait > 0) {
                System.out.println(mapManager.showMap());
                try {
                    Thread.sleep(timeToWait);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            steps++;
            if (steps>500)
                break;
        }
        return mapManager.extractMap();
    }

    //removes and returns a random agent, used for mutation
    public AgentParams extractRandomAgent() {
        int index = (int)(Math.random()*agentComposition.size());
        return agentComposition.remove( index );
    }

    //adds an agent, used for mutation
    public void addAgent(AgentParams newAgent) {
        agentComposition.add(newAgent);
    }

    //returns a copy, used for genetic algorithm
    public Generator copy() {
        ArrayList<AgentParams> parametersCopy = new ArrayList<AgentParams>(agentComposition.size());
        for (int i=0; i<agentComposition.size(); i++)
            parametersCopy.add((AgentParams) agentComposition.get(i).clone());
        return new Generator(parametersCopy);
    }

    public int getNoAgents() {
        return agentComposition.size();
    }
}
