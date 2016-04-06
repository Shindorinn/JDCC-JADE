package bidderBehaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.BidderAgent;

public class BidderAgentWinner extends CyclicBehaviour 
{
	private BidderAgent parent;

    public BidderAgentWinner(BidderAgent agent) 
    {
        super(agent);
        parent = agent;
    }
	
	@Override
	public void action() 
	{
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
        ACLMessage msg = parent.receive(mt);
        
        // Check if message is empty, else process it
        if (msg != null) 
        {
        	// Split message
            String[] content = msg.getContent().split(",");
            int finalPrice = Integer.parseInt(content[1]);
            String itemName = content[2];
            
            System.out.println("-" + parent.getLocalName() + ": I won " + itemName);
            
            // decrease money
            parent.money -= finalPrice;
        }
        else 
        {
            block();
        }
		
	}

}
