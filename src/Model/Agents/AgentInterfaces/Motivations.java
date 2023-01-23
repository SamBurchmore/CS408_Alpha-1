package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.EnvironmentTile;

public interface Motivations {

    int run(AgentVision tile, Attributes attributes, Scores scores);

}
