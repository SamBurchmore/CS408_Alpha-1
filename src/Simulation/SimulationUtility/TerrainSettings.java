package Simulation.SimulationUtility;

public class TerrainSettings {

    // [0] - circle(0) OR cluster(1)
    // [1] - point(0) OR line(1)
    // [2] - circleSize(1-100)
    // [3] - objectDensity(1,10000)
    // [4] -  (2,50)
    // [5] - clusterDensity(1,10000)
    // [6] - lineSize(1,600)
    // [7] - lineDensity(1,10000)

    private int rockSize;
    private int clusterSize;
    private int clusterDensity;
    private int lineSize;
    private int lineDensity;
    private int objectDensity;

    public TerrainSettings(int rockSize, int clusterSize, int clusterDensity, int lineSize, int lineDensity, int objectDensity) {
        this.rockSize = rockSize;
        this.clusterSize = clusterSize;
        this.clusterDensity = clusterDensity;
        this.lineSize = lineSize;
        this.lineDensity = lineDensity;
        this.objectDensity = objectDensity;
    }

    public int getRockSize() {
        return rockSize;
    }

    public void setRockSize(int rockSize) {
        this.rockSize = rockSize;
    }

    public int getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public int getClusterDensity() {
        return clusterDensity;
    }

    public void setClusterDensity(int clusterDensity) {
        this.clusterDensity = clusterDensity;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public int getLineDensity() {
        return lineDensity;
    }

    public void setLineDensity(int lineDensity) {
        this.lineDensity = lineDensity;
    }

    public int getObjectDensity() {
        return objectDensity;
    }

    public void setObjectDensity(int objectDensity) {
        this.objectDensity = objectDensity;
    }

    @Override
    public String toString() {
        return "TerrainSettings{" +
                "rockSize=" + rockSize +
                ", clusterSize=" + clusterSize +
                ", clusterDensity=" + clusterDensity +
                ", lineSize=" + lineSize +
                ", lineDensity=" + lineDensity +
                ", objectDensity=" + objectDensity +
                '}';
    }
}
