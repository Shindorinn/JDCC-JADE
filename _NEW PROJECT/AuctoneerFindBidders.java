import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AuctoneerFindBidders extends Behaviour 
{
	private Auctioneer parent;
	
	public AuctoneerFindBidders(Auctioneer agent) 
	{
        super(agent);
        parent = agent;
    }
	
	public void action()
	{
		if(!parent.foundBidders)
		{
			DFAgentDescription dfTemplate = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("auctionBidderAgent");
            dfTemplate.addServices(sd);
            
            try 
            {
	            DFAgentDescription[] agentsFound = DFService.search(parent, dfTemplate); 
	            if (agentsFound.length > 0) 
	            {
	                parent.bidders = new AID[agentsFound.length];
	                for (int i = 0; i < agentsFound.length; ++i) 
	                {
	                    parent.bidders[i] = agentsFound[i].getName();
	                }
	                parent.foundBidders = true;  
	            }
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
		}
	}
	
	public boolean done() 
	{
        return parent.foundBidders;
    }
}
