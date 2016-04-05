package auctioneerBehaviours;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.Auctioneer;

public class AuctioneerSendBidProposal extends Behaviour 
{
	private Auctioneer parent;
    private String itemName;
    private int itemStartingPrice;

    public AuctioneerSendBidProposal(Auctioneer agent, String itemName, int itemStartingPrice) 
    {
        super(agent);
        parent = agent;
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
            
            cfp.setContent(this.itemName + "," + this.itemStartingPrice);
            cfp.setConversationId("SecondPrice-bid");
            cfp.setReplyWith("cfp"+System.currentTimeMillis());
            parent.send(cfp);

            // Prepare message template
            parent.mt = MessageTemplate.and(MessageTemplate.MatchConversationId("SecondPrice-bid"), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
        
            parent.bidProposalSent = true;
        }
	}
    
    public boolean done() 
    {
        return parent.bidProposalSent;
    }
}
