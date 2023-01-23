package Model.Agents.AgentStructs;

import Model.Agents.AgentInterfaces.Scores;
import Model.Environment.Location;

public class AgentUpdate {

    private Location location;
    private Scores scores;

    public AgentUpdate(Location location, Scores scores) {
        this.location = location;
        this.scores = scores;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }
}
