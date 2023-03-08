package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseAttributes;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentStructs.ColorModel;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/** Makes up the agents attributes. Attributes are set values that don't change during the agent's life, except if
 * the agent mutates. Attributes define the agents qualities and behavior as well as their color and name.
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class BasicAttributes extends BaseAttributes {

    /**
     * Constructs a BasicAttributes object using the input parameters.
     * <p>
     * The constructor simply takes the input parameters and assigns them
     * to the new BasicAttributes instance. Then it calls the calculateAttributes()
     * method to calculate the rest of the attributes. If the attributes mutationMagnitude
     * is greater than zero, it will generate its mutating color from its seed color.
     * @param spawningWeight the weight used for this agent when populating the environment
     * @param name the string that identifies the agent in the diagnostics view
     * @param ID the agents unique identifier, an agent will breed with and not eat agents with the same code
     * @param seedColor the agents initial color
     * @param mutationChance the percentage value for how likely the attributes are to mutate
     * @param range how far the agent can see and move in one turn
     * @param size used to calculate all the agents calculated stats
     * @param creationSize how many adjacent squares the agent will try to have children in, also used to calculate the creation cost.
     */
    public BasicAttributes(int spawningWeight, String name, int ID, Color seedColor, ColorModel colorModel, int randomColorModelMagnitude, int mutationChance, int range, int size, int creationSize) {
        super(spawningWeight, name, ID, seedColor, colorModel, randomColorModelMagnitude, mutationChance, range, size, creationSize);
    }

    /**
     * Constructs a BasicAttributes object using two BasicAttributes objects.
     * <p>
     * The constructor takes two BasicAttributes objects and uses their fields to\
     * construct a new instance. mutationMagnitude, name, code, seedColor and spawningWeight
     * are all taken from the first input. seedColor, mutatingColor, size, creationSize, and range
     * are randomly taken from either input with an equal chance for either to be taken.
     */
    public BasicAttributes(Attributes attributesA, Attributes attributesB) {
        super(attributesA, attributesB);
        calculateAttributes();
    }

    @Override
    public Attributes combine(Attributes attributesB) {
        return new BasicAttributes(this, attributesB);
    }

    @Override
    public void mutateAttributesColor(double a, double b, double c, int constant) {

    }

    @Override
    public String toString() {
        return "null";
    }
    @Override
    public Attributes copy() {
        return new BasicAttributes(
                getSpawningWeight(),
                getName(),
                getID(),
                getSeedColor(),
                getColorModel(),
                getRandomColorModelMagnitude(),
                getMutationChance(),
                getRange(),
                getSize(),
                getCreationSize());
    }
}