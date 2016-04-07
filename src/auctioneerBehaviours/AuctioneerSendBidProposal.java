package auctioneerBehaviours;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.Auctioneer;
import main.Database;

public class AuctioneerSendBidProposal extends Behaviour 
{
	private Auctioneer parent;
	private int itemIndex;
    private String itemName;
    private int itemStartingPrice;

    public AuctioneerSendBidProposal(Auctioneer agent, int itemIndex, String itemName, int itemStartingPrice) 
    {
        super(agent);
        parent = agent;
        this.itemIndex = itemIndex;
        this.itemName = itemName;
        this.itemStartingPrice = itemStartingPrice;
    }
    
	public void action()
	{
		if (!parent.bidProposalSent && parent.foundBidders) 
        {
			System.out.println("Started auction for: " + itemName + " with starting price: " + itemStartingPrice);
			
            // Send the cfp to all bidders
            System.out.println("Sending CFP to all bidders..");
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            for (int i = 0; i < parent.bidders.length; ++i) 
            {
                cfp.addReceiver(parent.bidders[i]);
            } 
            
            // construct message based on auction type
            if(parent.db.getAuctionType() == Database.AuctionDatabase.ENGLISH_AUCTION)
    		{
	            cfp.setContent(this.itemIndex + "," + this.itemName + "," + this.itemStartingPrice);
	            cfp.setConversationId("English-bid");
	            cfp.setReplyWith("cfp"+System.currentTimeMillis());
	            parent.send(cfp);
	
	            // Prepare message template
	            parent.mt = MessageTemplate.and(MessageTemplate.MatchConversationId("English-bid"), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
    		}
            else if(parent.db.getAuctionType() == Database.AuctionDatabase.DUTCH_AUCTION)
            {
            	cfp.setContent(this.itemIndex + "," + this.itemName + "," + this.itemStartingPrice);
	            cfp.setConversationId("Dutch-bid");
	            cfp.setReplyWith("cfp"+System.currentTimeMillis());
	            parent.send(cfp);
	
	            // Prepare message template
	            parent.mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Dutch-bid"), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
            }
            else if(parent.db.getAuctionType() == Database.AuctionDatabase.SECOND_PRICE_AUCTION)
            {
            	cfp.setContent(this.itemIndex + "," + this.itemName + "," + this.itemStartingPrice);
	            cfp.setConversationId("SecondPrice-bid");
	            cfp.setReplyWith("cfp"+System.currentTimeMillis());
	            parent.send(cfp);
	
	            // Prepare message template
	            parent.mt = MessageTemplate.and(MessageTemplate.MatchConversationId("SecondPrice-bid"), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
            }
            
            System.out.println("AuctioneerSendBidProposal : " + cfp.toString());
            parent.bidProposalSent = true;
        }
	}
    
    public boolean done() 
    {
        return parent.bidProposalSent;
    }
}
