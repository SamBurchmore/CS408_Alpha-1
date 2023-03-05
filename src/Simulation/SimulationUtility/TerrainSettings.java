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
    private int caveSize;
    private int caveDensity;
    private int bendDensity;
    private int caveLength;
    private int lineDensity;
    private int cavernSize;
    private int upperCaveSize;
    private int lowerCaveSize;
    private int caveWave;
    private int terrainAmount;
    private boolean terrain;

    public TerrainSettings(int rockSize, int caveSize, int caveDensity, int bendDensity, int caveLength, int lineDensity, int cavernSize, int upperCaveSize, int lowerCaveSize, int caveWave, int terrainAmount, boolean terrain) {
        this.rockSize = rockSize;
        this.caveSize = caveSize;
        this.caveDensity = caveDensity;
        this.bendDensity = bendDensity;
        this.caveLength = caveLength;
        this.lineDensity = lineDensity;
        this.cavernSize = cavernSize;
        this.upperCaveSize = upperCaveSize;
        this.lowerCaveSize = lowerCaveSize;
        this.caveWave = caveWave;
        this.terrainAmount = terrainAmount;
        this.terrain = terrain;
    }

    public int getRockSize() {
        return rockSize;
    }

    public void setRockSize(int rockSize) {
        this.rockSize = rockSize;
    }

    public int getCaveSize() {
        return caveSize;
    }

    public void setCaveSize(int caveSize) {
        this.caveSize = caveSize;
    }

    public int getCaveDensity() {
        return caveDensity;
    }

    public void setCaveDensity(int caveDensity) {
        this.caveDensity = caveDensity;
    }

    public int getCaveLength() {
        return caveLength;
    }

    public void setCaveLength(int caveLength) {
        this.caveLength = caveLength;
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

    public boolean isTerrain() {
        return terrain;
    }

    public void setTerrain(boolean terrain) {
        this.terrain = terrain;
    }

    public int getCavernSize() {
        return cavernSize;
    }

    public void setCavernSize(int cavernSize) {
        this.cavernSize = cavernSize;
    }

    @Override
    public String toString() {
        return "-rockSize=" + rockSize +
                ",\n-clusterSize=" + caveSize +
                ",\n-upperCaveSize=" + upperCaveSize +
                ",\n-lowerCaveSize=" + lowerCaveSize +
                ",\n-clusterDensity=" + caveDensity +
                ",\n-bendDensity=" + bendDensity +
                ",\n-lineSize=" + caveLength +
                ",\n-lineDensity=" + lineDensity +
                ",\n-caveWave=" + caveWave +
                ",\n-lineMagnitude=" + terrainAmount;
    }
}
