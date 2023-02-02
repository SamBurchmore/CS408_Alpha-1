package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Environment.Location;

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
