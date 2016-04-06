package auctioneerBehaviours;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import main.Auctioneer;

public class AuctioneerFindBidders extends Behaviour 
{
	private Auctioneer parent;
	
	public AuctioneerFindBidders(Auctioneer agent) 
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
	            System.out.println("");
	            System.out.println("Bidders found: " + agentsFound.length);
	            if (agentsFound.length > 0) 
	            {
	                parent.bidders = new AID[agentsFound.length];
	                for (int i = 0; i < agentsFound.length; ++i) 
	                {
	                    parent.bidders[i] = agentsFound[i].getName();
	                    System.out.println("Bidder Name: " + parent.bidders[i].getName());
	                }
	                System.out.println("");
	                
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
