package ag;

import java.util.List;
import java.util.Random;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class kindAgent extends Agent{
	private int refusedPropositions;
	private int acceptedPropositions;
	private int totalPoints;
	private int nb_propositions;
	private AID receiver;
	
	@Override
	protected void setup() {
		
		// Variables initialization
		refusedPropositions = 0;
		acceptedPropositions = 0;
		totalPoints = 0;
		nb_propositions = 0;
		receiver = null;

		// Get agent arguments : the number of maximum interractions, a list of all agents name, 
		//	the number of point per proposition, and agent's id 
		Object[] args = getArguments();
		int max_interractions = (int)args[0];
		@SuppressWarnings("unchecked")
		List<String> agents = (List<String>)args[1];
		int pointsPerProposition = (int)args[2];
		int id = (int)args[3];
		
		// Random variable to send message to agents randomly
		Random rand = new Random();
		
		// Behaviour definition
        addBehaviour(new CyclicBehaviour(this) 
        {
             public void action() 
             {
            	// Check message queue and read message
                ACLMessage msg= receive();
                if (msg!=null) {
                	if (receiver != null && msg.getSender().getName().equals(receiver.getName()) && (msg.getContent().equals("false") || msg.getContent().equals("true"))) {
                		// When the sender is answering our request, we have to verify if it accepted the proposition
                		totalPoints += (msg.getContent().equals("true") ? pointsPerProposition / 2 : 0);
                		refusedPropositions += (msg.getContent().equals("false") ? 1 : 0);
                		acceptedPropositions += (msg.getContent().equals("true") ? 1 : 0);
                		receiver = null;
                	}
                	else if (msg.getContent().equals("delete")) {
                		// Delete when the coordinator requests it
                		doDelete();
                	}
                	else {
                		// A random message appears : it's a new proposition
                		// The kind agent is accepting every proposition so it always answers true
                		int proposition = Integer.parseInt(msg.getContent());
                		totalPoints += proposition;
                		acceptedPropositions += 1;
                	    ACLMessage reply = new ACLMessage( ACLMessage.INFORM );
                	    reply.setContent("true");
                	    reply.addReceiver(msg.getSender());
                	    send(reply);
                	}
                }
                else {
                	if (nb_propositions == max_interractions && receiver == null) {
                		// When the agent has send max_interractions request, it has to stop and specify it to the coordinator
                		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                		message.addReceiver(new AID("COORDINATOR", AID.ISLOCALNAME));
                    	message.setContent("Done");
                    	send(message);
                    	nb_propositions += 1;
                    	
                	}
                	else if (nb_propositions < max_interractions && receiver == null) {
                		// When the agent has nothing to do, it initiates a new proposition to a random agent
                		// The kind agent always split the points it has to share
                    	ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    	
                    	// To avoid the agent to send message to itself
                    	int id_receiver = rand.nextInt(agents.size());
                    	while (id_receiver == id) {
                    		id_receiver = rand.nextInt(agents.size());
                    	}
                    	
                    	receiver = new AID("AGENT" + String.valueOf(id_receiver), AID.ISLOCALNAME);
                    	message.addReceiver(receiver);
                    	message.setContent(String.valueOf(pointsPerProposition / 2));
                    	send(message);
                    	nb_propositions += 1;
                	}

                }
             }
        });
	}
	
	@Override
	protected void takeDown() {
		// Before deleting it then sends its type, total points, accepted transaction and refused transactions to the coordinator
    	ACLMessage message = new ACLMessage(ACLMessage.INFORM);
    	message.addReceiver(new AID("COORDINATOR", AID.ISLOCALNAME));
    	message.setContent("kindAgent" + " " + String.valueOf(totalPoints) + " " + String.valueOf(acceptedPropositions) + " " + String.valueOf(refusedPropositions));
    	send(message);
	}
}



