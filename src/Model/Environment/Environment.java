package Model.Environment;

import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentStructs.AgentVision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class contains an array of WorldTile objects and allows them to be interacted
 * with as if they where stored in a square 2d grid.
 *
 * This class is a singlton, only one instance of it will ever exist.
  */
public class Environment {

    private EnvironmentTile[] grid;

    final int size;
    private int maxFoodLevel;
    private int minFoodLevel;
    Color max = new Color(0, 255,0);
    Color high = new Color(102, 204,0);
    Color medium = new Color(153, 153,0);
    Color low = new Color(102, 102, 0);
    Color min = new Color(192, 192,192);

    /**
     * @param size_ : An int value corresponding to the length of a squares side.
     * @param starting_food_level : What food level each tile will start of with.
     * @param minFoodLevel : The min food level each tile will have.
     * @param maxFoodLevel : The max food level each tile will have.
     */
    public Environment(int size_, int starting_food_level, int maxFoodLevel, int minFoodLevel) {
        // We need to assert the max food level is greater than or equal to the min food level.
        assert maxFoodLevel >= minFoodLevel : "Error: Maximum food level must be greater than or equal to the minimum food level";
            // First we declare the array where the tiles are stored. The grid is a square so the number of cells needed equals the size^2.
            this.grid = new EnvironmentTile[size_*size_];
            // Populate the array with WorldTile objects. We also keep track of each array cells corresponding coordinate in order to initialise each WorldTile with it.
            int x = 0; int y =  0;
            for (int i = 0; i<size_*size_; i++){
                this.grid[i] = new EnvironmentTile(starting_food_level, x, y);
                x++;
                if (x == size_) {
                    x = 0;
                    y++;
                }
            }
            // Set the size, the min food level and the max food level.
            this.size = size_;
            this.maxFoodLevel = maxFoodLevel;
            this.minFoodLevel = minFoodLevel;
    }

    // Returns the WorldTile corresponding to the coordinates input. Each x and y pair corresponds to one cell in the array.
    public EnvironmentTile getTile(Location location){
        return this.grid[location.getY() *  this.size + location.getX()];
    }

    public EnvironmentTile getTile(int x, int y){
        return this.grid[y *  this.size + x];
    }

    public void setTileAgent(Location location, Agent new_agent) {
        this.grid[location.getY() * this.size + location.getX()].setOccupant(new_agent);
    }

    public Iterator<EnvironmentTile> iterator() {
        return new WorldGridIterator();
    }

    public class WorldGridIterator implements Iterator<EnvironmentTile> {

        private int x_index;
        private int y_index;
        private boolean has_next;

        public WorldGridIterator() {
            this.x_index = 0;
            this.y_index = 0;
            this.has_next = true;
        }

        @Override
        public boolean hasNext() {
            return this.has_next;
        }

        // TODO seeing as this will run every time the simulation runs one turn, we should optimise this if we can.
        @Override
        public EnvironmentTile next() {
            // If the iterator has no more elements, then there's no point in looking for more elements.
            if (this.has_next) {
                // Get the next element from the WorldGrid.
                EnvironmentTile wt = Environment.this.getTile(this.x_index, this.y_index);
                this.x_index++;
                // Iterate the x coordinate by 1, if its now equal to the WorldGrid size, then iterate the y coordinate and rest x to 0.
                if (this.x_index >= Environment.this.size) {
                    this.x_index = 0;
                    this.y_index++;
                    // Iterate the y coordinate, if its now equal to the WorldGrid size then we've gone through every element, so set the has next flag to false.
                    if (this.y_index >= Environment.this.size) {
                        this.has_next = false;
                    }
                }
                return wt;
            }
            return null;
        }
    }

    public ArrayList<Location> emptyAdjacent(Location location) {
        ArrayList<Location> empties = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x_coord = location.getX() + i;
                int y_coord = location.getY() + j;
                // Checks the agent isn't looking outside the grid
                if (((x_coord < this.getSize()) && (y_coord < this.getSize())) && ((x_coord >= 0) && (y_coord >= 0)) && !(i == 0 && j == 0)) {
                    if (!this.getTile(x_coord, y_coord).isOccupied()) {
                        empties.add(new Location(x_coord, y_coord));
                        //System.out.println("X: " + i + " " + "Y:" + j);
                    }
                }
            }
        }
        Collections.shuffle(empties);
        return empties;
    }

    public EnvironmentTile[] getGrid() {
        return this.grid;
    }

    public int getSize() {
        return this.size;
    }

    public void setGrid(EnvironmentTile[] grid) {
        this.grid = grid;
    }

    public int getMaxFoodLevel() {
        return this.maxFoodLevel;
    }

    public void setMaxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
    }

    public int getMinFoodLevel() {
        return this.minFoodLevel;
    }

    public void setMinFoodLevel(int minFoodLevel) {
        this.minFoodLevel = minFoodLevel;
    }

    public void setTileFoodLevel(Location location, int foodLevel) {
        this.getTile(location).setFoodLevel(foodLevel);
    }

    public int getTileFoodLevel(Location location) {
        return this.getTile(location).getFoodLevel();
    }

    /**
     * This method takes an input and add its to the tiles food level.
     * If the input makes the food level greater than the max food level then
     * food level will be set to the max food level. If its made less than the min,
     * then it will be set to the min food level.
     * @param food_level_modifier the integer value the food level will be modified by.
     */
    //TODO There'll be a few ways to implement the logic for this, not really sure whats the optimal way but this method will be called a lot so definetly have a wee look
    public void modifyTileFoodLevel(Location location, int foodLevelModifier) {
        this.getTile(location).setFoodLevel(this.getTile(location).getFoodLevel() + foodLevelModifier);
        if (this.getTile(location).getFoodLevel() < this.minFoodLevel) {
            this.getTile(location).setFoodLevel(this.minFoodLevel);
        }
        else if (this.getTile(location).getFoodLevel() > this.maxFoodLevel) {
            this.getTile(location).setFoodLevel(this.maxFoodLevel);
        }
    }

    public Color getTileColor(int x, int y) {
        Location location = new Location(x, y);
        if (this.getTile(location).isOccupied()) {
            return this.getTile(location).getOccupant().getColor();
        }
        if (this.getTile(location).getFoodLevel() == this.maxFoodLevel) {
            return this.max;
        }
        if (this.getTile(location).getFoodLevel() > this.maxFoodLevel - this.maxFoodLevel / 4 ) {
            return this.high;
        }
        if (this.getTile(location).getFoodLevel() > this.maxFoodLevel / 2 ) {
            return this.medium;
        }
        if (this.getTile(location).getFoodLevel() > this.maxFoodLevel - (this.maxFoodLevel / 4)*3 ) {
            return this.low;
        }
        if (this.getTile(location).getFoodLevel() == this.minFoodLevel ) {
            return this.min;
        }
        return Color.white;
    }

    public AgentVision getTileView(Location location) {
        if (this.getTile(location).isOccupied()) {
            return new AgentVision(this.getTile(location).getFoodLevel(), this.getTile(location).isOccupied(), location, this.getTile(location).getOccupant().getAttributes());
        }
        return new AgentVision(this.getTile(location).getFoodLevel(), false, location);
    }

    public AgentVision getTileView(int x, int y) {
        Location location = new Location(x, y);
        if (this.getTile(location).isOccupied()) {
            return new AgentVision(this.getTile(location).getFoodLevel(), this.getTile(location).isOccupied(), location, this.getTile(location).getOccupant().getAttributes());
        }
        return new AgentVision(this.getTile(location).getFoodLevel(), false, location);
    }

    public BufferedImage toBufferedImage() {
        BufferedImage worldImage = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                worldImage.setRGB(x, y, this.getTileColor(x, y).getRGB());
            }
        }
        return worldImage;
    }

}
