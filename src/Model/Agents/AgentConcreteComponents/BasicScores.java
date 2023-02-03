package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseScores;

public class BasicScores extends BaseScores {
    public BasicScores(int hunger, int health, int maxHunger, int maxHealth, int maxAge, int creationDelay) {
        super(hunger, health, maxHunger, maxHealth, maxAge, creationDelay);
    }
}
