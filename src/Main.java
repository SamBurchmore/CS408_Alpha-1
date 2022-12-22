import Controller.MainController;

public class Main {
    public static void main(String[] args) {
        MainController mainController = new MainController(100, 10, 0, 5, 6);
        mainController.populateWorld();
        for (int i = 0; i < 100000; i++) {
            mainController.runStep();
        }
    }
}
