package Simulation;

import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentStructs.ColorModel;
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
    private final AgentEditor agentEditor;
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
                        agent = agentLogic.getAgentFromEditor(j);
                        EnvironmentTile wt = environment.getGrid()[i];
                        agent.setLocation(wt.getLocation());
                        wt.setOccupant(agent);
                        agentList.add(agent);
                        diagnostics.addToAgentStats(
                                j,
                                1, agent.getScores().getEnergy(),
                                agent.getScores().getAge(),
                                agent.getAttributes().getSize(),
                                agent.getAttributes().getCreationSize(),
                                agent.getAttributes().getRange());
                    }
                }
            }
        });
    }

    int seasonCounter = 0;
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
                    diagnostics.addToAgentStats(
                            currentAgent.getAttributes().getID(),
                            1, currentAgent.getScores().getEnergy(),
                            currentAgent.getScores().getAge(),
                            currentAgent.getAttributes().getSize(),
                            currentAgent.getAttributes().getCreationSize(),
                            currentAgent.getAttributes().getRange()
                    );
                }
            }
        }
        agentList = aliveAgentList;
        Collections.shuffle(agentList);
        aliveAgentList = new ArrayList<>();
        // && !environment.getGrid()[i].isTerrain()
        IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
            if (random.nextInt(10000) / 100.0 < environment.getEnergyRegenChance()) {
                int modifyAmount = environment.modifyTileEnergyLevel(environment.getGrid()[i].getLocation(), environment.getEnergyRegenAmount());
                diagnostics.modifyCurrentEnvironmentEnergy(modifyAmount);
            }
        });
        seasonCounter++;
        if (seasonCounter > 100) {
            environment.setEnergyRegenChance(environment.getEnergyRegenChance() + 0.1);
            seasonCounter = 0;
            diagnostics.addToLogQueue("Environment Energy Regen Chance = " + environment.getEnergyRegenChance());
        }
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
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * environment.getSize()*environment.getSize());
            diagnostics.resetCurrentEnvironmentEnergy();
        }
        else if (environmentSettings.getMaxEnergyLevel() < environment.getMaxEnergyLevel()) {
            environment.setEnvironmentSettings(environmentSettings);
            diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * environment.getSize()*environment.getSize());
            diagnostics.resetCurrentEnvironmentEnergy();
        }
        environment.setEnvironmentSettings(environmentSettings);
        diagnostics.setMaxEnvironmentEnergy(environmentSettings.getMaxEnergyLevel() * environment.getSize()*environment.getSize());
    }

    /**
     * Groups the logical methods used for running agents.
     * <p>
     * This class is responsible for the agent's logic. These methods are placed here so the environment and agents can
     * communicate directly, rather than passing objects around. Only 2 methods in this class are public: runAgent() and
     * getAgentFromEditor(). All other methods exist to support the runAgent() method.
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    private class AgentLogic {

        /**
         * Runs the input agent for one day.
         * <p>
         * This method modifies the following Simulation parameters: environment, aliveAgentList, and diagnostics.
         * First the method checks if the agent has been eaten, this means its place on the environment has been taken,
         * so at this point the method ends. If an agent survives the day, it will be added to the aliveAgentList so by simply
         * ending the method we've now removed this agent from the simulation. If the method continues, it then calls the agents
         * liveDay() method, this increments its age and decrements its creationCounter. If the agent is still alive after this,
         * a collection of AgentVision objects is produced by the lookaround() method. This is then transformed into a single AgentDecision
         * method. The method then handles the agents decision.
         * @param agent the agent to be run
         */
        public void runAgent(Agent agent) {
            if (agent.isEaten()) {
                return; // Agent has been eaten by another agent, therefor its already been removed from the environment, all we need to do is not add it to the aliveAgentList
            }
            agent.liveDay(); // Increments its age and decrements its creationCounter
            if (agent.isDead()) {
                environment.setOccupant(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
                return;
            }
            ArrayList<AgentVision> agentView = lookAround(agent);
            AgentDecision agentDecision = reactToView(agent, agentView);
            if (agentDecision.agentAction().equals(AgentAction.NONE)) { // Do nothing
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.MOVE)) { //Just Move
                environment.setOccupant(agent.getLocation(), null); // Remove agent from old location
                agent.move(agentDecision.location(), environment.getTile(agentDecision.location()).isTerrain()); // Move to the new location
                environment.setOccupant(agent); // Set the agent to the new location
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.CREATE)) { // Create children
                ArrayList<Agent> childAgents;
                if (agent.mutates()) { // If the agent is a mutating agent, then handle their mutations before adding them to the environment
                    childAgents = mutateAndPlaceAgents(agent.create(agentDecision.location(), agent.getAttributes().getCreationCost(), environment), agent.getAttributes().getMutationChance());
                }
                else { // Else we just add them
                    childAgents = placeAgents(agent.create(agentDecision.location(), agent.getAttributes().getCreationCost(), environment));
                }
                aliveAgentList.addAll(childAgents); // Add new agents to the alive agents list
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.GRAZE)) { // Take energy from the environment
                environment.setOccupant(agent.getLocation(), null); // Remove agent from old location
                agent.move(agentDecision.location(), environment.getTile(agentDecision.location()).isTerrain()); // Move to chosen location
                environment.setOccupant(agent); // Set the agent to the new location
                int grazeAmount = -agent.graze(environment.getTile(agent.getLocation())); // Take energy, grazeAmount equals how much was successfully taken
                environment.modifyTileEnergyLevel(agent.getLocation(), grazeAmount); // Update environment with grazeAmount
                aliveAgentList.add(agent); // Agent is still alive
                diagnostics.modifyCurrentEnvironmentEnergy(grazeAmount); // Track the energy change in the diagnostics class
            }
            else if (agentDecision.agentAction().equals(AgentAction.PREDATE)) { // Take energy from another agent and take its place
                environment.getTile(agentDecision.location()).getOccupant().setBeenEaten(); // We set the preys hasBeenEaten flag to true
                agent.predate(environment.getTile(agentDecision.location()).getOccupant().getScores()); // Predator gains energy from the prey
                environment.setOccupant(agent.getLocation(), null); // Move to chosen location
                agent.move(agentDecision.location(), environment.getTile(agentDecision.location()).isTerrain()); // Predator now occupies preys location
                environment.setOccupant(agent); // Overwrite the occupant to the predator
                aliveAgentList.add(agent); // Agent is still alive
            }
            if (agent.isDead()) { // Agent may have exhausted its energy so check again here
                environment.setOccupant(agent.getLocation(), null); // If the agent is now dead, remove it from the board and don't add it to aliveAgentList
            }
        }

        /**
         * Returns a copy of the agent from the agentEditor at the specified index
         * <p>
         * @param index the index of the agent to copy
         */
        public BasicAgent getAgentFromEditor(int index) {
            return (BasicAgent) agentEditor.getAgent(index).copy();
        }

        /**
         * Trys to mutate child agents before placing them on the environment.
         * <p>
         * Iterates through the input collection of child agents and trys to mutate them based on the mutationChance.
         * Before placing an agent it will re-generate its mutating color. New agents are tracked here.
         * @param childAgents the collection of new agents
         * @param mutationChance the percent chance a child agent will mutate
         */
        private ArrayList<Agent> mutateAndPlaceAgents(ArrayList<Agent> childAgents, int mutationChance) {
            for (Agent child : childAgents) {
                diagnostics.addToAgentsBornLastStep(child.getAttributes().getID(), 1); // log that a new agents been born
                double[] oldStats = new double[]{ // store stats before mutating, allows us to track the stat change.
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
                        125);
                child.getAttributes().calculateAttributes(); // Calculate the agents calculated attributes
                environment.setOccupant(child);
            }
            return childAgents;
        }

        /**
         * Places agents on the environment and possibly mutates their seed color.
         * <p>
         * Iterates through the input collection of child agents, checks if it uses the RANDOM color model. If so then
         * mutate its seed color before adding it to the environment.
         * @param childAgents the collection of new agents
         */
        private ArrayList<Agent> placeAgents(ArrayList<Agent> childAgents) {
            for (Agent child : childAgents) {
                diagnostics.addToAgentsBornLastStep(child.getAttributes().getID(), 1); // log that a new agents been born
                child.getAttributes().calculateAttributes();
                environment.setOccupant(child);
                if (child.getAttributes().getColorModel().equals(ColorModel.RANDOM)) { // Check if the agent is using the random color model and handle accordingly
                    child.getAttributes().mutateSeedColor(6);
                }
            }
            return childAgents;
        }

        /**
         * Mutates the input attributes object.
         * <p>
         * Has an 80% chance of mutating size, and a 10% chance for range or creationSize. If the diagnostics verbosity is
         * set to high, then a message with the agents name, mutated attribute and how much it mutated by is added to the log queue.
         * @param attributes the attributes object to be mutated.
         */
        private Attributes mutate(Attributes attributes) {
            int ran = random.nextInt(10);
            if (ran < 8) {
                // Mutate size
                int oldSize = attributes.getSize();
                attributes.setSize(Math.min(Math.max(attributes.getSize() + mutationMagnitude(), 2), 10));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Size mutated by: " + (attributes.getSize() - oldSize) + ".");
                }
            }
            else if (ran < 9) {
                // Mutate range
                int oldRange = attributes.getRange();
                attributes.setRange(Math.min(Math.max(attributes.getRange() + mutationMagnitude(), 0), 5));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Range mutated by: " + (attributes.getRange() - oldRange) + ".");
                }
            }
            else {
                // Mutate creationAmount
                int oldCreationSize = attributes.getCreationSize();
                attributes.setCreationSize(Math.min(Math.max(attributes.getCreationSize() + mutationMagnitude(), 1), 8));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ": Litter Size mutated by: " + (attributes.getCreationSize() - oldCreationSize) + ".");
                }
            }
            return attributes;
        }

        /**
         * Returns an integer between -3 and 3, weighted towards 1.
         * <p>
         * 3 = 1%
         * 2 = 11.5%
         * 1 = 37.5%
         * 1 = 37.5%
         * 2 = 11.5%
         * 3 = 1%
         */
        private int mutationMagnitude() {
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

        /**
         * Returns an AgentVision object of each tile within the agents range.
         * <p>
         * Iterates in a square pattern centered on the agent. A range of 1 means only the 8 adjacent tiles
         * will be looked at. A range of 2 means the surrounding 24 tiles are looked at and so on.
         * @param agent the agent to look around
         */
        private ArrayList<AgentVision> lookAround(Agent agent) {
            Location agentLocation = agent.getLocation();
            int visionRange = agent.getAttributes().getRange();
            ArrayList<AgentVision> agentViews = new ArrayList<>();
            for (int i = -visionRange; i <= visionRange; i++) {
                for (int j = -visionRange; j <= visionRange; j++) {
                    int X = agentLocation.getX() + i;
                    int Y = agentLocation.getY() + j;
                    // Checks the agent isn't looking outside the grid, at its current tile or at terrain.
                    if (((  X < environment.getSize())
                            && (Y < environment.getSize()))
                            && ((X >= 0) && (Y >= 0))
                            && !(i == 0 && j == 0)
                            ) //&& !environment.getTile(X, Y).isTerrain()
                    {
                        AgentVision av = environment.getTileView(X, Y);
                        agentViews.add(av);
                    }
                }
            }
            Collections.shuffle(agentViews);
            return agentViews;
        }

        /**
         * Produces an AgentDecision from a collection of AgentVision objects and the agents motivations.
         * <p>
         * Iterates over each AgentVision object and produces a possible decision for each one. It then calls
         * the getBestDecision() method to get the chosen AgentDecision.
         * @param agent the agent to look around
         * @param agentView the collection of AgentVision objects
         */
        private static AgentDecision reactToView(Agent agent, ArrayList<AgentVision> agentView) {
            ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
            for (AgentVision currentAV : agentView) {
                possibleDecisions.add(reactToTile(agent, currentAV));
            }
            return getBestDecision(possibleDecisions);
        }

        /**
         * Produces an AgentDecision from a single AgentVision object and the agents motivations.
         * <p>
         * Iterates over each of the agents motivations and produces a possible decision for each one. It then calls
         * getBestDecision() to return the chosen decision.
         * @param agent the agent to look around
         * @param agentVision the AgentVision object to produce a decision for
         */
        private static AgentDecision reactToTile(Agent agent, AgentVision agentVision) {
            ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
            for (Motivation motivation : agent.getMotivations()) {
                AgentDecision ad = motivation.run(agentVision, agent.getAttributes(), agent.getScores());
//                if (ad.agentAction().equals(AgentAction.CREATE)) {
//                    System.out.println(ad.agentAction() + " " + ad.decisionScore());
//                }                if (ad.agentAction().equals(AgentAction.CREATE)) {
//                    System.out.println(ad.agentAction() + " " + ad.decisionScore());
//                }
                possibleDecisions.add(ad);
            }
            return getBestDecision(possibleDecisions);
        }

        /**
         * Returns the AgentDecision with the highest score.
         * <p>
         * @param agentDecisions the agent to look around
         */
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
    }

    /**
     * Groups the methods which paint terrain on the environment.
     * <p>
     * Whether a tile is terrain or not is defined by a boolean value. This class contains methods that sets environment tiles
     * terrain flags to true in varying patterns. At the lowest level a circle is generated with a specified size, then using the
     * same algorithm and a Random object, a cluster of circles is generated. A cluster size and density is specified. Then a line
     * of clusters is generated with a line size and  density specified. Finally, a number of lines generated at random positions
     * with random directions are generated. Here only a density is specified. By changing the values, fairly different forms of terrain
     * can be generated.
     * @author Sam Burchmore
     * @version 1.0a
     * @since 1.0a
     */
    public class TerrainGenerator {
        // The TerrainSettings object that stores the initial and current terrain settings
        private TerrainSettings terrainSettings;
        public TerrainGenerator() {
            terrainSettings = new TerrainSettings(10, 10, 10, 3, 1000, 500, 2500, 100, 10, 2, true);
        }

        public void fillTerrain() {
            for (EnvironmentTile environmentTile:
                    environment.getGrid()) {
                environmentTile.setTerrain(true);
            }
        }

        /**
         * Sets all tiles in the environment to not terrain.
         */
        public void clearTerrain() {
            IntStream.range(0, environment.getSize() * environment.getSize()).sequential().forEach(i->{
                environment.getGrid()[i].setTerrain(false);
            });
        }

        public void placeLine() {
            random.setSeed(Instant.now().toEpochMilli());
            generateCircleLines(
                    terrainSettings.getRockSize(),
                    terrainSettings.getClusterSize(),
                    terrainSettings.getClusterDensity(),
                    terrainSettings.getLineSize(),
                    terrainSettings.getLineDensity(),
                    terrainSettings.getTerrainAmount());
        }

        public void placeVariableCave() {
            random.setSeed(Instant.now().toEpochMilli());
            generateVariableCave(
                    terrainSettings.getRockSize(),
                    terrainSettings.getLowerCaveSize(),
                    terrainSettings.getUpperCaveSize(),
                    terrainSettings.getCaveWave(),
                    terrainSettings.getLineSize(),
                    terrainSettings.getTerrainAmount(),
                    terrainSettings.getBendDensity());
        }

        public void placeCave() {
            random.setSeed(Instant.now().toEpochMilli());
            generateCave(
                    terrainSettings.getRockSize(),
                    terrainSettings.getLineSize(),
                    terrainSettings.getTerrainAmount(),
                    terrainSettings.getBendDensity());
        }

        /**
         * Trys to paint a lines at random locations and in random directions.
         * <p>
         * For each tile in the environment, a random location will be chosen, along with a random direction. The method will then
         * try to paint the line at the location. The likelihood  of success is defined by the objectDensity parameter.
         * @param rockSize the circles radius
         * @param clusterSize the cluster radius
         * @param clusterDensity the likelihood a circle will be placed at each tile.
         * @param lineSize how many times the location will be incremented in the direction (how long the line is)
         * @param lineDensity the likelihood of a cluster being painted at a location.
         * @param objectAmount the likelihood of a line being painted at a tile.
         */
        public void generateCircleLines(int rockSize, int clusterSize, int clusterDensity,  int lineSize, int lineDensity, int objectAmount){
            for (int i = 0; i < objectAmount; i++) {
                Location location = new Location(random.nextInt(environment.getSize()), random.nextInt(environment.getSize()));
                int dx = random.nextInt(3) - 1;
                int dy = random.nextInt(3) - 1;
                generateCircleLine(rockSize, clusterSize, clusterDensity, lineSize, lineDensity, location, dx, dy);
            }
        }

        public void generateCave(int caveSize, int caveLength, int caveAmount, int bendDensity) {
            //generateVariableCircleRockCluster((int) (rockSize/Math.pow(rockSize, 0.25)), rockSize, 10, location);
            for (int j = 0; j < caveAmount; j++) {
                int dx = 0;
                int dy = 0;
                int dx2 = 0;
                int dy2 = 0;
                Location location = new Location(environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3), environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3));
                for (int i = 0; i < caveLength; i++) {
                    if (random.nextInt(10000) < bendDensity) {
                        dx2 = getPosNegRandom(caveSize+1);
                        dy2 = getPosNegRandom(caveSize+1);
                    }
                    dx = dx + dx2;
                    dy = dy + dy2;
                    Location caveLocation = new Location(location.getX() + dx, location.getY() + dy);
                    if (environment.isLocationOnGrid(caveLocation)) {
                        generateCircleRock(caveSize, caveLocation);
                    } else {
                        dx = dx - dx2;
                        dy = dy - dy2;
                    }
                }
            }
        }

        public void generateVariableCave(int rockSize, int caveLowerBound, int caveUpperBound, int caveWave, int caveLength, int caveAmount, int bendDensity) {
            for (int j = 0; j < caveAmount; j++) {
                int dx = 0;
                int dy = 0;
                int dx2 = 0;
                int dy2 = 0;
                Location location = new Location(environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3), environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3));
                int caveSize = caveLowerBound;
                for (int i = 0; i < caveLength/(caveWave*2); i++) {
                    int cw = random.nextInt(caveWave);
                    for (int c = -cw; c < cw; c++) {
                        if (c < 0) {
                            caveSize += caveUpperBound/cw;
                        }
                        else {
                            caveSize -= caveUpperBound/cw;
                        }
                        if (random.nextInt(10000) < bendDensity) {
                            dx2 = getPosNegRandom(caveLowerBound + 1);
                            dy2 = getPosNegRandom(caveLowerBound + 1);
                        }
                        dx = dx + dx2;
                        dy = dy + dy2;
                        Location caveLocation = new Location(location.getX() + dx, location.getY() + dy);
                        if (environment.isLocationOnGrid(caveLocation)) {
                            generateCircleRock(caveSize, caveLocation);
                        } else {
                            dx = dx - dx2;
                            dy = dy - dy2;
                        }
                    }
                }
            }
        }

        public void generateCavernousCave(int rockSize, int caveSize, int caveLength, int caveDensity, int bendDensity, int cavernDensity, int cavernSize, Location location) {
            //generateVariableCircleRockCluster((int) (rockSize/Math.pow(rockSize, 0.25)), rockSize, 10, location);
            for (int j = 0; j < caveDensity; j++) {
                int dx = 0;
                int dy = 0;
                int dx2 = 0;
                int dy2 = 0;
                for (int i = 0; i < caveLength; i++) {
                    if (random.nextInt(10000) < bendDensity) {
                        dx2 = getPosNegRandom(caveSize+1);
                        dy2 = getPosNegRandom(caveSize+1);
                    }
                    dx = dx + dx2;
                    dy = dy + dy2;
                    Location caveLocation = new Location(location.getX() + dx, location.getY() + dy);
                    if (environment.isLocationOnGrid(caveLocation)) {
                        if (random.nextInt(10000) < cavernDensity) {
                            generateCircleClearCluster(cavernSize/10, cavernSize, 500, caveLocation);
                        }
                        else {
                            clearCircle(caveSize, caveLocation);
                        }
                    } else {
                        dx = dx - dx2;
                        dy = dy - dy2;
                    }
                }
            }
        }

        private static int overflow(int a, int b, int overflowUpper, int overflowLower) {
            if (a + b > overflowUpper) {
                return a + b - overflowUpper;
            }
            else if (a + b < overflowLower) {
                return overflowUpper - a + b;
            }
            return a + b;
        }

        public void generateCircleClearCluster(int rockSize, int clusterSize, int clusterDensity, Location location) {
            int seedX = location.getX();
            int seedY = location.getY();
            int x1;
            int y1;
            for (int x = -clusterSize; x <= clusterSize; x++) {
                for (int y = -clusterSize; y <= clusterSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= clusterSize * clusterSize
                            && random.nextInt(10000) < clusterDensity
                    )
                    {
                        clearCircle(rockSize, new Location(x1, y1));
                    }
                }
            }
        }

        /**
         * Paints a circle pattern of terrain flags centered at the specified location.
                * <p>
         * Iterates in a square pattern centered on the location specified. Works similarly to AgentLogic.lookAround(Agent agent).
                * If a tile satisfies the formula: (x-x1)^2 + (y-x1)^2 = r^2 then set its terrain flag to true, where (x, y) are the center
         * coordinates and (x1, y1) are the subject coordinates.
                * @param rockSize the circles radius
         * @param location the location to paint at.
                */
        public void generateCircleRock(int rockSize, Location location) {
            int seedX = location.getX();
            int seedY = location.getY();
            int x1;
            int y1;
            for (int x = -rockSize; x <= rockSize; x++) {
                for (int y = -rockSize; y <= rockSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= rockSize * rockSize
                    )
                    {
                        environment.setTileTerrain(new Location(x1, y1), terrainSettings.isTerrain());
                    }
                }
            }
        }

        public void clearCircle(int clearSize, Location location) {
            int seedX = location.getX();
            int seedY = location.getY();
            int x1;
            int y1;
            for (int x = -clearSize; x <= clearSize; x++) {
                for (int y = -clearSize; y <= clearSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= clearSize * clearSize
                    )
                    {
                        environment.setTileTerrain(new Location(x1, y1), false);
                    }
                }
            }
        }

        /**
         * Paints a cluster of circles centered at the specified location.
         * <p>
         * Uses the same algorithm as the generateCircleRock() method, but instead of setting every tile to terrain, it will try to paint
         * a circle centered on the tile. The success of this is decided by the clusterDensity paramter.
         * @param rockSize the circles radius
         * @param clusterSize the cluster radius
         * @param clusterDensity the likelihood a circle will be placed at each tile.
         * @param location the location to paint at.
         */
        public void generateVariableCircleRockCluster(int rockSize, int clusterSize, int clusterDensity, Location location) {
            int seedX = location.getX();
            int seedY = location.getY();
            int x1;
            int y1;
            for (int x = -clusterSize; x <= clusterSize; x++) {
                for (int y = -clusterSize; y <= clusterSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
                    if (((  x1 < environment.getSize())
                            && (y1 < environment.getSize()))
                            && ((x1 >= 0) && (y1 >= 0))
                            && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= clusterSize * clusterSize
                            && random.nextInt(Math.abs(clusterSize * clusterSize)) < clusterDensity
                    )
                    {
                        generateCircleRockCluster(rockSize/10, rockSize, random.nextInt(500), new Location(x1, y1));
                    }
                }
            }
        } // Places one cluster

        /**
         * Paints a cluster of circles centered at the specified location.
         * <p>
         * Uses the same algorithm as the generateCircleRock() method, but instead of setting every tile to terrain, it will try to paint
         * a circle centered on the tile. The success of this is decided by the clusterDensity paramter.
         * @param rockSize the circles radius
         * @param clusterSize the cluster radius
         * @param clusterDensity the likelihood a circle will be placed at each tile.
         * @param location the location to paint at.
         */
        public void generateCircleRockCluster(int rockSize, int clusterSize, int clusterDensity, Location location) {
            int seedX = location.getX();
            int seedY = location.getY();
            int x1;
            int y1;
            for (int x = -clusterSize; x <= clusterSize; x++) {
                for (int y = -clusterSize; y <= clusterSize; y++) {
                    x1 = seedX + x;
                    y1 = seedY + y;
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

        /**
         * Paints a line of clusters at the specified location and in the specified direction.
         * <p>
         * Trys to paint a cluster at the specified location before incrementing the location in the specified direction.
         * @param rockSize the circles radius
         * @param clusterSize the cluster radius
         * @param clusterDensity the likelihood a circle will be placed at each tile.
         * @param lineSize how many times the location will be incremented in the direction (how long the line is)
         * @param lineDensity the likelihood of a cluster being painted at a location.
         * @param location the location to paint at.
         * @param dx the x direction
         * @param dy the y direction
         */
        public void generateCircleLine(int rockSize, int clusterSize, int clusterDensity,  int lineSize, int lineDensity, Location location, int dx, int dy) {
            for (int i = 0; i < lineSize; i++) {
                if (random.nextInt(100000) < lineDensity) {
                    generateCircleRockCluster(1 + rockSize, clusterSize, clusterDensity, location);
                }
                location.setX(location.getX() + dx);
                location.setY(location.getY() + dy);
            }
        }

        /**
         * Calls the generateCircleLines() method with the terrainSettings as parameters.
         */
        public void generateTerrain() {
            random.setSeed(Instant.now().toEpochMilli());
//            generateCircleLines(
//                    terrainSettings.getRockSize(),
//                    terrainSettings.getClusterSize(),
//                    terrainSettings.getClusterDensity(),
//                    terrainSettings.getLineSize(),
//                    terrainSettings.getLineDensity(),
//                    terrainSettings.getLineMagnitude());
        }

        public TerrainSettings getTerrainSettings() {
            return terrainSettings;
        }
        public void setTerrainSettings(TerrainSettings terrainSettings) {
            this.terrainSettings = terrainSettings;
        }

        private int getPosNegRandom(int bound) {
            if (random.nextInt(2) == 0) {
                return -random.nextInt(bound);
            }
            return random.nextInt(bound);
        }}

    public void setDiagnosticsVerbosity(int diagnosticsVerbosity) {
        this.diagnosticsVerbosity = diagnosticsVerbosity;
    }
    public void updateAgentNames() {
        diagnostics.setAgentNames(agentEditor.getAgentNames());
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
    public Environment getEnvironment() {
        return environment;
    }
    public Diagnostics getDiagnostics() {
        return this.diagnostics;
    }
    public AgentEditor getAgentEditor() {
        return agentEditor;
    }
    public TerrainGenerator getTerrainGenerator() {
        return terrainGenerator;
    }
}

