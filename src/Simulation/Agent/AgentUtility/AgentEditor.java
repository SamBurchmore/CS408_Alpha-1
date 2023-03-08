package Simulation.Agent.AgentUtility;

import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentConcreteComponents.*;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Environment.Location;

import java.awt.*;
import java.util.ArrayList;

public class AgentEditor {

    private final int AGENT_NUMBER = 8;
    final private ArrayList<Agent> activeAgents;
    private int editingAgentIndex;

    public AgentEditor() {
        activeAgents = new ArrayList<>();
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(new CreatorMotivation(20, 1));
        motivations.add(new GrazerMotivation(10, 1));
        ArrayList<Motivation> grazerMotivations = new ArrayList<>();
        grazerMotivations.add(new CreatorMotivation(20, 1));
        grazerMotivations.add(new GrazerMotivation(10, 1));
        ArrayList<Motivation> predMotivations = new ArrayList<>();
        predMotivations.add(new CreatorMotivation(20, 1));
        predMotivations.add(new PredatorMotivation(10, 1));
        ArrayList<Motivation> omniMotivations = new ArrayList<>();
        omniMotivations.add(new CreatorMotivation(20, 1));
        omniMotivations.add(new GrazerMotivation(10, 1));
        omniMotivations.add(new PredatorMotivation(10, 1));
        ArrayList<ArrayList<Motivation>> predOrPrey = new ArrayList<>();
        predOrPrey.add(omniMotivations);
        predOrPrey.add(grazerMotivations);
        Color[] agentColors = new Color[]{new Color(30, 30, 200), new Color(200, 30, 30), new Color(30, 200, 30), new Color(180, 0, 190), new Color(255, 102, 0), Color.cyan, Color.pink, Color.yellow};
        String[] agentNames = new String[]{"Blue", "Red", "Green", "Purple", "Orange", "Cyan", "Pink", "Yellow"};
        for (int i = 0; i < 8; i++) {
            activeAgents.add(i, new BasicAgent(
                    new Location(-1, -1),
                    new BasicAttributes(0, agentNames[i], i, agentColors[i], ColorModel.RANDOM, 5, 0,1, 3, 4),
                    (ArrayList<Motivation>) grazerMotivations.clone()));
        }
        editingAgentIndex = 0;
    }

    public void setEditingAgentIndex(int index) {
        editingAgentIndex = index;
    }

    public int getEditingAgentIndex() {
        return editingAgentIndex;
    }

    public AgentSettings getEditingAgentSettings() {
        return new AgentSettings(activeAgents.get(editingAgentIndex).getAttributes(), activeAgents.get(editingAgentIndex).getMotivations());
    }

    public AgentSettings getAgentSettings(int index) {
        return new AgentSettings(activeAgents.get(index).getAttributes(), activeAgents.get(index).getMotivations());
    }

    public void setAgentSettings(AgentSettings agentSettings, int index) {
        getAgent(index).setAttributes(agentSettings.getAttributes());
        getAgent(index).setMotivations(agentSettings.getMotivations());
    }

    public void setEditingAgentSettings(AgentSettings agentSettings) {
        getAgent(editingAgentIndex).setAttributes(agentSettings.getAttributes());
        getAgent(editingAgentIndex).setMotivations(agentSettings.getMotivations());
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents;
    }

    public Agent getAgent(int index) {
        return activeAgents.get(index);
    }

    public String[] getAgentNames() {
        String[] names = new String[8];
        for (int i = 0; i < AGENT_NUMBER; i++) {
            names[i] = activeAgents.get(i).getAttributes().getName();
        }
        return names;
    }

    public int getWeight() {
        int weight = 0;
        for (Agent agent : activeAgents) {
            weight += agent.getAttributes().getSpawningWeight();
        }
        return weight;
    }

    public ActiveAgentsSettings getActiveAgentsSettings() {
        ArrayList<AgentSettings> activeAgentSettings = new ArrayList<>();
        for (int i  = 0; i < AGENT_NUMBER; i++) {
            activeAgentSettings.add(i, getAgentSettings(i));
        }
        return new ActiveAgentsSettings(activeAgentSettings);
    }

    public void setActiveAgentsSettings(ActiveAgentsSettings activeAgentsSettings) {
        for (int i  = 0; i < AGENT_NUMBER; i++) {
            setAgentSettings(activeAgentsSettings.getSavedSettings(i), i);
        }
    }

}
