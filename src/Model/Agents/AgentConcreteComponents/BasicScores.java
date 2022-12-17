package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseScores;

public class BasicScores extends BaseScores {
    public BasicScores(int hunger_, int health_, int age_, int MAX_HUNGER_, int MAX_HEALTH_, int MAX_AGE_, int creationDelay_) {
        super(hunger_, health_, age_, MAX_HUNGER_, MAX_HEALTH_, MAX_AGE_, creationDelay_);
    }
}
