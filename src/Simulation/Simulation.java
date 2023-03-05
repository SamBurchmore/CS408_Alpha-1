package Simulation;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import Simulation.Agent.AgentConcreteComponents.BasicAgent;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Scores;
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
            if (!currentAgent.spaceTaken()) {
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
            if (agent.spaceTaken()) {
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
                agent.move(agentDecision.location()); // Move to the new location
                environment.setOccupant(agent); // Set the agent to the new location
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.CREATE)) { // Create children
                ArrayList<Agent> childAgents;
                if (agent.mutates()) { // If the agent is a mutating agent, then handle their mutations before adding them to the environment
                    childAgents = mutateAndPlaceAgents(agent.create(agentDecision.location(), environment), agent.getAttributes().getMutationChance());
                }
                else { // Else we just add them
                    childAgents = placeAgents(agent.create(agentDecision.location(), environment));
                }
                aliveAgentList.addAll(childAgents); // Add new agents to the alive agents list
                aliveAgentList.add(agent); // Agent is still alive
            }
            else if (agentDecision.agentAction().equals(AgentAction.GRAZE)) { // Take energy from the environment
                environment.setOccupant(agent.getLocation(), null); // Remove agent from old location
                agent.move(agentDecision.location()); // Move to chosen location
                clearSpace(agent);
                environment.setOccupant(agent); // Set the agent to the new location
                int grazeAmount = -agent.graze(environment.getTile(agent.getLocation())); // Take energy, grazeAmount equals how much was successfully taken
                environment.modifyTileEnergyLevel(agent.getLocation(), grazeAmount); // Update environment with grazeAmount
                aliveAgentList.add(agent); // Agent is still alive
                diagnostics.modifyCurrentEnvironmentEnergy(grazeAmount); // Track the energy change in the diagnostics class
            }
            else if (agentDecision.agentAction().equals(AgentAction.PREDATE)) { // Take energy from another agent and take its place
                environment.getTile(agentDecision.location()).getOccupant().setSpaceTaken(); // We set the preys hasBeenEaten flag to true
                agent.predate(environment.getTile(agentDecision.location()).getOccupant().getScores()); // Predator gains energy from the prey
                environment.setOccupant(agent.getLocation(), null); // Move to chosen location
                agent.move(agentDecision.location()); // Predator now occupies preys location
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
         * Before placing an agent it will re-generate its mutating color. New agents are tracked here. Additionally,
         * checks if the child agents new location is occupied, if so it sets that agents spaceTaken flag to true.
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
                child.setScores(initScores(child));
                agentLogic.clearSpace(child);
                environment.setOccupant(child);
            }
            return childAgents;
        }

        /** Checks if the agents location is occupied, if so it sets the occupants spaceTaken to true.
         * @param agent The agent moving to a new space.
         */
        private void clearSpace(Agent agent) {
            if (environment.getTile(agent.getLocation()).isOccupied()) {
                environment.getTile(agent.getLocation()).getOccupant().setSpaceTaken();
                //System.out.println(agent.getAttributes().getName() + " moved to " + environment.getTile(agent.getLocation()).getOccupant().getAttributes().getName() + "'s space.");
            }
        }

        /**
         * Places agents on the environment and possibly mutates their seed color.
         * <p>
         * Iterates through the input collection of child agents, checks if it uses the RANDOM color model. If so then
         * mutate its seed color before adding it to the environment. Additionally, checks if the child agents new location is
         * occupied, if so it sets that agents spaceTaken flag to true.
         * @param childAgents the collection of new agents
         */
        private ArrayList<Agent> placeAgents(ArrayList<Agent> childAgents) {
            for (Agent child : childAgents) {
                diagnostics.addToAgentsBornLastStep(child.getAttributes().getID(), 1); // log that a new agents been born
                diagnostics.addToLogQueue("[AGENT]: " + child.getAttributes().getName() + " born.");
                child.getAttributes().calculateAttributes();
                child.setScores(agentLogic.initScores((child)));
                agentLogic.clearSpace(child);
                environment.setOccupant(child);
                if (child.getAttributes().getColorModel().equals(ColorModel.RANDOM)) { // Check if the agent is using the random color model and handle accordingly
                    child.getAttributes().mutateSeedColor(5);
                }
            }
            return childAgents;
        }

        /**
         * Initialises the agents scores with its attributes.
         * <p>
         * As mutations are handled in this class, calculated attributes must be handled here too. As scores
         * require some of the agents calculated attributes, their initialisation needs to be handled here to.
         */
        private Scores initScores(Agent agent) {
            Scores scores = agent.getScores();
            scores.setMaxEnergy(agent.getAttributes().getEnergyCapacity());
            scores.setMaxAge(agent.getAttributes().getLifespan());
            scores.setAge(0);
            scores.setCreationCounter(agent.getAttributes().getCreationAge());
            return scores;
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
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ":Born and size mutated by: " + (attributes.getSize() - oldSize) + ".");
                }
            }
            else if (ran < 9) {
                // Mutate range
                int oldRange = attributes.getRange();
                attributes.setRange(Math.min(Math.max(attributes.getRange() + mutationMagnitude(), 0), 5));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ":Born and range mutated by: " + (attributes.getRange() - oldRange) + ".");
                }
            }
            else {
                // Mutate creationAmount
                int oldCreationSize = attributes.getCreationSize();
                attributes.setCreationSize(Math.min(Math.max(attributes.getCreationSize() + mutationMagnitude(), 1), 8));
                if (diagnosticsVerbosity >= 1) {
                    diagnostics.addToLogQueue("[AGENT]: " + attributes.getName() + ":Born and litter Size mutated by: " + (attributes.getCreationSize() - oldCreationSize) + ".");
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
                            && !environment.getTile(X, Y).isTerrain())
                    {
                        AgentVision av = environment.getTileView(new Location(X, Y));
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
                possibleDecisions.add(motivation.run(agentVision, agent.getAttributes(), agent.getScores()));
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
            terrainSettings = new TerrainSettings(2, 5, 5000, 500, 400, 10000, 15,2, 2, 4, 1, true);
        }

        /**
         * Sets all tiles in the environment to terrain.
         */
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

        public void placeCave() {
            random.setSeed(Instant.now().toEpochMilli());
            generateCave();
        }

        public void placeVariableCave() {
            random.setSeed(Instant.now().toEpochMilli());
            generateVariableCave();
        }

        public void generateCave() {
            for (int j = 0; j < terrainSettings.getTerrainAmount(); j++) {
                int dx = 0;
                int dy = 0;
                int dx2 = 0;
                int dy2 = 0;
                Location location = new Location(environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3), environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3));
                for (int i = 0; i < terrainSettings.getCaveLength(); i++) {
                    if (random.nextInt(10000) < terrainSettings.getBendDensity()) {
                        dx2 = getPosNegRandom(terrainSettings.getCaveSize()+1);
                        dy2 = getPosNegRandom(terrainSettings.getCaveSize()+1);
                    }
                    dx = dx + dx2;
                    dy = dy + dy2;
                    Location caveLocation = new Location(location.getX() + dx, location.getY() + dy);
                    if (environment.isLocationOnGrid(caveLocation)) {
                        if (random.nextInt(10000) < terrainSettings.getLineDensity()) {
                            generateCircleRockCluster(terrainSettings.getRockSize(), terrainSettings.getCaveSize(), terrainSettings.getCaveDensity(), caveLocation);
                        }
                    } else {
                        dx = dx - dx2;
                        dy = dy - dy2;
                    }
                }
            }
        }

        public void generateVariableCave() {
            for (int j = 0; j < terrainSettings.getTerrainAmount(); j++) {
                int dx = 0;
                int dy = 0;
                int dx2 = 0;
                int dy2 = 0;
                Location location = new Location(environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3), environment.getSize()/6 + random.nextInt(environment.getSize() - environment.getSize()/3));
                int caveSize = terrainSettings.getLowerCaveSize();
                for (int i = 0; i < terrainSettings.getCaveLength()/(terrainSettings.getCaveWave()*2); i++) {
                    int cw = random.nextInt(terrainSettings.getCaveWave());
                    for (int c = -cw; c < cw; c++) {
                        if (c < 0) {
                            caveSize += terrainSettings.getUpperCaveSize()/cw;
                        }
                        else {
                            caveSize -= terrainSettings.getUpperCaveSize()/cw;
                        }
                        if (random.nextInt(10000) < terrainSettings.getBendDensity()) {
                            dx2 = getPosNegRandom(terrainSettings.getLowerCaveSize() + 1);
                            dy2 = getPosNegRandom(terrainSettings.getLowerCaveSize() + 1);
                        }
                        dx = dx + dx2;
                        dy = dy + dy2;
                        Location caveLocation = new Location(location.getX() + dx, location.getY() + dy);
                        if (environment.isLocationOnGrid(caveLocation)) {
                            if (random.nextInt(10000) < terrainSettings.getLineDensity()) {
                                generateCircleRockCluster(terrainSettings.getRockSize(), caveSize, terrainSettings.getCaveDensity(), caveLocation);
                            }
                        } else {
                            dx = dx - dx2;
                            dy = dy - dy2;
                        }
                    }
                }
            }
        }

        public void generateGraphBranchCave(Location seedLocation, int dx, int dy, int level, boolean firstLevel) {
            if (!firstLevel) {
                generateCircleRockCluster(terrainSettings.getUpperCaveSize(), terrainSettings.getUpperCaveSize() + random.nextInt(terrainSettings.getCavernSize() - terrainSettings.getUpperCaveSize()), terrainSettings.getCaveDensity(), seedLocation);
            }
            Location caveLocation = new Location(seedLocation.getX() + dx, seedLocation.getY() + dy);
            for (int j = 0; j < terrainSettings.getTerrainAmount(); j++) {
                int dx2 = 0;
                int dy2 = 0;
                int caveSize = terrainSettings.getLowerCaveSize();
                int straightCounter = 0;
                for (int i = 0; i < terrainSettings.getCaveLength()/(terrainSettings.getCaveWave()*2); i++) {
                    int cw = random.nextInt(terrainSettings.getCaveWave());
                    for (int c = -cw; c < cw; c++) {
                        if (c < 0) {
                            caveSize += terrainSettings.getUpperCaveSize()/cw;
                        }
                        else {
                            caveSize -= terrainSettings.getUpperCaveSize()/cw;
                        }
                        if (straightCounter > terrainSettings.getCavernSize() && random.nextInt(10000) < terrainSettings.getBendDensity()) {
                            if (random.nextInt(2) == 0) {
                                dx2 = getPosNegRandom(terrainSettings.getRockSize());
                                dx = dx2;

                            }
                            else {
                                dy2 = getPosNegRandom(terrainSettings.getRockSize());
                                dy = dy2;
                            }
                        }
                        caveLocation.setX(caveLocation.getX() + dx);
                        caveLocation.setY(caveLocation.getY() + dy);
                        if (environment.isLocationOnGrid(caveLocation)) {
                            if (random.nextInt(10000) < terrainSettings.getLineDensity()) {
                                generateCircleRockCluster(terrainSettings.getRockSize(), caveSize, terrainSettings.getCaveDensity(), caveLocation);
                            }
                        } else {
                            dx = dx - dx2;
                            dy = dy - dy2;
                        }
                        straightCounter++;
                    }
                }
            }
            if (level > 0) {
                //System.out.println("Generating subgraph at - " + caveLocation.getX() + "," + caveLocation.getY()+ " : " + level);
                generateGraphBranchCave(caveLocation, getPosNegRandom(terrainSettings.getRockSize()), getPosNegRandom(terrainSettings.getRockSize()), level-1, false);
                //System.out.println("Finished generating subgraph at - " + caveLocation.getX() + "," + caveLocation.getY()+ " : " + level);

            }
        }

        public void generateGraphCave() {
            Location seedLocation = new Location(environment.getSize()/2, environment.getSize()/2);
            generateCircleRockCluster(terrainSettings.getRockSize(), terrainSettings.getCavernSize(), terrainSettings.getCaveDensity(), seedLocation);
            int bx = 1;
            int by = 1;
            for (int i = -bx; i <= bx; i++) {
                for (int j = -by; j <= by; j++) {
                    if (random.nextInt(4) < 3 ) {
                        generateGraphBranchCave(seedLocation, i, j, 5, true);
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

