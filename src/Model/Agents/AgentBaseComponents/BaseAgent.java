package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.*;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseAgent implements Agent {

    private Location location;
    private Attributes attributes;
    private Scores scores;
    private ArrayList<Motivation> motivations;

    private boolean hasBeenEaten; // A flag which lets the model controller know this agents been eaten.

    public BaseAgent(Location location, Attributes attributes, Scores scores, ArrayList<Motivation> motivations) {
        this.location = location;
        this.attributes = attributes;
        this.scores = scores;
        this.motivations = motivations;
        hasBeenEaten = false;
    }

    public BaseAgent(Location location, Attributes attributes, ArrayList<Motivation> motivations) {
        this.location = location;
        this.attributes = attributes;
        this.scores = new BasicScores(attributes.getEnergyCapacity(), 0, attributes.getEnergyCapacity(), 0, attributes.getLifespan(), attributes.getCreationDelay());
        this.motivations = motivations;
        hasBeenEaten = false;
    }

    public BaseAgent(Location location, Agent parentA, Agent parentB) {
        this.location = location;
        this.attributes = new BasicAttributes(parentA.getAttributes(), parentB.getAttributes());
        this.motivations = parentA.copyMotivations();
        this.scores = new BasicScores(parentA.getAttributes().getEnergyCapacity(), 0, parentA.getAttributes().getEnergyCapacity(), parentA.getScores().getMaxHealth(), parentA.getAttributes().getLifespan(), parentA.getAttributes().getCreationAge());
        this.scores.setCreationCounter(parentA.getAttributes().getCreationAge());
        hasBeenEaten = false;
    }

    @Override
    public void liveDay() {
        this.getScores().setEnergy((this.getScores().getEnergy() - this.getAttributes().getSize()));
        this.getScores().setAge(this.getScores().getAge()+1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));
    }

    @Override
    public boolean isDead() {
        return this.getScores().getEnergy() <= 0 || this.getScores().getAge() >= this.getAttributes().getLifespan();
    }

    @Override
    public void move(Location newLocation) {
        this.setLocation(newLocation);
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
    public Scores getScores() {
        return this.scores;
    }

    @Override
    public void setScores(Scores scores) {
        this.scores = scores;
    }

    @Override
    public int graze(EnvironmentTile environmentTile) {
        if (environmentTile.getEnergyLevel() <= 0) {
            return 0; // If there's no energy, environment loses nothing and agent gains no energy.
        }
        if (environmentTile.getEnergyLevel() >= getAttributes().getEatAmount()) {
            getScores().setEnergy(getScores().getEnergy() + getAttributes().getEatAmount()); // If there's more food than the agents eat amount, environment loses agents eat amount and agent gets it
            return getAttributes().getEatAmount();
        }
        getScores().setEnergy(getScores().getEnergy() + environmentTile.getEnergyLevel()); // If there's less food than the agents eat amount, environment loses all and agent gains it.
        return environmentTile.getEnergyLevel();
    }

    @Override
    public void predate(Attributes preyAttributes) {
        getScores().setEnergy(getScores().getEnergy() + preyAttributes.getSize() + getAttributes().getEatAmount());
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment) {
        ArrayList<Location> childLocations = environment.emptyAdjacent(this.getLocation());
        ArrayList<Agent> childAgents = new ArrayList<>();
        if (!childLocations.isEmpty()) {
            Collections.shuffle(childLocations);
            for (Location childLocation : childLocations.subList(0, Math.min(childLocations.size(), this.getAttributes().getCreationAmount()))) {
                Agent child = combine(environment.getTile(parentBLocation).getOccupant(), childLocation);
                childAgents.add(child);
            }
        }
        getScores().setCreationCounter(getAttributes().getCreationDelay());
        return childAgents;
    }

    public Agent combine(Agent parentB, Location childLocation) {
        getScores().setCreationCounter(getScores().getCreationDelay());
        Agent newAgent = new BasicAgent(childLocation, this, parentB);

        getScores().setEnergy(getScores().getEnergy() - 1);
        newAgent.getScores().setEnergy(getAttributes().getEnergyCapacity() / getAttributes().getCreationAmount());

        return newAgent;
    }

    @Override
    public ArrayList<Motivation> getMotivations() {
        return motivations;
    }

    @Override
    public void setMotivations(ArrayList<Motivation> motivations) {
        this.motivations = motivations;
    }

    @Override
    public ArrayList<Motivation> copyMotivations() {
        ArrayList<Motivation> motivations = new ArrayList<>();
        for (Motivation motivation : getMotivations()) {
            motivations.add(motivation.copy());
        }
        return motivations;
    }

    @Override
    public boolean isEaten() {
        return hasBeenEaten;
    }

    @Override
    public void setBeenEaten() {
        hasBeenEaten = true;
    }



}
