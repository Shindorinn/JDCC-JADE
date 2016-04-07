package auctioneerBehaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.Auctioneer;
import main.Database;

public class AuctioneerAnnounceWinner extends Behaviour 
{
	private Auctioneer parent;
	private boolean auctionEnded = false;
	
	public AuctioneerAnnounceWinner(Auctioneer agent) 
	{
        parent = agent;
    }
	
	@Override
	public void action() 
	{
		// Check if there were any bids
		if(parent.maxBid > 0)
		{
			String itemName = parent.db.getItems()[parent.currentItemIndex].getName();
			ACLMessage winMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			
			// if there was only one bid, just get the max bid, else get the second max bid
			int finalPrice;
			if(parent.secondMaxBid > 0)
			{
				finalPrice = parent.secondMaxBid;
			}
			else
			{
				finalPrice = parent.maxBid;
			}
			
        	System.out.println("Winner: " + parent.maxBidAgent.getLocalName() + " item: " + itemName + " for: " + finalPrice);
			System.out.println("");

			// Create message to send to the winner to let him know that his bid was accepted
			winMsg.addReceiver(parent.maxBidAgent);
			winMsg.setContent("won," + finalPrice + "," + itemName);
			winMsg.setReplyWith("winner"+System.currentTimeMillis());
			

			// Create message to send to all other agents that they lost
			ACLMessage loseMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
			for(AID agent : parent.bidders)
			{
				if(!agent.equals(parent.maxBidAgent))
				{
					loseMsg.addReceiver(agent);
				}
			}
			loseMsg.setContent("lost," + itemName);
			loseMsg.setReplyWith("loser"+System.currentTimeMillis());
					
	        // construct message based on auction type
	        if(parent.db.getAuctionType() == Database.AuctionDatabase.ENGLISH_AUCTION)
			{
	            winMsg.setConversationId("English-bid");
	            loseMsg.setConversationId("English-loser");
			}
	        else if(parent.db.getAuctionType() == Database.AuctionDatabase.DUTCH_AUCTION)
	        {
	            winMsg.setConversationId("Dutch-bid");
	            loseMsg.setConversationId("Dutch-loser");
	                 }
	        else if(parent.db.getAuctionType() == Database.AuctionDatabase.SECOND_PRICE_AUCTION)
	        {
	        	winMsg.setConversationId("SecondPrice-winner");
	        	loseMsg.setConversationId("SecondPrice-loser");
	        }
			parent.send(winMsg);
			parent.send(loseMsg);
						
			// Initialize all variables for next round
            parent.foundBidders = false;
            parent.bidProposalSent = false;
            parent.bidProposalsRepliesReceived = false;                
            parent.maxBid = 0;
            parent.maxBidAgent = null;
		}
		else 
		{
			String itemName = parent.db.getItems()[parent.currentItemIndex].getName();
            System.out.println(itemName + ": No bids were made. No Winner");
            System.out.println("");
        }
        auctionEnded = true;
        
     // go to the next item
        parent.currentItemIndex++; 
	}

	@Override
	public boolean done() 
	{
		return auctionEnded;
	}

}
