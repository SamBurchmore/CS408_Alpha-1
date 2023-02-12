package Simulation.AgentUtility;

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
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Blue", 0,    Color.blue, true, 10,                                               1, 2, 1, 8, 0), (ArrayList<Motivation>) grazerMotivations.clone()), 0);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0.25, "Red", 1,    Color.red, true, 10,                                                 1, 5, 10, 1, 0), (ArrayList<Motivation>) predMotivations.clone()), 1);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0.35, "Green", 2,    Color.green, true, 10,                                             1, 4, 14, 2, 0), (ArrayList<Motivation>) omniMotivations.clone()), 2);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Purple", 3,    new Color(180, 0, 190), false, 0,                        1, 3, 8, 8, 0), (ArrayList<Motivation>) grazerMotivations.clone()), 3);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Orange", 4,    new Color(255, 102, 0), false, 0,                        1, 3, 8, 8, 0), (ArrayList<Motivation>) grazerMotivations.clone()), 4);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Yellow", 5,    Color.yellow, false, 0,                                           1, 3, 8, 8, 0), (ArrayList<Motivation>) grazerMotivations.clone()), 5);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Pink", 6,    Color.pink, false, 0,                                               1, 3, 8, 8, 0), (ArrayList<Motivation>) grazerMotivations.clone()), 6);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(0, "Cyan", 7,    Color.cyan, false, 0,                                               1, 3, 8, 8, 0), (ArrayList<Motivation>) grazerMotivations.clone()), 7);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Blue", 0,    Color.blue,                        1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 0);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Red", 1, Color.red,                             1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 1);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Green", 2, Color.green,                         1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 2);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Purple", 3,    new Color(180, 0, 190), 1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 3);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Orange", 4, new Color(255, 102, 0),    1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 4);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Yellow", 5, Color.yellow,                       1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 5);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Pink", 6, Color.pink,                           1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 6);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1),                      new BasicAttributes(1, "Cyan", 7, Color.cyan,                           1, 1, 10, 2, 48, 8, 3, 9), (ArrayList<Motivation>) grazerMotivations.clone()), 7);
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

    public ArrayList<AgentSettings> getActiveAgentsSettings() {
        ArrayList<AgentSettings> activeAgentSettings = new ArrayList<>();
        for (int i  = 0; i < AGENT_NUMBER; i++) {
            activeAgentSettings.add(i, getAgentSettings(i));
        }
        return activeAgentSettings;
    }

    public void setActiveAgentsSettings(SavedAgents savedAgents) {
        for (int i  = 0; i < AGENT_NUMBER; i++) {
            setAgentSettings(savedAgents.getSavedSettings(i), i);
        }
    }

}
