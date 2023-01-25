package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseScores;

public class BasicScores extends BaseScores {
    public BasicScores(int hunger, int health, int age, int MAXHUNGER, int MAXHEALTH, int MAXAGE, int creationDelay) {
        super(hunger, health, age, MAXHUNGER, MAXHEALTH, MAXAGE, creationDelay);
    }
}
