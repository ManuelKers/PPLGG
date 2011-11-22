package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;

// class PlayableAgent
//
// Creates a playable platform path from left to right
public class PlayablePathAgent extends AbstractAgent {

	// steps between platforms in y and x direction: Y can change direction
	int minXDist;
	int maxXDist;
	int minYDist;
	int maxYDist;
	// start and end position of path, default is 0 and end of map
	int xStart;
	int xEnd;
	
	// platformSize
	int minPlatformSize;
	int maxPlatformSize;
	
	// is true when end has been reached
	boolean isEnded;
	

	public PlayablePathAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
        super(newManager, tokens, spawnPos, waitingPeriod); 
		minXDist = 1;// what is reachable with a jump
		maxXDist = 3;
		minYDist = 1;
		maxYDist = 3;
		xStart = 0;
		xEnd = askForMapWidth()-1;
		minPlatformSize = 2;
		maxPlatformSize = 5;
		isEnded = false;
		
	}

	@Override
	public void performStep() {
		// TODO Auto-generated method stub
		
		if (!isEnded){
		//calc platform size
		int platformSize = (int)(minPlatformSize + (Math.random()*(this.maxPlatformSize-this.minPlatformSize)));
		// create platform at current loc,
		for (int x=0;x<platformSize;x++){
			if ((this.x+x)<this.askForMapWidth()){
			this.sendMapRequest(this.x+x, this.y, Terrain.SOLID);
			this.decreaseTokens(1);
			}
		}
		// move to new position, ready to create platform
		// calc new move distance
		
		int xMove = (int)(this.minXDist+ (Math.random()*(this.maxXDist-this.minXDist)));
		int yMove = (int)(this.minYDist+ (Math.random()*(this.maxYDist-this.minYDist)));
		
		
		// calc new X Position from end of platform
		int newX = (this.askForMapWidth()+this.x+platformSize + xMove) % askForMapWidth();
		
		
		
		
		// calc new Y Position
		int direction;
		// Dont go outside map 
	    if ((this.y + yMove) >= this.askForMapHeight()) direction = -1;
	    else if ((this.y - yMove) < 0) direction = 1;
	    // 50% probability of each direction
	    else if (Math.random()>0.5) direction = 1;
	    else direction = -1;
	    
	    
		int newY = this.y+direction*yMove;

		// setting the new position
		this.moveTo(newX, newY);
		}
		
	}

}

