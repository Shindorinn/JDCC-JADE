package auctioneerBehaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import main.Auctioneer;

public class AuctioneerReceiveBids extends Behaviour 
{
	private Auctioneer parent;
	int replies = 0;
	
	public AuctioneerReceiveBids(Auctioneer agent) 
	{
        super(agent);
        parent = agent;
    }
	
	public void action()
	{
		// check if the bid proposals were already sent to the bidders
		if(parent.bidProposalSent)
		{
			// receive replies
			ACLMessage msg = myAgent.receive(parent.mt);
			
			// check if message is empty
			if (msg != null) 
			{
                // check if this reply was a new bid
                if (msg.getPerformative() == ACLMessage.PROPOSE) 
                {
                    int bid = Integer.parseInt(msg.getContent());
                    if (parent.maxBidAgent == null || bid > parent.maxBid) 
                    {                    	
                        // store as a new max bid
                    	parent.secondMaxBid = parent.maxBid;
                        parent.maxBid = bid;
                        parent.maxBidAgent = msg.getSender();
                    }
                    else if(bid > parent.secondMaxBid)
                    {
                    	parent.secondMaxBid = bid;
                    }

                    // Let the agent know that the bid was received with an inform reply
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("bid received");
                    parent.send(reply);
                }
                else if (msg.getPerformative() == ACLMessage.REFUSE)
                {
                    // only for english and dutch
                }

                replies++;
            }
            else 
            {
                block();
            }
			
			if (replies >= parent.bidders.length) 
			{
                // We have received all bids
                parent.bidProposalsRepliesReceived = true;
            }
		}
	}
	
	public boolean done() 
	{
        return parent.bidProposalsRepliesReceived;
    }
}
