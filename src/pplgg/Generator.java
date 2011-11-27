package pplgg;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.PriorityQueue;

import agents.AbstractAgent;
import agents.GapAgent;
import agents.GroundRaiseAgent;
import agents.PlayablePathAgent;
import agents.RandomCrawlAgent;




public class Generator {

    public static int width;
    public static int height;
    
    public static void setMarioDimensions() {
        width = 320;
        height = 15;
    }

    public static Generator randomGenerator() {
        int noAgents = (int) (10+10 * Math.random());
        ArrayList<AgentParams> agentComposition = new ArrayList<AgentParams>(noAgents);
        for (int i=0; i<noAgents; i++) {
            AgentParams parameters = new AgentParams();
            //TODO: collect possible agents from agent package
            switch ((int) (Math.random()*4)) {
                case 0:
                    parameters.agentClass = RandomCrawlAgent.class;
                    break;
                case 1:
                    parameters.agentClass = GroundRaiseAgent.class;
                    break;
                case 2:
                    parameters.agentClass = PlayablePathAgent.class;
                    break;
                case 3:
                    parameters.agentClass = GapAgent.class;
                    break;
                default:
                    parameters.agentClass = null;
                    break;
            }
            parameters.pos = new Position((int) (width*Math.random()), (int) (height*Math.random()));
            parameters.spawnRadius = 10;
            parameters.tokens = (int) (10+70*Math.random());
            parameters.spawnTime = (int) (1+200*Math.random());
            parameters.waitingPeriod = (int) (1+4*Math.random());
            
            agentComposition.add( i, parameters);
        }

        return new Generator(agentComposition);
    }

    private ArrayList<AgentParams> agentComposition;

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
        }
        return mapManager.extractMap();
    }
}
