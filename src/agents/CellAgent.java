package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;


public class CellAgent extends AbstractAgent {

	int size;
	boolean first = true;

	public CellAgent(MapManager newManager, int tokens, Position spawnPos,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);
		// TODO Auto-generated constructor stub

		size = 10;


	}

	@Override
	public void performStep() {
		
		if (first){
			//create glider
			sendMapRequest((width+x-1)%width, y, Terrain.SOLID);
			sendMapRequest(x, (height+y+1)%height, Terrain.SOLID);
			sendMapRequest((width+x+1)%width, (height+y-1)%height, Terrain.SOLID);
			sendMapRequest((width+x+1)%width, y, Terrain.SOLID);
			sendMapRequest((width+x+1)%width, (height+y+1)%height, Terrain.SOLID);
			
			first = false;
		}
		// TODO Auto-generated method stub
		for (int x = -size; x <=size; x++){
			int tmpX = (width+this.x+x)%width;
			for (int y = -size; y <= size; y++){
				int tmpY = (height+this.y+y)%height;



				//put game of life code here
				int neighborsAlive = 0;

				// check neighbors (re-wrap on map)
				//for (int checkX = -1 ; checkX )
				
				//Check neighbor cells moving one y up and down
				for (int i=-1; i<=1;i++){
					if (isAlive((width + tmpX+i)%width,(height+tmpY-1)%height)) neighborsAlive++;
					if (isAlive((width + tmpX+i)%width,(height+tmpY+1)%height)) neighborsAlive++;
					
				}
				
				if (isAlive((width + tmpX+1)%width, tmpY)) neighborsAlive++;
				if (isAlive((width + tmpX-1)%width, tmpY)) neighborsAlive++;
				
				
				
				/*for (int i=1;i<=2;i++){
					// first check to the right
					if (isAlive((width + tmpX+i)%width,tmpY+1)) neighboursAlive++;
					// check to the left
					if (isAlive((width+tmpX-i)&width,tmpY)) neighboursAlive++;
					// check down
					if (isAlive(tmpX, (height+tmpY+1)%height)) neighboursAlive++;
					// check up
					if (isAlive(tmpX, (height+tmpY-1)%height)) neighboursAlive++;
				}*/



                //boolean somethingHappened = false;
				// we should now have collected the number of neighbors alive
                // GameOFLIFE
				if (isAlive(tmpX,tmpY)){
					if (neighborsAlive<2 || neighborsAlive > 3){
						sendMapRequest(tmpX, tmpY, Terrain.EMPTY);
					//decreaseTokens(1);
						//somethingHappened = true;
					}
				}
				else if (neighborsAlive==3){
					sendMapRequest(tmpX, tmpY, Terrain.SOLID);
					//somethingHappened = true;
				}
				
				
				// debug
				/*if (!somethingHappened){
					sendMapRequest(tmpX, tmpY, Terrain.SOLID);
				}*/
					
				
				
			}

		}
        
		// the size of this agent will continue to grow until tokens have been used
		
		//if (size<width && size< height) size++;
		
	}

	private boolean isAlive(int x,int y){
		boolean b=false;
		if (requestMapInformation(x, y)==Terrain.SOLID){
			b = true;
		}

		return b;
	}

}
