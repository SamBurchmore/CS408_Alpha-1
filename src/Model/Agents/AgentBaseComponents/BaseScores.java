package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Scores;

import java.io.Serializable;

public abstract class BaseScores implements Scores, Serializable {

    private int hunger;
    private int health;
    private int age;
    private int maxHunger;
    private int maxHealth;
    private int maxAge;
    private int creationDelay;
    private int creationCounter;

    public BaseScores(int hunger, int health, int maxHunger, int maxHealth, int maxAge, int creationDelay_) {
        this.hunger = hunger;
        this.health = health;
        this.maxHunger = maxHunger;
        this.maxHealth = maxHealth;
        this.maxAge = maxAge;
        this.creationDelay = creationDelay_;
        this.creationCounter = 0;
        this.age = 0;

    }

    @Override
    public void setEnergy(int hunger) {
        this.hunger = hunger;
        if (this.hunger > this.maxHunger) {
            this.hunger = this.maxHunger;
        }
    }

    @Override
    public int getEnergy() {
        return this.hunger;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    @Override
    public int getMaxEnergy() {
        return this.maxHunger;
    }

    @Override
    public int getMaxAge() {
        return this.maxAge;
    }

    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setMaxEnergy(int maxHunger) {
        this.maxHunger = maxHunger;
    }

    @Override
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public int getCreationDelay() {
        return this.creationDelay;
    }

    @Override
    public void setCreationDelay(int creationDelay_) {
        this.creationDelay = creationDelay_;
        if (this.creationDelay <= 0) {
            this.creationDelay = 0;
        }
    }

    public int getCreationCounter() {
        return this.creationCounter;
    }

    public void setCreationCounter(int creationCounter) {
        this.creationCounter = creationCounter;
        if (this.creationCounter < 0) {
            this.creationCounter = 0;
        }
    }
}
