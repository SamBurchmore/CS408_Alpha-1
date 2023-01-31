package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

import Model.AgentEditor.AgentEditor;
import Model.Agents.AgentConcreteComponents.BasicAgent;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivation;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;

/**
 * This class will be the main controller for the simulation. It will handle running the agents, moving them, removing, and adding them to the grid when needed.
 */
public class ModelController {

    // The data structure which represents the world.
    private Environment environment;

    private AgentEditor agentEditor;

    private ArrayList<Agent> agentList; // Here we store all the agents currently in the simulation

    private ArrayList<Agent> aliveAgentList; // When cycle() is called, all newly born and surviving agents are placed in here. At the end agentList is set to this

    // This is where all diagnostic data on the simulation is stored.
    private Diagnostics diagnostics;

    private final int environmentSize;

    private Random randomGen;

    public ModelController(int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount){
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
        this.randomGen = new Random();
        this.agentList = new ArrayList<>();
        this.aliveAgentList = new ArrayList<>();
        this.environmentSize = size;
        this.diagnostics = new Diagnostics();
        this.agentEditor = new AgentEditor();
    }

    public void populate(double density) {
        ArrayList<Agent> activeAgents = agentEditor.getActiveAgents();
        IntStream.range(0, environmentSize * environmentSize).sequential().forEach(i->{
                if (this.randomGen.nextInt(10000) / 100.0 < density && !environment.getGrid()[i].isOccupied()) {
                    int agentIndex = randomGen.nextInt(activeAgents.size());
                    BasicAgent agent;
                    for (int j = 0; j < activeAgents.size(); j++) {
                        if (j == agentIndex) {
                            agent = (BasicAgent) agentEditor.getAgent(j).copy();
                            EnvironmentTile wt = environment.getGrid()[i];
                            agent.setLocation(wt.getLocation());
                            wt.setOccupant(agent);
                            agentList.add(agent);
                            diagnostics.addToStats(j, 1, agent.getScores().getHunger(), agent.getScores().getAge());
                        }
                    }
                }
        });
    }

    public void cycle() {
        diagnostics.clearStats();
        for (Agent currentAgent : agentList) {
            runAgent(currentAgent); // Iterate and run over all agents in the simulation
        }
        agentList = aliveAgentList;
        aliveAgentList = new ArrayList<>();
        IntStream.range(0, environmentSize * environmentSize).parallel().forEach(i->{
            if (randomGen.nextInt(10000) / 100.0 < environment.getEnergyRegenChance()) {
                environment.modifyTileFoodLevel(environment.getGrid()[i].getLocation(), environment.getEnergyRegenAmount());
            }
        });
    }

    public void clear() {
        IntStream.range(0, environmentSize * environmentSize).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<>();
    }

    public void replenishEnvironmentEnergy() {
        IntStream.range(0, environmentSize * environmentSize).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setFoodLevel(environment.getMaxEnergyLevel());
        });
    }

    public void updateEnvironmentSettings(int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount) {
        environment.setMinEnergyLevel(minEnergyLevel);
        environment.setMaxEnergyLevel(maxEnergyLevel);
        environment.setEnergyRegenChance(energyRegenChance);
        environment.setEnergyRegenAmount(energyRegenAmount);

    }

    public Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    public BufferedImage getEnvironmentImage(int scale) {
        return this.environment.toBufferedImage(scale);
    }

    public AgentEditor getAgentEditor() {
        return agentEditor;
    }

    public void updateAgentNames() {
        diagnostics.setAgentNames(agentEditor.getAgentNames());
    }

    public void setAgentEditor(AgentEditor agentEditor) {
        this.agentEditor = agentEditor;
    }

    public void setEnvironmentColors(Color[] color) {
        environment.setMinColor(color[0]);
        environment.setLowColor(color[1]);
        environment.setMediumLowColor(color[2]);
        environment.setMediumHighColor(color[3]);
        environment.setHighColor(color[4]);
        environment.setMaxColor(color[5]);
    }

    public Color[] getEnvironmentColors() {
        Color[] colors = new Color[6];
        colors[0] = environment.getMinColor();
        colors[1] = environment.getLowColor();
        colors[2] = environment.getMediumLowColor();
        colors[3] = environment.getMediumHighColor();
        colors[4] = environment.getHighColor();
        colors[5] = environment.getMaxColor();
        return colors;
    }

    public int getEnergyRegenAmount() {
        return environment.getEnergyRegenAmount();
    }

    public double getEnergyRegenChance() {
        return environment.getEnergyRegenChance();
    }

    public int getMaxEnergy() {
        return environment.getMaxEnergyLevel();
    }

    public int getMinEnergy() {
        return environment.getMinEnergyLevel();
    }

    public void setMaxEnergy(int maxEnergy) {
        environment.setMaxEnergyLevel(maxEnergy);
    }

    public void setMinEnergy(int minEnergy) {
        environment.setMinEnergyLevel(minEnergy);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getEnvironmentSize() {
        return environmentSize;
    }

    public void runAgent(Agent agent) {
        if (agent.isEaten()) {
          return; // Agent has been eaten by another agent, therefor its already been removed from the environment, all we need to do is not add it to the aliveAgentList
        }
        agent.liveDay(); // Live a day, i.e. reduce hunger, increase age, reduce creation cooldown.
        if (agent.isDead()) {
            environment.setTileAgent(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
            return;
        }
        ArrayList<AgentVision> agentView = lookAround(agent);
        AgentDecision agentDecision = reactToView(agent, agentView);
        if (agentDecision.getAgentAction().equals(AgentAction.NONE)) { // Do nothing
            aliveAgentList.add(agent); // Agent is still alive
        }
        else if (agentDecision.getAgentAction().equals(AgentAction.MOVE)) {
            environment.setTileAgent(agent.getLocation(), null);
            agent.move(agentDecision.getLocation()); // Move to the chosen location
            environment.setTileAgent(agent);
            aliveAgentList.add(agent); // Agent is still alive
        }
        else if (agentDecision.getAgentAction().equals(AgentAction.CREATE)) { // Create children
            ArrayList<Agent> childAgents = agent.create(agentDecision.getLocation(), environment);
            for (Agent child : childAgents) {
                environment.setTileAgent(child);
            }
            aliveAgentList.addAll(childAgents); // Create new agents with found mate,
            aliveAgentList.add(agent); // Agent is still alive
        }
        else if (agentDecision.getAgentAction().equals(AgentAction.GRAZE)) {
            environment.setTileAgent(agent.getLocation(), null);
            agent.move(agentDecision.getLocation()); // Move to chosen location
            environment.setTileAgent(agent);
            environment.modifyTileFoodLevel(agent.getLocation(), -agent.graze(environment.getTile(agent.getLocation()))); // Consume energy at chosen location
            aliveAgentList.add(agent); // Agent is still alive
        }
        else if (agentDecision.getAgentAction().equals(AgentAction.PREDATE)) {
            environment.getTile(agentDecision.getLocation()).getOccupant().setBeenEaten(); // We set the preys hasBeenEaten flag to true
            agent.predate(environment.getTile(agentDecision.getLocation()).getOccupant().getAttributes()); // Predator gains energy from the prey
            environment.setTileAgent(agent.getLocation(), null);
            agent.move(agentDecision.getLocation()); // Predator now occupies preys location
            environment.setTileAgent(agent);
            aliveAgentList.add(agent); // Agent is still alive
        }
    }

    public ArrayList<AgentVision> lookAround(Agent agent) {
        Location agentLocation = agent.getLocation();
        int visionRange = agent.getAttributes().getVisionRange();
        int agentRange = agent.getAttributes().getMovementRange();
        // Generate a new ArrayList of the AgentVision object, everything the agent sees will be stored here.
        ArrayList<AgentVision> agentViews = new ArrayList<>();
        // Retrieve the agents vision attribute, lets us know how far the agent can see.
        // Now we iterate over all the surrounding tiles, adding their visible contents to the AgentVision ArrayList.
        for (int i = -visionRange; i <= visionRange; i++) {
            for (int j = -visionRange; j <= visionRange; j++) {
                int x_coord = agentLocation.getX() + i;
                int y_coord = agentLocation.getY() + j;
                // Checks the agent isn't looking outside the grid and prevents a null pointer exception // TODO - there must be a better way to do this.
                if (((x_coord < environment.getSize()) && (y_coord < environment.getSize())) && ((x_coord >= 0) && (y_coord >= 0)) && !(i == 0 && j == 0)) {
                    AgentVision av = environment.getTileView(x_coord, y_coord);
                    if (Math.abs(i) <= agentRange && Math.abs(j) <= agentRange) {
                        av.setInRange(true);
                    }
                    else {
                        av.setInRange(false);
                    }
                    agentViews.add(av);
                }
            }
        }
        Collections.shuffle(agentViews);
        return agentViews;
    }

    public AgentDecision reactToView(Agent agent, ArrayList<AgentVision> agentView) {
        ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
        for (AgentVision currentAV : agentView) {
            if (currentAV.isInRange()) {
                possibleDecisions.add(reactToTile(agent, currentAV));
            }
        }
        return getBestDecision(possibleDecisions);
    }

    public AgentDecision reactToTile(Agent agent, AgentVision agentVision) {
        ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
        for (Motivation motivation : agent.getMotivations()) {
            possibleDecisions.add(motivation.run(agentVision, agent.getAttributes(), agent.getScores()));
        }
        return getBestDecision(possibleDecisions);
    }

    // A utility function which takes a collection of agent decisions, and returns the one with the highest decision score.
    private static AgentDecision getBestDecision(ArrayList<AgentDecision> agentDecisions) {
        AgentDecision finalDecision = new AgentDecision(null, AgentAction.NONE, 0);
        for (AgentDecision agentDecision : agentDecisions) {
            if (agentDecision.getDecisionScore() > finalDecision.getDecisionScore()) {
                finalDecision = agentDecision;
            }
        }
        return finalDecision;
    }

}
