import Controller.MainController;
import Model.AgentEditor.AgentEditor;
import Model.ModelController;
import View.MainView;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatPropertiesLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    int C = 10;
    int Ae = 16;
    int Te = 8;
    int S = 1;
    public static void main(String[] args) throws InterruptedException, IOException {
        FlatIntelliJLaf.setup();
        UIManager.put("Panel.background", new Color(224, 224, 224));
        MainController mainController = new MainController(300, 8, 0, 8, 8, 4, 2);
//        mainController.getModelController().populate(15);
//        for (int i = 0; i < 100000; i++) {
//            mainController.runStep();
//        }
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
            ModelController modelController = new ModelController(600, 8, 0, 8, 10, 4);
            modelController.getDiagnostics().setAgentNames(modelController.getAgentEditor().getAgentNames());
            modelController.getDiagnostics().setMaxEnvironmentEnergy(modelController.getMaxTileEnergy() * modelController.getEnvironmentSize()*modelController.getEnvironmentSize());
            modelController.getDiagnostics().resetCurrentEnvironmentEnergy();
            modelController.populate(25);
            counter = 0;
            while (modelController.getDiagnostics().getAgentPopulations()[1] > 0) {
                for (int j = 0; j < 100; j++) {
                    modelController.cycle();
                    counter++;
                }
                System.out.println(counter);
            }
            System.out.println("Red lasted: " + counter + " steps. " + modelController.getAgentEditor().getAgent(1).getAttributes());
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
