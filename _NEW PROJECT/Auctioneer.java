import java.util.*;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Auctioneer extends Agent 
{
	public enum AuctionType 
	{
	    english,
	    dutch,
	    secondPrice
	}
	
	AuctionType auctionType;
	boolean auctionStarted = false;
	boolean foundBidders = false;
	
	public AID[] bidders;
	
	@Override
	protected void setup()
	{
		// Create the items database
		Database.AuctionDatabase db = new Database.AuctionDatabase();
		
		// find all available agents/bidders
		findBidders();
		
		if(auctionType == AuctionType.secondPrice)
		{
			
		}
	}
	
	private void findBidders()
	{
		// Check if all agents were already found
		if(!foundBidders)
		{
			DFAgentDescription dfTemplate = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("auctionBidderAgent");
            dfTemplate.addServices(sd);
            
            try 
            {
            	// Use the created DFAgentDecription to find all matching agents and add them to the bidders list
	            DFAgentDescription[] agentsFound = DFService.search(this, dfTemplate); 
	            if (agentsFound.length > 0) 
	            {
	                bidders = new AID[agentsFound.length];
	                for (int i = 0; i < agentsFound.length; ++i) 
	                {
	                    bidders[i] = agentsFound[i].getName();
	                }
	                foundBidders = true;  
	            }
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
		}
	}
}
