package Model;

import java.util.ArrayDeque;

public class Diagnostics {

    final private int activeAgentsNumber = 8;

    long step; // The current step the simulation is on. Resets to 0 when the world is cleared.

    String[] agentNames; // The names of the agents
    Integer[] agentPopulations; // The size of each agent population
    Double[] averagePopulationsEnergy; // The average percent of max energy in each agent population
    Double[] averagePopulationsLifespan; // The average percent of max age in each agent population

    Integer[] lastStepsAgentPopulations; // The size of each agent population from the last step, used to calculate new agents born each step.

    Integer[] extinctFlags; // A register of which agents are extinct (true) and which are not (false)

    private ArrayDeque<String> logQueue;

    public void addToLogQueue(String logMsg) {
        logQueue.add(logMsg);
    }

    public String printLogQueue() {
            StringBuilder logString = new StringBuilder();
            for (String logMsg : logQueue) {
                logString.append(logMsg).append("\n");
            }
            logQueue.clear();
            return logString.toString();
    }

    public boolean logMsgsInQueue() {
        return !logQueue.isEmpty();
    }

    public Diagnostics() {
        agentNames = new String[]{"Agent 1", "Agent 2", "Agent 3", "Agent 4", "Agent 5", "Agent 6", "Agent 7", "Agent 8"};
        agentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        averagePopulationsEnergy = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averagePopulationsLifespan = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        logQueue = new ArrayDeque<>();
        step = 0;
        lastStepsAgentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        extinctFlags = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public void update() {
        System.out.println(step);
        step = step + 1;
        System.out.println(step);
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

    public void setAgentName(int index, String name) {
        agentNames[index] = name;
    }

    public void addToAgentPopulation(int index, int newAgents) {
        agentPopulations[index] += newAgents;
    }

    public void addToAveragePopulationEnergy(int index, double energy) {
        averagePopulationsEnergy[index] += energy;
    }

    public void addToAveragePopulationLifespan(int index, double age) {
        averagePopulationsLifespan[index] += age;
    }

    public void addToStats(int index, int population, double energy, double age) {
        addToAgentPopulation(index, population);
        addToAveragePopulationEnergy(index, energy);
        addToAveragePopulationLifespan(index, age);
    }

    public void setAgentNames(String[] agentNames) {
        for (int i = 0; i < activeAgentsNumber; i++) {
            setAgentName(i, agentNames[i]);
        }
    }

    public Object[][] getAgentStats() {
        return new Object[][]{agentNames, agentPopulations, calculateAverages(averagePopulationsEnergy), calculateAverages(averagePopulationsLifespan), getAgentsBornLastStep()};
    }

    private Double[] calculateAverages(Double[] statistics) {
        Double[] averages = new Double[statistics.length];
        for (int i = 0; i < 8; i++) {
            averages[i] = Math.round((statistics[i] / agentPopulations[i]) * 10) / 10.0;
        }
        return averages;
    }

    public void clearAgentStats() {
        lastStepsAgentPopulations = agentPopulations;
        agentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        averagePopulationsEnergy = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averagePopulationsLifespan = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
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
                addToLogQueue("[AGENTS]: " + agentNames[i] + " has gone extinct at step " + step);
            }
        }
    }

    public long getStep() {
        return step;
    }

    public void clearSteps() { step = 0; }
}
