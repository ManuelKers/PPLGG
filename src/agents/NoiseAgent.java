package agents;

import pplgg.MapManager;
import pplgg.Position;


public class NoiseAgent extends AbstractAgent {

	private float d;
	private float dx;
	private float dy;
	
	public NoiseAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);
		// TODO Auto-generated constructor stub
		d = 0;
		
	}

	@Override
	public void performStep() {
		// TODO Auto-generated method stub

	}

}
