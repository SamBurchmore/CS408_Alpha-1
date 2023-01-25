package Model;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

import Model.AgentEditor.AgentEditor;
import Model.Agents.AgentConcreteComponents.BasicAgent;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentStructs.AgentModelUpdate;
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

    private ArrayList<Agent> agentList;
    // This is where all diagnostic data on the simulation is stored.
    private Diagnostics diagnostics;


    private int foodRegenAmount;
    double foodRegenChance;
    private int maxFood;
    private int minFood;
    private final int worldSize;


    private Random randomGen;

    public ModelController(int size, int starting_food_level, int minFoodLevel, int maxFoodLevel, double foodRegenChance, int foodRegenAmount){
        this.environment = new Environment(size, starting_food_level, maxFoodLevel, minFoodLevel);
        this.randomGen = new Random();
        this.agentList = new ArrayList<>();
        this.worldSize = size;
        this.foodRegenChance = foodRegenChance;
        this.minFood = minFoodLevel;
        this.maxFood = maxFoodLevel;
        this.foodRegenAmount = foodRegenAmount;
        this.diagnostics = new Diagnostics(this.worldSize);
        this.agentEditor = new AgentEditor();
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
        ArrayList<Agent> activeAgents = agentEditor.getActiveAgents();
        IntStream.range(0, worldSize*worldSize).parallel().forEach(i->{
                if (this.randomGen.nextInt(100) < density) {
                    int agentIndex = randomGen.nextInt(activeAgents.size());
                    BasicAgent agent;
                    for (int j = 0; j < activeAgents.size(); j++) {
                        if (j == agentIndex) {
                            agent = (BasicAgent) agentEditor.getAgent(j).copy();
                            EnvironmentTile wt = environment.getGrid()[i];
                            agent.setLocation(wt.getLocation());
                            wt.setOccupant(agent);
                            agentList.add(agent);
                        }
                    }
                }
        });
    }

    public void cycle() {
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
        }
        //System.out.println("Agent Cycle took: " + ( time - System.currentTimeMillis()) / -1000);
        IntStream.range(0, worldSize*worldSize).parallel().forEach(i->{
            if (randomGen.nextInt(10000) / 100.0 < foodRegenChance) {
                environment.modifyTileFoodLevel(environment.getGrid()[i].getLocation(), foodRegenAmount);
            }
        });

        //System.out.println("Environment Cycle took: " + ( time - System.currentTimeMillis()) / -1000);
        agentList = aliveAgents;
    }

    public void clear() {
        IntStream.range(0, worldSize*worldSize).parallel().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<Agent>();
    }

    public void setEnvironmentMaxFoodLevel(int newMax) {
        this.environment.setMaxFoodLevel(newMax);
    }

    public void setEnvironmentMinFoodLevel(int newMin) {
        this.environment.setMinFoodLevel(newMin);
    }

    public void setFoodRegenAmount(int foodRegenAmount) {
        this.foodRegenAmount = foodRegenAmount;
    }

    public void setFoodRegenChance(double foodRegenChance) {
        this.foodRegenChance = foodRegenChance;
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
            if (wt_iterator.next().getOccupant().getAttributes().getCode() == agent.getAttributes().getCode()) {
                count++;
            }
        }
        return count;
    }

    public AgentEditor getAgentEditor() {
        return agentEditor;
    }

    public void setAgentEditor(AgentEditor agentEditor) {
        this.agentEditor = agentEditor;
    }
}
