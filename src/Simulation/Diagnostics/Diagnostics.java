package Simulation.Diagnostics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;

public class Diagnostics {

    final private int activeAgentsNumber = 8;

    long step; // The current step the simulation is on. Resets to 0 when the world is cleared.

    private String[] agentNames; // The names of the agents
    private Integer[] agentPopulations; // The size of each agent population
    private Integer[] agentsBornLastStep; // How many agents where born last step
    private Double[] averagePopulationsEnergy; // The average percent of max energy in each agent population
    private Double[] averagePopulationsLifespan; // The average percent of max age in each agent population
    private Double[] averageSize;
    private Double[] averageCreationSize;
    private Double[] averageRange;

    private Integer[] lastStepsAgentPopulations; // The size of each agent population from the last step, used to calculate new agents born each step.

    private Integer maxEnvironmentEnergy;
    private Integer currentEnvironmentEnergy;

    private Integer[] extinctFlags; // A register of which agents are extinct (true) and which are not (false)

    private ArrayDeque<String> logQueue;

    public Diagnostics(int maxEnvironmentEnergy) {
        agentNames = new String[]{"Agent 1", "Agent 2", "Agent 3", "Agent 4", "Agent 5", "Agent 6", "Agent 7", "Agent 8"};
        agentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        agentsBornLastStep = new Integer[]{0,0,0,0,0,0,0,0};
        averagePopulationsEnergy = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averagePopulationsLifespan = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averageSize = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averageCreationSize = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averageRange = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        logQueue = new ArrayDeque<>();
        step = 0;
        lastStepsAgentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        extinctFlags = new Integer[]{-1, -1, -1, -1, -1, -1, -1, -1};
        this.maxEnvironmentEnergy = maxEnvironmentEnergy;
        currentEnvironmentEnergy = maxEnvironmentEnergy;
    }

    public void addToLogQueue(String logMsg) {
        logQueue.add(logMsg);
    }

    public String printLogQueue() {
        StringBuilder logString = new StringBuilder();
        for (String logMsg : logQueue) {
            logString.append(logMsg).append("\n");
        }
        logQueue.clear();
        logString.deleteCharAt(logString.length()-1);
        return logString.toString();
    }

    public boolean logMsgsInQueue() {
        return !logQueue.isEmpty();
    }

    public void iterateStep() {
        step = step + 1;
        boolean extinctionOccured = false;
        for (int i = 0; i < 8; i++) {
            if (agentPopulations[i] <= 0 && extinctFlags[i] == 0) {
                extinctFlags[i] = 1;
                extinctionOccured = true;
            }
        }
        if (extinctionOccured) {
            createExtinctAgentMsgs();
        }
    }

    public void setAgentPopulation(int index, int population) {
        agentPopulations[index] = population;
    }

    public void setAveragePopulationEnergy(int index, Double populationEnergy) {
        averagePopulationsEnergy[index] = populationEnergy;
    }

    public void setAveragePopulationLifespan(int index, Double populationAge) {
        averagePopulationsLifespan[index] = populationAge;
    }

    public void setExtinctFlags(int flag) {
        this.extinctFlags = new Integer[]{flag,flag,flag,flag,flag,flag,flag,flag};
    }

    public void setAgentName(int index, String name) {
        agentNames[index] = name;
    }

    public void addToAgentPopulation(int index, int newAgents) {
        agentPopulations[index] += newAgents;
    }

    public void addToAgentsBornLastStep(int index, int newAgents) {
        agentsBornLastStep[index] += newAgents;
    }

    public void addToAveragePopulationEnergy(int index, double energy) {
        averagePopulationsEnergy[index] += energy;
    }

    public void addToAveragePopulationLifespan(int index, double age) {
        averagePopulationsLifespan[index] += age;
    }

    public void addToAverageSize(int index, double age) {
        averageSize[index] += age;
    }

    public void addToAverageCreationSize(int index, double age) {
        averageCreationSize[index] += age;
    }

    public void addToAverageRange(int index, double age) {
        averageRange[index] += age;
    }


    public void setMaxEnvironmentEnergy(int maxEnvironmentEnergy) {
        this.maxEnvironmentEnergy = maxEnvironmentEnergy;
    }

    public void modifyCurrentEnvironmentEnergy(int modifyValue) {
        this.currentEnvironmentEnergy = Math.min(Math.max(currentEnvironmentEnergy + modifyValue, 0), maxEnvironmentEnergy);
    }

    public void resetCurrentEnvironmentEnergy() {
        currentEnvironmentEnergy = maxEnvironmentEnergy;
    }

    public Integer getCurrentEnvironmentEnergy() {
        return currentEnvironmentEnergy;
    }

    public Object[] getEnvironmentStats() {
        Object[] stats = new Object[3];
        stats[0] = maxEnvironmentEnergy;
        stats[1] = currentEnvironmentEnergy;
        stats[2] = Math.round((currentEnvironmentEnergy / (double) maxEnvironmentEnergy) * 10000) / 100.0;
        return stats;
    }

    public void addToAgentStats(int index, int population, double energy, double age, double size, double creationSize, double range) {
        addToAgentPopulation(index, population);
        addToAveragePopulationEnergy(index, energy);
        addToAveragePopulationLifespan(index, age);
        addToAverageSize(index, size);
        addToAverageCreationSize(index, creationSize);
        addToAverageRange(index, range);
    }

    public void setAgentNames(String[] agentNames) {
        for (int i = 0; i < activeAgentsNumber; i++) {
            setAgentName(i, agentNames[i]);
        }
    }

    public Object[][] getAgentStats() {
        return new Object[][]{
                agentNames,
                agentPopulations,
                calculateAverages(averagePopulationsEnergy),
                calculateAverages(averagePopulationsLifespan),
                agentsBornLastStep,
                calculateAverages(averageSize),
                calculateAverages(averageCreationSize),
                calculateAverages(averageRange)};
    }

    private Double[] calculateAverages(Double[] statistics) {
        Double[] averages = new Double[statistics.length];
        for (int i = 0; i < 8; i++) {
            averages[i] = Math.round((statistics[i] / agentPopulations[i]) * 10) / 10.0;
        }
        return averages;
    }

    public void clearAgentStats() {
        agentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        agentsBornLastStep = new Integer[]{0,0,0,0,0,0,0,0};
        averagePopulationsEnergy = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averagePopulationsLifespan = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averageSize = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averageCreationSize = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averageRange = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    }

    public void clearExtinctFlags() {
        extinctFlags = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public Integer[] getAgentPopulations() {
        return agentPopulations;
    }

    public Integer[] getAgentsBornLastStep() {
        Integer[] agentsBornLastStep = new Integer[8];
        for (int i = 0; i < lastStepsAgentPopulations.length; i++) {
            agentsBornLastStep[i] = Math.max(agentPopulations[i] - lastStepsAgentPopulations[i], 0);
        }
        return agentsBornLastStep;
    }

    private void createExtinctAgentMsgs() {
        for (int i = 0; i < activeAgentsNumber; i++) {
            if (extinctFlags[i] == 1) {
                extinctFlags[i] = 2;
                addToLogQueue("[AGENT]: " + agentNames[i] + " has gone extinct at step " + step);
            }
        }
    }

    public long getStep() {
        return step;
    }

    public void clearSteps() { step = 0; }
}
