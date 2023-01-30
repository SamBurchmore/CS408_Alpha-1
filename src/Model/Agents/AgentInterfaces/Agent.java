package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public interface Agent {

    AgentModelUpdate run(Environment environment);
    AgentDecision liveDay(Environment environment);
    void move(Location newLocation);
    ArrayList<Agent> create(Location parentBLocation, Environment environment);
    boolean isDead();
    Attributes getAttributes();
    void setAttributes(Attributes attributes);
    void setLocation(Location location);
    Location getLocation();
    Reaction getReaction();
    void setReaction(Reaction reaction);
    Vision getVision();
    void setVision(Vision vision);
    Scores getScores();
    void setScores(Scores scores);
    Agent combine(Agent parentB, Location childLocation);
    Object copy();
    String toString();
}
