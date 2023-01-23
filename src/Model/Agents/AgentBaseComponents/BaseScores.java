package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Scores;

public abstract class BaseScores implements Scores {

    private int hunger;
    private int age;
    private int maxHunger;
    private int maxAge;
    private int creationCounter;

    public BaseScores(int hunger, int age, int maxHunger, int maxAge, int creationCounter) {
        this.hunger = hunger;
        this.age = age;
        this.maxHunger = maxHunger;
        this.maxAge = maxAge;
        this.creationCounter = creationCounter;
    }

    @Override
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setMaxEnergy(int maxHunger) {
        this.maxHunger = maxHunger;
    }

    @Override
    public int getMaxEnergy() {
        return maxHunger;
    }

    @Override
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public void setCreationCounter(int creationCounter) {
        this.creationCounter = creationCounter;
    }

    @Override
    public int getCreationCounter() {
        return creationCounter;
    }
}
