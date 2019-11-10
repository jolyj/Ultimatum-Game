import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.ControllerException;
import jade.wrapper.AgentContainer;

public class MainContainer {

	public static void main(String[] args) {
		// We start the main container with this function
		try {
			Runtime rt = Runtime.instance();
			Properties p = new ExtendedProperties();
			p.setProperty("gui", "true");
			ProfileImpl pc = new ProfileImpl(p);
			AgentContainer cntr = rt.createMainContainer(pc);
			cntr.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
