package Model.AgentBuilder;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Scores;

public class AgentSettings {

    // Stored Attributes
    private Attributes attributes;

    // Stores Scores
    private Scores scores;

    public AgentSettings(Attributes attributes, Scores scores) {
        this.attributes = attributes;
        this.scores = scores;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }
}
