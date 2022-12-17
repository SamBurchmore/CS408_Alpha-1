package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldPanel extends JPanel {

    private BufferedImage worldImage;

    public WorldPanel(int size_) {
        this.setPreferredSize(new Dimension(size_, size_));
        this.worldImage = new BufferedImage(size_, size_, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(this.worldImage, 0, 0, this);
    }

    public void updateWorldImage(BufferedImage newWorldImage) {
        this.worldImage = newWorldImage;
    }

}