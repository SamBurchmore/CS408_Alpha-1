package Simulation.SimulationUtility;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

import java.util.HashMap;
import java.util.Stack;

public abstract class CompositeTerrain extends BaseTerrain {

    Stack<Terrain> terrainComponents;

    public CompositeTerrain() {
        super();
        terrainComponents = new Stack<>();
    }

    @Override
    public Environment generateTerrain(Environment environment) {
        for (Terrain terrain : terrainComponents) {
            environment = terrain.generateTerrain(environment);
        }
        return environment;
    }

    public void addTerrain(Terrain terrainComponent) {
        terrainComponents.add(terrainComponent);
    }

    public void removeTerrain() {
        terrainComponents.pop();
    }

    public void removeTerrain(int index) {
        terrainComponents.remove(index);
    }
}
