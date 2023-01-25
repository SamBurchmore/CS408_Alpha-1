package Model;

import java.util.ArrayList;

public class Diagnostics {

    private int environmentSize;
    final private int activeAgentsNumber = 8;

    ArrayList<Integer> agentPopulations; // The size of each agent population
    ArrayList<Integer> averagePopulationsEnergy; // The average percent of max energy in each agent population
    ArrayList<Integer> averagePopulationsLifespan; // The average percent of max age in each agent population

    public Diagnostics() {
        agentPopulations = new ArrayList<>();
        averagePopulationsEnergy = new ArrayList<>();
        averagePopulationsLifespan = new ArrayList<>();
        for (int i = 0; i < activeAgentsNumber; i++) {
            agentPopulations.add(0);
            averagePopulationsEnergy.add(0);
            averagePopulationsLifespan.add(0);
        }
    }

    public String getAgentStatStrings() {
        StringBuilder statString = new StringBuilder();
        for (int i = 0; i < activeAgentsNumber; i++) {
            statString.append("Agent ").append(i).append(": Population=").append(agentPopulations.get(i)).append(" | Avg Energy=").append(averagePopulationsEnergy.get(i) / agentPopulations.get(i)).append(" | Avg Lifespan=").append(averagePopulationsLifespan.get(i) / agentPopulations.get(i));
            statString.append("\n");
        }
        return statString.toString();
    }

    public void setEnvironmentSize(int environmentSize) {
        this.environmentSize = environmentSize;
    }

    public void setAgentPopulation(int index, int population) {
        agentPopulations.set(index, population);
    }

    public void setAveragePopulationEnergy(int index, int population) {
        averagePopulationsEnergy.set(index, population);
    }

    public void setAveragePopulationLifespan(int index, int population) {
        averagePopulationsLifespan.set(index, population);
    }

    public void addToAgentPopulation(int index, int newAgents) {
        agentPopulations.set(index, agentPopulations.get(index) + newAgents);
    }

    public void addToAveragePopulationEnergy(int index, int newValue) {
        averagePopulationsEnergy.set(index, averagePopulationsEnergy.get(index) + newValue);
    }

    public void addToAveragePopulationLifespan(int index, int newValue) {
        averagePopulationsLifespan.set(index, averagePopulationsLifespan.get(index) + newValue);
    }

    public void clearAgentStats() {
        for (int i = 0; i < activeAgentsNumber; i++) {
            agentPopulations.set(i, 0);
            averagePopulationsEnergy.set(i, 0);
            averagePopulationsLifespan.set(i, 0);
        }
    }

}
