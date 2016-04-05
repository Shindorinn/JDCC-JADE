package auctioneerBehaviours;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import main.Auctioneer;

public class AuctioneerSecondPrice extends TickerBehaviour 
{	
	private Auctioneer parent;
	
	public AuctioneerSecondPrice(Auctioneer agent)
	{
		super(agent, 500);
		parent = agent;
	}
	
	@Override
	public void onTick()
	{
		if(parent.db.getItems().length > parent.currentItemIndex)
		{	
			// Find available Bidders
	        parent.addBehaviour(new AuctioneerFindBidders(parent));
	        
	        int currentItem = parent.currentItemIndex;
	        String currentItemName = parent.db.getItems()[currentItem].getName();
	        int currentItemStartingPrice = parent.db.getItems()[currentItem].getStartingPrice();
	        
	        // Send bid proposal to all bidders
	        parent.addBehaviour(new AuctioneerSendBidProposal(parent, currentItemName, currentItemStartingPrice));
	        
	        // Receive the replies to the bid proposals
	        parent.addBehaviour(new AuctioneerReceiveBids(parent));
	        
	        // if all replies to the bid proposals were received, announce the winner
	        if(parent.bidProposalsRepliesReceived)
	        {
	        	parent.addBehaviour(new AuctioneerAnnounceWinner(parent));
	        }
		}
	}
}
