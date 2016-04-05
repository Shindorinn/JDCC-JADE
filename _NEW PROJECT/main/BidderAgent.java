package main;
import java.util.Random;

import bidderBehaviours.BidderAgentRecieveBidProposal;
import bidderBehaviours.BidderAgentWinner;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BidderAgent extends Agent 
{
	public int money;
	
	protected void setup()
	{
		// Register the agent
		DFAgentDescription df = new DFAgentDescription();
		df.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("auctionBidderAgent");
		sd.setName("auctionBidderAgent");
		df.addServices(sd);
		try 
		{
			DFService.register(this, df);
		}
		catch (FIPAException fe) 
		{
			fe.printStackTrace();
		}
		
		// generate random money
		//Random rd = new Random();
		money = 50000;
		
		//Add behaviour to receive bid proposal messages
		addBehaviour(new BidderAgentRecieveBidProposal(this));
		
		// Add behaviour to receive the winning message if this agent won the auction
		addBehaviour(new BidderAgentWinner(this));
	}
}
