import Controller.MainController;

public class Main {
    public static void main(String[] args) {
        MainController mainController = new MainController(100, 6, 0, 6, 6);
        mainController.populateWorld();
        for (int i = 0; i < 100000; i++) {
            mainController.runStep();
        }
    }
}

// Useful diagnostics print
//System.out.println("Hunger: " + super.getScores().getHunger() + ", Health: " + super.getScores().getHealth() + ", Age: " + super.getScores().getAge() + ", (" + super.getLocation().getX() + "," + super.getLocation().getY() + ")");
