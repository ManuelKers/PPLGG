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
    
    protected int randomBetween(int min, int max){
    	int i = min + (int)Math.random()*(max-min);
    	return i;
    }
    
    protected boolean actionIsRequired(int i){
    	boolean b = false;
    	switch(i) {
    	
    		case 1:
    			if (this.requestMapInformation(x, y)==Terrain.SOLID)
    				b = true;
    			break;
		
    		case 2:
    			if (this.requestMapInformation(x, y)==Terrain.EMPTY)
    				b = true;
    			break;	
    	}
    	return b;
    }
    
    protected void makeMove(int type){
    	switch(type){
    		
    	// move from left to right, when on border, go one line down, start from x=0
    	case 1:
    		if ((x++)>this.askForMapWidth()-1){
    			x = 0;
    			if ((y+1)>this.askForMapHeight()-1) y = 0;
    			
    			else y++;
    	}
    		else x++;
    		break;
    }
    }
    
    protected boolean isOnMap(int x,int y){
    	boolean b = false;
    	if (x>=0 && x<this.askForMapWidth() && y >= 0 && y < this.askForMapHeight()) b = true;
    	return b;
    }
    
    protected void doAction(int action, int actionParameter){
    	switch(action){
    	// case 1 if solid is found, bomb is triggered
    	case 1:
    		for (int x = 0; x < actionParameter; x++){
    			for (int y = 0; y < actionParameter; y++){
    				int newX = this.x + x;
    				int newY = this.y + y;
    				if (isOnMap(newX,newY)) this.sendMapRequest(newX, newY, Terrain.SOLID);
    			}
    		}
    		
    		break;
    }
    }
    
}
