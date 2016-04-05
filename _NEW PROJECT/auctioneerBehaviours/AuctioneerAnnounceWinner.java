package auctioneerBehaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import main.Auctioneer;

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
			
			// send message to the winner to let him know that his bid was accepted
			ACLMessage winMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			winMsg.addReceiver(parent.maxBidAgent);
			winMsg.setContent("won," + finalPrice);
			winMsg.setConversationId("SecondPrice-winner");
			winMsg.setReplyWith("winner"+System.currentTimeMillis());
			
			String itemName = parent.db.getItems()[parent.currentItemIndex].getName();
			System.out.println("Winner: " + parent.maxBidAgent.getLocalName() + " item: " + itemName + " for: " + finalPrice);
			System.out.println("");
			
			// Initialize all variables for next round
            parent.foundBidders = false;
            parent.bidProposalSent = false;
            parent.bidProposalsRepliesReceived = false;                
            parent.maxBid = 0;
            parent.maxBidAgent = null;
		}
		else 
		{
            System.out.println("No bids were made. No Winner");
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
