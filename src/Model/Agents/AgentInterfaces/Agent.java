package Model.Agents.AgentInterfaces;

import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;

import java.util.ArrayList;
import java.util.Collections;

public interface Agent {

    void liveDay();
    void move(Location newLocation);
    boolean isDead();
    boolean isEaten();
    void setBeenEaten();
    Attributes getAttributes();
    void setAttributes(Attributes attributes);
    void setLocation(Location location);
    Location getLocation();
    Scores getScores();
    void setScores(Scores scores);
    int graze(EnvironmentTile environmentTile);
    void predate(Attributes preyAttributes);
    ArrayList<Agent> create(Location parentBLocation, Environment environment);
    Object copy();
    String toString();
    ArrayList<Motivation> getMotivations();
    void setMotivations(ArrayList<Motivation> motivations);
    ArrayList<Motivation> copyMotivations();
}
