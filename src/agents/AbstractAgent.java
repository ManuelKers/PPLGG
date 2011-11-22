package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;


public abstract class AbstractAgent {
    
    protected int x;
    protected int y;
    protected MapManager myManager;
    protected int tokens;
    protected int waitingPeriod;
    
    //start at top left
    public AbstractAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
        setTokens( tokens );
        x = spawnPos.x;
        y = spawnPos.y;
        this.waitingPeriod = waitingPeriod;
        registerWithManager( newManager );
    }
    
    public void registerWithManager(MapManager manager) {
        myManager = manager;
        manager.registerAgent(this);
    }
    
    //use this to request the terrain type at a certain position
    protected Terrain requestMapInformation(int x, int y) {
        return myManager.requestMapInformation( x, y );
    
    }
    
    //use this to request a terrain change at a certain position
    protected void sendMapRequest(int x, int y, Terrain requestedTerrain) {
        myManager.requestChange( x, y, requestedTerrain );
    }
    
    //use these to ask for the map dimensions
    protected int askForMapWidth() {return myManager.getMapWidth();}
    protected int askForMapHeight() {return myManager.getMapHeight();}
    
    //use this to move the agent somewhere
    protected void moveTo (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    //this needs to be implemented and is called every iteration of the algorithm
    public abstract void performStep();
    
    public int getWaitingPeriod() {
        return waitingPeriod;
    }
    
    protected void setTokens(int tokens) {
        this.tokens = tokens;
    }
    
    protected void decreaseTokens(int amount) {
        tokens -= amount;
        if (tokens<=0)
            myManager.deregisterAgent(this);
    }
    
}
