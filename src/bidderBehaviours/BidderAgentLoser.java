package bidderBehaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.BidderAgent;

public class BidderAgentLoser extends CyclicBehaviour 
{
	private BidderAgent parent;

    public BidderAgentLoser(BidderAgent agent) 
    {
        super(agent);
        parent = agent;
    }
	
	@Override
	public void action() 
	{
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
        ACLMessage msg = parent.receive(mt);
        
        // Check if message is empty, else process it
        if (msg != null) 
        {
        	// Split message
            String[] content = msg.getContent().split(",");
            String itemName = content[1];
            
            System.out.println("-" + parent.getLocalName() + ": I lost " + itemName);
        	
        	// TODO if agent lose
        	
        }
        else 
        {
            block();
        }
		
	}

}
