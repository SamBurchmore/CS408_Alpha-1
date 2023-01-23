package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAttributes;
import Model.Agents.AgentInterfaces.Attributes;

import java.awt.*;

public class BasicAttributes extends BaseAttributes {
    public BasicAttributes(int speed, int size, int vision, int eatAmount, int creationDelay, int maxEnergy, int maxAge, int agentCode, Color agentColour) {
        super(speed, size, vision, eatAmount, creationDelay, maxEnergy, maxAge, agentCode, agentColour);
    }

    public BasicAttributes(Attributes attributesA, Attributes attributesB) {
        super(attributesA, attributesB);
    }
}
