package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;


//class RoomAgent -- creates rooms if there is space for it, moves randomly to a new position
public class RoomAgent extends AbstractAgent {


	private int minWidth = 3;
	private int maxWidth = 10;

	private int minHeight = 3;
	private int maxHeight = 10;

	private int roomWidth;
	private int roomHeight;

	private int maxGap = 0;

	private int gapX;

	private int newY;
	private int displaceY;




	public RoomAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
		super(newManager, tokens, spawnPos, waitingPeriod);

		roomWidth = (minWidth + (int)(Math.random()*(maxWidth-minWidth)));
		roomHeight = (minHeight + (int)(Math.random()*(maxHeight-minHeight)));

	}


	@Override
	public void performStep() {
		// TODO Auto-generated method stub

		// uncomment if agent should always make a room
		
		boolean empty = true;
		if (isOnMap(x, y)&&isOnMap(x+roomWidth, y+roomHeight)){
			for (int x = 0; x < roomWidth; x++){
				for (int y = 0; y < roomHeight; y++){
					if (requestMapInformation( x+this.x, y+this.y )== Terrain.SOLID){
						empty=false;
						break;
					}
				}
			}


			if (empty){
				newY = makeRoom(newY);
				calcNew();

			}
	
           else init();
		}

		/*if (isOnMap(x, y)&&isOnMap(x+roomWidth, y+roomHeight)){
			newY = makeRoom(newY);
			calcNew();
		}

		else init();
*/

	}

	private int makeRoom (int yDoor){

		// creating floor and ceiling
		for (int x=0;x<roomWidth;x++){
			sendMapRequest(this.x+x,this.y,Terrain.SOLID);
			sendMapRequest(this.x+x,this.y+roomHeight-1,Terrain.SOLID);
			decreaseTokens(2);
		}

		int doorSizeLeft = 1;
		int doorSizeRight = 1;

		
		int doorPosRight = randomBetween(doorSizeRight, roomHeight-doorSizeRight);


		// creating walls
		for (int y=0;y<roomHeight;y++){

			if (y+this.y==yDoor) sendMapRequest(this.x,this.y+y,Terrain.EMPTY);
			else sendMapRequest(this.x,this.y+y,Terrain.SOLID);

			if (y==doorPosRight) sendMapRequest(this.x+roomWidth-1,this.y+y,Terrain.EMPTY);
			else sendMapRequest(this.x+roomWidth-1,this.y+y,Terrain.SOLID);




			decreaseTokens(2);
		}

		int newY = doorPosRight+this.y;

		return newY;
	}

	private void calcNew(){
		gapX = randomBetween(0, maxGap);


		// next room Size
		roomWidth = (minWidth + (int)(Math.random()*(maxWidth-minWidth)));
		roomHeight = (minHeight + (int)(Math.random()*(maxHeight-minHeight)));

		// calculate Y position
		// new room will only be moved whithin the height of old room on the y Axis.
		displaceY = (int)((Math.random()-1)*2*roomHeight);


		//		int direction;
		//		// Dont go outside map 
		//		if ((newY + displaceY) >= height) direction = -1;
		//		else if ((newY - displaceY) < 0) direction = 1;
		//		// 50% probability of each direction
		//		else if (Math.random()>0.5) direction = 1;
		//		else direction = -1;

		//int newY = (int)((height+y+((Math.random()/2-1)*roomHeight)))%height;
		int newY = y + displaceY;
		moveTo((width+x+roomWidth+gapX)%width,newY);

	}
	private void init(){
		gapX = randomBetween(0, maxGap);


		// next room Size
		roomWidth = (minWidth + (int)(Math.random()*(maxWidth-minWidth)));
		roomHeight = (minHeight + (int)(Math.random()*(maxHeight-minHeight)));

		// calculate Y position
		// new room will only be moved whithin the height of old room on the y Axis.
		displaceY = (int)((Math.random()-1)*2*roomHeight);


		//		int direction;
		//		// Dont go outside map 
		//		if ((newY + displaceY) >= height) direction = -1;
		//		else if ((newY - displaceY) < 0) direction = 1;
		//		// 50% probability of each direction
		//		else if (Math.random()>0.5) direction = 1;
		//		else direction = -1;

		//int newY = (int)((height+y+((Math.random()/2-1)*roomHeight)))%height;
		int newY = y + displaceY;
		moveTo(x,newY);

	}

}





