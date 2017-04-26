package unitn.aose.warehousesim.agent;

import aos.jack.jak.agent.Agent;

public class DataLog{
    private Agent agent;
    //metodo che stampa messaggio che gli do
    public DataLog(Agent a){
        agent = a;
    }
    
    public void log(String msg){
        System.out.println(agent.timer.getTime() + " " + agent.getName() + " " + msg);
    }
}
