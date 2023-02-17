package Simulation.Agent.AgentUtility;

import java.io.Serializable;
import java.util.ArrayList;

public class ActiveAgentsSettings implements Serializable {

    ArrayList<AgentSettings> savedAgentSettings;

    public ActiveAgentsSettings(ArrayList<AgentSettings> savedAgentSettings) {
        this.savedAgentSettings = savedAgentSettings;
    }

    public AgentSettings getSavedSettings(int index) {
        return savedAgentSettings.get(index);
    }

}
