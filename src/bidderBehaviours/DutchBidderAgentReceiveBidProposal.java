package bidderBehaviours;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.BidderAgent;

public class DutchBidderAgentReceiveBidProposal extends CyclicBehaviour 
{
	private BidderAgent parent;
	
	public DutchBidderAgentReceiveBidProposal(BidderAgent agent)
	{
		super(agent);
		parent = agent;
	}
	
	public void action()
	{
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		ACLMessage msg = parent.receive(mt);
		
		// Check if a message was received and process it
		if (msg != null) 
		{
			// split the message content
			String[] content = msg.getContent().split(",");
			String itemName = content[0];
			int itemStartingPrice = Integer.parseInt(content[1]);
			
			// create a reply
			ACLMessage reply = msg.createReply();
            
            // Check if there are enough money to participate in the auction
            if (parent.money > 0 && parent.money >= itemStartingPrice) 
            {
            	// calculate bid
            	int bid = itemStartingPrice;
                
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(bid + "");
                System.out.println(parent.getLocalName() + " sent a new bid: " + bid);
            }
            else 
            {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("drop auction");
                System.out.println(parent.getAID().getLocalName() + " is not bidding (Not enough money)");
            }

            // send the reply
            System.out.println("DutchBidderAgentReceiveBidProposal : " + reply);
            parent.send(reply);
		}
		else 
		{
            block();
        }
	}
}
