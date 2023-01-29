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
        setBackground(Color.white);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x = (600 - worldImage.getWidth(null)) / 2;
        int y = (600 - worldImage.getHeight(null)) / 2;
        g.drawImage(this.worldImage, x, y, this);
    }

    public void updateWorldImage(BufferedImage worldImage) {
        this.worldImage = worldImage;
    }

}