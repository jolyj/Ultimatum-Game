package ag;

import java.util.List;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class coordinatorAgent extends Agent {
	// all agent classes
	String[] agentClasses = {"kindAgent", "honestAgent", "selfishAgent", "greedyAgent"};
	// array in which we store the results
	int[][]	 results;
	// number of agents that have completed their work
	int agentsDone;
	// number of reports send by agents
	int nb_reports;
	
	@Override
	protected void setup() {
		nb_reports = 0;
		agentsDone = 0;
		results = new int [agentClasses.length][3];
		Object[] args = getArguments();
		
		for (int a = 0; a < agentClasses.length; a++) {
			for (int b = 0; b < 3; b++) {
				results[a][b] = 0;
			}
		}

		List<String> agents = (List<String>)args[1];
		
		addBehaviour(new CyclicBehaviour(this) 
        {
             public void action() 
             {
                ACLMessage msg= receive();
                if (msg!=null) {
                	String[] info = msg.getContent().split(" "); 
                	if (info[0].equals("Done")) {
                		// count the number of agents that have finish their work
                    	agentsDone += 1;
                	}
                	else {
                		// Store the report of each agent in the result array
                		// info[0] is the agent class
                		// info[1] is the total of points of the agent
                		// info[2] is the number of accepted transaction
                		// info[3] is the number of refused transaction
                    	int i = 0;
                    	while (!agentClasses[i].equals(info[0])) {
                    		i++;
                    	}
                    	results[i][0] += Integer.parseInt(info[1]);
                    	results[i][1] += Integer.parseInt(info[2]);
                    	results[i][2] += Integer.parseInt(info[3]);
                    	nb_reports += 1;
                    	if (nb_reports == agents.size()) {
                    		// When all reports have been received we delete the coordinator and print the result
                    		doDelete();
                    	}
                	}
                }
                else  if (agentsDone == agents.size()){
                	// When all agents are done with their task the coordinator delete them
                	for (int i = 0; i < agents.size(); i++) {
                      	ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    	message.addReceiver(new AID("AGENT" + String.valueOf(i), AID.ISLOCALNAME));
                    	message.setContent("delete");
                    	send(message);
                	}
                }
             }
        });
	}
	
	@Override
	protected void takeDown() {
		// Print result before deleting the coordinator
		System.out.println("-------------------------------------------SUM UP-------------------------------------------\n");
		System.out.println("------------\tTotal Points\tAccepted Transactions\tRefused Transactions\n");
		System.out.println("kindAgent:\t" + String.valueOf(results[0][0]) + "\t\t" + String.valueOf(results[0][1]) + "\t\t\t" + String.valueOf(results[0][2]) );
		System.out.println("honestAgent:\t" + String.valueOf(results[1][0]) + "\t\t" + String.valueOf(results[1][1]) + "\t\t\t" + String.valueOf(results[1][2]) );
		System.out.println("selfishAgent:\t" + String.valueOf(results[2][0]) + "\t\t" + String.valueOf(results[2][1]) + "\t\t\t" + String.valueOf(results[2][2]) );
		System.out.println("greedyAgent:\t" + String.valueOf(results[3][0]) + "\t\t" + String.valueOf(results[3][1]) + "\t\t\t" + String.valueOf(results[3][2]) + "\n" );
		System.out.println("TOTAL:\t\t" + String.valueOf(results[0][0] + results[1][0] + results[2][0] + results[3][0])
				+ "\t\t" + String.valueOf(results[0][1] + results[1][1] + results[2][1] + results[3][1])
				+ "\t\t\t" + String.valueOf(results[0][2] + results[1][2] + results[2][2] + results[3][2]));
				
	}

}
