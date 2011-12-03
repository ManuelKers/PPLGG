package agents;

import pplgg.MapManager;
import pplgg.Position;

//class SwipingBomb
// swipes from left to right beeing triggered whenever it meets solid


public class SwipingBombAgent extends AbstractAgent {

	int minRadius = 1;
	int maxRadius = 5;
	int radius;
	int moveStyleAction = 1;// how will it move after an action is performed
	int moveStyleNoAction = 1; // how will it move after no action is performed
	Actions action = Actions.BOMB; // what action is performed, 
	ActionTriggers actionTrigger = ActionTriggers.SOLID; // what will trigger the action
	int moveX = -1;
	int moveY = 1;
	//int swipesLeft = 10;


	public SwipingBombAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);

		radius = this.randomBetween(this.minRadius, this.maxRadius);
		//System.out.println("bomb Size "+ radius);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performStep() {
		// check if action is required at current position

		// if so do action, and move
		if (actionIsRequired(actionTrigger)){
			doAction(action,radius);
			makeMove(moveStyleAction,moveX,moveY);
			
			
			
			// swipeFromLeftToRight
		}
		else makeMove(this.moveStyleNoAction,moveX,moveY);
		

	}
}
