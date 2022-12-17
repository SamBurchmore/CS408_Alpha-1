package Model.Agents.AgentInterfaces;

import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;

public interface Agent {

    Environment run(Environment environment_);
    Environment move(Location newLocation, Environment environment_);
    Environment create(Location parentBLocation, Environment environment_);
    void liveDay();
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

}
