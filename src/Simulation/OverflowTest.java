package Simulation;

import Controller.MainController;

public class OverflowTest {
    private static int overflow(int a, int b, int overflowUpper, int overflowLower) {
        if (a + b > overflowUpper) {
            return a + b - overflowUpper;
        }
        else if (a + b < overflowLower) {
            return overflowUpper - Math.abs(a + b) - overflowLower;
        }
        return a + b;
    }

    public OverflowTest() {
//        for(int i = 0; i < 1000; i++) {
//        }
        System.out.println(overflow(10, 1, 10, 0) + " " + 1);
        assert overflow(10, 1, 10, 0) == 1;
        System.out.println(overflow(5, -2, 5, 4) + " " + 5);
        assert overflow(5, -6, 5, 0) == 5;
        System.out.println(overflow(16, -20, 16, 0) + " " + 12);
        assert overflow(16, -20, 16, 0) == 12;
    }
}
