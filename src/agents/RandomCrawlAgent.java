package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;

public class RandomCrawlAgent extends AbstractAgent {

    public RandomCrawlAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
        super(newManager, tokens, spawnPos, waitingPeriod);
    }

    @Override
    public void performStep() {
        //switch terrain at position

        if (requestMapInformation( x, y ) == Terrain.EMPTY) {
            sendMapRequest( x, y, Terrain.SOLID );
        } else {
            sendMapRequest( x, y, Terrain.EMPTY );
        }

        //sendMapRequest( x, y, Terrain.SOLID );

        //move to a new position
        moveRandomly();
    }

    private void moveRandomly() {
        int dx, dy;
        int newX, newY;
        do {
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
            newX = x+dx;
            newY = y+dy;
          //check whether this move does not go out of bounds, else repeat
        } while (!(newX < askForMapWidth() && newX >=0 && newY < askForMapHeight() && newY >=0));
        
        //actually move
        moveTo(newX, newY);
        decreaseTokens( 1 );
    }

}
