package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public interface Agent {

    AgentModelUpdate run(Environment environment_);
    AgentDecision liveDay(Environment environment);
    void move(Location newLocation);
    ArrayList<Agent> create(Location parentBLocation, Environment environment_);
    boolean isDead();
    Color getColor();
    void setColor(Color color_);
    Attributes getAttributes();
    void setAttributes(Attributes attributes_);
    void setLocation(Location location_);
    Location getLocation();
    Reaction getReaction();
    void setReaction(Reaction reaction_);
    Vision getVision();
    void setVision(Vision vision_);
    Scores getScores();
    void setScores(Scores scores_);
    Agent combine(Agent parent_b, Location childLocation);
    Object copy();
}
