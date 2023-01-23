package Model.Agents.AgentStructs;

import Model.Agents.AgentInterfaces.Scores;
import Model.Environment.Location;

public class AgentUpdate {

    private Location location;
    private Scores scores;
    private AgentAction action;

    public AgentUpdate(Location location, Scores scores, AgentAction action) {
        this.location = location;
        this.scores = scores;
        this.action = action;
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

    public AgentAction getAction() {
        return action;
    }

    public void setAction(AgentAction action) {
        this.action = action;
    }
}
