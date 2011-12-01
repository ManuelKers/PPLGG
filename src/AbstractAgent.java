package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;


public abstract class AbstractAgent {

	protected int x;
	protected int y;
	protected MapManager myManager;
	protected int tokens;
	protected int waitingPeriod;
	protected int width;
	protected int height;
	
	protected static enum moveStyles{
		SWIPEX
	
	}

	//start at top left
	public AbstractAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
		setTokens( tokens );
		x = spawnPos.x;
		y = spawnPos.y;
		this.waitingPeriod = waitingPeriod;
		registerWithManager( newManager );
		width = this.askForMapWidth();
		height = this.askForMapHeight();
	}

	public void registerWithManager(MapManager manager) {
		myManager = manager;
		manager.registerAgent(this);
	}

	//use this to request the terrain type at a certain position
	protected Terrain requestMapInformation(int x, int y) {
		return myManager.requestMapInformation( x, y );

	}

	//use this to request a terrain change at a certain position
	protected void sendMapRequest(int x, int y, Terrain requestedTerrain) {
		myManager.requestChange( x, y, requestedTerrain );
	}

	//use these to ask for the map dimensions
	protected int askForMapWidth() {return myManager.getMapWidth();}
	protected int askForMapHeight() {return myManager.getMapHeight();}

	//use this to move the agent somewhere
	protected void moveTo (int x, int y) {
		this.x = x;
		this.y = y;
	}

	//this needs to be implemented and is called every iteration of the algorithm
	public abstract void performStep();

	public int getWaitingPeriod() {
		return waitingPeriod;
	}

	protected void setTokens(int tokens) {
		this.tokens = tokens;
		if (tokens<=0)
			myManager.deregisterAgent(this);
	}

	protected void decreaseTokens(int amount) {
		tokens -= amount;
		if (tokens<=0)
			myManager.deregisterAgent(this);
	}

	protected int randomBetween(int min, int max){
		int i = min + (int)(Math.random()*(max-min));
		return i;
	}

	protected boolean actionIsRequired(ActionTriggers e){
		boolean b = false;
		switch(e) {

		case EMPTY:
			if (this.requestMapInformation(x, y)==Terrain.EMPTY)
				b = true;
			break;


		case SOLID:
			if (this.requestMapInformation(x, y)==Terrain.SOLID)
				b = true;
			break;

		case ALWAYS:
			b= true;
			break;


		}
		return b;
	}

	protected void makeMove(moveStyles style,int stepX,int stepY){
		switch(style){

		// Swiping on x with going stepY up or down each time we end on a border
		case SWIPEX:
			if (x+stepX>(width-1)||x+stepX<0) y = (height+y+stepY)%(height);
			x = (width+x+stepX)%(width);

			break;

		case 1:

			// Swiping on y
			if (y+stepY>(height-1)||y+stepY<0) x = (width+x+stepX)%(width);
			y = (height+y+stepY)%(height);

			break;

		case 2:
			// going from right to left
			if ((x-1)<0){
				x = height-1;
				if ((y+1)>this.height-1) y = 0;

				else y++;
			}
			else x++;
			break;



		}
	}

	protected boolean isOnMap(int x,int y){
		boolean b = false;
		if (x>=0 && x<this.width && y >= 0 && y < this.height) b = true;
		return b;
	}

	protected void doAction(Actions action, int actionParameter){
		switch(action){
		// removes solid on position
		case SOLID:
			if (isOnMap(x,y)) this.sendMapRequest(x, y, Terrain.EMPTY);
			break;
			// creates solid on position
		case EMPTY:
			if (isOnMap(x,y)) this.sendMapRequest(x, y, Terrain.SOLID);
			break;


		case BOMB:
			// remove solid from position in a certain range defined by parameter
			for (int x = 0; x < actionParameter; x++){
				for (int y = 0; y < actionParameter; y++){
					int newX = this.x + x;
					int newY = this.y + y;
					if (isOnMap(newX,newY)) this.sendMapRequest(newX, newY, Terrain.EMPTY);
					this.decreaseTokens(1);
				}
			}

			break;

		case SQUARE:
			// Creates solid square from center
			for (int x = 0; x < actionParameter; x++){
				for (int y = 0; y < actionParameter; y++){
					int newX = this.x + x;
					int newY = this.y + y;
					if (isOnMap(newX,newY)) this.sendMapRequest(newX, newY, Terrain.SOLID);
				}
			}

			break;

		case CIRCLE:
			// grows around point, action parameter = radius
			float range = (float)Math.PI*2;
			float steps = range/100;
			for (float d = 0; d < range; d+=steps){

				int newX = (int) (this.x + actionParameter*Math.cos(d));
				int newY = (int) (this.y - actionParameter*Math.sin(d));
				if (isOnMap(newX,newY)){
					if (this.requestMapInformation(newX, newY)==Terrain.EMPTY){
						this.sendMapRequest(newX, newY, Terrain.SOLID);
						this.decreaseTokens(1);
					}

				}

			}

			break;
		}
	}
}


