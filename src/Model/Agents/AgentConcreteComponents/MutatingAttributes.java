package Model.Agents.AgentConcreteComponents;
import Model.Agents.AgentStructs.AgentType;

public class MutatingAttributes extends BaseAttributes {

    public MutatingAttributes(int speed_, int size_, int vision_, int eatAmount_, AgentType agentType_) {
        super(speed_, size_, vision_, eatAmount_, agentType_);
        mutate();
    }

    public MutatingAttributes(Attributes attributes_a, Attributes attributes_b){
        super(attributes_a, attributes_b);
    }

    public void mutate() {
        if (super.getRandom().nextInt(100) > 98) {
            // We've decided the agent is going to mutate, now we need to randomly decide what attribute to mutate.
            switch (super.getRandom().nextInt(3)) {
                case 0: // Mutate speed and vision
                    super.setSpeed(super.getSpeed() + this.mutationMagnitude());
                    super.setVision(super.getVision() + this.mutationMagnitude());
                    break;
                case 1: // Mutate size
                    super.setEatAmount(super.getEatAmount() + this.mutationMagnitude());
                    break;
                case 2: // Mutate vision
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
        int r = super.getRandom().nextInt(100);
        int modifier = super.getRandom().nextInt(2);
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
}
