package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.AgentUtility.AgentEditor;
import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;
import Simulation.Diagnostics.Diagnostics;
import Simulation.Environment.Environment;
import Simulation.Environment.EnvironmentSettings;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;

/**
 * This class will be the main controller for the simulation. It will handle running the agents, moving them, removing, and adding them to the grid when needed.
 */
public class Simulation {

    private Environment environment; // The instance of the environment class

    private ArrayList<Agent> agentList; // Here we store all the agents currently in the simulation

    private ArrayList<Agent> aliveAgentList; // When cycle() is called, all newly born and surviving agents are placed in here. At the end agentList is set to this

    private AgentEditor agentEditor; // The instance of the agent editor class

    private Diagnostics diagnostics; // The instance of the diagnostics class

    private final AgentLogic agentLogic;

    private Random random;

    public Simulation(int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount){
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
        this.random = new Random();
        this.agentList = new ArrayList<>();
        this.aliveAgentList = new ArrayList<>();
        this.diagnostics = new Diagnostics();
        this.agentEditor = new AgentEditor();
        this.agentLogic = new AgentLogic();
    }

    public void setEnvironment(int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount) {
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void populate(double density) {
        ArrayList<Agent> activeAgents = agentEditor.getActiveAgents();
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            if (this.random.nextInt(10000) / 100.0 < density && !environment.getGrid()[i].isOccupied()) {
                int agentIndex = random.nextInt(activeAgents.size());
                BasicAgent agent;
                for (int j = 0; j < activeAgents.size(); j++) {
                    if (j == agentIndex && agentEditor.getAgent(j).getAttributes().getSpawningWeight() * 100 > random.nextInt(100)) {
                        agent = (BasicAgent) agentEditor.getAgent(j).copy();
                        EnvironmentTile wt = environment.getGrid()[i];
                        agent.setLocation(wt.getLocation());
                        wt.setOccupant(agent);
                        agentList.add(agent);
                        diagnostics.addToAgentStats(j, 1, agent.getScores().getEnergy(), agent.getScores().getAge());
                    }
                }
            }
        });
    }

    public void cycle() {
        diagnostics.clearAgentStats();
        for (Agent currentAgent : agentList) {
            if (!currentAgent.isEaten()) {
                agentLogic.runAgent(currentAgent); // Iterate and run over all agents in the simulation
                if (!currentAgent.isDead()) {
                    diagnostics.addToAgentStats(currentAgent.getAttributes().getCode(), 1, currentAgent.getScores().getEnergy(), currentAgent.getScores().getAge());
                }
            }
        }
        agentList = aliveAgentList;
        aliveAgentList = new ArrayList<>();
        IntStream.range(0, environment.getSize() * environment.getSize()).parallel().forEach(i->{
            if (random.nextInt(10000) / 100.0 < environment.getEnergyRegenChance()) {
                int modifyAmount = environment.modifyTileFoodLevel(environment.getGrid()[i].getLocation(), environment.getEnergyRegenAmount());
                diagnostics.modifyCurrentEnvironmentEnergy(modifyAmount);
            }
        });
    }

    public void clearAgents() {
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<>();
    }

    public void replenishEnvironmentEnergy() {
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setFoodLevel(environment.getMaxEnergyLevel());
        });
        diagnostics.resetCurrentEnvironmentEnergy();
    }

    public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
        if (environmentSettings.getSize() != environment.getSize()) {
            clearAgents();
            environment.setEnvironmentSettings(environmentSettings);
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * getEnvironmentSize()*getEnvironmentSize());
            diagnostics.resetCurrentEnvironmentEnergy();
        }
        else if (environmentSettings.getMaxEnergyLevel() < environment.getMaxEnergyLevel()) {
            environment.setEnvironmentSettings(environmentSettings);
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * getEnvironmentSize()*getEnvironmentSize());
            diagnostics.resetCurrentEnvironmentEnergy();
        }
            environment.setEnvironmentSettings(environmentSettings);
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * getEnvironmentSize() * getEnvironmentSize());
        }


    public EnvironmentSettings getEnvironmentSettings() {
        return new EnvironmentSettings(environment.getSize(),
                                       environment.getMaxEnergyLevel(),
                                       environment.getMinEnergyLevel(),
                                       environment.getEnergyRegenChance(),
                                       environment.getEnergyRegenAmount(),
                                       environment.getColors());
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

    public int getMaxTileEnergy() {
        return environment.getMaxEnergyLevel();
    }

    public int getMinTileEnergy() {
        return environment.getMinEnergyLevel();
    }

    public void setMaxTileEnergy(int maxEnergy) {
        environment.setMaxEnergyLevel(maxEnergy);
    }

    public void setMinTileEnergy(int minEnergy) {
        environment.setMinEnergyLevel(minEnergy);
    }

    public int getEnvironmentSize() {
        return environment.getSize();
    }


    private class AgentLogic {

        public void runAgent(Agent agent) {
            if (agent.isEaten()) {
                return; // Agent has been eaten by another agent, therefor its already been removed from the environment, all we need to do is not add it to the aliveAgentList
            }
            agent.liveDay(); // Live a day, i.e. increase age, reduce creation cooldown.
            if (agent.isDead()) {
                environment.setTileAgent(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
                return;
            }
            ArrayList<AgentVision> agentView = lookAround(agent);
            AgentDecision agentDecision = reactToView(agent, agentView);
            if (agentDecision.agentAction().equals(AgentAction.NONE)) { // Do nothing
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.MOVE)) {
                environment.setTileAgent(agent.getLocation(), null);
                agent.move(agentDecision.location()); // Move to the chosen location
                environment.setTileAgent(agent);
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.CREATE)) { // Create children
                ArrayList<Agent> childAgents = agent.create(agentDecision.location(), (agent.getAttributes().getEnergyCapacity() / agent.getAttributes().getCreationAmount()) / 2, environment);
                if (agent.getAttributes().getMutates()) { // Check if the parent agent mutates, if so then mutate all its children before adding them to the board
                    for (Agent child : childAgents) {
                        child.setAttributes(mutate(child.getAttributes()));
                        environment.setTileAgent(child);
                    }
                }
                else {
                    for (Agent child : childAgents) { // Otherwise just add the agents straight to the board
                        environment.setTileAgent(child);
                    }
                }
                aliveAgentList.addAll(childAgents); // Create new agents with found mate,
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.GRAZE)) {
                environment.setTileAgent(agent.getLocation(), null);
                agent.move(agentDecision.location()); // Move to chosen location
                environment.setTileAgent(agent);
                int grazeAmount = -agent.graze(environment.getTile(agent.getLocation()));
                environment.modifyTileFoodLevel(agent.getLocation(), grazeAmount); // Consume energy at chosen location
                aliveAgentList.add(agent); // Agent is still alive
                diagnostics.modifyCurrentEnvironmentEnergy(grazeAmount);
            }
            else if (agentDecision.agentAction().equals(AgentAction.PREDATE)) {
                environment.getTile(agentDecision.location()).getOccupant().setBeenEaten(); // We set the preys hasBeenEaten flag to true
                agent.predate(environment.getTile(agentDecision.location()).getOccupant().getScores()); // Predator gains energy from the prey
                environment.setTileAgent(agent.getLocation(), null);
                agent.move(agentDecision.location()); // Predator now occupies preys location
                environment.setTileAgent(agent);
                aliveAgentList.add(agent); // Agent is still alive
            }
            if (agent.isDead()) {
                environment.setTileAgent(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
            }
        }

        public Attributes mutate(Attributes attributes) {
            // We've decided the agent is going to mutate, now we need to randomly decide what attribute to mutate.
            switch (random.nextInt(4)) {
                case 0 -> {
                    // Mutate size
                    int oldSize = attributes.getSize();
                    attributes.setSize(Math.min(Math.max(attributes.getSize() + mutationMagnitude(random), 2), 10));
                    System.out.println("Size mutated by: " + (attributes.getSize() - oldSize));
                }
                case 1 -> {
                    // Mutate range
                    int oldRange = attributes.getRange();
                    attributes.setRange(Math.min(Math.max(attributes.getRange() + mutationMagnitude(random), 0), 5));
                    System.out.println("Range mutated by: " + (attributes.getRange() - oldRange));
                }
                case 2 -> {
                    // Mutate creationAmount
                    int oldCreationAmount = attributes.getCreationAmount();
                    attributes.setCreationAmount(Math.min(Math.max(attributes.getCreationAmount() + mutationMagnitude(random), 1), 8));
                    System.out.println("Creation Amount mutated by: " + (attributes.getCreationAmount() - oldCreationAmount));
                }
                case 3 -> {
                    // Mutate creationAge
                    int oldCreationAge = attributes.getCreationAge();
                    attributes.setCreationAge(Math.min(Math.max(attributes.getCreationAge() + mutationMagnitude(random), 1), 100 + attributes.getSize() * attributes.getSize()));
                    System.out.println("Creation Age mutated by: " + (attributes.getCreationAge() - oldCreationAge));
                }
            }
            return attributes;
        }

        private ArrayList<AgentVision> lookAround(Agent agent) {
            Location agentLocation = agent.getLocation();
            int visionRange = agent.getAttributes().getRange();
            int agentRange = agent.getAttributes().getRange();
            // Generate a new ArrayList of the AgentVision object, everything the agent sees will be stored here.
            ArrayList<AgentVision> agentViews = new ArrayList<>();
            // Retrieve the agents vision attribute, lets us know how far the agent can see.
            // Now we iterate over all the surrounding tiles, adding their visible contents to the AgentVision ArrayList.
            for (int i = -visionRange; i <= visionRange; i++) {
                for (int j = -visionRange; j <= visionRange; j++) {
                    int X = agentLocation.getX() + i;
                    int Y = agentLocation.getY() + j;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  X < environment.getSize())
                            && (Y < environment.getSize()))
                            && ((X >= 0) && (Y >= 0))
                            && !(i == 0 && j == 0))
                    {
                        AgentVision av = environment.getTileView(X, Y);
                        agentViews.add(av);
                    }
                }
            }
            Collections.shuffle(agentViews);
            return agentViews;
        }

        private static AgentDecision reactToView(Agent agent, ArrayList<AgentVision> agentView) {
            ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
            for (AgentVision currentAV : agentView) {
                possibleDecisions.add(reactToTile(agent, currentAV));
            }
            return getBestDecision(possibleDecisions);
        }

        private static AgentDecision reactToTile(Agent agent, AgentVision agentVision) {
            ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
            for (Motivation motivation : agent.getMotivations()) {
                possibleDecisions.add(motivation.run(agentVision, agent.getAttributes(), agent.getScores()));
            }
            return getBestDecision(possibleDecisions);
        }

        // A utility function which takes a collection of agent decisions, and returns the one with the highest decision score.
        private static AgentDecision getBestDecision(ArrayList<AgentDecision> agentDecisions) {
            AgentDecision finalDecision = new AgentDecision(null, AgentAction.NONE, 0);
            Collections.shuffle(agentDecisions);
            for (AgentDecision agentDecision : agentDecisions) {
                if (agentDecision.decisionScore() > finalDecision.decisionScore()) {
                    finalDecision = agentDecision;
                }
            }
            return finalDecision;
        }

        private static int mutationMagnitude(Random random) {
            int r = random.nextInt(100);
            int modifier = random.nextInt(2);
            if (modifier == 0) {
                modifier -= 1;
            }
            if (r < 45) {
                return modifier;
            }
            else if (r < 70) {
                return 2*modifier;
            }
            else if (r < 85) {
                return 3*modifier;
            }
            else if (r < 95) {
                return 4*modifier;
            }
            else {
                return 5*modifier;
            }
        }
    }
}
