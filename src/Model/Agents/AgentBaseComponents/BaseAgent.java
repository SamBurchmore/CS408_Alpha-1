package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseAgent implements Agent {

    private Location location;
    private Attributes attributes;
    private Scores scores;
    private ArrayList<Motivation> motivations;

    public BaseAgent(Location location, Attributes attributes, Scores scores, ArrayList<Motivation> motivations) {
        this.location = location;
        this.attributes = attributes;
        this.scores = scores;
        this.motivations = motivations;
    }

    public BaseAgent(Location location, Agent parentA, Agent parentB) {
        this.location = location;
        this.attributes = new BasicAttributes(parentA.getAttributes(), parentB.getAttributes());
        this.motivations = parentA.getMotivations();
        this.scores = new BasicScores(parentA.getAttributes().getEnergyCapacity(), parentA.getScores().getMAX_HEALTH(), 0, parentA.getAttributes().getEnergyCapacity(), parentA.getScores().getMAX_HEALTH(), parentA.getAttributes().getLifespan(), parentA.getAttributes().getCreationDelay());
        this.scores.setCreationCounter(parentA.getAttributes().getCreationAge());
    }

    @Override
    public AgentModelUpdate run(Environment environment) {
        liveDay(); // Live a day, i.e. reduce hunger, increase age, reduce creation cooldown.
        if (isDead()) {
            return new AgentModelUpdate(null, new ArrayList<>());
        }
        ArrayList<AgentVision> agentView = lookAround(environment);
        AgentDecision agentDecision = reactToView(agentView);
        if (agentDecision.getAgentAction().equals(AgentAction.NONE)) {
            return new AgentModelUpdate(this, new ArrayList<>());
        }
        if (agentDecision.getAgentAction().equals(AgentAction.MOVE)) {
            move(agentDecision.getLocation());
            return new AgentModelUpdate(this, new ArrayList<>());
        }
        if (agentDecision.getAgentAction().equals(AgentAction.CREATE)) {
            return new AgentModelUpdate(this, create(agentDecision.getLocation(), environment));
        }
        if (agentDecision.getAgentAction().equals(AgentAction.GRAZE)) {
            move(agentDecision.getLocation());
            return new AgentModelUpdate(this, new ArrayList<>(), this.graze(environment.getTile(getLocation())));
        }
        if (agentDecision.getAgentAction().equals(AgentAction.PREDATE)) {
            predate(environment.getTile(agentDecision.getLocation()).getOccupant().getAttributes());
            move(agentDecision.getLocation());
            return new AgentModelUpdate(this, new ArrayList<>());
        }
        return new AgentModelUpdate(this, new ArrayList<>());
    }

    @Override
    public void liveDay() {
        this.getScores().setHunger((this.getScores().getHunger() - this.getAttributes().getSize()));
        this.getScores().setAge(this.getScores().getAge()+1);
        this.getScores().setCreationCounter((this.getScores().getCreationCounter() - 1));
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
        if (!childLocations.isEmpty()) {
            Collections.shuffle(childLocations);
            for (Location childLocation : childLocations.subList(0, Math.min(childLocations.size(), this.getAttributes().getCreationAmount()))) {
                Agent child = combine(environment.getTile(parentBLocation).getOccupant(), childLocation);
                childAgents.add(child);
            }
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
    public Scores getScores() {
        return this.scores;
    }

    @Override
    public void setScores(Scores scores) {
        this.scores = scores;
    }

    public Agent combine(Agent parentB, Location childLocation) {
        getScores().setCreationCounter(getScores().getCreationDelay());
        Agent newAgent = new BasicAgent(childLocation, this, parentB);

        getScores().setHunger(getScores().getHunger() - getScores().getMAX_HUNGER() / 8);
        newAgent.getScores().setHunger(newAgent.getScores().getMAX_HUNGER() / 8);

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

    public ArrayList<AgentVision> lookAround(Environment environment) {
        Location agentLocation = this.getLocation();
        int visionRange = this.getAttributes().getVisionRange();
        int agentRange = this.getAttributes().getMovementRange();
        // Generate a new ArrayList of the AgentVision object, everything the agent sees will be stored here.
        ArrayList<AgentVision> agentViews = new ArrayList<>();
        // Retrieve the agents vision attribute, lets us know how far the agent can see.
        // Now we iterate over all the surrounding tiles, adding their visible contents to the AgentVision ArrayList.
        for (int i = -visionRange; i <= visionRange; i++) {
            for (int j = -visionRange; j <= visionRange; j++) {
                int x_coord = agentLocation.getX() + i;
                int y_coord = agentLocation.getY() + j;
                // Checks the agent isn't looking outside the grid and prevents a null pointer exception // TODO - there must be a better way to do this.
                if (((x_coord < environment.getSize()) && (y_coord < environment.getSize())) && ((x_coord >= 0) && (y_coord >= 0))) {
                    AgentVision av = environment.getTileView(x_coord, y_coord);
                    if (Math.abs(i) <= agentRange && Math.abs(j) <= agentRange) {
                        av.setInRange(true);
                    }
                    else {
                        av.setInRange(false);
                    }
                    agentViews.add(av);
                }
            }
        }
        Collections.shuffle(agentViews);
        return agentViews;
    }

    public AgentDecision reactToView(ArrayList<AgentVision> agentView) {
        ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
        for (AgentVision currentAV : agentView) {
            if (currentAV.isInRange()) {
                possibleDecisions.add(reactToTile(currentAV));
            }
        }
        return getBestDecision(possibleDecisions);
    }

    public AgentDecision reactToTile(AgentVision agentVision) {
        ArrayList<AgentDecision> possibleDecisions = new ArrayList<>();
        for (Motivation motivation : motivations) {
            possibleDecisions.add(motivation.run(agentVision, attributes, scores));
        }
        return getBestDecision(possibleDecisions);
    }

    // A utility function which takes a collection of agent decisions, and returns the one with the highest decision score.
    private static AgentDecision getBestDecision(ArrayList<AgentDecision> agentDecisions) {
        AgentDecision finalDecision = new AgentDecision(null, AgentAction.NONE, 0);
        for (AgentDecision agentDecision : agentDecisions) {
            if (agentDecision.getDecisionScore() > finalDecision.getDecisionScore()) {
                finalDecision = agentDecision;
            }
        }
        return finalDecision;
    }

    public int graze(EnvironmentTile environmentTile) {
        if (environmentTile.getFoodLevel() <= 0) {
            return 0;
        }
        if (environmentTile.getFoodLevel() >= getAttributes().getEatAmount()) {
            getScores().setHunger(getScores().getHunger() + getAttributes().getEatAmount());
            return getAttributes().getEatAmount();
        }
        getScores().setHunger(getScores().getHunger() + environmentTile.getFoodLevel());
        return environmentTile.getFoodLevel();
    }

    public void predate(Attributes preyAttributes) {
        getScores().setHunger(getScores().getHunger() + preyAttributes.getSize());
    }

}
