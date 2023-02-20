package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentUtility.AgentEditor;
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
import Simulation.SimulationUtility.TerrainSettings;
import View.SimulationPanel;

/** This class represents the simulation. It acts as a controller to the Environment and the Agents. It contains 2 inner classes: AgentLogic and TerrainGenerator.
 * AgentLogic contains all the methods required for agents to interact with and live in the simulation. TerrainGenerator contains methods which generate terrain shapes
 * on the Environment.
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class Simulation {

    // The instance of the environment class
    private Environment environment;
    // Here we store all the agents currently in the simulation
    private ArrayList<Agent> agentList;
    // All newly born and surviving agents from each cycle are placed in here. At the end agentList is set to this
    private ArrayList<Agent> aliveAgentList;
    // The instance of the agent editor class
    private AgentEditor agentEditor;
    // The instance of the diagnostics class
    private final Diagnostics diagnostics;
    // The instance of the agent logic inner class
    private final AgentLogic agentLogic;
    // The instance of the terrain generator class
    private final TerrainGenerator terrainGenerator;
    // The random instance used for decisions within the class
    private final Random random = new Random();
    // How much info is logged by the diagnostics class = (0=low, 1=high)
    private int diagnosticsVerbosity = 1;

    public Simulation(int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount){
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
        this.agentList = new ArrayList<>();
        this.aliveAgentList = new ArrayList<>();
        this.diagnostics = new Diagnostics(maxEnergyLevel * size);
        this.agentEditor = new AgentEditor();
        this.agentLogic = new AgentLogic();
        this.terrainGenerator = new TerrainGenerator();
    }

    /**
     * Populates the environment with agents.
     * <p>
     * Iterates over every environment tile and tries to place an agent on one. The chance of an agent being placed corresponds
     * to the density parameter, i.e. a 100.0 an agent will always be placed, and at 1.0 and agent will have a 1/100 chance of
     * being placed. If this passes, then it randomly selects one agent from the AgentEditor instance. The chance of this agent
     * being placed corresponds with its spawning weight. At 1, it will always spawn and never at 0.
     * @param density how densely should the environment be populated with agents.
     */
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

    /**
     * Cycles the environment for one step.
     * <p>
     * Iterates over the agentList. If an agent hasn't been eaten earlier in the cycle, it runs the agent. After it's
     * iterated over every agent, it then overwrites agentList with aliveAgentList. Then it iterates over each environment
     * tile and possible regenerates its energy, depending on the environments settings.
     */
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
        Collections.shuffle(agentList);
        aliveAgentList = new ArrayList<>();
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            if (random.nextInt(10000) / 100.0 < environment.getEnergyRegenChance() && !environment.getGrid()[i].isTerrain()) {
                int modifyAmount = environment.modifyTileEnergyLevel(environment.getGrid()[i].getLocation(), environment.getEnergyRegenAmount());
                diagnostics.modifyCurrentEnvironmentEnergy(modifyAmount);
            }
        });
    }

    /**
     * Removes all agents from the environment.
     * <p>
     * Iterates over the environment and sets every tile occupant to null.
     */
    public void clearAgents() {
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<>();
    }

    /**
     * Replenishes the environment's energy.
     * <p>
     * Iterates over the environment and sets every tile's energy level to its max.
     */
    public void replenishEnvironmentEnergy() {
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setEnergyLevel(environment.getMaxEnergyLevel());
        });
        diagnostics.resetCurrentEnvironmentEnergy();
    }

    /**
     * Updates the environments settings.
     * <p>
     * Sets the environments settings to the input settings and handles removing the agents and updating the diagnostics accordingly.
     * @param environmentSettings the new environment settings
     */
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

    private class AgentLogic {

        public void runAgent(Agent agent) {
            if (agent.isEaten()) {
                return; // Agent has been eaten by another agent, therefor its already been removed from the environment, all we need to do is not add it to the aliveAgentList
            }
            agent.liveDay(); // Live a day, i.e. increase age, reduce creation cooldown.
            if (agent.isDead()) {
                environment.setOccupant(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
                return;
            }
            ArrayList<AgentVision> agentView = lookAround(agent);
            AgentDecision agentDecision = reactToView(agent, agentView);
            if (agentDecision.agentAction().equals(AgentAction.NONE)) { // Do nothing
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.MOVE)) {
                environment.setOccupant(agent.getLocation(), null);
                agent.move(agentDecision.location()); // Move to the chosen location
                environment.setOccupant(agent);
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.CREATE)) { // Create children
                ArrayList<Agent> childAgents;
                if (agent.mutates()) {
                    childAgents = mutateAndPlaceAgents(agent.create(agentDecision.location(), agent.getAttributes().getCreationCost(), environment), agent.getAttributes().getMutationChance());
                }
                else {
                    childAgents = placeAgents(agent.create(agentDecision.location(), agent.getAttributes().getCreationCost(), environment));
                }
                aliveAgentList.addAll(childAgents); // Add new agents to the alive agents list
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.GRAZE)) {
                environment.setOccupant(agent.getLocation(), null);
                agent.move(agentDecision.location()); // Move to chosen location
                environment.setOccupant(agent);
                int grazeAmount = -agent.graze(environment.getTile(agent.getLocation()));
                environment.modifyTileEnergyLevel(agent.getLocation(), grazeAmount); // Consume energy at chosen location
                aliveAgentList.add(agent); // Agent is still alive
                diagnostics.modifyCurrentEnvironmentEnergy(grazeAmount);
            }
            else if (agentDecision.agentAction().equals(AgentAction.PREDATE)) {
                environment.getTile(agentDecision.location()).getOccupant().setBeenEaten(); // We set the preys hasBeenEaten flag to true
                agent.predate(environment.getTile(agentDecision.location()).getOccupant().getScores()); // Predator gains energy from the prey
                environment.setOccupant(agent.getLocation(), null);
                agent.move(agentDecision.location()); // Predator now occupies preys location
                environment.setOccupant(agent);
                aliveAgentList.add(agent); // Agent is still alive
            }
            if (agent.isDead()) {
                environment.setOccupant(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
            }
        }

        private ArrayList<Agent> mutateAndPlaceAgents(ArrayList<Agent> childAgents, int mutationChance) {
            for (Agent child : childAgents) {
                double[] oldStats = new double[]{
                        child.getAttributes().getSize(),
                        child.getAttributes().getCreationSize(),
                        child.getAttributes().getRange()
                };
                if (random.nextInt(100) < mutationChance) {
                    child.setAttributes(agentLogic.mutate(child.getAttributes()));
                }
                child.getAttributes().mutateAttributesColor(
                        (child.getAttributes().getSize() / 100.0) - (oldStats[0] / 100),
                        (child.getAttributes().getCreationSize() / 8.0) - (oldStats[1] / 8),
                        (child.getAttributes().getRange() / 5.0) - (oldStats[2] / 5),
                        125
                );
                child.getAttributes().calculateAttributes();
                environment.setOccupant(child);
            }
            return childAgents;
        }

        private ArrayList<Agent> placeAgents(ArrayList<Agent> childAgents) {
            for (Agent child : childAgents) {
                child.getAttributes().calculateAttributes();
                environment.setOccupant(child);
            }
            return childAgents;
        }

        public BasicAgent getAgent(int index) {
            return (BasicAgent) agentEditor.getAgent(index).copy();
        }

        private Attributes mutate(Attributes attributes) {
            // We've decided the agent is going to mutate, now we need to randomly decide what attribute to mutate.
            int ran = random.nextInt(10);
            if (ran < 8) {
                // Mutate size
                int oldSize = attributes.getSize();
                attributes.setSize(Math.min(Math.max(attributes.getSize() + mutationMagnitude(random), 2), 10));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Size mutated by: " + (attributes.getSize() - oldSize) + ".");
                }
            }
            else if (ran < 9) {
                // Mutate range
                int oldRange = attributes.getRange();
                attributes.setRange(Math.min(Math.max(attributes.getRange() + mutationMagnitude(random), 0), 5));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Range mutated by: " + (attributes.getRange() - oldRange) + ".");
                }
            }
            else {
                // Mutate creationAmount
                int oldCreationSize = attributes.getCreationSize();
                attributes.setCreationSize(Math.min(Math.max(attributes.getCreationSize() + mutationMagnitude(random), 1), 8));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Litter Size mutated by: " + (attributes.getCreationSize() - oldCreationSize) + ".");
                }
            }
            return attributes;
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
                            && !(i == 0 && j == 0)
                            && !environment.getTile(X, Y).isTerrain())
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

    public class TerrainGenerator {
        // TODO the settings system is ridiculously hard to read, maybe clean it up
        //(x-a)^2 + (y-b)^2 = r ^2

        private TerrainSettings terrainSettings;

        public TerrainGenerator() {
            terrainSettings = new TerrainSettings(2, 4, 60, 600, 8500, 1);
        }

        public TerrainSettings getTerrainSettings() {
            return terrainSettings;
        }

        public void setTerrainSettings(TerrainSettings terrainSettings) {
            this.terrainSettings = terrainSettings;
        }

        public void clearTerrain() {
            IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
                environment.getGrid()[i].setTerrain(false);
            });
        }

        public void generateCircleLines(int rockSize, int clusterSize, int clusterDensity,  int lineSize, int lineDensity, int objectDensity){
            IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
                if (random.nextInt(10000) < objectDensity) {
                    generateCircleLine(rockSize, clusterSize, clusterDensity, lineSize, lineDensity);
                }
            });
        }

        public void generateCircleLine(int rockSize, int clusterSize, int clusterDensity,  int lineSize, int lineDensity) {
            Location seedLocation = new Location(random.nextInt(environment.getSize()), random.nextInt(environment.getSize()));
            int dx = random.nextInt(3) - 1;
            int dy = random.nextInt(3) - 1;
            for (int j = 0; j < 10; j++) {
                int rand = random.nextInt(10000);
                for (int i = 0; i < lineSize / 10; i++) {
                    if (rand < lineDensity) {
                        generateCircleCluster(1 + rockSize, clusterSize, clusterDensity, seedLocation);
                    }
                    seedLocation.setX(seedLocation.getX() + dx);
                    seedLocation.setY(seedLocation.getY() + dy);
                }
            }
        }

        public void generateTerrain() {
            generateCircleLines(
                    terrainSettings.getRockSize(),
                    terrainSettings.getClusterSize(),
                    terrainSettings.getClusterDensity(),
                    terrainSettings.getLineSize(),
                    terrainSettings.getLineDensity(),
                    terrainSettings.getLineMagnitude());
        }

        public void generateCircleRock(int rockSize) {
            Location seedLocation = new Location(random.nextInt(environment.getSize()), random.nextInt(environment.getSize()));
            int seedX = seedLocation.getX();
            int seedY = seedLocation.getY();
            int x1;
            int y1;
            for (int x = -rockSize; x <= rockSize; x++) {
                for (int y = -rockSize; y <= rockSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= rockSize * rockSize
                    )
                    {
                        environment.setTileTerrain(new Location(x1, y1), true);
                    }
                }
            }
        } // Places one circle

        public void generateCircleRock(int rockSize, Location location) {
            Location seedLocation = location;
            int seedX = seedLocation.getX();
            int seedY = seedLocation.getY();
            int x1;
            int y1;
            for (int x = -rockSize; x <= rockSize; x++) {
                for (int y = -rockSize; y <= rockSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= rockSize * rockSize
                    )
                    {
                        environment.setTileTerrain(new Location(x1, y1), true);
                    }
                }
            }
        } // Places one circle

        public void generateCircleCluster(int rockSize, int clusterSize, int clusterDensity) {
            Location seedLocation = new Location(random.nextInt(environment.getSize()), random.nextInt(environment.getSize()));
            int seedX = seedLocation.getX();
            int seedY = seedLocation.getY();
            int x1;
            int y1;
            for (int x = -clusterSize; x <= clusterSize; x++) {
                for (int y = -clusterSize; y <= clusterSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= clusterSize * clusterSize
                            && random.nextInt(1000000) < clusterDensity
                    )
                    {
                        generateCircleRock(rockSize, new Location(x1, y1));
                    }
                }
            }
        } // Places one cluster

        public void generateCircleCluster(int rockSize, int clusterSize, int clusterDensity, Location seedLocation) {
            int seedX = seedLocation.getX();
            int seedY = seedLocation.getY();
            int x1;
            int y1;
            for (int x = -clusterSize; x <= clusterSize; x++) {
                for (int y = -clusterSize; y <= clusterSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= clusterSize * clusterSize
                            && random.nextInt(10000) < clusterDensity
                    )
                    {
                        generateCircleRock(rockSize, new Location(x1, y1));
                    }
                }
            }
        } // Places one cluster

        public void generateCircleRocks(int rockSize, int objectDensity) {
            IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
                if (random.nextInt(10000) < objectDensity) {
                    generateCircleRock(1 + random.nextInt(rockSize-1));
                }
            });
        } // Places circles randomly depending on objectDensity

        public void generateCircleClusters(int rockSize, int clusterSize, int clusterDensity, int objectDensity) {
            IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
                if (random.nextInt(10000) < objectDensity) {
                    generateCircleCluster(rockSize, clusterSize, clusterDensity);
                }
            });
        } // Places clusters randomly depending on objectDensity


        public void squareRock(int rockSize) {
            Location seedLocation = new Location(random.nextInt(environment.getSize()), random.nextInt(environment.getSize()));
            for (int i = -rockSize; i <= rockSize; i++) {
                for (int j = -rockSize; j <= rockSize; j++) {
                    int X = seedLocation.getX() + i;
                    int Y = seedLocation.getY() + j;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  X < environment.getSize())
                            && (Y < environment.getSize()))
                            && ((X >= 0) && (Y >= 0)))
                    {
                        environment.setTileTerrain(new Location(X, Y), true);
                    }
                }
            }
        }

        public void squareRock(int rockSize, Location location) {
            Location seedLocation = location;
            for (int i = -rockSize; i <= rockSize; i++) {
                for (int j = -rockSize; j <= rockSize; j++) {
                    int X = seedLocation.getX() + i;
                    int Y = seedLocation.getY() + j;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  X < environment.getSize())
                            && (Y < environment.getSize()))
                            && ((X >= 0) && (Y >= 0)))
                    {
                        environment.setTileTerrain(new Location(X, Y), true);
                    }
                }
            }
        }

        public void placeCluster(int rockSize, int clusterSize, int clusterDensity) {
            Location seedLocation = new Location(random.nextInt(environment.getSize()), random.nextInt(environment.getSize()));
            for (int i = -clusterSize; i <= clusterSize; i++) {
                for (int j = -clusterSize; j <= clusterSize; j++) {
                    int X = seedLocation.getX() + i;
                    int Y = seedLocation.getY() + j;
                    // Checks the agent isn't looking outside the grid or at its current tile
                    if (((  X < environment.getSize())
                            && (Y < environment.getSize()))
                            && ((X >= 0) && (Y >= 0)))
                    {
                        if (random.nextInt(100) < clusterDensity) {
                            squareRock(rockSize);
                        }
                    }
                }
            }
        }
    }

    public void setEnvironmentColors(Color[] color) {
        environment.setColors(color);
    }

    public Color[] getEnvironmentColors() {
        return environment.getColors();
    }

    public void setEnvironment(int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount) {
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
    }

    public Environment getEnvironment() {
        return environment;
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

    public TerrainGenerator getTerrainGenerator() {
        return terrainGenerator;
    }

}

