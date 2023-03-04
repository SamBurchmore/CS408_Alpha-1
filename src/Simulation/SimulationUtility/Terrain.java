package Simulation.SimulationUtility;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public interface Terrain {

    Environment generateTerrain(Environment environment);

    TerrainSettings getTerrainSettings();

    void setTerrainSettings(TerrainSettings terrainSettings);

    Location getLocation();

    void setLocation(Location location);

}
