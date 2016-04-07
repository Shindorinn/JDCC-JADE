package bidderBehaviours;
import java.util.prefs.Preferences;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.BidderAgent;

public class BidderAgentReceiveBidProposal extends CyclicBehaviour 
{
	private BidderAgent parent;
	private int itemIndex;
	private String itemName;
	private int itemStartingPrice;
	
	public BidderAgentReceiveBidProposal(BidderAgent agent)
	{
		super(agent);
		parent = agent;
	}
	
	public void action()
	{
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		ACLMessage msg = parent.receive(mt);
		
		// Check if a message was received and process it
		if (msg != null) 
		{
			// split the message content
			String[] content = msg.getContent().split(",");
			itemIndex = Integer.parseInt(content[0]);
			itemName = content[1];
			itemStartingPrice = Integer.parseInt(content[2]);
			
			// create a reply
			ACLMessage reply = msg.createReply();
            
            // Check if there are enough money to participate in the auction
            if (parent.money > 0 && parent.money >= itemStartingPrice) 
            {
            	// calculate bid
            	int bid = calculateBid();
                
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(bid + "");
                System.out.println(parent.getLocalName() + " sent a new bid: " + bid);
            }
            else 
            {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("drop auction");
                System.out.println(parent.getAID().getLocalName() + " is out (Not enough money)");
            }

            // send the reply
            parent.send(reply);
		}
		else 
		{
            block();
        }
	}
	
	private int calculateBid()
	{
		int bid = 0;
		int maxPref = 0;
		
		// Find the most preferred item from the next items
		for(int i = itemIndex+1; i < parent.preferences.length; i++)
		{
			if(parent.preferences[i] > maxPref)
			{
				maxPref = parent.preferences[i];
			}
		}
		
		// The max amount of money that the agent is willing to pay for this item, is affected by:
		// 1) How much money the agent initially had
		// 2) how much it wants/prefers some of the upcoming items.
		// If there is an item next in line that the agent wants a lot, it will affect how much money it is willing to bid on this item
		// For example, if the agent wants an item next in line VERY MUCH, it will prefer to risk losing this item more in order to try more for that preferred item
		int preferenceDiff = Math.abs(maxPref - parent.preferences[itemIndex]);
		int maxBid = parent.initMoney * preferenceDiff/100;
		
		// Check if there is enough money to pay in case of bidding the max bid.
		// If not, just set the maxBid equal to all the agent's money
		if(maxBid > parent.money)
		{
			maxBid = parent.money;
		}
		
		// calculate the new bid based on the auction type
		if(parent.db.getAuctionType() == 1)
		{
			// the bid jump is based on the default percentage of the starting price and the agent's preference for this item 
			int bidJumpPercentage = parent.db.getBidJumpPercentage();
			int bidJump = itemStartingPrice * bidJumpPercentage/100;
			bidJump = bidJump * parent.preferences[itemIndex];
			
			//bid = currentBid + bidJump;
			if(bid > maxBid)
			{
				bid = maxBid;
			}
		}
		else if(parent.db.getAuctionType() == 3)
		{
			bid = maxBid;
		}
		
		return bid;
	}
}
