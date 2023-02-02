import Controller.MainController;
import View.MainView;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    int C = 10;
    int Ae = 16;
    int Te = 8;
    int S = 1;
    public static void main(String[] args) throws InterruptedException, IOException {
        Random random = new Random();
        FlatIntelliJLaf.setup();
        MainController mainController = new MainController(600, 8, 0, 8, 3, 8);
        //testMotivations();
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
