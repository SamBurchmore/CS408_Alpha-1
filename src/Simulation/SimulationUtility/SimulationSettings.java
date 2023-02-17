package Simulation.SimulationUtility;

import Simulation.Agent.AgentUtility.ActiveAgentsSettings;
import Simulation.Environment.EnvironmentSettings;

import java.io.Serializable;

public class SimulationSettings implements Serializable {

    private String presetName;

    private ActiveAgentsSettings agentSettings;
    private EnvironmentSettings environmentSettings;

    public SimulationSettings(String presetName, ActiveAgentsSettings agentSettings, EnvironmentSettings environmentSettings) {
        this.presetName = presetName;
        this.agentSettings = agentSettings;
        this.environmentSettings = environmentSettings;
    }

    public String getPresetName() {
        return presetName;
    }

    public ActiveAgentsSettings getAgentSettings() {
        return agentSettings;
    }

    public EnvironmentSettings getEnvironmentSettings() {
        return environmentSettings;
    }

}
