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
    protected int width;
    protected int height;
    
    //start at top left
    public AbstractAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
        setTokens( tokens );
        x = spawnPos.x;
        y = spawnPos.y;
        this.waitingPeriod = waitingPeriod;
        registerWithManager( newManager );
        width = this.askForMapWidth();
        height = this.askForMapHeight();
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
    	int i = min + (int)(Math.random()*(max-min));
    	return i;
    }
    
    protected boolean actionIsRequired(int i){
    	boolean b = false;
    	switch(i) {
    	
    	case 0:
			if (this.requestMapInformation(x, y)==Terrain.EMPTY)
				b = true;
			break;
    	
    	
    		case 1:
    			if (this.requestMapInformation(x, y)==Terrain.SOLID)
    				b = true;
    			break;
		
    			
    	}
    	return b;
    }
    
    protected void makeMove(int type,int stepX,int stepY){
    	switch(type){
    		
    	// Swiping on x with going stepY up or down each time we end on a border
    	case 0:
    		if (x+stepX>(width-1)||x+stepX<0) y = (height+y+stepY)%(height);
    		x = (width+x+stepX)%(width);
    		
    		break;
    		
    	case 1:
    		
    		// Swiping on y
    		if (y+stepY>(height-1)||y+stepY<0) x = (width+x+stepX)%(width);
    		y = (height+y+stepY)%(height);
    		
    		break;
    		
    		case 2:
    			// going from right to left
    			if ((x-1)<0){
        			x = height-1;
        			if ((y+1)>this.height-1) y = 0;
        			
        			else y++;
        	}
        		else x++;
        		break;
    			
    		
    		
    }
    }
    
    protected boolean isOnMap(int x,int y){
    	boolean b = false;
    	if (x>=0 && x<this.width && y >= 0 && y < this.height) b = true;
    	return b;
    }
    
    protected void doAction(int action, int actionParameter){
    	switch(action){
    	// removes solid on position
    	case 0:
    		if (isOnMap(x,y)) this.sendMapRequest(x, y, Terrain.EMPTY);
    		break;
        // creates solid on position
    	case 1:
    		if (isOnMap(x,y)) this.sendMapRequest(x, y, Terrain.SOLID);
    		break;
    		
    		
    	case 2:
    		// remove solid from position
    		for (int x = 0; x < actionParameter; x++){
    			for (int y = 0; y < actionParameter; y++){
    				int newX = this.x + x;
    				int newY = this.y + y;
    				if (isOnMap(newX,newY)) this.sendMapRequest(newX, newY, Terrain.EMPTY);
    			}
    		}
    		
    		break;
    	 
    	case 3:
    		// Creates solid square from center
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
