package agents;

import pplgg.MapManager;
import pplgg.Position;
import pplgg.PPLGG.Terrain;

public class GroundRaiseAgent extends AbstractAgent {
    
    private int targetHeight;
    private int currentWidth;
    private static int startWidth = 20; 
    
    
    public GroundRaiseAgent(MapManager newManager, int tokens, Position spawnPos, int waitingPeriod) {
        super(newManager, tokens, spawnPos, waitingPeriod);        
        y = myManager.getMapHeight()-1; //move to the bottom (ignore y in spawnPos argument)
        targetHeight = myManager.getMapHeight() - (int)( 2+ (myManager.getMapHeight()-10) * Math.random() ); //pick a random height for the platform
        currentWidth = startWidth - (int)(0.4*startWidth) + (int)(0.8*startWidth*Math.random()); //choose a width for the current platform
    }

    @Override
    public void performStep() {
        
        //determine the range of the platform (take level boundaries into account)
        int leftX = Math.max( 0, x - currentWidth/2 );
        int rightX = Math.min( myManager.getMapWidth(), x + currentWidth/2 );
        
        //raise the current platform a level
        for (int xx = leftX; xx<rightX; xx++)
            sendMapRequest( xx, y, Terrain.SOLID );
        y -= 1;
        decreaseTokens( rightX-leftX ); //remove tokens equal to the amount of ground addes
            
        //if the targetheight was reached
        if (y == targetHeight) {
            y = myManager.getMapHeight()-1; //move back to the bottom
            targetHeight = myManager.getMapHeight() - (int)( 2+ (myManager.getMapHeight()-2) * Math.random() ); //pick a new height
            //move one platform's width either right or left
            switch((int)(2*Math.random())) {
                case 0:
                    x -= currentWidth;
                    break;
                case 1:
                    x += currentWidth;
                    break;
            }
            currentWidth = startWidth - (int)(0.2*startWidth) + (int)(0.4*Math.random()); //choose a new width
            x = (x+myManager.getMapWidth()) % myManager.getMapWidth(); //periodic boundaries
        }

    }

}
