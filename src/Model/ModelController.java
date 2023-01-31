package Model;

import java.awt.*;
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

    private final int environmentSize;

    private Random randomGen;

    public ModelController(int size, int startingEnergyLevel, int minEnergyLevel, int maxEnergyLevel, double energyRegenChance, int energyRegenAmount){
        this.environment = new Environment(size, startingEnergyLevel, maxEnergyLevel, minEnergyLevel, energyRegenChance, energyRegenAmount);
        this.randomGen = new Random();
        this.agentList = new ArrayList<>();
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
//        for (int i = 0; i < agentEditor.getActiveAgents().size(); i++) {
//            if (diagnostics.getAgentPopulations()[i] <= 0) {
//                diagnostics.addToLogQueue("[AGENT]: Agent " + agentEditor.getAgent(i).getAttributes().getCode() + " has gone extinct.");
//            }
//        }
        IntStream.range(0, environmentSize * environmentSize).parallel().forEach(i->{
            if (randomGen.nextInt(10000) / 100.0 < environment.getEnergyRegenChance()) {
                environment.modifyTileFoodLevel(environment.getGrid()[i].getLocation(), environment.getEnergyRegenAmount());
            }
        });
        agentList = aliveAgents;
    }

    public void clear() {
        IntStream.range(0, environmentSize * environmentSize).sequential().forEach(i->{
            EnvironmentTile current_wt = environment.getGrid()[i];
            current_wt.setOccupant(null);
        });
        agentList = new ArrayList<Agent>();
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


}
