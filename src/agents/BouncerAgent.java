package agents;

import pplgg.MapManager;
import pplgg.PPLGG.Terrain;
import pplgg.Position;

public class BouncerAgent extends AbstractAgent {

    
	
	private float speedX;
	private float speedY;
	private float floatX;
	private float floatY;

	public BouncerAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);

		//speeds can vari between -1 and 1;
		speedX = (float)Math.random()*2-1;
		speedY = (float)Math.random()*2-1;
		floatX = x;
		floatY = y;

	}

	@Override
	public void performStep() {

		//create ground on position decrease token
		if (isOnMap(x, y)){
			sendMapRequest(x, y, Terrain.SOLID);
			decreaseTokens(1);
		}

		//calc new tmpfloat pos;
		float tmpFloatX = floatX + speedX;
		float tmpFloatY = floatY + speedY;

		// update speeds
		if (!isOnMap((int)tmpFloatX,0)) speedX = speedX * -1;

		if (!isOnMap(0,(int)tmpFloatY)) speedY = speedY *-1;

		//calc final float pos
		floatX +=  speedX;
		floatY +=  speedY; 
		// move 
		moveTo((int)floatX, (int)floatY);

		// TODO Auto-generated method stub

	}

}
