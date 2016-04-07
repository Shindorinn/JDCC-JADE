package auctioneerBehaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.Auctioneer;

public class AuctioneerSendDutchBidProposal extends Behaviour {

	private Auctioneer parent;
    private String itemName;
    private int currentItemPrice;
    
    public AuctioneerSendDutchBidProposal(Auctioneer agent, String itemName, int currentItemPrice) 
    {
        super(agent);
        parent = agent;
        this.itemName = itemName;
        this.currentItemPrice = currentItemPrice;
    }
    
	@Override
	public void action() {		
		if (!parent.bidProposalSent && parent.foundBidders) {
			System.out.println("Auction for: " + itemName + " with current price: " + currentItemPrice);
			
	        // Send the cfp to all bidders
	        System.out.println("Sending CFP to all bidders..");
	        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
	        for (int i = 0; i < parent.bidders.length; ++i) 
	        {
	            cfp.addReceiver(parent.bidders[i]);
	        } 
	        
	        cfp.setContent(this.itemName + "," + this.currentItemPrice);
	        cfp.setConversationId("Dutch-bid");
	        cfp.setReplyWith("cfp"+System.currentTimeMillis());
	        parent.send(cfp);
	
	        // Prepare message template
	        parent.mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Dutch-bid"), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
	    
	        parent.bidProposalSent = true;
	        

            System.out.println("DutchAuctioneerSendBidProposal : " + cfp.toString());
	    }
	}

	@Override
    public boolean done() 
    {
        return parent.bidProposalSent;
    }

}
