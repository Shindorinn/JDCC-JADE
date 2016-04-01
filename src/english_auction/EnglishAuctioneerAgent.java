package english_auction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import base.AuctionItem;
import english_auction.auctioneer.EnglishAuctioneerBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class EnglishAuctioneerAgent extends Agent {

	private static String DEFAULT_NICKNAME = "George";
	
	private AID nickname;
	private Random random;
	
	private List<AuctionItem> itemsToAuction;
	
	protected void setup(){
		
//		Object[] args = super.getArguments();
//		if(args != null && args.length > 0){
//			nickname = new AID((String)args[0], AID.ISLOCALNAME);
//		}
		
		this.random = new Random();
		this.itemsToAuction = new ArrayList<AuctionItem>();
		
		int itemsToCreate = 5; //TODO ARGUMENT DESU!!
		for (int i = 0; i < itemsToCreate; i++){
			itemsToAuction.add(new AuctionItem("AuctionItem"+i, 1000+random.nextInt(99000)));
		}
		
		super.addBehaviour(new EnglishAuctioneerBehaviour(itemsToAuction));
	}
	
	protected void takeDown(){
		
	}
}
