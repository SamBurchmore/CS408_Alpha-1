package Simulation.Agent.AgentInterfaces;

import Simulation.Environment.Environment;
import Simulation.Environment.EnvironmentTile;
import Simulation.Environment.Location;

import java.util.ArrayList;

public interface Agent {

    void liveDay();
    void move(Location newLocation);
    boolean isDead();
    boolean spaceTaken();
    void setSpaceTaken();
    Attributes getAttributes();
    void setAttributes(Attributes attributes);
    void setLocation(Location location);
    Location getLocation();
    Scores getScores();
    void setScores(Scores scores);
    int graze(EnvironmentTile environmentTile);
    void predate(Scores preyScores);
    ArrayList<Agent> create(Location parentBLocation, Environment environment);
    Object copy();
    String toString();
    ArrayList<Motivation> getMotivations();
    void setMotivations(ArrayList<Motivation> motivations);
    ArrayList<Motivation> copyMotivations();
    boolean mutates();
}
