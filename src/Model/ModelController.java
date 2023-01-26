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
        this.diagnostics = new Diagnostics();
        this.agentEditor = new AgentEditor();
    }

    public void populate(int density) {
        ArrayList<Agent> activeAgents = agentEditor.getActiveAgents();
        IntStream.range(0, worldSize*worldSize).sequential().forEach(i->{
                if (this.randomGen.nextInt(100) < density && !environment.getGrid()[i].isOccupied()) {
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
        diagnostics.clearAgentStats();
        ArrayList<Agent> aliveAgents = new ArrayList<>();
        for (Agent currentAgent : agentList) {
            Location oldLocation = currentAgent.getLocation();
            AgentModelUpdate agentModelUpdate = currentAgent.run(environment);
            environment.setTileAgent(oldLocation, null);
            if (agentModelUpdate.getAgent() != null) {
                environment.setTileAgent(agentModelUpdate.getAgent());
                environment.modifyTileFoodLevel(agentModelUpdate.getAgent().getLocation(), -agentModelUpdate.getEatAmount());
                aliveAgents.add(agentModelUpdate.getAgent());
                diagnostics.addToStats(agentModelUpdate.getAgent().getAttributes().getCode(), 1, agentModelUpdate.getAgent().getScores().getHunger(), agentModelUpdate.getAgent().getScores().getAge());
            }
            if (!agentModelUpdate.getChildAgents().isEmpty()) {
                for (Agent childAgent : agentModelUpdate.getChildAgents()) {
                    environment.setTileAgent(childAgent);
                    aliveAgents.add(childAgent);
                }
            }
        }
        IntStream.range(0, worldSize*worldSize).parallel().forEach(i->{
            if (randomGen.nextInt(10000) / 100.0 < foodRegenChance) {
                environment.modifyTileFoodLevel(environment.getGrid()[i].getLocation(), foodRegenAmount);
            }
        });
        agentList = aliveAgents;
    }

    public void clear() {
        IntStream.range(0, worldSize*worldSize).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<Agent>();
    }

    public void replenishEnvironmentEnergy() {
        IntStream.range(0, worldSize*worldSize).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setFoodLevel(maxFood);
        });
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

    public AgentEditor getAgentEditor() {
        return agentEditor;
    }

    public void updateAgentNames() {
        diagnostics.setAgentNames(agentEditor.getAgentNames());
    }

    public void setAgentEditor(AgentEditor agentEditor) {
        this.agentEditor = agentEditor;
    }

}
