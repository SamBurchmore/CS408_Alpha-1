package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.util.ArrayList;

public interface Vision {

    ArrayList<AgentVision> lookAround(Environment environment_, Location agentLocation, int visionRange, int agentRange);

}
