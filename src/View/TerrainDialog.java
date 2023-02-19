package View;

import javax.swing.*;
import java.awt.*;

public class TerrainDialog extends JDialog {

    final private JPanel terrainPanel;

    public TerrainDialog(JFrame frame) {
        super(frame);
        terrainPanel = new JPanel();
        terrainPanel.setPreferredSize(new Dimension(500, 600));
    }
}
