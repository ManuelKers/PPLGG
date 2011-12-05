package pplgg;

//make static?
public class AgentParams implements Cloneable {
    
    public Class<?> agentClass; //the class type of the agent
    
    //movementtype
    //triggertype
    //boudnaries
    public int spawnTime; //at which step should the agent start
    public int waitingPeriod; //period (in steps) to wait before each call of performStep of agent
    public int tokens; //avaiblable tokens for the agent
    public Position pos; //start position of agent
    public double spawnRadius; //radius around pos where agent is allowed to start
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}