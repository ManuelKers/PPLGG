package agents;

import pplgg.MapManager;
import pplgg.PPLGG.Terrain;
import pplgg.Position;

public class StairAgent extends AbstractAgent {
    
	private int dirX;
	private int dirY;
	private int minStep = 2;
	private int maxStep = 6;
	private int stepCounter;
	private int stepLength;
	
	private static enum States{
		FALLING,
		
		BUILDING
	}
	States state;
	
	private static enum BuildDirections{
		UP,
		RIGHT
	}
	
	BuildDirections direction;
	
	public StairAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);
		// TODO Auto-generated constructor stub
		
        
        stepCounter = 0;
        
        stepLength = this.randomBetween(minStep, maxStep);
		state = States.BUILDING;
		
		direction = BuildDirections.UP;
	}

	@Override
	public void performStep() {
		// TODO Auto-generated method stub
		// fall down
		switch (state){
		
		case FALLING:
			//calculating new position
			int newX = this.x + dirX;
			int newY = this.y + dirY;
			if (this.isOnMap(newX, newY)){
				this.x = newX;
				this.y = newY;
				if (this.requestMapInformation(newX, newY)==Terrain.SOLID)
					state = States.BUILDING;
			}
			else this.decreaseTokens(1);
			break;
		
		case BUILDING:
			
			
			switch(this.direction){
			
			case UP:
				y--;
				stepCounter++;
				if(this.isOnMap(x, y)){
						this.sendMapRequest(x, y, Terrain.SOLID);
						this.decreaseTokens(1);
				}
				else  y = height-1;
				
				if (stepCounter >= stepLength){
					stepLength = this.randomBetween(minStep, maxStep);
					stepCounter = 0;
					direction = BuildDirections.RIGHT;
				}
				break;
				
			case RIGHT:
				x++;
				stepCounter++;
				if(this.isOnMap(x, y)){
					this.sendMapRequest(x, y, Terrain.SOLID);
					this.decreaseTokens(1);
			}
				else  x = 0;
				if (stepCounter >= stepLength){
					stepLength = this.randomBetween(minStep, maxStep);
					stepCounter = 0;
					direction = BuildDirections.UP;
				}
				break;
			}
			break;
		}
		
	}

}
