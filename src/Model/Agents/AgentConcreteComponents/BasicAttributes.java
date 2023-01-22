package Model.Agents.AgentConcreteComponents;
import Model.Agents.AgentBaseComponents.BaseAttributes;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentStructs.AgentType;

public class BasicAttributes extends BaseAttributes {

    public BasicAttributes(int speed_, int size_, int vision_, int eatAmount_, AgentType agentType_) {
        super(speed_, size_, vision_, eatAmount_, agentType_);
    }

    public BasicAttributes(Attributes attributes_a, Attributes attributes_b){
        super(attributes_a, attributes_b);
    }

}
