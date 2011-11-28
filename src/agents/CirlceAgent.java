package agents;

import pplgg.MapManager;
import pplgg.Position;

public class CirlceAgent extends AbstractAgent {
	
	int minRadius = 2;
	int maxRadius = 8;
	int radius;
	int moveStyleAction = 1;// how will it move after an action is performed
	int moveStyleNoAction = 1; // how will it move after no action is performed
	Actions action = Actions.CIRCLE; // what action is performed, 
	ActionTriggers actionTrigger = ActionTriggers.ALWAYS; // what will trigger the action
	int moveX = -1; // the direction of the move on x
	int moveY = 1; // the direction of the move on y
	public CirlceAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);
		radius = randomBetween(minRadius, maxRadius);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performStep() {
		// if so do action, and move
				if (actionIsRequired(actionTrigger)){
					doAction(action,radius);
					//makeMove(moveStyleAction,moveX,moveY);
					radius++;
					this.decreaseTokens(1);
					
					
					
				}
				else makeMove(this.moveStyleNoAction,moveX,moveY);
				//System.out.println("y pos "+ this.y);


	}

}
