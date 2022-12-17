package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseVision;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.util.ArrayList;
import java.util.Collections;

public class BasicVision extends BaseVision {

    @Override
    public ArrayList<AgentVision> lookAround(Environment environment_, Location agentLocation, int visionRange, int agentRange) {
        // Generate a new ArrayList of the AgentVision object, everything the agent sees will be stored here.
        ArrayList<AgentVision> agentVision = new ArrayList<>();
        // Retrieve the agents vision attribute, lets us know how far the agent can see.
        // Now we iterate over all the surrounding tiles, adding their visible contents to the AgentVision ArrayList.
        for (int i = -visionRange; i <= visionRange; i++) {
            for (int j = -visionRange; j <= visionRange; j++) {
                int x_coord = agentLocation.getX() + i;
                int y_coord = agentLocation.getY() + j;
                // Checks the agent isn't looking outside the grid and prevents a null pointer exception // TODO - there must be a better way to do this.
                if (((x_coord < environment_.getSize()) && (y_coord < environment_.getSize())) && (((x_coord >= 0) && (y_coord >= 0))) && !(i == 0 && j == 0)) {
                    AgentVision av = environment_.getTileView(x_coord, y_coord);
                    if (Math.abs(i) <= agentRange && Math.abs(j) <= agentRange) {
                        av.setInRange(true);
                    }
                    else {
                        av.setInRange(false);
                    }
                    agentVision.add(av);
                }
            }
        }
        Collections.shuffle(agentVision);
        return agentVision;
    }

}
