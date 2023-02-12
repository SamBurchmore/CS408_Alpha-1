package Simulation.Agent.AgentStructs;

import Simulation.Environment.Location;

public record AgentDecision(Location location, AgentAction agentAction, int decisionScore) {

}
