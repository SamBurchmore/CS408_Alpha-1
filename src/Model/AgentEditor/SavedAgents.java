package Model.AgentEditor;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedAgents implements Serializable {

    ArrayList<AgentSettings> savedAgentSettings;

    public SavedAgents(ArrayList<AgentSettings> savedAgentSettings) {
        this.savedAgentSettings = savedAgentSettings;
    }

    public AgentSettings getSavedSettings(int index) {
        return savedAgentSettings.get(index);
    }

}
