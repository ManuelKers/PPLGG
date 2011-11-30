package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;


public class CellAgent extends AbstractAgent {

	int size;

	public CellAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);
		// TODO Auto-generated constructor stub

		size = 1;


	}

	@Override
	public void performStep() {
		// TODO Auto-generated method stub
		for (int x = -size; x <=size; x++){
			int tmpX = this.x+x;
			for (int y = -size; y <= size; y++){
				int tmpY = this.y+y;



				//put game of life code here
				int neighboursAlive = 0;

				// check neighbours (rewrap on map)
				//for (int checkX = -1 ; checkX )
				for (int i=1;i<=2;i++){
					// first check to the right
					if (isAlive((width + tmpX+i)%width,tmpY+1)) neighboursAlive++;
					// check to the left
					if (isAlive((width+tmpX-i)&width,tmpY)) neighboursAlive++;
					// check down
					if (isAlive(tmpX, (height+tmpY+1)%height)) neighboursAlive++;
					// check up
					if (isAlive(tmpX, (height+tmpY-1)%height)) neighboursAlive++;
				}




				// we should now have collected the number of neighbours alive
                // GameOFLIFE
				if (isAlive(tmpX,tmpY)){
					if (neighboursAlive<=2 || neighboursAlive > 4) sendMapRequest(tmpX, tmpY, Terrain.EMPTY);
					//decreaseTokens(1);
				}
				else {
					if (neighboursAlive==3) sendMapRequest(tmpX, tmpY, Terrain.SOLID);
					//decreaseTokens(1);
				}

			}

		}
        
		// the size of this agent will continue to grow until tokens have been used
		size++;
	}

	private boolean isAlive(int x,int y){
		boolean b=false;
		if (requestMapInformation(x, y)==Terrain.SOLID){
			b = true;
		}

		return b;
	}

}
