package main;

import auctioneerBehaviours.AuctioneerDutchAuction;

import main.Auctioneer.AuctionType;

public class DAuctioneer extends Auctioneer {

	public AuctionType auctionType = AuctionType.dutch;

	public int currentItemPrice;
	public boolean bidTimerRanOut = false;
	
	@Override
	protected void setup()
	{		
		// Create the items database
		db = new Database.AuctionDatabase();
		
		if(auctionType == AuctionType.dutch)
		{
			addBehaviour(new AuctioneerDutchAuction(this));
		}
	}
}
