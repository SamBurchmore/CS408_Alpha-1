package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Scores;

public abstract class BaseScores implements Scores {

    private int hunger;
    private int health;
    private int age;
    private int MAX_HUNGER;
    private int MAX_HEALTH;
    private int MAX_AGE;
    private int creationDelay;
    private int creationCounter;

    public BaseScores(int hunger_, int health_, int age_, int MAX_HUNGER_, int MAX_HEALTH_, int MAX_AGE_, int creationDelay_) {
        this.hunger = hunger_;
        this.health = health_;
        this.age = age_;
        this.MAX_HUNGER = MAX_HUNGER_;
        this.MAX_HEALTH = MAX_HEALTH_;
        this.MAX_AGE = MAX_AGE_;
        this.creationDelay = creationDelay_;
        this.creationCounter = 0;

    }

    @Override
    public void setHunger(int hunger_) {
        this.hunger = hunger_;
        if (this.hunger > this.MAX_HUNGER) {
            this.hunger = this.MAX_HUNGER;
        }
        //System.out.println(this.hunger);
    }

    @Override
    public int getHunger() {
        return this.hunger;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void setAge(int age_) {
        this.age = age_;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health_) {
        this.health = health_;
        if (this.health > this.MAX_HEALTH) {
            this.health = this.MAX_HEALTH;
        }
    }

    @Override
    public int getMAX_HUNGER() {
        return this.MAX_HUNGER;
    }

    @Override
    public int getMAX_AGE() {
        return this.MAX_AGE;
    }

    @Override
    public int getMAX_HEALTH() {
        return this.MAX_HEALTH;
    }

    @Override
    public void setMAX_HUNGER(int MAX_HUNGER_) {
        this.MAX_HUNGER = MAX_HUNGER_;
    }

    @Override
    public void setMAX_AGE(int MAX_AGE_) {
        this.MAX_AGE = MAX_AGE_;
    }

    @Override
    public void setMAX_HEALTH(int MAX_HEALTH_) {
        this.MAX_HEALTH = MAX_HEALTH_;
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
        if (this.creationCounter <= 0) {
            this.creationCounter = 0;
        }
    }
}
