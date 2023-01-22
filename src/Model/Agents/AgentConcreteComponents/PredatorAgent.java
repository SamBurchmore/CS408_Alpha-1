package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.*;
import Model.Agents.AgentStructs.AgentUpdate;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PredatorAgent extends BaseAgent {

    public PredatorAgent(Location location_, Color agentColor_, Model.Agents.AgentInterfaces.Reaction reaction_, Vision vision_, Attributes attributes_, Scores scores_) {
        super(location_, agentColor_, reaction_, vision_, attributes_, scores_);
    }

    public PredatorAgent(Location location_, Agent parentA, Agent parentB) {
        super(location_, parentA, parentB);
    }

    @Override
    public AgentModelUpdate run(Environment environment) {

        AgentUpdate agentUpdate = super.liveDay(environment);
        if (super.isDead()) {
            return new AgentModelUpdate(null, new ArrayList<Agent>());
        }
        ArrayList<Agent> childAgents = new ArrayList<>();

        if (agentUpdate.isNull()) {
            if (agentUpdate.getAgentAction().equals(AgentAction.MOVE)) {
                super.move(agentUpdate.getLocation());
            }
            if (agentUpdate.getAgentAction().equals(AgentAction.CREATE) && !environment.emptyAdjacent(this.getLocation()).isEmpty()) {
                childAgents = this.create(agentUpdate.getLocation(), environment);
            }
            if (agentUpdate.getAgentAction().equals(AgentAction.ATTACK)) {
                this.predate();
                super.move(agentUpdate.getLocation());
            }
        }
        return new AgentModelUpdate(this, childAgents);
    }

    @Override
    public Agent combine(Agent parentB, Location childLocation) {
        super.getScores().setCreationCounter(super.getScores().getCreationDelay());
        return new PredatorAgent(childLocation, this, parentB);
    }

    @Override
    public Object copy() {
        return null;
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment_) {
        ArrayList<Location> childLocations = environment_.emptyAdjacent(super.getLocation(), AgentType.PREY);
        ArrayList<Agent> childAgents = new ArrayList<>();
        Collections.shuffle(childLocations);
        for (Location childLocation : childLocations.subList(0, childLocations.size() / 2)) {
            Agent child = this.combine(environment_.getTile(parentBLocation).getOccupant(), childLocation);
            child.getScores().setHunger(child.getScores().getMAX_HUNGER() / 4);
            childAgents.add(child);
        }
        return childAgents;
    }

    public void predate() {
        super.getScores().setHunger(super.getScores().getHunger() + super.getAttributes().getEatAmount());
    }

}
