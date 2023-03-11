package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StatGraph extends JPanel {

    final private int size = 600;

    private BufferedImage graphImage;

    public StatGraph() {
        this.setPreferredSize(new Dimension(size, size/2));
        this.graphImage = new BufferedImage(size, size/2, BufferedImage.TYPE_INT_RGB);
    }
}
