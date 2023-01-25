package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentType;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseAgent implements Agent {

    private Location location;
    private Reaction reaction = null;
    private Vision vision = null;
    private Attributes attributes;
    private Scores scores = null;

    public BaseAgent(Location location_, Reaction reaction_, Vision vision_, Attributes attributes_, Scores scores_) {
        this.location = location_;
        this.reaction = reaction_;
        this.vision = vision_;
        this.attributes = attributes_;
        this.scores = scores_;
    }

    public BaseAgent(Location location_, Agent parent_a, Agent parent_b) {
        this.location = location_;
        this.reaction = new PreyReaction(new PreyMotivations());
        this.vision = new BasicVision();
        this.attributes = new BasicAttributes(parent_a.getAttributes(), parent_b.getAttributes());
        this.scores = new BasicScores(parent_a.getScores().getMAX_HUNGER(), parent_a.getScores().getMAX_HEALTH(), 0, parent_a.getScores().getMAX_HUNGER(), parent_a.getScores().getMAX_HEALTH(), parent_a.getScores().getMAX_AGE(), parent_a.getScores().getCreationDelay());
        this.scores.setCreationCounter(parent_a.getScores().getCreationDelay());
    }

    @Override
    public AgentModelUpdate run(Environment environment_) {
        return new AgentModelUpdate(this, null);
    }

    @Override
    public AgentDecision liveDay(Environment environment) {
        this.getScores().setHunger((this.getScores().getHunger() - attributes.getSize()));
        this.getScores().setAge(this.getScores().getAge()+1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));

        ArrayList<AgentVision> agentSight = this.getVision().lookAround(environment, this.getLocation(), this.getAttributes().getVisionRange(), this.getAttributes().getMovementRange());
        return this.getReaction().react(agentSight, this.getAttributes(), this.getScores());
    }

    @Override
    public boolean isDead() {
        return this.getScores().getHunger() <= 0 || this.getScores().getAge() >= this.getScores().getMAX_AGE();
    }

    @Override
    public void move(Location newLocation) {
        this.setLocation(newLocation);
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment_) {
        ArrayList<Location> childLocations = environment_.emptyAdjacent(this.location);
        ArrayList<Agent> childAgents = new ArrayList<>();
        if (childLocations.size() > 0) {
            Collections.shuffle(childLocations);
            Location childLocation = childLocations.get(0);
            Agent child = this.combine(environment_.getTile(parentBLocation).getOccupant(), childLocation);
            childAgents.add(child);
        }
        return childAgents;
    }

    @Override
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public void setAttributes(Attributes attributes_) {
        this.attributes = attributes_;
    }

    @Override
    public void setLocation(Location location_) {
        this.location = location_;
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
    public void setReaction(Reaction reaction_) {
        this.reaction = reaction_;
    }

    @Override
    public Vision getVision() {
        return this.vision;
    }

    @Override
    public void setVision(Vision vision_) {
        this.vision = vision_;
    }

    @Override
    public Scores getScores() {
        return this.scores;
    }

    @Override
    public void setScores(Scores scores_) {
        this.scores = scores_;
    }

}
