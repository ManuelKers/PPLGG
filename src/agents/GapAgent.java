/**
 * 
 */
package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;

/**
 * @author jeppetuxen
 *
 */
public class GapAgent extends AbstractAgent {

	int minWidth = 1;
	int maxWidth = 3;
	int width;
	/**
	 * @param newManager
	 */
	public GapAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
        super(newManager, tokens, spawnPos, waitingPeriod); 
        y = 0; //move to the top
        width = (int)(minWidth+(maxWidth-minWidth)*Math.random());
        
		
	}

	/* (non-Javadoc)
	 * @see agents.AbstractAgent#performStep()
	 */
	@Override
	public void performStep() {
		
		// do this code if position is solid, Create gap
		if (this.requestMapInformation(x, y) == Terrain.SOLID ){
			for(int i = 0; i < width ; i++){
			    if((this.x+i)<askForMapWidth()){
				this.sendMapRequest(x+i, y, Terrain.EMPTY);
			
			this.decreaseTokens(1);
			    }
			}
		}
		
		//always move agent, if on the border return to top
		if (this.y==this.askForMapHeight()-1) this.moveTo((int)(Math.random()*this.askForMapWidth()), 0);
		else this.moveTo(x, y+1);
		
		 
	}

}
