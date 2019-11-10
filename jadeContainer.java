import java.util.ArrayList;
import java.util.List;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class jadeContainer {
	
	public static void main(String[] args) {
		try {
			// The number of agents in the environment and the number of interraction per agents
			int NB_AGENTS = 20;
			int NB_INTERRACTIONS = NB_AGENTS / 2;
			List<AgentController> agents = new ArrayList<AgentController>();
			List<String> names = new ArrayList<String>();
			String[] agentTypes = {"ag.kindAgent", "ag.honestAgent", "ag.selfishAgent", "ag.greedyAgent"};
			
			// Launch container
			Runtime rt = Runtime.instance();
			ProfileImpl pc = new ProfileImpl(false);
			pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer container = rt.createAgentContainer(pc);
			
			// Create agents names
			for (int i = 0; i < NB_AGENTS; i++) {
				names.add("AGENT" + String.valueOf(i));
			}
			
			// Create and start the coordinator
			AgentController coor = container.createNewAgent("COORDINATOR", "ag.coordinatorAgent", new Object[]{NB_INTERRACTIONS, names, 10});
			coor.start();
			
			// Create agents and then start them
			// Agents types are equally distributed
			for (int i = 0; i < NB_AGENTS; i++) {
				agents.add(container.createNewAgent(names.get(i), agentTypes[i % agentTypes.length], new Object[]{NB_INTERRACTIONS, names, 10, i}));
			}
			for (int i = 0; i < NB_AGENTS; i++) {
				agents.get(i).start();
			}
			
			
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
