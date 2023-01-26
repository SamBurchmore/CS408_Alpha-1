package Model;

import java.util.ArrayList;

public class Diagnostics {

    final private int activeAgentsNumber = 8;

    String[] agentNames; // The names of the agents
    Integer[] agentPopulations; // The size of each agent population
    Double[] averagePopulationsEnergy; // The average percent of max energy in each agent population
    Double[] averagePopulationsLifespan; // The average percent of max age in each agent population

    public Diagnostics() {
        agentNames = new String[]{"Agent 1", "Agent 2", "Agent 3", "Agent 4", "Agent 5", "Agent 6", "Agent 7", "Agent 8"};
        agentPopulations = new Integer[]{0,0,0,0,0,0,0,0};
        averagePopulationsEnergy = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averagePopulationsLifespan = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
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
        return new Object[][]{agentNames, agentPopulations, calculateAverages(averagePopulationsEnergy), calculateAverages(averagePopulationsLifespan)};
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
        averagePopulationsEnergy = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        averagePopulationsLifespan = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    }

}
