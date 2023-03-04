package Simulation.SimulationUtility;

import Simulation.Environment.Location;

public abstract class BaseTerrain implements Terrain{

    private Location location;
    private TerrainSettings terrainSettings;

    @Override
    public TerrainSettings getTerrainSettings() {
        return terrainSettings;
    }

    @Override
    public void setTerrainSettings(TerrainSettings terrainSettings) {
        this.terrainSettings = terrainSettings;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
