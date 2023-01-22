package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.EnvironmentTile;

public class PredatorMotivations implements Motivations {
    @Override
    public int run(AgentVision tile, Attributes attributes, Scores scores) {
        if (tile.getAgentAttributes().getAgentCode() != attributes.getAgentCode()) {
            if (tile.getAgentAttributes().getSize() <= attributes.getSize()) {
                return tile.getAgentAttributes().getSize();
            }
        }
        return 0;
    }
}
