import Controller.MainController;
import View.MainView;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MainController mainController = new MainController(150, 6, 0, 6, 4);
        mainController.populateWorld();
        //TimeUnit.SECONDS.sleep(5);
//        for (int i = 0; i < 100000; i++) {
//            mainController.runStep();
//        }
        //testMotivations();
    }


    public static void testMotivations(){
        int max = 8;
        int dynamic = 8;
        int count = 0;
        for (int i = 0; i < 8; i++) {
            if (max + dynamic > max * 2 - dynamic) {
                count += 1;
                System.out.println(dynamic);
            }
            dynamic--;
        }
        System.out.println("create: " + count);
    }
}

// Useful diagnostics print
//System.out.println("Hunger: " + super.getScores().getHunger() + ", Health: " + super.getScores().getHealth() + ", Age: " + super.getScores().getAge() + ", (" + super.getLocation().getX() + "," + super.getLocation().getY() + ")");
