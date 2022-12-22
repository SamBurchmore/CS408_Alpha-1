package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentStructs.AgentType;

import java.util.Random;

public abstract class BaseAttributes implements Attributes {

    private int speed;
    private int size;
    private int vision;
    private int eatAmount;
    private AgentType agentType;
    Random random = new Random();

    public BaseAttributes(int speed_, int size_, int vision_, int eatAmount_, AgentType agentType_) {
        this.speed = speed_;
        this.size = size_;
        this.vision = vision_;
        this.eatAmount = eatAmount_;
        this.agentType = agentType_;
    }

    public BaseAttributes(Attributes attributes_a, Attributes attributes_b){
        // set the new agents type.
        this.agentType = attributes_a.getType();
        // First lets combine the parents attributes to get our new child attributes.
//        this.speed = (attributes_a.getSpeed() / 2) + (attributes_b.getSpeed() / 2);
//        this.size = (attributes_a.getSize() / 2) + (attributes_b.getSize() / 2);
//        this.vision = (attributes_a.getVision() / 2) + (attributes_b.getVision() / 2);
//        this.eatAmount = (attributes_a.getEatAmount() / 2) + (attributes_b.getEatAmount() / 2);
        // Now lets randomly decide if the agent will mutate in some way.
        //this.mutate();
        // now we make sure the size attribute is not < 20.
        //if (this.size < 20) { this.size = 20; }
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

    public int getSpeed() {
        return this.speed;
    }

    public int getSize() {
        return this.size;
    }

    public int getVision() {
        return this.vision;
    }

    @Override
    public int getEatAmount() {
        return this.eatAmount;
    }

    public AgentType getType() {
        return this.agentType;
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

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
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

    public AgentType getAgentType() {
        return this.agentType;
    }
}
