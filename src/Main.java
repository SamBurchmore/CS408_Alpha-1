import Controller.MainController;
import Simulation.Agent.AgentConcreteComponents.BasicAttributes;
import Simulation.Agent.AgentInterfaces.Agent;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Simulation;
import View.TerrainDialog;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        FlatIntelliJLaf.setup();
        UIManager.put("Panel.background", new Color(224, 224, 224));
        MainController mainController = new MainController(600, 8, 0, 8, 1.5, 8);
    }

    private static void testColors() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        frame.setUndecorated(true);
        frame.setVisible(true);
        Random random = new Random();
        int imageSize = 400;
        int imageScale = 2;
        Attributes attributes = new BasicAttributes(
                1,
                "Agent 1",
                0,
                new Color(100, 100, 100),
                1,
                1,
                1,
                1);
        Color[] agentColors = new Color[]{new Color(0, 0, 255), new Color(255, 0, 0), new Color(0, 255, 0), new Color(180, 0, 190), new Color(255, 102, 0), Color.cyan, Color.pink, Color.yellow};
        BufferedImage worldImage = new BufferedImage(imageSize * imageScale, imageSize * imageScale, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x <= imageScale * imageSize; x += imageScale) {
            for (int y = 0; y <= imageScale * imageSize; y += imageScale) {

                //attributes = processAttributes(attributes, random); // Mutate attributes and update color
                Color color = agentColors[random.nextInt(8)];

                for (int i = 0; i < imageScale; i++) {
                    for (int j = 0; j < imageScale; j++) {
                        if (((x + i < imageScale * imageSize) && (y + j < imageScale * imageSize)) && ((x + i >= 0) && (y + j >= 0))) {
                            worldImage.setRGB(x + i, y + j, color.getRGB());
                        }
                    }
                }

            }
        }
        label.setIcon(new ImageIcon(worldImage));
        frame.getContentPane().add(label,BorderLayout.CENTER);
        //frame.setLocationRelativeTo(null);
        frame.pack();
    }

    private static Attributes processAttributes(Attributes attributes, Random random) {
        double[] oldStats = new double[]{attributes.getSize(), attributes.getCreationSize(), attributes.getRange()};
            attributes = mutate(attributes, random);
            double[] newStats = new double[]{attributes.getSize(), attributes.getCreationSize(), attributes.getRange()};
            System.out.println("---\n" + attributes.getMutatingColor().toString());
            attributes.generateColor(
                        (newStats[0] / 100) - (oldStats[0] / 100),
                        (newStats[1] / 8) - (oldStats[1] / 8),
                        (newStats[2] / 5) - (oldStats[2] / 5),
                        125
                );
                System.out.println(attributes.getMutatingColor().toString());
        return attributes;
    }

    private static Attributes mutate(Attributes attributes, Random random) {
        // We've decided the agent is going to mutate, now we need to randomly decide what attribute to mutate.
        int ran = random.nextInt(9);
        if (ran < 0) {
            // Mutate size
            //int oldSize = attributes.getSize();
            attributes.setSize(Math.min(Math.max(attributes.getSize() + mutationMagnitude(random), 2), 10));
            //System.out.println("Size mutated by: " + (attributes.getSize() - oldSize));
            return attributes;
        }
        if (ran < 0) {
            // Mutate range
            //int oldRange = attributes.getRange();
            attributes.setRange(Math.min(Math.max(attributes.getRange() + mutationMagnitude(random), 0), 5));
            //System.out.println("Range mutated by: " + (attributes.getRange() - oldRange));
            return attributes;
        }
        if (ran < 9) {
            // Mutate creationAmount
            //int oldCreationAmount = attributes.getCreationSize();
            attributes.setCreationSize(Math.min(Math.max(attributes.getCreationSize() + mutationMagnitude(random), 1), 8));
            //System.out.println("Creation Amount mutated by: " + (attributes.getCreationSize() - oldCreationAmount));
            return attributes;
        }
        return attributes;
    }

    private static int mutationMagnitude(Random random) {
        int r = random.nextInt(100);
//        int modifier = random.nextInt(2);
//        if (modifier == 0) {
//            modifier -= 1;
//        }
        int modifier = 1;
//        if (r < 75) {
//            return modifier;
//        }
//        if (r < 98) {
//            return 2*modifier;
//        }
//        return 3*modifier;
        return modifier;
    }

}



// Useful diagnostics print
//System.out.println("Hunger: " + super.getScores().getHunger() + ", Health: " + super.getScores().getHealth() + ", Age: " + super.getScores().getAge() + ", (" + super.getLocation().getX() + "," + super.getLocation().getY() + ")");
