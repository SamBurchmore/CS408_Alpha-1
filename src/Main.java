import Controller.MainController;
import Simulation.Simulation;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    int C = 10;
    int Ae = 16;
    int Te = 8;
    int S = 1;
    public static void main(String[] args) throws InterruptedException, IOException {
        FlatIntelliJLaf.setup();
        UIManager.put("Panel.background", new Color(224, 224, 224));
        MainController mainController = new MainController(300, 16, 0, 16, 8, 8, 2);
        mainController.getModelController().populate(50);
//        TimeUnit.SECONDS.sleep(4);
        for (int i = 0; i < 1000000; i++) {
            mainController.runStep();
        }
//        test();
//        int counter;
//        for (int i = 0; i < 1000; i++) {
//            mainController.getModelController().populate(25);
//            counter = 0;
//            while (mainController.getModelController().getDiagnostics().getAgentPopulations()[1] > 0) {
//                mainController.runStep();
//                counter++;
//            }
//            System.out.println("Red lasted: " + counter + " steps. " + mainController.getModelController().getAgentEditor().getAgent(1).getAttributes());
//            mainController.clear();
//            mainController.replenishEnvironment();
//            counter = 0;
    }

    public static void test() {
        int counter;
        for (int i = 0; i < 1000; i++) {
            Simulation simulation = new Simulation(600, 8, 0, 8, 10, 4);
            simulation.getDiagnostics().setAgentNames(simulation.getAgentEditor().getAgentNames());
            simulation.getDiagnostics().setMaxEnvironmentEnergy(simulation.getMaxTileEnergy() * simulation.getEnvironmentSize()* simulation.getEnvironmentSize());
            simulation.getDiagnostics().resetCurrentEnvironmentEnergy();
            simulation.populate(25);
            counter = 0;
            while (simulation.getDiagnostics().getAgentPopulations()[1] > 0) {
                for (int j = 0; j < 100; j++) {
                    simulation.cycle();
                    counter++;
                }
                System.out.println(counter);
            }
            System.out.println("Red lasted: " + counter + " steps. " + simulation.getAgentEditor().getAgent(1).getAttributes());
            counter = 0;
        }
    }

    public static void testMotivations(){
        int C = 10;
        int maxEnergy = 16;
        int maxTileEnergy = 8;
        int S = 3;
        for (int i = 16; i > 0; i--) {
            for (int j = 0; j < maxTileEnergy; j++) {
                getMotivation(i, j, 1);
                }
            }
        }

        public static void getMotivation(int neededEnergy, int tileEnergy, int size) {
        int constant = 10;
            if (constant * (neededEnergy / size) > (neededEnergy * tileEnergy)) {
                System.out.println("Main{" +
                        "C=" + constant +
                        ", neededEnergy=" + neededEnergy +
                        ", tileEnergy=" + tileEnergy +
                        ", S=" + size +
                        '}'+ " CREATE=" + (constant * (neededEnergy / size)) + " graze=" + (neededEnergy * tileEnergy));
            }
            else {
                System.out.println("Main{" +
                        "C=" + constant +
                        ", neededEnergy=" + neededEnergy +
                        ", tileEnergy=" + tileEnergy +
                        ", S=" + size +
                        '}'+ " create=" + (constant * (neededEnergy / size)) + " GRAZE=" + (neededEnergy * tileEnergy));
            }
        }

    }


// Useful diagnostics print
//System.out.println("Hunger: " + super.getScores().getHunger() + ", Health: " + super.getScores().getHealth() + ", Age: " + super.getScores().getAge() + ", (" + super.getLocation().getX() + "," + super.getLocation().getY() + ")");
