import Controller.MainController;

public class Main {
    public static void main(String[] args) {
        MainController mainController = new MainController(300, 10, 0, 5, 2);
        mainController.populateWorld();
        for (int i = 0; i < 100000; i++) {
            mainController.runStep();
        }
    }
}
