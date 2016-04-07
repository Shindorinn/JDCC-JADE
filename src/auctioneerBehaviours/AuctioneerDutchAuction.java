package auctioneerBehaviours;

import jade.core.behaviours.TickerBehaviour;
import main.AuctionItem;
import main.DAuctioneer;

public class AuctioneerDutchAuction extends TickerBehaviour {

	private DAuctioneer parent;
	private boolean auctionBusy;
	
	public AuctioneerDutchAuction(DAuctioneer agent)
	{
		super(agent, 500);
		parent = agent;
		auctionBusy = false;
	}
	
	@Override
	protected void onTick() {

		//System.out.println("STARTING DUTCH AUCTIONS");
		
		if(parent.currentItemIndex >= parent.db.getItems().length){
			// TODO : Finish the auctions nicely
			parent.removeBehaviour(this);
			this.stop();
		}
		
		AuctionItem currentItem = parent.db.getItems()[parent.currentItemIndex];
        String currentItemName = currentItem.getName();
        int currentItemStartingPrice = currentItem.getStartingPrice();
        
		
		if(parent.db.getItems().length > parent.currentItemIndex && !auctionBusy)
		{				    
	        if(parent.currentItemPrice < 0)
	        	parent.currentItemPrice = currentItemStartingPrice;
	        
	        auctionBusy = true;
		}
		
		if(auctionBusy)
			// Find available Bidders
	        parent.addBehaviour(new AuctioneerFindBidders(parent));
	        			
	        // Send bid proposal to all bidders
	        parent.addBehaviour(new AuctioneerSendBidProposal(parent, currentItemName, currentItemStartingPrice));
	        
	        // Receive the replies to the bid proposals
	        parent.addBehaviour(new AuctioneerDutchReceiveBids(parent));
	        
	        if(parent.bidTimerRanOut){
	        	if(parent.maxBidAgent != null){
	        		// if all replies to the bid proposals were received, announce the winner
	    	       parent.addBehaviour(new AuctioneerAnnounceWinner(parent));
    	        	parent.bidTimerRanOut = false;
    	        	parent.currentItemPrice = -1;
    	        	parent.bidProposalsRepliesReceived = false;
    	        	auctionBusy = false;
    	        } else {
    	        	parent.currentItemPrice -= (currentItem.getStartingPrice() - currentItem.getMinimumPrice()) / 10;
    	        }
	        }
	        
	        if(parent.currentItemPrice < currentItem.getMinimumPrice()){
	        	// Auction failed for this item.
	        	System.out.println("Auction failed for item: " + currentItemName);
	        	parent.currentItemIndex++;
	        	parent.bidTimerRanOut = false;
	        	auctionBusy = false;
	        }   
		}
}
