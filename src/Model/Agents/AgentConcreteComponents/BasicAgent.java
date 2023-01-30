package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

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

}
