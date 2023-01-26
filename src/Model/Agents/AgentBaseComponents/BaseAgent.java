package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseAgent implements Agent {

    private Location location;
    private Reaction reaction;
    private Vision vision;
    private Attributes attributes;
    private Scores scores;

    public BaseAgent(Location location, Reaction reaction, Vision vision, Attributes attributes, Scores scores) {
        this.location = location;
        this.reaction = reaction;
        this.vision = vision;
        this.attributes = attributes;
        this.scores = scores;
    }

    public BaseAgent(Location location, Agent parent_a, Agent parent_b) {
        this.location = location;
        this.reaction = new PreyReaction(new PreyMotivations());
        this.vision = new BasicVision();
        this.attributes = new BasicAttributes(parent_a.getAttributes(), parent_b.getAttributes());
        this.scores = new BasicScores(parent_a.getAttributes().getEnergyCapacity(), parent_a.getScores().getMAX_HEALTH(), 0, parent_a.getAttributes().getEnergyCapacity(), parent_a.getScores().getMAX_HEALTH(), parent_a.getAttributes().getLifespan(), parent_a.getAttributes().getCreationDelay());
        this.scores.setCreationCounter(parent_a.getAttributes().getCreationAge());
    }

    @Override
    public AgentModelUpdate run(Environment environment) {
        return new AgentModelUpdate(this, null);
    }

    @Override
    public AgentDecision liveDay(Environment environment) {
        this.getScores().setHunger((this.getScores().getHunger() - this.getAttributes().getSize()));
        this.getScores().setAge(this.getScores().getAge()+1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));

        ArrayList<AgentVision> agentSight = this.getVision().lookAround(environment, this.getLocation(), this.getAttributes().getVisionRange(), this.getAttributes().getMovementRange());
        return this.getReaction().react(agentSight, this.getAttributes(), this.getScores());
    }

    @Override
    public boolean isDead() {
        return this.getScores().getHunger() <= 0 || this.getScores().getAge() >= this.getAttributes().getLifespan();
    }

    @Override
    public void move(Location newLocation) {
        this.setLocation(newLocation);
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment) {
        ArrayList<Location> childLocations = environment.emptyAdjacent(this.getLocation());
        ArrayList<Agent> childAgents = new ArrayList<>();
        Collections.shuffle(childLocations);
        for (Location childLocation : childLocations.subList(0, Math.min(childLocations.size(), this.getAttributes().getCreationAmount()) )) {
            Agent child = this.combine(environment.getTile(parentBLocation).getOccupant(), childLocation);
            childAgents.add(child);
        }
        return childAgents;
    }

    @Override
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Reaction getReaction() {
        return this.reaction;
    }

    @Override
    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    @Override
    public Vision getVision() {
        return this.vision;
    }

    @Override
    public void setVision(Vision vision) {
        this.vision = vision;
    }

    @Override
    public Scores getScores() {
        return this.scores;
    }

    @Override
    public void setScores(Scores scores) {
        this.scores = scores;
    }

}
