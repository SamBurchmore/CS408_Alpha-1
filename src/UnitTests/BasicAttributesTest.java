package UnitTests;

import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentStructs.ColorModel;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BasicAttributesTest {

    @Test
    public void testMutateAttributesColor() {
        Color oldColor = Color.blue;
        Attributes attributes = new BasicAttributes(0, "0", 0, oldColor, ColorModel.STATIC, 0, 1, 1, 3, 4);

        attributes.mutateAttributesColor(0, 0, 0, 125);
        assertEquals(attributes.getMutatingColor().getRed(), oldColor.getRed());
        attributes.mutateAttributesColor(0.1, 0, 0, 125);
        assertNotEquals(attributes.getMutatingColor().getRed(), oldColor.getRed());
        attributes.setMutatingColor(oldColor);

        attributes.mutateAttributesColor(0, 0, 0, 125);
        assertEquals(attributes.getMutatingColor().getGreen(), oldColor.getGreen());
        attributes.mutateAttributesColor(0, 0.1, 0, 125);
        assertNotEquals(attributes.getMutatingColor().getGreen(), oldColor.getGreen());
        attributes.setMutatingColor(oldColor);

        attributes.mutateAttributesColor(0, 0, 0, 125);
        assertEquals(attributes.getMutatingColor().getBlue(), oldColor.getBlue());
        attributes.mutateAttributesColor(0, 0, 0.1, 125);
        assertNotEquals(attributes.getMutatingColor().getBlue(), oldColor.getBlue());
        attributes.setMutatingColor(oldColor);
    }

    @Test
    public void testCalculateAttributes() {
        Attributes attributes = new BasicAttributes(0, "0", 0, Color.blue, ColorModel.STATIC, 0, 1,1, 3, 4);
        int expectedEnergyCapacity = 30;
        int expectedEnergyLostPerTile = 2;
        int expectedEatAmount = 4;
        int expectedLifespan = 30;
        int expectedCreationAge = 6;
        int expectedCreationCost = 3;
        int expectedCreationDelay = 2;
        attributes.calculateAttributes();
        assertEquals(30, attributes.getEnergyCapacity());
        assertEquals(2, attributes.getEnergyLostPerTile());
        assertEquals(4, attributes.getEatAmount());
        assertEquals(30, attributes.getLifespan());
        assertEquals(6, attributes.getCreationAge());
        assertEquals(3, attributes.getCreationCost());
        assertEquals(2, attributes.getCreationDelay());
    }
}
