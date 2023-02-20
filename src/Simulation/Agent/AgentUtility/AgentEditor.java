package Simulation.Agent.AgentUtility;

import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentConcreteComponents.*;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Environment.Location;

import java.awt.*;
import java.util.ArrayList;

public class AgentEditor {

    private final int AGENT_NUMBER = 8;

    final private ActiveAgents activeAgents;

    private int editingAgentIndex;

    public AgentEditor() {
        activeAgents = new ActiveAgents();
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
        //        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Blue", 0,    Color.blue, true, 10,                                               1, 2, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 0);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0.25, "Red", 1,    Color.red, true, 10,                                                 1, 5, 1), (ArrayList<Motivation>) predMotivations.clone()), 1);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0.35, "Green", 2,    Color.green, true, 10,                                             1, 4, 2), (ArrayList<Motivation>) omniMotivations.clone()), 2);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Purple", 3,    new Color(180, 0, 190), false, 0,                        1, 3, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 3);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Orange", 4,    new Color(255, 102, 0), false, 0,                        1, 3, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 4);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Yellow", 5,    Color.yellow, false, 0,                                           1, 3, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 5);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Pink", 6,    Color.pink, false, 0,                                               1, 3, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 6);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Cyan", 7,    Color.cyan, false, 0,                                               1, 3, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 7);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Blue", 0,    Color.blue,                        1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 0);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Red", 1, Color.red,                             1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 1);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Green", 2, Color.green,                         1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 2);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Purple", 3,    new Color(180, 0, 190), 1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 3);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Orange", 4, new Color(255, 102, 0),    1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 4);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Yellow", 5, Color.yellow,                       1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 5);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Pink", 6, Color.pink,                           1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 6);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Cyan", 7, Color.cyan,                           1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 7);
        for (int i = 0; i < 8; i++) {
            activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, agentNames[i], i, agentColors[i], 0,1, 3, 4), (ArrayList<Motivation>) omniMotivations.clone()), i);
        }
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Agent 1", 0, agentColors[0], 1,1, 3, 8), (ArrayList<Motivation>) grazerMotivations.clone()), 0);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Agent 2", 1, agentColors[1], 1,1, 4, 4), (ArrayList<Motivation>) predMotivations.clone()), 1);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Agent 3", 2, agentColors[2], 1,1, 10, 4), (ArrayList<Motivation>) omniMotivations.clone()), 2);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Agent 4", 3, agentColors[3], 1,1, 3, 4), (ArrayList<Motivation>) grazerMotivations.clone()), 3);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Agent 5", 4, agentColors[4], 1,1, 3, 4), (ArrayList<Motivation>) grazerMotivations.clone()), 4);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Agent 6", 5, agentColors[5], 1,1, 3, 4), (ArrayList<Motivation>) grazerMotivations.clone()), 5);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Agent 7", 6, agentColors[6], 1,1, 3, 4), (ArrayList<Motivation>) grazerMotivations.clone()), 6);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Agent 8", 7, agentColors[7], 1,1, 3, 4), (ArrayList<Motivation>) grazerMotivations.clone()), 7);
        editingAgentIndex = 0;
    }

    public void setEditingAgentIndex(int index) {
        editingAgentIndex = index;
    }

    public Agent getEditingAgent() {
        return activeAgents.getAgent(editingAgentIndex);
    }

    public int getEditingAgentIndex() {
        return editingAgentIndex;
    }

    public AgentSettings getEditingAgentSettings() {
        return new AgentSettings(activeAgents.getAgent(editingAgentIndex).getAttributes(), activeAgents.getAgent(editingAgentIndex).getMotivations());
    }

    public AgentSettings getAgentSettings(int index) {
        return new AgentSettings(activeAgents.getAgent(index).getAttributes(), activeAgents.getAgent(index).getMotivations());
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
        return activeAgents.getActiveAgents();
    }

    public Agent getAgent(int index) {
        return activeAgents.getAgent(index);
    }

    public String[] getAgentNames() {
        String[] names = new String[8];
        for (int i = 0; i < AGENT_NUMBER; i++) {
            names[i] = activeAgents.getAgent(i).getAttributes().getName();
        }
        return names;
    }

    public int getWeight() {
        int weight = 0;
        for (Agent agent : activeAgents.getActiveAgents()) {
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
