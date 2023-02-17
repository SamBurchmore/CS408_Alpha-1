package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentUtility.AgentEditor;
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
import Simulation.SimulationUtility.SimulationSettings;
import View.SimulationPanel;

/**
 * This class will be the main controller for the simulation. It will handle running the agents, moving them, removing, and adding them to the grid when needed.
 */
public class Simulation {


    private Environment environment; // The instance of the environment class

    private ArrayList<Agent> agentList; // Here we store all the agents currently in the simulation

    private ArrayList<Agent> aliveAgentList; // When cycle() is called, all newly born and surviving agents are placed in here. At the end agentList is set to this

    private AgentEditor agentEditor; // The instance of the agent editor class

    private Diagnostics diagnostics; // The instance of the diagnostics class

    private final AgentLogic agentLogic; // The instance of the agent logic inner class

    private Random random;

    private int diagnosticsVerbosity = 1; // How much info is logged by the diagnostics class = (0=low, 1=standard, 2=high)

    private SimulationPanel simulationPanel;

    public Simulation(SimulationPanel simulationPanel, int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount){
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
        this.random = new Random();
        this.agentList = new ArrayList<>();
        this.aliveAgentList = new ArrayList<>();
        this.diagnostics = new Diagnostics(maxEnergyLevel * size);
        this.agentEditor = new AgentEditor();
        this.agentLogic = new AgentLogic();
        this.simulationPanel = simulationPanel;
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
                        agent = agentLogic.getAgent(j);
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
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
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
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * getEnvironmentSize()*getEnvironmentSize());
        }

    public SimulationSettings getSimulationSettings(String name) {
        return new SimulationSettings(
                name,
                agentEditor.getActiveAgentsSettings(),
                environment.getEnvironmentSettings());
    }

    public void setSimulationSettings(SimulationSettings simulationSettings) {
        environment.setEnvironmentSettings(simulationSettings.getEnvironmentSettings());
        agentEditor.setActiveAgentsSettings(simulationSettings.getAgentSettings());
    }

    public Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    public BufferedImage getSimulationImage(int scale) {
        return this.environment.toBufferedImage(scale);
    }

    public AgentEditor getAgentEditor() {
        return agentEditor;
    }

    public void updateAgentNames() {
        diagnostics.setAgentNames(agentEditor.getAgentNames());
    }

    public int getDiagnosticsVerbosity() {
        return diagnosticsVerbosity;
    }

    public void setDiagnosticsVerbosity(int diagnosticsVerbosity) {
        this.diagnosticsVerbosity = diagnosticsVerbosity;
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
                ArrayList<Agent> childAgents = processAgents(agent.create(agentDecision.location(), (agent.getAttributes().getEnergyCapacity() / agent.getAttributes().getCreationSize()) / 2, environment), agent.getAttributes().getMutationMagnitude());
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

        private ArrayList<Agent> processAgents(ArrayList<Agent> childAgents, int mutationChance) {
            if (mutationChance > random.nextInt(100)) { // Check if the parent agent mutates, if so then mutate all its children before adding them to the board
                for (Agent child : childAgents) {
                    double[] oldStats = new double[]{child.getAttributes().getSize(), child.getAttributes().getCreationSize(), child.getAttributes().getRange()};
                    child.setAttributes(agentLogic.mutate(child.getAttributes()));
                    double[] newStats = new double[]{child.getAttributes().getSize(), child.getAttributes().getCreationSize(), child.getAttributes().getRange()};
                    child.getAttributes().generateColor(
                            (newStats[0] / 100) - (oldStats[0] / 100),
                            (newStats[1] / 8) - (oldStats[1] / 8),
                            (newStats[2] / 5) - (oldStats[2] / 5),
                            125
                    );
                    environment.setTileAgent(child);
                }
            }
            else {
                for (Agent child : childAgents) { // Otherwise just add the agents straight to the board
                    environment.setTileAgent(child);
                }
            }
            return childAgents;
        }

        public BasicAgent getAgent(int index) {
            BasicAgent basicAgent = (BasicAgent) agentEditor.getAgent(index).copy();
            basicAgent.getAttributes().generateColor(
                    basicAgent.getAttributes().getSize() / 100.0,
                    basicAgent.getAttributes().getCreationSize() / 8.0,
                    basicAgent.getAttributes().getRange() / 5.0,
                    125
                    );
            return basicAgent;
        }

        private Attributes mutate(Attributes attributes) {
            // We've decided the agent is going to mutate, now we need to randomly decide what attribute to mutate.
            int ran = random.nextInt(11);
            if (ran < 5) {
                // Mutate size
                int oldSize = attributes.getSize();
                attributes.setSize(Math.min(Math.max(attributes.getSize() + mutationMagnitude(random), 2), 10));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Size mutated by: " + (attributes.getSize() - oldSize) + ".");
                }
                return attributes;
            }
            if (ran < 8) {
                // Mutate range
                int oldRange = attributes.getRange();
                attributes.setRange(Math.min(Math.max(attributes.getRange() + mutationMagnitude(random), 0), 5));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Range mutated by: " + (attributes.getRange() - oldRange) + ".");
                }
                return attributes;
            }
            else {
                // Mutate creationAmount
                int oldCreationSize = attributes.getCreationSize();
                attributes.setCreationSize(Math.min(Math.max(attributes.getCreationSize() + mutationMagnitude(random), 1), 8));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Litter Size mutated by: " + (attributes.getCreationSize() - oldCreationSize) + ".");
                }
                System.out.println();
                return attributes;
            }
        }

        private ArrayList<AgentVision> lookAround(Agent agent) {
            Location agentLocation = agent.getLocation();
            int visionRange = agent.getAttributes().getRange();
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
            if (r < 75) {
                return modifier;
            }
            if (r < 98) {
                return 2*modifier;
            }
            return 3*modifier;
        }

    }


}
