package Model;

import java.awt.image.BufferedImage;
import java.util.*;

import Model.Agents.AgentFactory;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentType;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;

/**
 * This class will be the main controller for the simulation. It will handle running the agents, moving them, removing, and adding them to the grid when needed.
 */
public class ModelController {

    // The data structure which represents the world.
    private Environment environment;
    // We store the grid size here as its used in a lot of logical operations within this class.
    final int worldSize;

    private ArrayList<Agent> agentList;
    // This is where all diagnostic data on the simulation is stored.
    private Diagnostics diagnostics;

    Random randomGen;

    public ModelController(int size_, int starting_food_level, int minFoodLevel, int maxFoodLevel){
        this.environment = new Environment(size_, starting_food_level, maxFoodLevel, minFoodLevel);
        this.worldSize = size_;
        this.randomGen = new Random();
        this.agentList = new ArrayList<>();
        this.diagnostics = new Diagnostics(this.worldSize);
    }

    /**
     * This method populates the WorldGrid object with predator and prey agents based on the input ratio.
     * So if they are both one, then the ratio will be 1:1.
     * The total number of agents is decided by the density parameter.
     * Agents are populated pseudo randomly.
     * @param predator_percent The percentage of agents to be of predators. The rest will be prey.
     * @param density The percentage of tiles to ber occupied.
     */
    // TODO - implement predator prey ratio so that both controls work
    public void populate(int predator_percent, int density) {
        AgentFactory agentFactory = new AgentFactory();
        for (Iterator<EnvironmentTile> wt_iterator = this.environment.iterator(); wt_iterator.hasNext();) {
            if (this.randomGen.nextInt(100) < density) {
                if (this.randomGen.nextInt(100) < predator_percent) {
                    EnvironmentTile wt = wt_iterator.next();
                    Agent newAgent = agentFactory.createAgent(AgentType.PREDATOR, wt.getLocation());
                    wt.setOccupant(newAgent);
                    agentList.add(newAgent);
                }
                else  {
                    EnvironmentTile wt = wt_iterator.next();
                    Agent newAgent = agentFactory.createAgent(AgentType.PREY, wt.getLocation());
                    wt.setOccupant(newAgent);
                    agentList.add(newAgent);
                }
            }
            else {
                wt_iterator.next();
            }
        }
    }

    public void cycle() {
        int agent0Count = 0;
        int agent1Count = 0;
        ArrayList<Agent> aliveAgents = new ArrayList<>();
        for (Agent currentAgent : agentList) {
            Location oldLocation = currentAgent.getLocation();
            AgentModelUpdate agentModelUpdate = currentAgent.run(environment);
            environment.setTileAgent(oldLocation, null);
            if (agentModelUpdate.getAgent() != null) {
                environment.setTileAgent(agentModelUpdate.getAgent());
                environment.modifyTileFoodLevel(agentModelUpdate.getAgent().getLocation(), -agentModelUpdate.getEatAmount());
                aliveAgents.add(agentModelUpdate.getAgent());
            }
            if (!agentModelUpdate.getChildAgents().isEmpty()) {
                for (Agent childAgent : agentModelUpdate.getChildAgents()) {
                    environment.setTileAgent(childAgent);
                    aliveAgents.add(childAgent);
                }
            }
            if (currentAgent.getAttributes().getType().equals(AgentType.PREDATOR)) {
                agent0Count++;
            }
            else {
                agent1Count++;
            }
        }
        for (Iterator<EnvironmentTile> wt_iterator = this.environment.iterator(); wt_iterator.hasNext();) {
            if (randomGen.nextInt(worldSize*worldSize) > worldSize*worldSize- (worldSize * 2)) {
                environment.modifyTileFoodLevel(wt_iterator.next().getLocation(), 1);
            }
            else {
                wt_iterator.next();
            }

        }
        agentList = aliveAgents;
        this.diagnostics.setAgent0Count(agent0Count);
        this.diagnostics.setAgent1Count(agent1Count);
    }

    public void clear() {
        for (Iterator<EnvironmentTile> wt_iterator = this.environment.iterator(); wt_iterator.hasNext();) {
            EnvironmentTile current_wt = wt_iterator.next();
            current_wt.setOccupant(null);
        }
        agentList = new ArrayList<Agent>();
    }

    public void setEnvironmentMaxFoodLevel(int newMax) {
        this.environment.setMaxFoodLevel(newMax);
    }

    public void setEnvironmentMinFoodLevel(int newMin) {
        this.environment.setMinFoodLevel(newMin);
    }

    public Diagnostics getDiagnostics() {
        return this.diagnostics;
    }

    public BufferedImage getEnvironmentImage(int scale) {
        return this.environment.toBufferedImage(scale);
    }

    public int getRandomDecision(int things) {
        return this.randomGen.nextInt(things);
    }

    public int countAgents(Agent agent) {
        int count = 0;
        for (Iterator<EnvironmentTile> wt_iterator = this.environment.iterator(); wt_iterator.hasNext();) {
            if (wt_iterator.next().getOccupant().getAttributes().getType().equals(agent.getAttributes().getType())) {
                count++;
            }
        }
        return count;
    }

}
