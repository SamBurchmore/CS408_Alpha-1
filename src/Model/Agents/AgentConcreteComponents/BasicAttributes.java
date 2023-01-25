package Model.Agents.AgentConcreteComponents;
import Model.Agents.AgentBaseComponents.BaseAttributes;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentStructs.AgentType;

import java.awt.*;

public class BasicAttributes extends BaseAttributes {

    public BasicAttributes(String name, int code, Color color, int visionRange, int movementRange, int size, int energyCapacity, int eatAmount, int lifespan, int creationAge, int creationAmount, int creationDelay) {
        super(name, code, color, visionRange, movementRange, size, energyCapacity, eatAmount, lifespan, creationAge, creationAmount, creationDelay);
    }

    public BasicAttributes(Attributes attributesA, Attributes attributesB) {
        super(attributesA, attributesB);
    }

}
