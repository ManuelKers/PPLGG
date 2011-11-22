package pplgg;

//make static?
public class AgentParams {
    
    Class<?> agentClass; //the class type of the agent
    int spawnTime; //at which step should the agent start
    int waitingPeriod; //period (in steps) to wait before each call of performStep of agent
    int tokens; //avaiblable tokens for the agent
    Position pos; //start position of agent
    float spawnRadius; //radius around pos where agent is allowed to start 

}