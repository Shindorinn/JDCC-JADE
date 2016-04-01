package english_auction.auctioneer;

import java.util.List;

import base.AuctionItem;
import jade.core.behaviours.Behaviour;

public class EnglishAuctioneerBehaviour extends Behaviour {

	private List<AuctionItem> itemsToAuction;
	
	public EnglishAuctioneerBehaviour(List<AuctionItem> itemsToAuction) {
		this.itemsToAuction = itemsToAuction;
	}
	
	@Override
	public void onStart(){
		//TODO start the damn auction
		for(AuctionItem item : itemsToAuction){
			Behaviour toExecute = new EnglishAuctionItemBehaviour(item);
			super.myAgent.addBehaviour(new EnglishAuctionItemBehaviour(item));
			while(!toExecute.done())
				try {
					super.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
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
