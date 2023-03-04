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
    private int upperCaveSize;
    private int lowerCaveSize;
    private int clusterDensity;
    private int bendDensity;
    private int lineSize;
    private int lineDensity;
    private int caveWave;
    private int terrainAmount;

    public TerrainSettings(int rockSize, int clusterSize, int upperCaveSize, int lowerCaveSize, int clusterDensity, int bendDensity, int lineSize, int lineDensity, int caveWave, int terrainAmount) {
        this.rockSize = rockSize;
        this.clusterSize = clusterSize;
        this.upperCaveSize = upperCaveSize;
        this.lowerCaveSize = lowerCaveSize;
        this.clusterDensity = clusterDensity;
        this.bendDensity = bendDensity;
        this.lineSize = lineSize;
        this.lineDensity = lineDensity;
        this.caveWave = caveWave;
        this.terrainAmount = terrainAmount;
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

    public int getTerrainAmount() {
        return terrainAmount;
    }

    public void setTerrainAmount(int terrainAmount) {
        this.terrainAmount = terrainAmount;
    }

    public int getUpperCaveSize() {
        return upperCaveSize;
    }

    public void setUpperCaveSize(int upperCaveSize) {
        this.upperCaveSize = upperCaveSize;
    }

    public int getLowerCaveSize() {
        return lowerCaveSize;
    }

    public void setLowerCaveSize(int lowerCaveSize) {
        this.lowerCaveSize = lowerCaveSize;
    }

    public int getBendDensity() {
        return bendDensity;
    }

    public void setBendDensity(int bendDensity) {
        this.bendDensity = bendDensity;
    }

    public int getCaveWave() {
        return caveWave;
    }

    public void setCaveWave(int caveWave) {
        this.caveWave = caveWave;
    }

    @Override
    public String toString() {
        return "-rockSize=" + rockSize +
                ",\n-clusterSize=" + clusterSize +
                ",\n-upperCaveSize=" + upperCaveSize +
                ",\n-lowerCaveSize=" + lowerCaveSize +
                ",\n-clusterDensity=" + clusterDensity +
                ",\n-bendDensity=" + bendDensity +
                ",\n-lineSize=" + lineSize +
                ",\n-lineDensity=" + lineDensity +
                ",\n-caveWave=" + caveWave +
                ",\n-lineMagnitude=" + terrainAmount;
    }
}
