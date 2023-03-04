package Simulation.SimulationUtility;

import Simulation.Environment.Environment;
import Simulation.Environment.Location;

public class Circle extends BaseTerrain {
    @Override
    public Environment generateTerrain(Environment environment) {
        int seedX = super.getLocation().getX();
        int seedY = super.getLocation().getY();
        int x1;
        int y1;
        for (int x = -super.getTerrainSettings().getRockSize(); x <= super.getTerrainSettings().getRockSize(); x++) {
            for (int y = -super.getTerrainSettings().getRockSize(); y <= super.getTerrainSettings().getRockSize(); y++) {
                x1 = seedX + x;
                y1 = seedY + y;
                if (((  x1 < environment.getSize())
                        && (y1 < environment.getSize()))
                        && ((x1 >= 0) && (y1 >= 0))
                        && ((x1 - seedX) * (x1 - seedX) + (y1 - seedY) * (y1 - seedY)) <= super.getTerrainSettings().getRockSize() * super.getTerrainSettings().getRockSize()
                )
                {
                    environment.setTileTerrain(new Location(x1, y1), true);
                }
            }
        }
        return environment;
    }
}
