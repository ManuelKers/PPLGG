package agents;

import pplgg.MapManager;
import pplgg.Position;

//class SwipingBomb
// swipes from left to right creating


public class SwipingBomb extends AbstractAgent {

	int minRadius = 1;
	int maxRadius = 5;
	int radius;
	int moveStyleAction = 1;// how will it move after an action is performed
	int moveStyleNoAction = 1; // how will it move after no action is performed
	int action = 1; // what action is performed, 
	int actionTrigger = 1; // what will trigger the action
	
	
	public SwipingBomb(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);
		
		radius = this.randomBetween(this.minRadius, this.maxRadius)
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performStep() {
	    // check if action is required at current position
		
		// if so do action, and move usi
       if (actionIsRequired(actionTrigger)){
    	   doAction(action,radius);
    	   makeMove(moveStyleAction);
		// swipeFromLeftToRight
       
	}

}
