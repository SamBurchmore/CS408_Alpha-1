package View;

import javax.swing.*;
import java.awt.*;

class SquareButton extends JButton {

    SquareButton() {
        super();
    }

    SquareButton(String name) {
        super(name);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        int size = (int) (Math.max(dimension.getWidth(), dimension.getHeight()));
        return new Dimension(size, size);
    }
}