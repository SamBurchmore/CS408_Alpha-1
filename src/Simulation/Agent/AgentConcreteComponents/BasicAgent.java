package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseAgent;
import Simulation.Agent.AgentInterfaces.*;
import Simulation.Environment.Location;

import java.util.ArrayList;

public class BasicAgent extends BaseAgent {

    public BasicAgent(Location location, Attributes attributes, Scores scores, ArrayList<Motivation> motivations) {
        super(location, attributes, scores, motivations);
    }

    public BasicAgent(Location location, Agent parentA, Agent parentB) {
        super(location, parentA, parentB);
    }

    public BasicAgent copy() {
        return new BasicAgent(this.getLocation(), this, this);
    }

    public BasicAgent(Location location, Attributes attributes, ArrayList<Motivation> motivations) {
        super(location, attributes, motivations);
    }
}
