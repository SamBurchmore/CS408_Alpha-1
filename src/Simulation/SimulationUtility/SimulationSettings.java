package Simulation.SimulationUtility;

import Simulation.Agent.AgentUtility.ActiveAgentsSettings;
import Simulation.Environment.EnvironmentSettings;

import java.io.Serializable;

public class SimulationSettings implements Serializable {

    private String presetName;

    private Boolean[] terrainMask;
    private ActiveAgentsSettings agentSettings;
    private EnvironmentSettings environmentSettings;

    public SimulationSettings(String presetName, ActiveAgentsSettings agentSettings, EnvironmentSettings environmentSettings, Boolean[] terrainMask) {
        this.presetName = presetName;
        this.agentSettings = agentSettings;
        this.environmentSettings = environmentSettings;
        this.terrainMask = terrainMask;
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

    public Boolean[] getTerrainMask() {
        return terrainMask;
    }

    public void setTerrainMask(Boolean[] terrainMask) {
        this.terrainMask = terrainMask;
    }

}
