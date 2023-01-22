package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentStructs.AgentType;

import java.util.Random;

public abstract class BaseAttributes implements Attributes {

    private int speed;
    private int size;
    private int vision;
    private int eatAmount;
    private int agentCode;
    Random random = new Random();

    public BaseAttributes(int speed_, int size_, int vision_, int eatAmount_, int agentCode) {
        this.speed = speed_;
        this.size = size_;
        this.vision = vision_;
        this.eatAmount = eatAmount_;
        this.agentCode = agentCode;
    }

    public BaseAttributes(Attributes attributes_a, Attributes attributes_b){
        // set the new agents type.
        this.agentCode = attributes_a.getAgentCode();
        this.speed = attributes_a.getSpeed();
        this.size = attributes_a.getSize();
        this.vision = attributes_a.getVision();
        this.eatAmount = attributes_a.getEatAmount();
    }

    public void mutate() {
        if (this.random.nextInt(100) > 98) {
            // We've decided the agent is going to mutate, now we need to randomly decide what attribute to mutate.
            switch (this.random.nextInt(3)) {
                case 0: // Mutate speed
                    this.speed += this.mutationMagnitude();
                    break;
                case 1: // Mutate size
                    this.size += this.mutationMagnitude();
                    break;
                case 2: // Mutate vision
                    this.vision += this.mutationMagnitude();
                    break;
            }
        }
    }

    /**
     * @return
     * This method returns an integer value between -5 and +5 excluding 0.
     * A magnitude is first calculated, a lower magnitude has a higher probability
     * of being returned. Before being returned, the magnitude is combined with a
     * modifier which has an equal chance of being -1 or +1.
     */
    public int mutationMagnitude() {
        // Used to make the weighted decision
        int r = this.random.nextInt(100);
        int modifier = this.random.nextInt(2);
        if (modifier == 0) {
            modifier -= 1;
        }
        if (r < 45) {
            return modifier;
        }
        else if (r < 70) {
            return 2*modifier;
        }
        else if (r < 85) {
            return 3*modifier;
        }
        else if (r < 95) {
            return 4*modifier;
        }
        else {
            return 5*modifier;
        }
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getVision() {
        return this.vision;
    }

    @Override
    public int getEatAmount() {
        return this.eatAmount;
    }

    @Override
    public int getAgentCode() {
        return this.agentCode;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public void setAgentCode(int agentCode) {
        this.agentCode = agentCode;
    }

    public Random getRandom() {
        return this.random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setEatAmount(int eatAmount) {
        this.eatAmount = eatAmount;
    }
}
