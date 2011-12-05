package agents;


import pplgg.MapManager;
import pplgg.PPLGG.Terrain;
import pplgg.Position;

public class GAgent extends AbstractAgent {



	// ENUMERATION 
	private enum MoveStyles{
		VECTOR,													//Follows a Vector
		RANDOM_DIRECTION,										//Changes Direction randomly moves 1 step, could maybe be excluded fuctionality is found in random step, when StepSize is 1
		RANDOM_STEP, 											//Steps in Random Direction 
		FIXED_STEP												// Doesn't Change Direction.

	}

	private enum TriggerState{
		TRIGGER_FOUND_ON_POS,									// If Trigger is found on position;
		ALWAYS_ON,
		AREA_FULL,												//Area full of triggerTerrain
		AREA_NONE,												//No TriggerTerrain is Found
		TIMER,													//Triggers in Intervals defined by intervalSteps, will be reset every time it is triggered
		PROBABILITY                                             //Triggers with a certain probability
	}



	private enum MoveStylesBoundaries{
		START,
		RANDOM_WHITHIN_AREA,
		BOUNCE
	}

	// actions
	private enum Actions{
		PLACE_AT_POSITION, ROOM_AT_POSITION, CIRCLE_AT_POSITION, PLATFORM_AT_POSITION, CROSS_AT_POSITION, RECT_AT_POSITION, WALL_AT_POSITION, GAME_OF_LIFE_IN_AREA
	}

	// SETTING THE AGENT TYPE
	private MoveStyles moveStyle;								//how will the agent move
	private MoveStylesBoundaries moveStyleBoundaries;			//how will the agent move when it hits a boundary	
	private TriggerState trigger;								//what will trigger the agent to perform an action
	private Actions triggedAction;								//what Action will it perform


	Terrain triggerTerrain;										//Kind of Terrain that the agents react to
	Terrain actionTerrain;										//Terrain that the Agent will place in the level
	Terrain switchTerrain;                                      //When ever the agent uses the switch action this is what it will switch to;

	// VARIABLES--MOVEMENT
	Position thisSpawnPos;

	// VECTOR
	private float xSpeed;
	private float ySpeed;
	private float xFloat;
	private float yFloat;




	private int areaSize;

	// random initializer values thes should probably be a part of the generator
	private int minWidth = 7;
	private int maxWidth = 10;
	private int minHeight = 7;
	private int maxHeight = 10;
	private int minStepSize = 1;
	private int maxStepSize = 5;
	private int minTimeStep = 1;
	private int maxTimeStep = 100;
	private float minProbability = 0;
	private float maxProbability = 1;

	private int areaWidth;
	private int areaHeight;
	private int stepX;
	private int stepY;

	private int intervalTimeSteps;
	private int currentTimeStep;

	private float probability;

	// used to determine direction of next move
	private int xDirection;
	private int yDirection;
	private int[] possibleDirections = {
			1,
			-1
	};








	public GAgent(MapManager newManager, int tokens, Position spawnPos_,
			int waitingPeriod) {
		super(newManager, tokens, spawnPos_, waitingPeriod);

		thisSpawnPos = spawnPos_;


		// section to initialize each agent setting all the variables
		xSpeed = (float)Math.random();
		ySpeed = (float)Math.random();

		areaSize = 100;
		areaWidth = randomBetween(minWidth, maxWidth);
		areaHeight = randomBetween(minHeight, maxHeight);

		minStepSize=1;
		maxStepSize=5;

		xDirection = possibleDirections[(int)Math.random()*possibleDirections.length];
		yDirection = possibleDirections[(int)Math.random()*possibleDirections.length];

		stepX = randomBetween(minStepSize, maxStepSize);
		stepY = randomBetween(minStepSize, maxStepSize);

        intervalTimeSteps = randomBetween(minTimeStep, maxTimeStep);
		currentTimeStep = 0;
		
		probability = minProbability+(float)(Math.random()*(maxProbability-minProbability));


		triggerTerrain = Terrain.values()[(int)(Math.random()*Terrain.values().length)];
		actionTerrain = Terrain.values()[(int)(Math.random()*Terrain.values().length)];
		switchTerrain = Terrain.values()[(int)(Math.random()*Terrain.values().length)];


		// setting the moveStyle
		moveStyle = MoveStyles.values()[(int)(Math.random()*MoveStyles.values().length)];

		// setting the move style when on Boundary
		moveStyleBoundaries = MoveStylesBoundaries.values()[(int)(Math.random()*MoveStylesBoundaries.values().length)];

		// setting the trigger
		trigger = TriggerState.values()[(int)(Math.random()*TriggerState.values().length)];



		// setting the action it is supposed to perform
		triggedAction = Actions.values()[(int)(Math.random()*Actions.values().length)];
	}

	@Override
	public void performStep() {
		// move
		move();

		// if triggered, doAction
		if (isTriggered()){
			performAction();
			//post Action Step regarding variables
			//postAction();
		}

		// else do nothing


	}

	private void move(){

		int tmpX;
		int tmpY;

		switch(moveStyle){

		case VECTOR:
			xFloat += xSpeed*xDirection;
			yFloat += ySpeed*yDirection;
			tmpX = (int)(xFloat);
			tmpY = (int)(yFloat);
			// check if it is on map
			if (isOnMap(tmpX,tmpY)) 
				moveTo(tmpX, tmpY);
			else onBoundaries(tmpX,tmpY);

			break;

		case RANDOM_DIRECTION:
			int dx, dy;

			switch ((int)(4*Math.random())) {
			case 0:
				dx = 1;
				dy = 0;
				break;
			case 1:
				dx = 0;
				dy = -1;
				break;
			case 2:
				dx = -1;
				dy = 0;
				break;
			case 3:
				dx = 0;
				dy = 1;
				break;
			default:
				dx = 0; dy = 0;
				break;
			}

			tmpX = x+dx;
			tmpY = y+dy;

			if (isOnMap(tmpX,tmpY)) 
				moveTo(tmpX, tmpY);
			else onBoundaries(tmpX,tmpY);

			break;

		case RANDOM_STEP:
			int dx1, dy1;
			stepX = randomBetween(minStepSize, maxStepSize);
			stepY = randomBetween(minStepSize, maxStepSize);


			switch ((int)(4*Math.random())) {
			case 0:
				dx1 = 1;
				dy1 = 0;
				break;
			case 1:
				dx1 = 0;
				dy1 = -1;
				break;
			case 2:
				dx1 = -1;
				dy1 = 0;
				break;
			case 3:
				dx1 = 0;
				dy1 = 1;
				break;
			default:
				dx1 = 0; dy1 = 0;
				break;
			}

			tmpX = x+stepX*dx1;
			tmpY = y+stepY*dy1;

			if (isOnMap(tmpX,tmpY)) 
				moveTo(tmpX, tmpY);
			else onBoundaries(tmpX,tmpY);

			break;

		case FIXED_STEP:
			tmpX = x+stepX*xDirection;
			tmpY = y+stepY*yDirection;

			if (isOnMap(tmpX,tmpY)) 
				moveTo(tmpX, tmpY);
			else onBoundaries(tmpX,tmpY);


			break;
		}




	}

	private void onBoundaries(int tmpX, int tmpY){
		// check whichBoundary it is
		switch (moveStyleBoundaries){
		case START:
			moveTo(thisSpawnPos.x,thisSpawnPos.y);
			xFloat = thisSpawnPos.x;
			yFloat = thisSpawnPos.y;
			break;

		case RANDOM_WHITHIN_AREA:
			int newX,newY;

			do {
				newX = thisSpawnPos.x + randomBetween((int)(-0.5*areaSize),(int) (0.5*areaSize));
				newY = thisSpawnPos.y + randomBetween((int)(-0.5*areaSize),(int)(0.5*areaSize));

			} while (!isOnMap(newX,newY));

			moveTo(newX,newY);
			xFloat = newX;
			yFloat = newY;


			break;

		case BOUNCE:
			if (tmpX<0||tmpX>width-1)
				xDirection *= -1;
			if (tmpY<0||tmpY>height-1)
				yDirection *= -1;
			break;
		}
	}

	boolean isTriggered(){
		boolean b = false;
		switch(trigger){

		case TRIGGER_FOUND_ON_POS:
			if(requestMapInformation(x, y)==triggerTerrain) b = true;
			break;

		case ALWAYS_ON:
			b = true;
			break;

			// is trigged when all the area is full of trigger Terrain
		case AREA_FULL:
			b = true;
			for (int x=0;x<areaWidth;x++){
				for (int y=0;y<areaHeight;y++){
					int tmpX = this.x+x;
					int tmpY = this.y+y;
					if (isOnMap(tmpX, tmpY)) {
						if (requestMapInformation(tmpX, tmpY)!=triggerTerrain){
							b = false;
							break;
						}
					}
				}		
			}



			break;

		case AREA_NONE:
			b = true;
			for (int x=0;x<areaWidth;x++){
				for (int y=0;y<areaHeight;y++){
					int tmpX = this.x+x;
					int tmpY = this.y+y;
					if (isOnMap(tmpX, tmpY)) {
						if (requestMapInformation(tmpX, tmpY)==triggerTerrain){
							b = false;
							break;
						}
					}
				}		
			}

			break;

		case PROBABILITY:
			if (Math.random()<probability) b=true;
			break;

		case TIMER:
			if (currentTimeStep>=intervalTimeSteps){
				b=true;
				currentTimeStep = 0;
			}
			else
				currentTimeStep++;
			break;

		}
		return b;
	}


	private void performAction(){
		switch(triggedAction){

		case PLACE_AT_POSITION:
			checkAndSendMapRequest(x, y, actionTerrain);
			break;

		case ROOM_AT_POSITION:
			// creating floor and ceiling
			int y1 = y;
			int y2 = y + areaHeight-1;
			int x1 = x;
			int x2 = x+areaWidth-1;

			for (int x=0;x<areaWidth;x++){
				int tmpX = x1+x;
				checkAndSendMapRequest(tmpX, y1, actionTerrain);
				checkAndSendMapRequest(tmpX, y2, actionTerrain);

			}
			// creating walls
			for (int y=0;y<areaHeight;y++){
				int tmpY = y1+y;
				checkAndSendMapRequest(x1, tmpY, actionTerrain);
				checkAndSendMapRequest(x2, tmpY, actionTerrain);

			}		
			break;

		case CIRCLE_AT_POSITION:
			// grows around point, action parameter = radius
			float range = (float)Math.PI*2;
			float steps = range/100;
			float xRadius = (float)0.5*areaWidth;
			float yRadius = (float)0.5*areaHeight;
			int oldX = -1;
			int oldY = -1;
			for (float d = 0; d < range; d+=steps){
                
				int newX = (int) (this.x + xRadius*Math.cos(d));
				int newY = (int) (this.y - yRadius*Math.sin(d));
				if (newX!=oldX||newY!=oldY){
					checkAndSendMapRequest(newX, newY, actionTerrain);
					oldX = newX;
					oldY = newY;
				}

			}

			break;

		case PLATFORM_AT_POSITION:
			for (int x=0; x< areaWidth; x++){
				int tmpX = this.x+x;
				checkAndSendMapRequest(tmpX, y, actionTerrain);
			}

			break;

		case CROSS_AT_POSITION:
			int xRange = (int)(areaWidth*0.5);
			int yRange = (int)(areaHeight*0.5);
			for (int x=-xRange;x<=xRange;x++){
				int tmpX = this.x+x;
				checkAndSendMapRequest(tmpX, y, actionTerrain);

			}

			for (int y=-yRange;y<=yRange;y++){
				int tmpY = this.y+y;
				checkAndSendMapRequest(x, tmpY, actionTerrain);

			}
			break;

		case RECT_AT_POSITION:
			for (int x=0;x<areaWidth;x++){
				for (int y=0;y<areaHeight;y++){
					int tmpX = this.x+x;
					int tmpY = this.y+y;
					checkAndSendMapRequest(tmpX, tmpY, actionTerrain);

				}
			}

			break;

		case WALL_AT_POSITION:
			for (int y=0; y< areaHeight; y++){
				int tmpY = this.y+y;
				checkAndSendMapRequest(y, tmpY, actionTerrain);

			}
			break;

		case GAME_OF_LIFE_IN_AREA:
			int xRange1 = (int)(areaWidth*0.5);
			int yRange1 = (int)(areaHeight*0.5);
			for (int x = -xRange1; x <=xRange1; x++){
				int tmpX = (width+this.x+x)%width;//Special Wrap Around Case
				for (int y = -yRange1; y <= yRange1; y++){
					int tmpY = (height+this.y+y)%height;//Again Wrap Around Case

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

					// GameOFLIFE
					if (isAlive(tmpX,tmpY)){
						if (neighborsAlive<2 || neighborsAlive > 3){
							checkAndSendMapRequest(tmpX, tmpY, this.actionTerrain);

						}
					}
					else if (neighborsAlive==3){
						checkAndSendMapRequest(tmpX, tmpY, this.switchTerrain);

					}




				}

			}


		}



	}

	private void checkAndSendMapRequest(int x, int y, Terrain t){
		if (isOnMap(x,y)){
			sendMapRequest(x, y, t);
			decreaseTokens(1);
		}
	}
	private boolean isAlive(int x,int y){
		boolean b=false;
		if (requestMapInformation(x, y)==actionTerrain){
			b = true;
		}

		return b;
	}
}


