package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SimulationPanel extends JPanel {

    final private int size = 600;

    private BufferedImage worldImage;

    public SimulationPanel() {
        this.setPreferredSize(new Dimension(size, size));
        this.worldImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x = (size - worldImage.getWidth(null)) / 2;
        int y = (size - worldImage.getHeight(null)) / 2;
        g.drawImage(this.worldImage, x, y, this);
    }

    public void updateWorldImage(BufferedImage worldImage) {
        this.worldImage = worldImage;
    }

}