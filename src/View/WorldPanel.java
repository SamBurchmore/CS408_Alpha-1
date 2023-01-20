package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldPanel extends JPanel {

    private BufferedImage worldImage;

    public WorldPanel() {
        this.setPreferredSize(new Dimension(600, 600));
        this.worldImage = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
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