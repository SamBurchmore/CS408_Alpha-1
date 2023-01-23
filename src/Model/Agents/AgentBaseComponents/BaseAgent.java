package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentType;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseAgent implements Agent {

    private Location location;
    private Color agentColor;
    private Reaction reaction = null;
    private Vision vision = null;
    private Attributes attributes;
    private Scores scores = null;

    public BaseAgent(Location location, Color agentColor, Reaction reaction, Vision vision, Attributes attributes, Scores scores) {
        this.location = location;
        this.agentColor = agentColor;
        this.reaction = reaction;
        this.vision = vision;
        this.attributes = attributes;
        this.scores = scores;
    }

    public BaseAgent(Location location, Agent parentA, Agent parentB) {
        this.location = location;
        this.agentColor = parentA.getColor();
        this.reaction = new BasicReaction((ArrayList<Motivations>) parentA.getReaction().getAgentMotivations().clone());
        this.vision = new BasicVision();
        this.attributes = new BasicAttributes(parentB.getAttributes(), parentB.getAttributes());
        this.scores = new BasicScores(parentA.getAttributes().getMaxEnergy(), 0, parentA.getAttributes().getMaxEnergy(), parentA.getAttributes().getMaxAge(), parentA.getAttributes().getCreationDelay());
    }

    @Override
    public AgentModelUpdate run(Environment environment_) {
        return new AgentModelUpdate(this, null);
    }

    @Override
    public void liveDay(Environment environment) {
        this.getScores().setHunger((this.getScores().getHunger() - attributes.getSize()));
        this.getScores().setAge(this.getScores().getAge()+1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));
    }

    @Override
    public boolean isDead() {
        return this.getScores().getHunger() <= 0 || this.getScores().getAge() >= this.getScores().getMaxAge();
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
    public Color getColor() {
        return this.agentColor;
    }

    @Override
    public void setColor(Color color_) {
        this.agentColor = color_;
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
