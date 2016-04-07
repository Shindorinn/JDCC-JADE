package main;
import java.util.*;

import auctioneerBehaviours.AuctioneerSecondPrice;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Auctioneer extends Agent {
	public enum AuctionType 
	{
	    english,
	    dutch,
	    secondPrice
	}
	
	public static AuctionType AUCTION_TYPE = AuctionType.secondPrice;
	
	public Database.AuctionDatabase db;
	
	public boolean auctionStarted = false;
	public boolean foundBidders = false;
	public boolean bidProposalSent = false;
	public boolean bidProposalsRepliesReceived = false;
	
	public int currentItemIndex = 0;
	public int maxBid = 0;
	public int secondMaxBid = 0;
	public AID maxBidAgent;
	
	public AID[] bidders;
    public MessageTemplate mt; 
	
	@Override
	protected void setup()
	{		
		// Create the items database
		db = new Database.AuctionDatabase();
		
		if(db.getAuctionType() == 3)
		{
			System.out.println("STARTING SECOND-PRICE AUCTIONS");
			addBehaviour(new AuctioneerSecondPrice(this));
		}
	}
}
