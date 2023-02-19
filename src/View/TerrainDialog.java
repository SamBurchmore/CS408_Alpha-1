package View;

import Simulation.SimulationUtility.TerrainSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerrainDialog extends JDialog {

    final private JPanel settingsPanel;
    final private JButton okButton;

    final private JPanel rockSizePanel;
    final private JPanel clusterSizePanel;
    final private JPanel clusterDensityPanel;
    final private JPanel lineSizePanel;
    final private JPanel lineDensityPanel;
    final private JPanel terrainDensityPanel;

    final private JLabel rockSizeLabel;
    final private JLabel clusterSizeLabel;
    final private JLabel clusterDensityLabel;
    final private JLabel lineSizeLabel;
    final private JLabel lineDensityLabel;
    final private JLabel terrainDensityLabel;

    final private JSpinner rockSizeSpinner;
    final private JSpinner clusterSizeSpinner;
    final private JSpinner clusterDensitySpinner;
    final private JSpinner lineSizeSpinner;
    final private JSpinner lineDensitySpinner;
    final private JSpinner terrainDensitySpinner;

    public TerrainDialog(JFrame parent, TerrainSettings terrainSettings) {
        super(parent);
        setSize(new Dimension(500, 600));

        settingsPanel = new JPanel(new GridLayout(4, 2));
        settingsPanel.setPreferredSize(new Dimension(500, 600));

        rockSizeSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 25, 1));
        rockSizeLabel = new JLabel("Rock Size: ");
        rockSizePanel = new JPanel();
        //rockSizePanel.setPreferredSize(new Dimension(200, 50));
        rockSizePanel.add(rockSizeLabel);
        rockSizePanel.add(rockSizeSpinner);

        clusterSizeSpinner = new JSpinner(new SpinnerNumberModel(6, 1, 25, 1));
        clusterSizeLabel = new JLabel("Cluster Size: ");
        clusterSizePanel = new JPanel();
        clusterSizePanel.add(clusterSizeLabel);
        clusterSizePanel.add(clusterSizeSpinner);

        clusterDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.01, 100.0, 0.01));
        clusterDensityLabel = new JLabel("Cluster Density: ");
        clusterDensityPanel = new JPanel();
        clusterDensityPanel.add(clusterDensityLabel);
        clusterDensityPanel.add(clusterDensitySpinner);

        lineSizeSpinner = new JSpinner(new SpinnerNumberModel(400, 1, 600, 1));
        lineSizeLabel = new JLabel("Line Size: ");
        lineSizePanel = new JPanel();
        lineSizePanel.add(lineSizeLabel);
        lineSizePanel.add(lineSizeSpinner);

        lineDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.01, 100.0, 0.01));
        lineDensityLabel = new JLabel("Line Density: ");
        lineDensityPanel = new JPanel();
        lineDensityPanel.add(lineDensityLabel);
        lineDensityPanel.add(lineDensitySpinner);

        terrainDensitySpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.01, 100.0, 0.01));
        terrainDensityLabel = new JLabel("Terrain Density: ");
        terrainDensityPanel = new JPanel();
        terrainDensityPanel.add(terrainDensityLabel);
        terrainDensityPanel.add(terrainDensitySpinner);

        setTerrainSettings(terrainSettings);

        settingsPanel.add(rockSizePanel);
        settingsPanel.add(clusterSizePanel);
        settingsPanel.add(clusterDensityPanel);
        settingsPanel.add(lineSizePanel);
        settingsPanel.add(lineDensityPanel);
        settingsPanel.add(terrainDensityPanel);

        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TerrainDialog.this.dispose();
            }
        });

        settingsPanel.add(okButton);

        add(settingsPanel);

        setModal(true);
        setVisible(true);
        pack();
        setLocationRelativeTo(parent);
    }

    public Integer[] getSettings() {
        return null;
    }

    public TerrainSettings getTerrainSettings() {
        return new TerrainSettings(
                (int) rockSizeSpinner.getValue(),
                (int) clusterSizeSpinner.getValue(),
                (int) ((double) clusterDensitySpinner.getValue() * 100),
                (int) lineSizeSpinner.getValue(),
                (int) ((double) lineDensitySpinner.getValue() * 100),
                (int) ((double) terrainDensitySpinner.getValue() * 100)
        );
    }

    public void setTerrainSettings(TerrainSettings terrainSettings) {
        rockSizeSpinner.setValue(terrainSettings.getRockSize());
        clusterSizeSpinner.setValue(terrainSettings.getClusterSize());
        clusterDensitySpinner.setValue((double) terrainSettings.getClusterDensity() / 100);
        lineSizeSpinner.setValue(terrainSettings.getLineSize());
        lineDensitySpinner.setValue((double) terrainSettings.getLineDensity() / 100);
        terrainDensitySpinner.setValue((double) terrainSettings.getObjectDensity() / 100);
    }
}
