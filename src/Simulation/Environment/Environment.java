package Simulation.Environment;

import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentStructs.AgentVision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/** This class represents the environment. It contains an array of EnvironmentTile objects and allows them
 * to be interacted with as if they were in a 2D array.
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class Environment implements Serializable {

    private EnvironmentTile[] grid;

    // The length of the grids sides
    private int size;
    // The maximum energy level each tile can store
    private int maxEnergyLevel;
    // The minimum energy level each tile can store
    private int minEnergyLevel;
    // The chance each tile will regenerate energy
    private double energyRegenChance;
    // The amount of energy a tile will regenerate
    private int energyRegenAmount;

    // The color of terrain tiles
    Color terrainColor = new Color(65, 65, 65);

    // Unoccupied tile colors
    Color minColor = new Color(0, 0,0);
    Color lowColor = new Color(11, 11,11);
    Color mediumLowColor = new Color(21, 21,21);
    Color mediumHighColor = new Color(31, 31, 31);
    Color highColor = new Color(41, 41, 41);
    Color maxColor = new Color(51, 51,51);

    /**
     * Constructs an Environment instance with the input parameters.
     * <p>
     * Builds an array of EnvironmentTiles with a length equal to the square of the sides. As it builds
     * the array it provides each EnvironmentTile with a location. This allows us to interact with it as
     * a 2d array.
     * @param size : An int value corresponding to the length of a squares side.
     * @param startingEnergyLevel : What food level each tile will start of with.
     * @param minEnergyLevel : The min food level each tile will have.
     * @param maxEnergyLevel : The max food level each tile will have.
     */
    public Environment(int size, int startingEnergyLevel, int maxEnergyLevel, int minEnergyLevel, double energyRegenChance, int energyRegenAmount) {
        // We need to assert the max food level is greater than or equal to the min food level.
        assert maxEnergyLevel >= minEnergyLevel : "Error: Maximum food level must be greater than or equal to the minimum food level";
            // First we declare the array where the tiles are stored. The grid is a square so the number of cells needed equals the size^2.
            this.grid = new EnvironmentTile[size*size];
            // Populate the array with WorldTile objects. We also keep track of each array cells corresponding coordinate in order to initialise each WorldTile with it.
            int x = 0; int y = 0;
            for (int i = 0; i<size*size; i++){
                this.grid[i] = new EnvironmentTile(startingEnergyLevel, x, y);
                x++;
                if (x == size) {
                    x = 0;
                    y++;
                }
            }
            this.size = size;
            this.maxEnergyLevel = maxEnergyLevel;
            this.minEnergyLevel = minEnergyLevel;
            this.energyRegenAmount = energyRegenAmount;
            this.energyRegenChance = energyRegenChance;
    }

    /**
     * Returns the tile at the input location.
     * <p>
     * @param location the desired location
     */
    public EnvironmentTile getTile(Location location){
        return this.grid[location.getY() *  this.size + location.getX()];
    }

    /**
     * Returns the tile at the input x and y coordinates.
     * <p>
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public EnvironmentTile getTile(int x, int y){
        return this.grid[y *  this.size + x];
    }

    /**
     * Sets the occupant of the desired tile.
     * <p>
     * As agents store their location, this is used when setting the occupant to null.
     * @param location the location to set the occupant of
     * @param agent the agent to set the occupant to
     */
    public void setOccupant(Location location, Agent agent) {
        this.grid[location.getY() * this.size + location.getX()].setOccupant(agent);
    }

    /**
     * Sets the occupant of the desired tile.
     * <p>
     * @param agent the agent to set the occupant to.
     */
    public void setOccupant(Agent agent) {
        this.grid[agent.getLocation().getY() * this.size + agent.getLocation().getX()].setOccupant(agent);
    }

    /**
     * Returns all unoccupied tiles adjacent to the input location.
     * <p>
     * Free tiles are shuffled before being returned.
     * @param location the location to check
     */
    public ArrayList<Location> freeSpace(Location location) {
        ArrayList<Location> empties = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int coordinateX = location.getX() + i;
                int coordinateY = location.getY() + j;
                if ((
                        (coordinateX < this.getSize())
                        && (coordinateY < this.getSize()))
                        && ((coordinateX >= 0) && (coordinateY >= 0))
                        && !(i == 0 && j == 0)) {
                    if (!this.getTile(coordinateX, coordinateY).isOccupied()) {
                        empties.add(new Location(coordinateX, coordinateY));
                    }
                }
            }
        }
        Collections.shuffle(empties);
        return empties;
    }

    /**
     * Adds to the specified tiles energy level and returns how much was added.
     * <p>
     * Returns how much the energy level changed by, used by the Simulation class.
     * @param location the desired location to add energy to
     * @param energyLevelModifier how much the energy level should change, can be negative.
     */
    public int modifyTileEnergyLevel(Location location, int energyLevelModifier) {
        int energyLevel = this.getTile(location).getEnergyLevel() + energyLevelModifier;
        this.getTile(location).setEnergyLevel(energyLevel);
        if (this.getTile(location).getEnergyLevel() < this.minEnergyLevel) {
            this.getTile(location).setEnergyLevel(this.minEnergyLevel);
            return energyLevelModifier + (energyLevel - this.minEnergyLevel);
        }
        else if (this.getTile(location).getEnergyLevel() > this.maxEnergyLevel) {
            this.getTile(location).setEnergyLevel(this.maxEnergyLevel);
            return energyLevelModifier - (energyLevel - this.maxEnergyLevel);
        }
        return energyLevelModifier;
    }

    /**
     * Returns an AgentVision object based of the input tile.
     * <p>
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public AgentVision getTileView(int x, int y) {
        Location location = new Location(x, y);
        if (this.getTile(location).isOccupied()) {
            return new AgentVision(
                    getTile(location).getEnergyLevel(),
                    getTile(location).isOccupied(),
                    location,
                    getTile(location).getOccupant().getAttributes(),
                    getTile(location).getOccupant().getScores());
        }
        return new AgentVision(
                getTile(location).getEnergyLevel(),
                false,
                location);
    }

    /**
     * A wrapper method for getTileView(int x, int y).
     * <p>
     * @param location the location to get the AgentVision from
     */
    public AgentVision getTileView(Location location) {
        return getTileView(location.getX(), location.getY());
    }

    /**
     * Returns the tiles current color.
     * <p>
     * If the tile is terrain, return the terrain color. If the tile is occupied,
     * then the color is the occupants color, otherwise the color is based of the
     * tiles current energy level.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Color getTileColor(int x, int y) {
        Location location = new Location(x, y);
        if (this.getTile(location).isTerrain()) {
            return terrainColor;
        }
        if (this.getTile(location).isOccupied()) {
            return this.getTile(location).getOccupant().getAttributes().getColor();
        }
        if (this.getTile(location).getEnergyLevel() >= this.maxEnergyLevel) {
            return this.maxColor;
        }
        if (this.getTile(location).getEnergyLevel() >= this.maxEnergyLevel - this.maxEnergyLevel / 4 ) {
            return this.highColor;
        }
        if (this.getTile(location).getEnergyLevel() >= this.maxEnergyLevel / 2 ) {
            return this.mediumHighColor;
        }
        if (this.getTile(location).getEnergyLevel() >= this.maxEnergyLevel - ( (maxEnergyLevel / 4) * 3)) {
            return this.lowColor;
        }
        if (this.getTile(location).getEnergyLevel() > this.minEnergyLevel) {
            return this.mediumLowColor;
        }
        return this.minColor;
    }

    /**
     * Returns the grid as a BufferedImage.
     * <p>
     * Produces a square BufferedImage of the same size as the Environment. Each pixel
     * corresponds to one tile. A pixels color is equal to its corresponding tile color.
     * @return environmentImage the image produced
     */
    public BufferedImage normalImage() {
        BufferedImage environmentImage = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                environmentImage.setRGB(x, y, this.getTileColor(x, y).getRGB());
            }
        }
        return environmentImage;
    }

    /**
     * Returns the grid as a BufferedImage, scaled up.
     * <p>
     * Produces a square BufferedImage in the same was as normalImage() except every tile is now scaled up.
     * I.e. if scale is 2 then each tile will now be represented by a 2x2 square of pixels.
     * @return environmentImage the image produced
     */
    public BufferedImage scaledImage(int scale) {
        BufferedImage environmentImage = new BufferedImage(this.size * scale, this.size * scale, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x <= scale * this.size; x += scale) {
            for (int y = 0; y <= scale * this.size; y += scale) {
                for (int i = 0; i < scale; i++) {
                    for (int j = 0; j < scale; j++) {
                        if (((x + i < scale * this.getSize()) && (y + j < scale * this.getSize())) && ((x + i >= 0) && (y + j >= 0))) {
                            environmentImage.setRGB(x + i, y + j, this.getTileColor(x / scale, y / scale).getRGB());
                        }
                    }
                }
            }
        }
        return environmentImage;
    }

    /**
     * Delegates to normalImage() or scaledImage() depending on the scale parameter.
     * <p>
     * @param scale the desired scale
     * @return environmentImage the image produced
     */
    public BufferedImage toBufferedImage(int scale) {
        if (scale <= 0) {
            return this.normalImage();
        }
        else {
            return this.scaledImage(scale);
        }

    }

    /**
     * Updates the environment settings.
     * <p>
     * Sets this classes fields to match those in the EnvironmentSettings parameter. If the settings
     * specify a size change then it re-makes the grid.
     * @param environmentSettings the new environment settings
     */
    public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
        setMaxEnergyLevel(environmentSettings.getMaxEnergyLevel());
        setMinEnergyLevel(environmentSettings.getMinEnergyLevel());
        setEnergyRegenAmount(environmentSettings.getEnergyRegenAmount());
        setEnergyRegenChance(environmentSettings.getEnergyRegenChance());
        setColors(environmentSettings.getEnvironmentColors());
        if (environmentSettings.getSize() != this.getSize()) {
            setSize(environmentSettings.getSize());
            newEnvironmentGrid();
        }
    }

    /**
     * Returns the current environment settings.
     * <p>
     * Returns the current environment settings as an EnvironmentSettings object.
     */
    public EnvironmentSettings getEnvironmentSettings() {
        return new EnvironmentSettings(getSize(),
                getMaxEnergyLevel(),
                getMinEnergyLevel(),
                getEnergyRegenChance(),
                getEnergyRegenAmount(),
                getColors());
    }

    /**
     * Overwrites the environment grid with a blank one.
     * <p>
     * The new environment grids size is the current environment size. This means if the size is to be updated to,
     * this method must be called after the environment size has been updated.
     */
    public void newEnvironmentGrid() {
        this.grid = new EnvironmentTile[size*size];
        // Populate the array with WorldTile objects. We also keep track of each array cells corresponding coordinate in order to initialise each WorldTile with it.
        int x = 0; int y = 0;
        for (int i = 0; i<size*size; i++){
            this.grid[i] = new EnvironmentTile(getMaxEnergyLevel(), x, y);
            x++;
            if (x == size) {
                x = 0;
                y++;
            }
        }
    }

    public EnvironmentTile[] getGrid() {
        return this.grid;
    }
    public void setGrid(EnvironmentTile[] grid) {
        this.grid = grid;
    }
    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getMaxEnergyLevel() {
        return this.maxEnergyLevel;
    }
    public void setMaxEnergyLevel(int maxEnergyLevel) {
        this.maxEnergyLevel = maxEnergyLevel;
    }
    public int getMinEnergyLevel() {
        return this.minEnergyLevel;
    }
    public void setMinEnergyLevel(int minEnergyLevel) {
        this.minEnergyLevel = minEnergyLevel;
    }
    public double getEnergyRegenChance() {
        return energyRegenChance;
    }
    public void setEnergyRegenChance(double energyRegenChance) {
        this.energyRegenChance = energyRegenChance;
    }
    public int getEnergyRegenAmount() {
        return energyRegenAmount;
    }
    public void setEnergyRegenAmount(int energyRegenAmount) {
        this.energyRegenAmount = energyRegenAmount;
    }
    public void setTileTerrain(Location location, boolean isTerrain) {
        getTile(location).setTerrain(isTerrain);
    }
    public Color getMaxColor() {
        return maxColor;
    }
    public Color getHighColor() {
        return highColor;
    }
    public Color getMediumHighColor() {
        return mediumHighColor;
    }
    public Color getMediumLowColor() {
        return mediumLowColor;
    }
    public Color getLowColor() {
        return lowColor;
    }
    public Color getMinColor() {
        return minColor;
    }
    public void setMaxColor(Color maxColor) {
        this.maxColor = maxColor;
    }
    public void setHighColor(Color highColor) {
        this.highColor = highColor;
    }
    public void setMediumHighColor(Color mediumHighColor) {
        this.mediumHighColor = mediumHighColor;
    }
    public void setMediumLowColor(Color mediumLowColor) {
        this.mediumLowColor = mediumLowColor;
    }
    public void setLowColor(Color lowColor) {
        this.lowColor = lowColor;
    }
    public void setMinColor(Color minColor) {
        this.minColor = minColor;
    }
    public void setTileEnergyLevel(Location location, int foodLevel) {
        this.getTile(location).setEnergyLevel(foodLevel);
    }
    public int getTileEnergyLevel(Location location) {
        return this.getTile(location).getEnergyLevel();
    }
    public Color[] getColors() {
        return new Color[]{minColor, lowColor, mediumLowColor, mediumHighColor, highColor, maxColor};
    }
    public void setColors(Color[] color) {
        setMinColor(color[0]);
        setLowColor(color[1]);
        setMediumLowColor(color[2]);
        setMediumHighColor(color[3]);
        setHighColor(color[4]);
        setMaxColor(color[5]);
    }
    @Override
    public String toString() {
        return  "-Max Energy Level=" + maxEnergyLevel +
                ",\n-Min Energy Level=" + minEnergyLevel +
                ",\n-Energy Regen Chance=" + energyRegenChance +
                ",\n-Energy Regen Amount=" + energyRegenAmount +
                ",\n-Environment Size=" + size +
                ".";
    }
}
