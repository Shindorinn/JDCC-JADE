package english_auction.auctioneer;

import base.AuctionItem;
import jade.core.AID;
import jade.core.behaviours.Behaviour;

public class EnglishAuctionItemBehaviour extends Behaviour {

	private AuctionItem itemToAuction;
	
	private int currentHighestBid;
	private AID currentHighestBidder;
	
	public EnglishAuctionItemBehaviour(AuctionItem item) {
		this.itemToAuction = item;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
