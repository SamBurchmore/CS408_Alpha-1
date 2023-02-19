package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseAgent;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Environment.Location;

import java.util.ArrayList;

public class TerrainAgent extends BaseAgent {

    public TerrainAgent(Location location, Attributes attributes, Scores scores, ArrayList<Motivation> motivations) {
        super(location, attributes, scores, motivations);
    }

    public TerrainAgent(Location location, Agent parentA, Agent parentB) {
        super(location, parentA, parentB);
    }

    public TerrainAgent copy() {
        return new TerrainAgent(this.getLocation(), this, this);
    }

    public TerrainAgent(Location location, Attributes attributes, ArrayList<Motivation> motivations) {
        super(location, attributes, motivations);
    }

    @Override
    public void liveDay() {
        this.getScores().setAge(this.getScores().getAge()+1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));
    }

}
