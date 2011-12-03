package pplgg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import agents.AbstractAgent;

import pplgg.PPLGG.Terrain;

public class MapManager {
    protected List<Terrain>[][] requests;
    protected Map managedMap;
    protected ArrayList<AbstractAgent> registeredAgents;
    protected ArrayList<AbstractAgent> agentsToRemove;
    protected HashMap<AbstractAgent, Integer> waitingTimes;

    
    @SuppressWarnings( "unchecked" )
    public MapManager(int w, int h) {
        waitingTimes = new HashMap<AbstractAgent, Integer>();
        managedMap = new Map(w,h);
        registeredAgents = new ArrayList<AbstractAgent>();
        agentsToRemove = new ArrayList<AbstractAgent>();
        requests = new List[w][h];
        for (int x=0; x<w; x++) {
            for (int y=0; y<h; y++) {
                requests[x][y] = new ArrayList<Terrain>();
            }
        }
    }
    
    //ask for information
    public Terrain requestMapInformation(int x, int y) {
        return managedMap.getTerrain( x, y );
    }
    
    public boolean agentsLeft() {
        return (registeredAgents.size()>0);
    }
    
    //add a new request
    public void requestChange (int x, int y, Terrain newTerrain) {
        requests[x][y].add( newTerrain );
    }
    
    public boolean outsideBoundaries(int x, int y) {
        return (x>=getMapWidth() || x < 0 || y >= getMapHeight() || y < 0);
    }
    
    //main iterative step
    public void performStep() {
        //ask each agent for its decisions, providing them information as side effects
        for (AbstractAgent a : registeredAgents) {
            waitingTimes.put( a, waitingTimes.get( a ) - 1  ); 
            if (waitingTimes.get( a ) == 0) {
                a.performStep();
                waitingTimes.put (a,a.getWaitingPeriod());
            }
        }
        
        removeAgents();
        
        //process the requests
        processRequests();
        
    }
    
    private void processRequests() {
        //iterate through the map
        for (int x=0; x<getMapWidth(); x++) {
            for (int y=0; y<getMapHeight(); y++) {
                //handle a request
                //TODO not pick the first received request
                if (requests[x][y].size()>0) {
                    managedMap.setTerrain( x, y, requests[x][y].get(0));
                    requests[x][y].clear();
                }
            }
        }
    }
    
    private void removeAgents() {
        //iterate through the map
        for (AbstractAgent a : agentsToRemove) {
            registeredAgents.remove( a );
            waitingTimes.remove( a );
        }
    }
    
    public void registerAgent(AbstractAgent toRegister) {
        registeredAgents.add( toRegister );
        waitingTimes.put( toRegister, 1 );
        
    }
    public void deregisterAgent(AbstractAgent toDeregister) {
        agentsToRemove.add( toDeregister );
    }
    
    public String showMap() {
        return managedMap.toString();
    }
    
    public int getMapWidth() {
        return managedMap.getWidth();
    }
    public int getMapHeight() {
        return managedMap.getHeight();
    }
    
    public Map extractMap() {
        return managedMap;
    }
}
